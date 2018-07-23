package com.example.dell.form_draft_4;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        onCreateDrawer();
    }

    protected void onCreateDrawer() {

        ////////////////////////////////////////////////////////////////////////////////////////////
        /*Drawer Stuff*/

        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawerOpen, R.string.drawerClosed);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        ////////////////////////////////////////////////////////////////////////////////////////////
        /*Drawer Stuff*/

        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawerOpen, R.string.drawerClosed);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_Dashboard);

        ////////////////////////////////////////////////////////////////////////////////////////////
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.mDrawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_Dashboard) {
            if (!navigationView.getMenu().findItem(R.id.nav_Dashboard).isChecked()) {
                Intent i = new Intent(this, Dashboard.class);
                startActivity(i);
            }
        } else if (id == R.id.nav_addAccount) {
            if (!navigationView.getMenu().findItem(R.id.nav_addAccount).isChecked()) {
                Intent i = new Intent(this, AddAccount_Screen.class);
                startActivity(i);
            }

        } else if (id == R.id.nav_addUser) {
            if (!navigationView.getMenu().findItem(R.id.nav_addUser).isChecked()) {
                Intent i = new Intent(this, TestScreen.class);
                startActivity(i);
            }

        } else if (id == R.id.nav_addDevice) {
            if (!navigationView.getMenu().findItem(R.id.nav_addDevice).isChecked()) {
                Intent i = new Intent(this, AddDevice_Screen.class);
                startActivity(i);
            }

        } else if (id == R.id.nav_addFuel) {


        } else if (id == R.id.nav_viewFuel) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.mDrawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
}
