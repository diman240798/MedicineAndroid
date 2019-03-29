package com.nanicky.medclient.main.task;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanicky.medclient.R;
import com.nanicky.medclient.helper.OnStartDragListener;
import com.nanicky.medclient.helper.SimpleItemTouchHelperCallback;
import com.nanicky.medclient.main.MainActivity;
import com.nanicky.medclient.main.Task;
import com.nanicky.medclient.main.TaskAdapter;
import com.nanicky.medclient.main.Typefaces;
import com.nanicky.medclient.util.ScrollingFABBehavior;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.nanicky.medclient.util.UtilUI.dpToPx;

public class TasksFragment extends Fragment implements OnStartDragListener, TaskView {

    private static int top, index = -1;

    private ItemTouchHelper mItemTouchHelper;
    private TextView tvNumber;
    private RecyclerView rv;
    private TaskAdapter taskAdapter;
    private LinearLayoutManager llm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.items_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Context context = getContext();
        MainActivity activity = (MainActivity) getActivity();
        TaskPresenter presenter = activity.getPresenter(this, TaskPresenter.class);

        // Date
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        TextView tvDay = (TextView) view.findViewById(R.id.tvDay);
        tvNumber = (TextView) view.findViewById(R.id.tvNumber);
        setItemsCount(presenter.itemList.size());

        // Date
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("MM.dd.yyyy", Locale.getDefault());
        assert tvDate != null;
        assert tvDay != null;
        tvDate.setTypeface(Typefaces.getRobotoBlack(context));
        tvDay.setTypeface(Typefaces.getRobotoBlack(context));
        tvDate.setText(dateformat.format(c.getTime()).toUpperCase());

        // recycler view
        rv = (RecyclerView) view.findViewById(R.id.cardList);
        assert rv != null;
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        // recycler adapter
        taskAdapter = taskAdapter = new TaskAdapter(presenter, this);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(taskAdapter, context);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(rv);
        rv.setAdapter(taskAdapter);


        // Fb
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openNewTaskFragment();
            }
        });
    }

    public void onNewTaskAdded(Task item) {
        llm.scrollToPositionWithOffset(0, dpToPx(56));
        taskAdapter.addItem(0, item);
    }

    @Override
    public void setItemsCount(int count) {
        tvNumber.setText(String.valueOf(count));
    }

    @Override
    public void onItemDissmissed(int position, Task item) {
        Context context = getContext();
        MainActivity activity = (MainActivity) getActivity();

        TaskPresenter presenter = activity.getPresenter(this, TaskPresenter.class);
        int size = presenter.itemList.size();
        tvNumber.setText(String.valueOf(size));

        final Snackbar snackbar = Snackbar
                .make(tvNumber, context.getResources().getString(R.string.item_deleted), Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(context, R.color.white))
                .setAction(context.getResources().getString(R.string.item_undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.onAddItem(item, position);
                        tvNumber.setText(String.valueOf(presenter.itemList.size()));

                    }
                });


        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        TextView tvSnack = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        TextView tvSnackAction = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_action);
        tvSnack.setTextColor(Color.WHITE);
        tvSnack.setTypeface(Typefaces.getRobotoMedium(context));
        tvSnackAction.setTypeface(Typefaces.getRobotoMedium(context));
        snackbar.show();

        Runnable runnableUndo = new Runnable() {

            @Override
            public void run() {
                tvNumber.setText(String.valueOf(presenter.itemList.size()));
                snackbar.dismiss();
            }
        };
        Handler handlerUndo = new Handler();
        handlerUndo.postDelayed(runnableUndo, 2500);

    }

    @Override
    public void notifyItemDissmissed(int position) {
        taskAdapter.notifyItemRemoved(position);
        taskAdapter.notifyItemRangeChanged(0, taskAdapter.getItemCount());
    }

    @Override
    public void notifyItemInserted(int position) {
        taskAdapter.notifyItemInserted(position);
    }

    @Override
    public void notifyItemMoved(int fromPosition, int toPosition) {
        taskAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public int getPresenterId() {
        return 1;
    }

    @Override
    public void onPause() {
        super.onPause();
        //read current recyclerview position
        index = llm.findFirstVisibleItemPosition();
        View v = rv.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - rv.getPaddingTop());
    }

    @Override
    public void onResume() {
        super.onResume();
        //set recyclerview position
        if (index != -1) {
            llm.scrollToPositionWithOffset(index, top);
        }
    }
}
