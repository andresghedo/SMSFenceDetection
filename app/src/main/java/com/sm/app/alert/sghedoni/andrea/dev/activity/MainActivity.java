package com.sm.app.alert.sghedoni.andrea.dev.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sm.app.alert.sghedoni.andrea.dev.fragment.HomeFragment;
import com.sm.app.alert.sghedoni.andrea.dev.fragment.InfoFragment;
import com.sm.app.alert.sghedoni.andrea.dev.utils.Constant;
import com.sm.app.alert.sghedoni.andrea.dev.utils.Controller;
import com.sm.app.alert.sghedoni.andrea.dev.R;
import com.sm.app.alert.sghedoni.andrea.dev.fragment.ServiceFragment;
import com.sm.app.alert.sghedoni.andrea.dev.fragment.CreditsFragment;
import com.sm.app.alert.sghedoni.andrea.dev.fragment.FenceListFragment;
import com.sm.app.alert.sghedoni.andrea.dev.fragment.MapFragment;

import java.util.ArrayList;
import java.util.List;

/**
 *  Main and unique Activity in the project.
 *  The different screens are contained in Fragments.
 *  @author Andrea Sghedoni
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
    {

    private String TAG = "[DebApp]MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // CONTROLLER Istance Initialization
        Controller.getInstance();
        // Set DB Manager
        Controller.setDbManager(getApplicationContext());
        // Resume all fences in Controller ArrayList
        Controller.resumeFencesFromDb();
        // Log of SQLiteDB
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

        HomeFragment fragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        boolean permissionFineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean permissionSendSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
        ArrayList<String> deniedPermissions = new ArrayList<String>();

        if(!permissionFineLocation)
            deniedPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        if(!permissionSendSMS)
            deniedPermissions.add(Manifest.permission.SEND_SMS);

        if (deniedPermissions.size() > 0) {
            String[] deniedPermissionsArray = new String[deniedPermissions.size()];
            deniedPermissions.toArray(deniedPermissionsArray);
            Controller.requestPermission((AppCompatActivity) this, 1, deniedPermissionsArray);
        }
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

    /* Click on action bar menù. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_info) {
            Fragment fragment = new InfoFragment();
            String title = Constant.TITLE_VIEW_INFO;
            this.runFragment(fragment, title);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Fragment fragment = null;
        // Fragment title
        String title = Constant.TITLE_VIEW_APP;

        if (id == R.id.nav_map_home) {
            // Home Fragment
            fragment = new HomeFragment();
            title = Constant.TITLE_VIEW_HOME;
        }else if (id == R.id.nav_map_geofences) {
            // Map Fragment
            fragment = new MapFragment();
            title = Constant.TITLE_VIEW_MAP_GEOFENCES;
        } else if (id == R.id.nav_list_geofences) {
            // Fence List(and add) List
            fragment = new FenceListFragment();
            title = Constant.TITLE_VIEW_LIST_GEOFENCES;
        } else if (id == R.id.nav_list_start_service) {
            // Service Fragment
            fragment = new ServiceFragment();
            title = Constant.TITLE_VIEW_START_SERVICE;
        } else if (id == R.id.nav_info_credits) {
            // Credits Fragment
            fragment = new CreditsFragment();
            title = Constant.TITLE_VIEW_CREDITS;
        }

        this.runFragment(fragment, title);
        return true;
    }

    /* Commit of Fragment */
    private void runFragment(Fragment fragment, String title) {
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
    }

    @Override
    protected void onStart() {
        super.onStart();
    };

    @Override
    protected void onRestart() {
        super.onRestart();
    };

    @Override
    protected void onResume() {
        super.onResume();
    };

    @Override
    protected void onPause() {
        super.onPause();
    };

    @Override
    protected void onStop() {
        super.onStop();
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        boolean showWarningToast = false;
        String toShow = "YOU MUST ACCEPT ALL PERMISSIONS!!";
        for(int i=0; i<permissions.length; i++) {
            Log.d(TAG, "Permission: " + permissions[i] + "  |  result:  " + grantResults[i]);
            if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                showWarningToast = true;
        }
        if(showWarningToast)
            Toast.makeText(getApplicationContext(), toShow, Toast.LENGTH_LONG).show();
    }
}
