package com.example.githubclient.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.githubclient.R;
import com.example.githubclient.network.response.AsyncResponse;
import com.example.githubclient.network.service.CodeLoader;

import io.github.kbiakov.codeview.CodeView;
import io.github.kbiakov.codeview.adapters.Options;
import io.github.kbiakov.codeview.highlight.ColorTheme;


public class CodeViewActivity extends AppCompatActivity  implements AsyncResponse {

    private CodeView mCodeView;
    private AsyncResponse delegate;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeview);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.progressBar = findViewById(R.id.code_view_progress_bar);
        this.mCodeView = (CodeView)findViewById(R.id.code_view);

        this.mCodeView.setOptions(Options.Default.get(this));
        this.mCodeView.getOptions().setTheme(ColorTheme.MONOKAI);
        this.delegate = this;

        new CodeLoader(delegate).execute(getIntent().getStringExtra("downloadUrl"));
        this.setTitle(getIntent().getStringExtra("title"));
    }

    @Override
    public void processFinish(String result) {
        this.mCodeView.setCode(result);

        this.progressBar.setVisibility(View.INVISIBLE);
        this.mCodeView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();

        return true;
    }
}
