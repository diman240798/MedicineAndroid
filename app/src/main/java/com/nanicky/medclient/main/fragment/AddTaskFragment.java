package com.nanicky.medclient.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nanicky.medclient.R;
import com.nanicky.medclient.main.Item;
import com.nanicky.medclient.main.MainActivity;

public class AddTaskFragment extends Fragment {
    private TextInputEditText txtName;
    private TextInputEditText txtDescription;
    private TextView txtProc;
    private SeekBar seekBar;
    private FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_task_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtName = (TextInputEditText) view.findViewById(R.id.textName);
        txtDescription = (TextInputEditText) view.findViewById(R.id.textDescription);
        txtProc = (TextView) view.findViewById(R.id.textProcent);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        fab = (FloatingActionButton) view.findViewById(R.id.task_fab);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtProc.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        fab.setOnClickListener(v -> {
            String name = txtName.getText().toString();
            String description = txtName.getText().toString();
            int attentionLevel = seekBar.getProgress();
            Item item = new Item(name, description, attentionLevel);
            MainActivity activity = (MainActivity) getActivity();
            activity.setItemsFragment();
            v.postDelayed(() -> {
                activity.onAddNewTask(item);
            }, 800);

        });
    }
}
