package com.kimo.examples.alexei.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devspark.progressfragment.ProgressFragment;
import com.kimo.examples.alexei.R;
import com.kimo.lib.alexei.Alexei;
import com.kimo.lib.alexei.Answer;
import com.kimo.lib.alexei.ImageProcessingThing;

import java.util.List;

/**
 * Created by Kimo on 8/19/14.
 */
public class ColorPalleteFragment extends ProgressFragment {

    public static final String TAG = ColorPalleteFragment.class.getSimpleName();

    private ImageView mImage;
    private TextView mElapsedTimeView;
    private LinearLayout mPalleteContainer;
    private CalculusExecutor mBackgroundThread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pallete, container, false);
        configure(view);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public void onResume() {
        super.onResume();

        performCalculus();
    }

    @Override
    public void onPause() {
        super.onPause();

        mBackgroundThread.cancel(true);
        mBackgroundThread = null;
    }

    private void configure(View view) {

        setHasOptionsMenu(true);

        mImage = (ImageView) view.findViewById(R.id.img);
        mPalleteContainer = (LinearLayout) view.findViewById(R.id.pallete_container);
        mElapsedTimeView = (TextView) view.findViewById(R.id.elapsed_time);
    }

    private void fillPalleteColors(List<Integer> colors) {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        for(int color : colors) {

            View palleteColor = inflater.inflate(R.layout.item_pallete, mPalleteContainer, false);
            palleteColor.setBackgroundColor(color);

            mPalleteContainer.addView(palleteColor);
        }
    }

    private void performCalculus() {
        mBackgroundThread = new CalculusExecutor();
        mBackgroundThread.execute();
    }

    private class CalculusExecutor extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(!isCancelled())
                setContentShown(false);
        }

        @Override
        protected Void doInBackground(Void... params) {

            if(!isCancelled()) {
                Alexei.analize(mImage)
                        .perform(ImageProcessingThing.COLOR_PALLETE)
                        .andGiveMe(new Answer<List<Integer>>() {
                            @Override
                            public void ifSucceeded(List<Integer> answer, long elapsedTime) {
                                fillPalleteColors(answer);
                                mElapsedTimeView.setText(new StringBuilder().append(elapsedTime).append(" milliseconds"));
                            }
                        });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(!isCancelled())
                setContentShown(true);
        }
    }
}
