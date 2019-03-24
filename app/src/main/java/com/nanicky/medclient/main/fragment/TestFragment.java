package com.nanicky.medclient.main.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
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

import java.util.Random;
import java.util.Timer;

public class TestFragment extends Fragment {
    private TextView timerText;
    private LinearLayout container;
    private FloatingActionButton FAB;
    private WordAnswerView wordAnswerView;
    private WordResulter wordResulter;
    private Timer timer;
    private CountDownTimer cTimer;
    private int i;
    private AlertDialog.Builder alert;
    private AlertDialog.Builder alertfinal;

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

        alert = new AlertDialog.Builder(getContext());
        alertfinal = new AlertDialog.Builder(getContext());
        timerText = (TextView) view.findViewById(R.id.timer_test);
        FAB = (FloatingActionButton) view.findViewById(R.id.test_fab);
        container = (LinearLayout) view.findViewById(R.id.containerView);
        wordResulter = new TestGenerator().generateTest();
        wordAnswerView = new WordAnswerView(getContext(), wordResulter.getResult() , 3, wordResulter);
        container.addView(wordAnswerView);
        alert.setTitle("Инструкция")
                .setMessage("Описание того как юзать таймер")
                .setPositiveButton("Начать тест", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        startTimer();
                    }
                });

        AlertDialog dialog = alert.create();
        dialog.show();

        FAB.setOnClickListener(v -> {
            finishDialog();
        });


    }

    void startTimer() {
        cTimer = new CountDownTimer(120000, 1000) {

            @Override
            public void onTick(long l) {
                timerText.setText("Осталось " + String.valueOf(l/1000) + " секунд");
            }

            @Override
            public void onFinish() {
                finishDialog();
            }
        };
        cTimer.start();
    }

    int genAttention(){
        int attention = 0;
        Random random = new Random();
        while (attention < 30) attention = random.nextInt(100);
        return attention;
    }

    void finishDialog(){
        alertfinal.setTitle("Тестирование завершено")
                .setMessage("Ваш результат: "  + String.valueOf(genAttention()))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog = alertfinal.create();
        dialog.show();
    }
}
