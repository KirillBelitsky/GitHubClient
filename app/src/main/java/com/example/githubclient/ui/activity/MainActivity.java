package com.example.githubclient.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.githubclient.R;
import com.example.githubclient.model.Repository;
import com.example.githubclient.network.response.AsyncResponse;
import com.example.githubclient.network.service.StarredRepositoryService;
import com.example.githubclient.network.session.UserSession;
import com.example.githubclient.ui.fragment.RepositoryFragment;
import com.example.githubclient.util.circleTransform.CircularTransformation;
import com.example.githubclient.util.parser.JsonParser;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.githubclient.constants.Constants.AVATAR_URL;
import static com.example.githubclient.constants.Constants.LOGIN;
import static com.example.githubclient.constants.Constants.USERNAME;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncResponse {

    private SharedPreferences preferences;
    private UserSession userSession;
    private Fragment repositoryFragment;
    private RecyclerView recyclerView;
    private StarredRepositoryService starredRepositoryService;
    private String starredRepoJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("authUser",MODE_PRIVATE);
        userSession = new UserSession(getApplicationContext());
        starredRepositoryService = new StarredRepositoryService();
        starredRepositoryService.delegate = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        Picasso.with(getApplicationContext()).load(preferences.getString(AVATAR_URL,""))
                .transform(new CircularTransformation()).into(((ImageView)header.findViewById(R.id.imageHeaderUser)));

        ((TextView)header.findViewById(R.id.headerName)).setText(preferences.getString(USERNAME,""));
        ((TextView)header.findViewById(R.id.headerLogin)).setText(preferences.getString(LOGIN,""));
        //((ImageView)header.findViewById(R.id.imageHeaderUser));
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.profile:
                Intent intent = new Intent(this,ProfileActivity.class);
                intent.putExtra(LOGIN,preferences.getString(LOGIN,""));
                startActivity(intent);
                break;

            case R.id.starred_repos:
                starredRepo();
                break;

            case R.id.about:
                break;

            case R.id.logout:
                userSession.invalidate();
                startActivity(new Intent(this, StartActivity.class));
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void starredRepo(){
        this.repositoryFragment = new RepositoryFragment();

        starredRepositoryService.execute(this.preferences.getString(LOGIN,""));
        processFinish(starredRepoJson);

        List<Repository> repositoryList = JsonParser.parseRepositories(starredRepoJson);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container,repositoryFragment);
        transaction.commit();
    }

    @Override
    public void processFinish(String result) {
        try{
            this.starredRepoJson = starredRepositoryService.get();

            System.out.println("11111" + starredRepoJson);
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
