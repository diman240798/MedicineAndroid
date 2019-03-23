package com.nanicky.medclient.main;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nanicky.medclient.R;
import com.nanicky.medclient.about.AboutActivity;
import com.nanicky.medclient.base.BaseActivity;
import com.nanicky.medclient.helper.OnStartDragListener;
import com.nanicky.medclient.main.fragment.ItemsFragment;
import com.nanicky.medclient.main.mvp.MainPresenter;


public class MainActivity extends BaseActivity<MainPresenter> {


    private ItemsFragment itemsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // tabLoyout
        setTabs();

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragmentFrame);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentFrame, itemsFragment)
                .commit();
    }

    private void setTabs() {

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        assert tabLayout != null;
        tabLayout.addTab(tabLayout.newTab().setText("Tasks").setTag(1));
        tabLayout.addTab(tabLayout.newTab().setText("Graph").setTag(2));
        tabLayout.addTab(tabLayout.newTab().setText("MedBot").setTag(3));
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

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tag = (Integer) tab.getTag();
                Fragment fragment = null;
                if (tag == 1) {
                    fragment = itemsFragment;
                } else if (tag == 2) {

                } else if (tag == 3) {

                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragmentFrame, fragment)
                        .commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
    protected void attachPresenter() {
        presenter = (MainPresenter) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = new MainPresenter();
        }
        itemsFragment = new ItemsFragment();
        presenter.setOnStartDragListener(itemsFragment);
        presenter.attachView(itemsFragment);
    }


}
