package org.pltw.examples.entertainmentcenter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(this, "onCreate() in MainActivity", Toast.LENGTH_LONG).show();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateFragment createFragment = new CreateFragment();
                getSupportFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.content_frame, createFragment).commit();
                fab.hide();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(findViewById(R.id.content_frame) != null){
            WelcomeFragment welcomeFragment = new WelcomeFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_frame, welcomeFragment).commit();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        // Get the fragment that is currently in the content_frame
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        this.hideShowFab(f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment contentFragment = null;

        if(id== R.id.nav_home) {
            contentFragment = new WelcomeFragment();
        } else if (id == R.id.nav_movies) {
            contentFragment = new MovieFragment();
        } else if (id == R.id.nav_video_games) {
            contentFragment = new VideoGameFragment();
        } else if (id == R.id.nav_board_games) {
            contentFragment = new BoardGameFragment();
        }

        if(contentFragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(null);
            ft.replace(R.id.content_frame, contentFragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onAttachFragment(Fragment fragment) {
        this.hideShowFab(fragment);
    }

    private void hideShowFab(Fragment fragment) {
        if (fragment != null && fragment instanceof CreateFragment){
            fab.hide();
        } else {
            fab.show();
        }
    }
}
