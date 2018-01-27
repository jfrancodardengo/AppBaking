package com.example.guto.appbaking.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.guto.appbaking.R;
import com.example.guto.appbaking.adapters.RecipeListAdapter;
import com.example.guto.appbaking.model.RecipeModel;
import com.example.guto.appbaking.utils.ConnectService;
import com.example.guto.appbaking.utils.JsonService;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String WHOLE_RESPONSE = "whole_response";

    @BindView(R.id.recipeListRecycler)
    public RecyclerView recipeRecyclerView;
    @BindView(R.id.noInternet)
    public TextView noInternet;
    private Context context;
    private final ConnectService connectService = new ConnectService();
    private List<RecipeModel> recipeModelList;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        editor.apply();

        ButterKnife.bind(this);
        if (ConnectService.isConnected(this)) {
            new FetchRecipes().execute();
            noInternet.setVisibility(View.GONE);
        } else {
            noInternet.setVisibility(View.VISIBLE);
        }
        recipeRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recipeRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public class FetchRecipes extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                String jsonResponse = connectService.run();

                editor.putString(WHOLE_RESPONSE, jsonResponse);
                editor.apply();

                recipeModelList = JsonService.getJsonRecipe(jsonResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);
            if (recipeModelList == null) {
                noInternet.setVisibility(View.VISIBLE);
                noInternet.setText("Erro ao baixar os dados");
            } else {
                RecipeListAdapter recipeListAdapter = new RecipeListAdapter(context, recipeModelList);
                recipeRecyclerView.setAdapter(recipeListAdapter);
            }
        }
    }
}
