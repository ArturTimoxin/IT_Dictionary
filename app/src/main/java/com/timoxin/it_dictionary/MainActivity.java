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
import android.view.MenuItem;

import com.timoxin.it_dictionary.data.DatabaseHelper;
import com.timoxin.it_dictionary.view.MainWordsFragment;
import com.timoxin.it_dictionary.view.MyWordsFragment;
import com.timoxin.it_dictionary.view.NewWordFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private android.support.v4.app.Fragment fragment = null;
    private android.support.v4.app.FragmentManager manager;
    private android.support.v4.app.FragmentTransaction ft;
    private DrawerLayout drawer;
    private DatabaseHelper db;
    private int indexOfPreviousFragment;
    private FragmentManager.BackStackEntry backEntry;
    private String tagOfFragment;

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

/*
 * Отслеживание собития нажатия кнопки назад организовано
 * по принципу получания из стека фрагментов предидущего фрагмента по индексу.
 * Т.к. метод replace удаляет фрагмент, то мы должны создать его заново и отобразить.
 * В дальнейшем при добавлении нового фрагмента ему также необходимо присваивать теги
 * для успешного отображения предидущего тега.
 * Например:
 * ft.replace(R.id.content_frame, Объект_фрагмента_который_ЗАМЕНЯЕТ
 *             "тег_объекта_который_заменяется").addToBackStack("тег_объекта_которого_ЗАМЕНЯЮТ").commit();
 * Так же для отображения тега его необх  заново создать, дописав в методе onBackPressed
 * проверку принадлежности к классу и соответвенно создание заново этого объекта
 * Т.О. при нажатии кнопки back будет произведена замена на предидущий тег.
 */

    @Override
    public void onBackPressed() {
        manager = getSupportFragmentManager();
        indexOfPreviousFragment = manager.getBackStackEntryCount() - 1;
        //Log.d("TAG", "" + indexOfPreviousFragment);
        backEntry = manager.getBackStackEntryAt(indexOfPreviousFragment);
        tagOfFragment = backEntry.getName();
        //Log.d("TAG", tagOfFragment);
        fragment = manager.findFragmentByTag(tagOfFragment);
        if (fragment instanceof MainWordsFragment) {
            fragment = new MainWordsFragment();
        } else if (fragment instanceof MyWordsFragment) {
            fragment = new MyWordsFragment();
        } else if (fragment instanceof NewWordFragment) {
            fragment = new NewWordFragment();
        }
        //Log.d("TAG", fragment.toString());
        ft = manager.beginTransaction();
        ft.replace(R.id.content_frame, fragment).commit();
    }

    public void displayView(int viewId) {
        ft = getSupportFragmentManager().beginTransaction();
        switch (viewId) {
            case R.id.nav_allWords:
                fragment = new MainWordsFragment();
                ft.replace(R.id.content_frame, fragment, "mainWordsFragment").addToBackStack("mainWordsFragment").commit();
                break;
            case R.id.nav_favorite:
                fragment = new MyWordsFragment();
                ft.replace(R.id.content_frame, fragment, "myWordsFragment").addToBackStack("mainWordsFragment").commit();
                break;
            case R.id.nav_newWord:
                fragment = new NewWordFragment();
                ft.replace(R.id.content_frame, fragment, "newWordFragment").addToBackStack("mainWordsFragment").commit();
                break;
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
