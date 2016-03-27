package com.koper.timetracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.koper.timetracker.fragments.StatsFragment;
import com.koper.timetracker.fragments.TrackerFragment;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity {


    private Drawer fDrawer;

    private int fCurrentDrawerPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, TrackerFragment.newInstance(), TrackerFragment.TAG)
                .commit();


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        PrimaryDrawerItem mTrackerItem = new PrimaryDrawerItem().withName("Tracker").withIcon(GoogleMaterial.Icon.gmd_timer);
        PrimaryDrawerItem mHistory = new PrimaryDrawerItem().withName("History").withIcon(GoogleMaterial.Icon.gmd_history);
        SecondaryDrawerItem mStats = new SecondaryDrawerItem().withName("Statistics").withIcon(FontAwesome.Icon.faw_pie_chart);

        fDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withDrawerWidthDp(250)
                .withAccountHeader(new AccountHeaderBuilder().withActivity(this).build())
                .addDrawerItems(mTrackerItem, mHistory, mStats)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (fDrawer != null && fCurrentDrawerPosition != position) {
                            switch (position) {
                                case 1:
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container, TrackerFragment.newInstance(), TrackerFragment.TAG).commit();
                                    break;
                                case 2:
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container, HistoryFragment.newInstance(), HistoryFragment.TAG).commit();
                                    break;
                                case 3:
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container, StatsFragment.newInstance(), StatsFragment.TAG).commit();
                                    break;
                            }
                        } else return true;
                        fCurrentDrawerPosition = position;
                        return false;
                    }
                })
                .build();

            fCurrentDrawerPosition = fDrawer.getCurrentSelectedPosition();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
