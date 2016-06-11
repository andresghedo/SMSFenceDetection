package com.sm.app.wifi.sghedoni.andrea.wifism;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sm.app.wifi.sghedoni.andrea.wifism.dummy.DummyContent;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ItemFragment.OnListFragmentInteractionListener, MapFragment.OnMapFragmentInteractionListener {

    private String TAG = "[DebApp]MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        String title = getString(R.string.app_name);

        if (id == R.id.nav_map_geofences) {
            // Handle the camera action
            Log.d(TAG, "Premuto l'evento del menu laterale camera");

            // Fragment mappa se ho scelto la mappa geofences
            fragment = new MapFragment();
            title = "Map Geofences";
        } else if (id == R.id.nav_list_geofences) {
            Log.d(TAG, "Premuto l'evento del menu laterale gallery");

            // ItenFragment se ho scelto la lista di geofences
            fragment = new ItemFragment();
            title = "List Geofences";
        } else if (id == R.id.nav_info_credits) {
            Log.d(TAG, "Premuto l'evento del menu laterale slideshow");

            title = "Credits";
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

        Log.d(TAG, "ON DESTROY CYCLE");
    };

    // implementa l'interfaccia relativa al ItemFragment che ho dovuto implementare(vedi implements nel MainActivity)
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Log.d(TAG, "BO");
    };

    // implementa l'interfacci relativa al MapFragment che ho dovuto implementare(vedi implements nel MainActivity) */
    public void onMapFragmentInteraction(Uri uri) {
        Log.d(TAG, "BO");
    };
}
