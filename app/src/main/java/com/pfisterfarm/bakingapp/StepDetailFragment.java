package com.pfisterfarm.bakingapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.pfisterfarm.bakingapp.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.view.View.GONE;
import static com.pfisterfarm.bakingapp.RecipeDetailFragment.ARG_ITEM_ID;

public class StepDetailFragment extends Fragment {

    private static final String STEP_SELECTED = "step_selected";

    private TextView stepInstructionsTV;
    private ImageView stepThumbIV;
    private int mCurrentStep;
    private int maxSteps;
    private Step mStep;
    private BottomNavigationView bott_nav;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    public StepDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mStep = getArguments().getParcelable(ARG_ITEM_ID);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail_fragment, container, false);
        stepInstructionsTV = rootView.findViewById(R.id.step_instructions);
        stepThumbIV = rootView.findViewById(R.id.step_thumb);
        mPlayerView = rootView.findViewById(R.id.step_video);
        setStepDescription(mStep.getDescription());
        setStepVisual(mStep);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    public void setStepDescription(String newText) {
        stepInstructionsTV.setText(newText);
    }

    public void setStepVisual(Step thisStep) {
        // Determine what visual (video or thumbnail) to display
        String thisVideoURL;
        String thisThumbURL;

        thisVideoURL = thisStep.getVideoURL();
        if ((thisVideoURL != null) && (!thisVideoURL.equals(""))) {
            mPlayerView.setVisibility(View.VISIBLE);
            stepThumbIV.setVisibility(View.GONE);
            initializePlayer(Uri.parse(thisVideoURL));
        } else {
            thisThumbURL = thisStep.getThumbnailURL();
            if ((thisThumbURL != null) && (!thisThumbURL.equals(""))) {
                mPlayerView.setVisibility(View.GONE);
                stepThumbIV.setVisibility(View.VISIBLE);
                Picasso.with(this.getContext()).
                        load(thisThumbURL).
                        error(R.drawable.baking).
                        fit().
                        into(stepThumbIV);
            } else {
                mPlayerView.setVisibility(View.GONE);
                stepThumbIV.setVisibility(View.GONE);
            }
        }
    }

    private void initializePlayer(Uri mediaUri) {
// Adapted from code presented in section 9 of Media Playback lesson of Udacity Advanced Android App Development course
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(this.getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this.getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    public void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

}
