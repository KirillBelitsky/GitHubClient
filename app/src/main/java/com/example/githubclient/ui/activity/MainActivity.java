package com.example.githubclient.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.githubclient.R;
import com.example.githubclient.model.User;
import com.example.githubclient.network.service.NetworkService;
import com.example.githubclient.network.session.UserSession;
import com.example.githubclient.ui.fragment.IssueListFragment;
import com.example.githubclient.ui.fragment.RepositoryFragment;
import com.example.githubclient.ui.fragment.SearchUserFragment;
import com.example.githubclient.util.circleTransform.CircularTransformation;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.githubclient.constants.Constants.AVATAR_URL;
import static com.example.githubclient.constants.Constants.LOGIN;
import static com.example.githubclient.constants.Constants.TOKEN;
import static com.example.githubclient.constants.Constants.USERNAME;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private SharedPreferences preferences;
    private UserSession userSession;
    private RepositoryFragment repositoryFragment;
    private IssueListFragment issuesListFragment;
    private SearchUserFragment searchUserFragment;
    private View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("authUser",MODE_PRIVATE);
        userSession = new UserSession(getApplicationContext());
        issuesListFragment = new IssueListFragment();
        repositoryFragment = new RepositoryFragment();
        searchUserFragment = new SearchUserFragment();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);

        Picasso.with(getApplicationContext()).load(preferences.getString(AVATAR_URL,""))
                .transform(new CircularTransformation()).into(((ImageView)header.findViewById(R.id.imageHeaderUser)));

        loadData();
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

            case R.id.issues:
                issues();
                break;

            case R.id.search:
                search();
                break;

            case R.id.my_repos:
                userRepo();
                break;

            case R.id.starred_repos:
                starredRepo();
                break;

            case R.id.about:
                break;

            case R.id.logout:
                userSession.invalidate();
                startActivity(new Intent(this, SplashActivity.class));
                finish();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void starredRepo(){
        if(repositoryFragment.isVisible()){
            if(repositoryFragment.getArguments().get("repo").equals("own"))
                repositoryFragment.changeToStarred();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString(LOGIN,preferences.getString(LOGIN,""));
        bundle.putString("repo","starred");

        loadFragmentRepo(bundle);
    }

    private void userRepo(){
        if(repositoryFragment.isVisible()){
            if(repositoryFragment.getArguments().get("repo").equals("starred"))
                repositoryFragment.changeToOwn();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString(LOGIN, preferences.getString(LOGIN,""));
        bundle.putString("repo","own");

        loadFragmentRepo(bundle);
    }


    private void issues(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container,issuesListFragment);
        transaction.commit();
    }

    private void search(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container,searchUserFragment);
        transaction.commit();
    }

    private void loadFragmentRepo(Bundle bundle){
        repositoryFragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container,repositoryFragment);
        transaction.commit();
    }

    private void loadData(){
        final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        NetworkService.getInstance()
                .getUserApi()
                .getCurrentUser(preferences.getString(TOKEN,""))
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        ((TextView)header.findViewById(R.id.headerCreatedAt)).append(" " + format.format(response.body().getDate()));
                        ((TextView)header.findViewById(R.id.headerLogin)).setText(response.body().getLogin());
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        call.cancel();
                    }
                });
    }
}
