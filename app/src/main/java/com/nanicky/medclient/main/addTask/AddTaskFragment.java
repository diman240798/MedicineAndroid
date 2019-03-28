package com.nanicky.medclient.main.addTask;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class AddTaskFragment extends Fragment implements AddTaskView {
    private TextInputEditText txtName;
    private TextInputEditText txtDescription;
    private TextView txtProc;
    private SeekBar seekBar;
    private FloatingActionButton fab;

    // Save
    String FLD_LEVEL = "level";
    String FLD_DESC = "desc";
    String FLD_NAME = "name";
    private static Bundle savedBundle;

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
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);

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

        MainActivity activity = (MainActivity) getActivity();

        fab.setOnClickListener(v -> {
            String name = txtName.getText().toString();
            String description = txtName.getText().toString();
            int attentionLevel = seekBar.getProgress();
            Item item = new Item(name, description, attentionLevel);
            assert activity != null;
            activity.setItemsFragment();
            v.postDelayed(() -> {
                activity.onAddNewTask(item);
            }, 800);

        });

        if (savedBundle != null) {
            String name = savedBundle.getString(FLD_NAME);
            String description = savedBundle.getString(FLD_DESC);
            int attentionLevel = savedBundle.getInt(FLD_LEVEL);
            savedBundle = null;

            txtName.setText(name);
            txtDescription.setText(description);
            seekBar.setProgress(attentionLevel);
        } else if (bundle != null) {
            savedBundle = bundle;
        }
    }
    
    ///////////// RESTORE STATE
    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);

        String name = txtName.getText().toString();
        String description = txtDescription.getText().toString();
        int attentionLevel = seekBar.getProgress();

        bundle.putInt(FLD_LEVEL, attentionLevel);
        bundle.putString(FLD_DESC, description);
        bundle.putString(FLD_NAME, name);
    }
    ///////////// RESTORE STATE

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public int getPresenterId() {
        return 4;
    }
}
