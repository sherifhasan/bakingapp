package example.android.bakingappudacity.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.android.bakingappudacity.R;
import example.android.bakingappudacity.models.Recipe;
import example.android.bakingappudacity.models.Step;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeStepDetailFragment extends Fragment {

    private static final String ARGUMENT_EXTRA = "Recipe";
    private static final String POSITION = "pos";
    private static final String SEEK_POSITION = "player_seek";
    private static final String ARGUMENT_EXTRA_INSTANCE = "Recipe_inst";
    private static final String POSITION_INSTANCE = "pos_inst";
    Recipe recipe;
    @BindView(R.id.video_player)
    SimpleExoPlayerView mPlayerContainer;
    SimpleExoPlayer mPlayer;
    @BindView(R.id.step_inst_desc_text_view)
    TextView mStepInstructionTextView;
    @BindView(R.id.thumb_image)
    ImageView mImageView;
    @BindView(R.id.prev_step)
    Button mPrevStepButton;
    @BindView(R.id.next_step)
    Button mNextStepButton;
    int currentPosition;
    long exoPlayerSeekPosition;

    public static RecipeStepDetailFragment newInstance(Recipe step, int pos) {
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_EXTRA, step);
        args.putInt(POSITION, pos);
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RecipeStepDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);
        if (getArguments() != null && getArguments().containsKey(ARGUMENT_EXTRA) && getArguments().getParcelable(ARGUMENT_EXTRA) != null) {
            currentPosition = getArguments().getInt(POSITION);
            recipe = getArguments().getParcelable(ARGUMENT_EXTRA);
            if (recipe.getSteps() != null && recipe.getSteps().get(currentPosition) != null)
                initViews(recipe.getSteps().get(currentPosition));
        } else if (getArguments() != null && getArguments().containsKey(ARGUMENT_EXTRA_INSTANCE) && getArguments().getParcelable(ARGUMENT_EXTRA_INSTANCE) != null) {
            exoPlayerSeekPosition = getArguments().getLong(SEEK_POSITION);
            recipe = getArguments().getParcelable(ARGUMENT_EXTRA_INSTANCE);
            currentPosition = getArguments().getInt(POSITION_INSTANCE);
            if (recipe.getSteps() != null && recipe.getSteps().get(currentPosition) != null)
                initViews(recipe.getSteps().get(currentPosition));
        }
        return rootView;
    }

    @OnClick({R.id.prev_step, R.id.next_step})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.prev_step:
                if (mPlayer != null) {
                    exoPlayerSeekPosition = 0;
                    mPlayer.stop();
                }
                currentPosition = Math.max(0, currentPosition - 1);
                initViews(recipe.getSteps().get(currentPosition));
                break;
            case R.id.next_step:
                if (mPlayer != null) {
                    exoPlayerSeekPosition = 0;
                    mPlayer.stop();
                }
                currentPosition = Math.min(recipe.getSteps().size() - 1, currentPosition + 1);
                initViews(recipe.getSteps().get(currentPosition));
                break;
        }
    }

    private void initViews(Step step) {

        if (currentPosition > 0) {
            mPrevStepButton.setVisibility(View.VISIBLE);
        } else {
            mPrevStepButton.setVisibility(View.INVISIBLE);
        }

        if (currentPosition < (recipe.getSteps().size() - 1)) {
            mNextStepButton.setVisibility(View.VISIBLE);
        } else {
            mNextStepButton.setVisibility(View.INVISIBLE);
        }

        Uri imageUrl;
        if (!TextUtils.isEmpty(step.getThumbnailURL())) {
            String format = "";

            int i = step.getThumbnailURL().lastIndexOf('.');
            if (i > 0) {
                format = step.getThumbnailURL().substring(i + 1);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (!Objects.equals(format, "png") || !Objects.equals(format, "jpg")) {
                    return;
                }
            }
            mImageView.setVisibility(View.VISIBLE);
            imageUrl = Uri.parse(step.getThumbnailURL());
            Picasso.with(getContext()).load(imageUrl).into(mImageView);
        }

        mStepInstructionTextView.setText(step.getDescription());

        initializePlayer(step);
    }

    private void initializePlayer(Step step) {

        mPlayerContainer.setVisibility(View.VISIBLE);
        Context context = getActivity();
        if (mPlayer == null) {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            mPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        }

        mPlayerContainer.setPlayer(mPlayer);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, getString(R.string.app_name))
        );

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        Uri videoUrl = null;
        if (!TextUtils.isEmpty(step.getVideoURL())) {
            videoUrl = Uri.parse(step.getVideoURL());
        }
        MediaSource videoSource = new ExtractorMediaSource(videoUrl, dataSourceFactory, extractorsFactory, null, null);
        mPlayer.prepare(videoSource);
        if (exoPlayerSeekPosition != 0)
            mPlayer.seekTo(exoPlayerSeekPosition);
        mPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        Bundle outState = new Bundle();
        outState.putInt(POSITION_INSTANCE, currentPosition);
        outState.putParcelable(ARGUMENT_EXTRA_INSTANCE, recipe);
        if (mPlayer != null) {
            outState.putLong(SEEK_POSITION, mPlayer.getCurrentPosition());
        }
        setArguments(outState);
        releasePlayer();
    }

    private void releasePlayer() {
        if (mPlayer == null) {
            return;
        }
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }
}