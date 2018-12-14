package com.timoxin.it_dictionary;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

    private Fragment fragment = null;
    private DrawerLayout drawer;
    private boolean viewIsAtHome;
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
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Log.d("TAG", "backPressed");
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (!viewIsAtHome) { //if the current view is not the News fragment
            moveTaskToBack(true);  //If view is in main_words fragment, exit application
        } else {
            displayView(R.id.nav_allWords); //display the main_words fragment
        }
    }



    public void displayView(int viewId) {
        switch (viewId) {
            case R.id.nav_allWords:
                fragment = new MainWordsFragment();
                viewIsAtHome = false;
                break;
            case R.id.nav_favorite:
                fragment = new MyWordsFragment();
                viewIsAtHome = true;
                break;
            case R.id.nav_newWord:
                fragment = new NewWordFragment();
                viewIsAtHome = true;
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displayView(item.getItemId());
        return true;
    }
}
