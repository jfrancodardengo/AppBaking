package com.example.guto.appbaking.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.guto.appbaking.R;
import com.example.guto.appbaking.adapters.RecipeListAdapter;
import com.example.guto.appbaking.model.RecipeModel;
import com.example.guto.appbaking.utils.ConnectService;
import com.example.guto.appbaking.utils.JsonService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recipeListRecycler)
    RecyclerView recipeRecyclerView;
    @BindView(R.id.noInternet)
    TextView noInternet;
    RecipeListAdapter recipeListAdapter;
    String jsonResponse;
    Context context;
    ConnectService connectService = new ConnectService();;
    //lista das receitas
    List<RecipeModel> recipeModelList;
    //representa a diposição dos dados no telefone
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(ConnectService.isConnected(this)){
            new FetchRecipes().execute();
            noInternet.setVisibility(View.GONE);
        }else{
            noInternet.setVisibility(View.VISIBLE);
        }
        recipeRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        recipeRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public class FetchRecipes extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                jsonResponse = connectService.run(JsonService.JSON_URL);
                recipeModelList = JsonService.getJsonRecipe(jsonResponse);
                Log.i("RESPOSTA: ",jsonResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            if(recipeModelList==null){
                noInternet.setVisibility(View.VISIBLE);
                noInternet.setText("Erro ao baixar os dados");
            }else{
//                recipeModelList = new ArrayList<>();
                recipeListAdapter = new RecipeListAdapter(context,recipeModelList);
                recipeRecyclerView.setAdapter(recipeListAdapter);
            }
        }
    }
}
