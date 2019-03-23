package com.nanicky.medclient.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.function.Consumer;


import com.nanicky.medclient.about.AboutActivity;
import com.nanicky.medclient.R;
import com.nanicky.medclient.base.BaseActivity;
import com.nanicky.medclient.helper.OnStartDragListener;
import com.nanicky.medclient.helper.SimpleItemTouchHelperCallback;
import com.nanicky.medclient.main.mvp.MainPresenter;
import com.nanicky.medclient.main.mvp.MainView;


public class MainActivity extends BaseActivity<MainPresenter> implements OnStartDragListener, MainView {

    private ItemTouchHelper mItemTouchHelper;
    private int nu=0;
    TextView tvNumber;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setTabs();

        TextView tvDate=(TextView)findViewById(R.id.tvDate);
        TextView tvDay=(TextView)findViewById(R.id.tvDay);
        tvNumber=(TextView)findViewById(R.id.tvNumber);
        setItemsCount(presenter.itemList.size());

        // Date
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("MM.dd.yyyy", Locale.getDefault());
        assert tvDate!=null;
        assert  tvDay!=null;
        tvDate.setTypeface(Typefaces.getRobotoBlack(this));
        tvDay.setTypeface(Typefaces.getRobotoBlack(this));
        tvDate.setText( dateformat.format(c.getTime()).toUpperCase());

        // recycler view
        recyclerView = (RecyclerView) findViewById(R.id.cardList);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        // recycler adapter
        final ItemAdapter itemAdapter = presenter.getItemAdapter();
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(itemAdapter,this);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(itemAdapter);


        // Fb
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab!=null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Item item = new Item();
                nu= presenter.getItemsSize();
                nu++;
                item.setItemName("item"+nu);
                llm.scrollToPositionWithOffset(0, dpToPx(56));
                itemAdapter.addItem(0, item);
            }
        });
    }

    private void setTabs() {

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        assert tabLayout !=null;
        tabLayout.addTab(tabLayout.newTab().setText("TAB1"));
        tabLayout.addTab(tabLayout.newTab().setText("TAB2"));
        tabLayout.addTab(tabLayout.newTab().setText("TAB3"));
        //TabLayout font & size
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(Typefaces.getRobotoBlack(this));
                    ((TextView) tabViewChild).setTextSize(3);
                }
            }
        }

    }

    @Override
    public void onDestroy() {
      super.onDestroy();
        //presenter.clearList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intAbout = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intAbout);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    protected void attachPresenter() {
        presenter = (MainPresenter) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = new MainPresenter();
        }
        presenter.setOnStartDragListener(this);
        presenter.attachView(this);
    }

    @Override
    public void setItemsCount(int count) {
        tvNumber.setText(String.valueOf(count));
    }

    @Override
    public void onItemDissmissed(int position, Item item) {
        Context context = this;

        tvNumber.setText(String.valueOf(presenter.itemList.size()));

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
}
