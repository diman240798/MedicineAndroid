package com.nanicky.medclient.main.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanicky.medclient.ModelHelpers.TestGenerator;
import com.nanicky.medclient.ModelHelpers.WordResult;
import com.nanicky.medclient.ModelHelpers.WordResulter;
import com.nanicky.medclient.R;
import com.nanicky.medclient.util.WordAnswerView;

import java.util.Random;
import java.util.Timer;

public class TestFragment extends Fragment implements TestView {
    private TextView timerText;
    private TextView text;
    private FloatingActionButton FAB;
    private WordAnswerView wordAnswerView;
    private WordResulter wordResulter;
    private CountDownTimer cTimer;
    private AlertDialog.Builder alert;
    private AlertDialog.Builder alertfinal;

    private int counter;
    String randomString = "fs43f352" + "алеьсин" + "gdswt" + "яблоко" + "4gv6buyf2" + "Котик" + "u39bci32k4btig7bajkeriw3";


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
        text = (TextView) view.findViewById(R.id.containerView);
        wordResulter = new TestGenerator().generateTest();


        String testText = wordResulter.getResult();

        /*SpannableString spannableString = new SpannableString(testText);
        spannableString.setSpan(clickableSpan, 0, testText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
*/
        text.setText(testText);
        text.setMovementMethod(LinkMovementMethod.getInstance());
        text.setClickable(true);
        text.setWidth(1000);

        String apelsin = "<font color='red'>" + "апельсин" + "</font>";
        String yabloko = "<font color='red'>" + "яблоко" + "</font>";
        String kotik = "<font color='red'>" + "Котик" + "</font>";


        text.setText(randomString);

        text.setOnClickListener(v -> {
            if (counter == 0) {
                randomString = randomString.replace("алеьсин", apelsin);
            } else if (counter == 1) {
                randomString = randomString.replace("яблоко", yabloko);
            } else {
                randomString = randomString.replace("Котик", kotik);
            }
            text.setText(Html.fromHtml(randomString));
            counter +=1;
        });

        /*text.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Layout layout = ((TextView) v).getLayout();
                int x = (int)event.getX();
                int y = (int)event.getY();
                if (layout!=null){
                    int line = layout.getLineForVertical(y);
                    int offset = layout.getOffsetForHorizontal(line, x);

                }
                return true;
            }
        });*/

                /*setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int index = (int) (event.getX() / baseWidth);
                // и устанавливаем границы выделения согласно описанным выше правилам
                WordResult wordResult = wordResulter.check(index);
                if (wordResult != null) {
                    int start = wordResult.start;
                    int end = wordResult.end;
                    String s = text.getText().toString();
                    String startSubstring = s.substring(0, start);

                    String clr = s.substring(start, end);
                    SpannableString spannableString = new SpannableString(clr);
                    spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW), 0, clr.length(), 0);

                    String endSubstring= s.substring(end, s.length());

                    text.setText(startSubstring + spannableString + endSubstring);
                }
                return true;
            }
        });*/

        //wordAnswerView = new WordAnswerView(getContext(), wordResulter.getResult() , 12, wordResulter);
        //text.addView(wordAnswerView);
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

    @Override
    public int getPresenterId() {
        return 3;
    }
}
