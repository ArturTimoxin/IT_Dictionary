package com.timoxin.it_dictionary;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import com.timoxin.it_dictionary.data.DatabaseHelper;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private android.support.v4.app.Fragment fragment = null;
    private android.support.v4.app.FragmentManager manager;
    private android.support.v4.app.FragmentTransaction ft;
    private DrawerLayout drawer;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        db = new DatabaseHelper(this);

        displayView(R.id.nav_allWords); //set start fragment

    }

    public DatabaseHelper getDataBaseHelperObject(){
        return this.db;
    }

    @Override
    public void onBackPressed() {
        manager = getSupportFragmentManager();
        int index = manager.getBackStackEntryCount() - 1;
        Log.d("TAG", "" + index);
        FragmentManager.BackStackEntry backEntry = manager.getBackStackEntryAt(index);
        String tag = backEntry.getName();
        Log.d("TAG", tag);
        fragment = manager.findFragmentByTag(tag);
        if(fragment instanceof MainWordsFragment) {
            fragment = new MainWordsFragment();
        } else if (fragment instanceof MyWordsFragment) {
            fragment = new MyWordsFragment();
        } else if (fragment instanceof NewWordFragment){
            fragment = new NewWordFragment();
        }
        Log.d("TAG", fragment.toString());
        ft = manager.beginTransaction();
        ft.replace(R.id.content_frame, fragment).addToBackStack(fragment.getTag()).commit();
    }

    public void displayView(int viewId) {
        switch (viewId) {
            case R.id.nav_allWords:
                fragment = new MainWordsFragment();
                break;
            case R.id.nav_favorite:
                fragment = new MyWordsFragment();
                break;
            case R.id.nav_newWord:
                fragment = new NewWordFragment();
                break;
        }

        if (fragment != null) {
            ft = getSupportFragmentManager().beginTransaction();
            if(fragment instanceof MainWordsFragment) {
                ft.replace(R.id.content_frame, fragment, "mainWordsFragment").addToBackStack("mainWordsFragment").commit();
            } else if (fragment instanceof MyWordsFragment) {
                ft.replace(R.id.content_frame, fragment, "myWordsFragment").addToBackStack("mainWordsFragment").commit();
            } else if (fragment instanceof NewWordFragment){
                ft.replace(R.id.content_frame, fragment, "newFragment").addToBackStack("mainWordsFragment").commit();
            }
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displayView(item.getItemId());
        return true;
    }

    public boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
