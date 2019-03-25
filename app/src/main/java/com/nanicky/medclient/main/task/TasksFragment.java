package com.nanicky.medclient.main.task;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.nanicky.medclient.main.Item;
import com.nanicky.medclient.main.ItemAdapter;
import com.nanicky.medclient.main.MainActivity;
import com.nanicky.medclient.main.Typefaces;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.nanicky.medclient.util.UtilUI.dpToPx;

public class TasksFragment extends Fragment implements OnStartDragListener, TaskView {

    private ItemTouchHelper mItemTouchHelper;
    TextView tvNumber;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
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
        TaskPresenter presenter = (TaskPresenter) activity.getPresenter(this);

        // Date
        TextView tvDate=(TextView)view.findViewById(R.id.tvDate);
        TextView tvDay=(TextView)view.findViewById(R.id.tvDay);
        tvNumber=(TextView)view.findViewById(R.id.tvNumber);
        setItemsCount(presenter.itemList.size());

        // Date
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("MM.dd.yyyy", Locale.getDefault());
        assert tvDate!=null;
        assert  tvDay!=null;
        tvDate.setTypeface(Typefaces.getRobotoBlack(context));
        tvDay.setTypeface(Typefaces.getRobotoBlack(context));
        tvDate.setText( dateformat.format(c.getTime()).toUpperCase());

        // recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.cardList);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        // recycler adapter
        itemAdapter = presenter.getItemAdapter();
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(itemAdapter,context);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(itemAdapter);


        // Fb
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        assert fab!=null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openNewTaskFragment();
            }
        });
    }

    public void onNewTaskAdded(Item item) {
        llm.scrollToPositionWithOffset(0, dpToPx(56));
        itemAdapter.addItem(0, item);
    }

    @Override
    public void setItemsCount(int count) {
        tvNumber.setText(String.valueOf(count));
    }

    @Override
    public void onItemDissmissed(int position, Item item) {
        Context context = getContext();
        MainActivity activity = (MainActivity) getActivity();

        TaskPresenter presenter = (TaskPresenter) activity.getPresenter(this);
        int size = presenter.itemList.size();
        tvNumber.setText(String.valueOf(size));

        final Snackbar snackbar =  Snackbar
                .make(tvNumber,context.getResources().getString(R.string.item_deleted), Snackbar.LENGTH_LONG)
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
        TextView tvSnackAction = (TextView) snackbar.getView().findViewById( android.support.design.R.id.snackbar_action );
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
        Handler handlerUndo=new Handler();handlerUndo.postDelayed(runnableUndo,2500);

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public int getPresenterId() {
        return 1;
    }
}
