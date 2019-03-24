package com.nanicky.medclient.main.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nanicky.medclient.ModelHelpers.TestGenerator;
import com.nanicky.medclient.ModelHelpers.WordResulter;
import com.nanicky.medclient.R;
import com.nanicky.medclient.util.WordAnswerView;

public class TestFragment extends Fragment {
    private TextView timerText;
    private LinearLayout container;
    private FloatingActionButton FAB;
    private WordAnswerView wordAnswerView;
    private WordResulter wordResulter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timerText = (TextView) view.findViewById(R.id.timer_test);
        FAB = (FloatingActionButton) view.findViewById(R.id.test_fab);
        container = (LinearLayout) view.findViewById(R.id.containerView);
        wordResulter = new TestGenerator().generateTest();
        wordAnswerView = new WordAnswerView(getContext(), wordResulter.getResult() , 3, wordResulter);
        container.addView(wordAnswerView);
        FAB.setOnClickListener(v -> {
        });

    }
}
