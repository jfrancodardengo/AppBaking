package com.example.guto.appbaking.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.example.guto.appbaking.R;
import com.example.guto.appbaking.model.StepsModel;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepDetailFragment extends DialogFragment {
    private static final String SAVED_INDEX_STATE = "savedIndex";
    private static final String SAVED_STEP_LIST = "stepListSaved";
    private static final String TAG = StepDetailFragment.class.getSimpleName();

    @BindView(R.id.text_detail_recipe_name)
    TextView textDetailRecipeName;
    @BindView(R.id.image_detail_step)
    ImageView imageDetailStep;
    @BindView(R.id.text_detail_step_desc)
    TextView textDetailDesc;
    @BindView(R.id.button_previous)
    Button buttonPrevious;
    @BindView(R.id.button_next)
    Button buttonNext;
    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;

    ArrayList<StepsModel> stepList;
    Integer idIndexCurrent;
    private SimpleExoPlayer mExoPlayer;
    private boolean playWhenReady;
    private int currentWindow;
    private long playBackPosition;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
        if(savedInstanceState==null){
            if(getArguments()!=null){
                stepList = getArguments().getParcelableArrayList(StepFragment.STEP_PARCELABLE);
                idIndexCurrent = getArguments().getInt(StepFragment.STEP_POSITION);
            }
        }else{
            stepList = savedInstanceState.getParcelableArrayList(SAVED_STEP_LIST);
            idIndexCurrent = savedInstanceState.getInt(SAVED_INDEX_STATE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Util.SDK_INT >23){
            initializePlayer(idIndexCurrent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        if(Util.SDK_INT <= 23 || mExoPlayer == null){
            initializePlayer(idIndexCurrent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this,rootView);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setupViews(idIndexCurrent);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: ");
        outState.putInt(SAVED_INDEX_STATE,idIndexCurrent);
        outState.putParcelableArrayList(SAVED_STEP_LIST,stepList);
    }

    public void setupViews(int position ){
        if(stepList.get(position).getIdStep() != null){
            String title = "Passo " + stepList.get(position).getIdStep();
            textDetailRecipeName.setText(title);
            textDetailDesc.setText(stepList.get(position).getDescription());
//            if(stepList.get(position).getThumbnailUrl().isEmpty()) {
//                Picasso.with(getContext())
//                        .load(R.id.image_detail_step)
//                        .into(imageDetailStep);
//            }
            initializePlayer(position);
        }
    }

    private void initializePlayer(int position) {
            TrackSelection.Factory trackSelection = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),new DefaultTrackSelector(trackSelection),
                new DefaultLoadControl());
            mPlayerView.setPlayer(mExoPlayer);
            if(stepList.get(position).getVideoUrl().isEmpty()){
                mPlayerView.setVisibility(View.GONE);
                imageDetailStep.setVisibility(View.VISIBLE);
            }else{
                mPlayerView.setVisibility(View.VISIBLE);
                imageDetailStep.setVisibility(View.GONE);
                mPlayerView.setForeground(null);
                Uri uri = Uri.parse(stepList.get(position).getVideoUrl());
                MediaSource mediaSource = new ExtractorMediaSource(uri,
                        new DefaultHttpDataSourceFactory("userAgent"),
                        new DefaultExtractorsFactory(),null,null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(playWhenReady);
                mExoPlayer.seekTo(currentWindow,playBackPosition);
                mPlayerView.setControllerHideOnTouch(true);
            }
    }

    private void releasePlayer(){
        if(mExoPlayer != null){
            playBackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
        if(Util.SDK_INT <= 23){
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
        if(Util.SDK_INT > 23){
            releasePlayer();
        }
    }

    @OnClick(R.id.button_previous)
    public void onPreviousButtonClicked(){
        if(idIndexCurrent == 0){
            Toast.makeText(getContext(), "You are in  the first Step!", Toast.LENGTH_SHORT).show();
        }else{
            idIndexCurrent = idIndexCurrent - 1;
            setupViews(idIndexCurrent);
        }
    }

    @OnClick(R.id.button_next)
    public void onNextButtonClicked(){
        int stepSize = stepList.size()-1;
        if(idIndexCurrent == stepSize){
            Toast.makeText(getContext(), "You have reached the last Step !",
                    Toast.LENGTH_SHORT).show();
        }else{
            idIndexCurrent = idIndexCurrent + 1;
            setupViews(idIndexCurrent);
        }
    }
}
