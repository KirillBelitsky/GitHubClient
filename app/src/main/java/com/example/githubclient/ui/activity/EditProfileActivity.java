package com.example.githubclient.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.githubclient.R;
import com.example.githubclient.model.User;
import com.example.githubclient.model.UserDataEdit;
import com.example.githubclient.network.service.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText company;
    private UserDataEdit userDataEdit;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.setTitle("Edit profile");

        loadData();

        name = findViewById(R.id.edit_name);
        email = findViewById(R.id.edit_email);
        company = findViewById(R.id.edit_company);
        userDataEdit = new UserDataEdit();
        preferences = getSharedPreferences("authUser",MODE_PRIVATE);
    }

    public void onSave(View view){
        userDataEdit.setName(name.getText().toString());
        userDataEdit.setEmail(email.getText().toString());
        userDataEdit.setCompany(company.getText().toString());

        NetworkService.getInstance()
                .getUserApi()
                .updateUser(userDataEdit)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.body()!=null){

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Unlucky!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        call.cancel();
                    }
                });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();

        return true;
    }

    private void loadData(){
        NetworkService.getInstance()
                .getUserApi()
                .getCurrentUser()
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            name.setText(response.body().getName());
                            email.setText(response.body().getEmail());
                            company.setText(response.body().getCompany());
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        call.cancel();
                    }
                });
    }
}
