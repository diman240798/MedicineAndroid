package com.nanicky.medclient.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nanicky.medclient.R;
import com.nanicky.medclient.about.AboutActivity;
import com.nanicky.medclient.base.BaseActivity;
import com.nanicky.medclient.base.BasePresenter;
import com.nanicky.medclient.base.BaseView;
import com.nanicky.medclient.main.addTask.AddTaskPresenter;
import com.nanicky.medclient.main.graph.GraphFragment;
import com.nanicky.medclient.main.graph.GraphPresenter;
import com.nanicky.medclient.main.mvp.MainPresenter;
import com.nanicky.medclient.main.mvp.MainView;
import com.nanicky.medclient.main.task.TaskPresenter;
import com.nanicky.medclient.main.task.TasksFragment;
import com.nanicky.medclient.main.addTask.AddTaskFragment;
import com.nanicky.medclient.main.test.TestFragment;
import com.nanicky.medclient.main.test.TestPresenter;

import java.util.Objects;


public class MainActivity extends BaseActivity<MainPresenter> implements MainView {


    public Fragment currentFragment;
    public TestFragment testFragment;
    public TasksFragment taskFragment;
    public GraphFragment graphFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // tabLoyout
        setTabs();

        int fragmentNumber = presenter.currentFragmentNumber;
        Fragment fragment = null;
        if (fragmentNumber == 1) {
            fragment = taskFragment;
        } else if (fragmentNumber == 2) {
            fragment = new AddTaskFragment(); // FIXME: FIXXXXXXX
        } else if (fragmentNumber == 3) {
            fragment = graphFragment;
        } else if (fragmentNumber == 4) {
            TestFragment testFragment = new TestFragment();
            fragment = testFragment;
        } else {
            throw new RuntimeException("NOT IMPLEMENTED FOR: " + fragmentNumber);
        }

        setFragment(fragment, fragmentNumber);

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
                if (currentFragment instanceof AddTaskFragment) {
                    Objects.requireNonNull(tabLayout.getTabAt(0)).select();
                    return;
                }

                int tag = (Integer) tab.getTag();
                Fragment fragment = null;
                int fragmentNumber = 1;
                if (tag == 1) {
                    fragment = taskFragment;
                    fragmentNumber = taskFragment.getPresenterId();
                } else if (tag == 2) {
                    fragment = graphFragment;
                    fragmentNumber = graphFragment.getPresenterId();
                } else if (tag == 3) {
                    TestFragment testFragment = new TestFragment();
                    fragment = testFragment;
                }

                setFragment(fragment, fragmentNumber);
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
            presenter = new MainPresenter(new TaskPresenter(), new GraphPresenter(), new AddTaskPresenter(), new TestPresenter());
        }

        taskFragment = new TasksFragment();
        graphFragment = new GraphFragment();
        testFragment = new TestFragment();

        currentFragment = taskFragment;

        presenter.attachView(this);
        presenter.getTaskPresenter().attachView(taskFragment);
        presenter.getGraphPresenter().attachView(graphFragment);
        presenter.getTaskPresenter().setOnStartDragListener(taskFragment);
        presenter.getTestPresenter().attachView(testFragment);

    }

    private void setFragment(Fragment fragment, int fragmentNumber) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentFrame, fragment)
                .commit();
        currentFragment = fragment;
        presenter.currentFragmentNumber = fragmentNumber;
    }

    public void openNewTaskFragment() {
        AddTaskFragment fragment = new AddTaskFragment();
        int presenterId = fragment.getPresenterId();
        setFragment(fragment, presenterId);
    }

    public void onAddNewTask(Item item) {
        taskFragment.onNewTaskAdded(item);
    }

    public void setItemsFragment() {
        setFragment(taskFragment, taskFragment.getPresenterId());
    }

    @Override
    public void onBackPressed() {
        if (currentFragment instanceof AddTaskFragment)
            setItemsFragment();
        else
            super.onBackPressed();
    }

    public <T extends BasePresenter> T getPresenter(BaseView view, Class<T> type) {
        int id = view.getPresenterId();
        T basePresenter = null;
        if (id == 1) {
            basePresenter = type.cast(presenter.getTaskPresenter());
        } else if (id == 2) {
            basePresenter = type.cast(presenter.getAddAaskPresenter());
        } else if (id == 3) {
            basePresenter = type.cast(presenter.getGraphPresenter());
        } else if (id == 4) {
            basePresenter = type.cast(presenter.getTestPresenter());
        } else {
            throw new RuntimeException("Not Implemented");
        }
        return basePresenter;
    }

    @Override
    public int getPresenterId() {
        return 0;
    }
}
