package com.sm.app.alert.sghedoni.andrea.dev.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sm.app.alert.sghedoni.andrea.dev.Constant;
import com.sm.app.alert.sghedoni.andrea.dev.Controller;
import com.sm.app.alert.sghedoni.andrea.dev.R;
import com.sm.app.alert.sghedoni.andrea.dev.fragment.ServiceFragment;
import com.sm.app.alert.sghedoni.andrea.dev.fragment.CreditsFragment;
import com.sm.app.alert.sghedoni.andrea.dev.fragment.FenceListFragment;
import com.sm.app.alert.sghedoni.andrea.dev.fragment.MapFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
    {

    private String TAG = "[DebApp]MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // CONTROLLER Istance Initialization
        Controller.getInstance();
        Controller.setDbManager(getApplicationContext());
        Controller.resumeFencesFromDb();
        Controller.getLogFenceEntities();


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
    * Alla selezione del menu in alto
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.d(TAG, "ON OPTIONS ITEM SELECTED WHIT ID: " + id);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        // prendo l'id del menu che ho selezionato
        int id = item.getItemId();
        //fragment che verrà chiamato
        Fragment fragment = null;
        //titolo in alto al cambiare dell item selezionato
        String title = Constant.TITLE_VIEW_APP;

        if (id == R.id.nav_map_geofences) {
            fragment = new MapFragment();
            title = Constant.TITLE_VIEW_MAP_GEOFENCES;
        } else if (id == R.id.nav_list_geofences) {
            fragment = new FenceListFragment();
            title = Constant.TITLE_VIEW_LIST_GEOFENCES;
        } else if (id == R.id.nav_list_start_service) {
            fragment = new ServiceFragment();
            title = Constant.TITLE_VIEW_START_SERVICE;
        } else if (id == R.id.nav_info_credits) {
            fragment = new CreditsFragment();
            title = Constant.TITLE_VIEW_CREDITS;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //CYCLE ACTIVITY
    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "ON START CYCLE");
    };

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(TAG, "ON RESTART CYCLE");
    };

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "ON RESUME CYCLE");
    };

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "ON PAUSE CYCLE");
    };

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "ON STOP CYCLE");
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Controller.mGoogleApiClient.disconnect();
        Log.d(TAG, "ON DESTROY CYCLE");
    };
}
