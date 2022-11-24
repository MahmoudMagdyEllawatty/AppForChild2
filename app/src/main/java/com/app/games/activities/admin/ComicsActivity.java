package com.app.games.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.app.games.R;
import com.app.games.adapters.ComicsAdapter;
import com.app.games.callback.ComicsCallback;
import com.app.games.controller.ComicsController;
import com.app.games.model.Comics;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ComicsActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    FloatingActionButton add;
    LoadingHelper loadingHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        add = findViewById(R.id.add);
        recyclerView = findViewById(R.id.list);
        recyclerView .setLayoutManager(new LinearLayoutManager(this));
        recyclerView .setItemAnimator(new DefaultItemAnimator());

        loadingHelper = new LoadingHelper(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.comics = null;
                Intent intent = new Intent(ComicsActivity.this, AddComicActivity.class);
                startActivity(intent);
            }
        });

        loadData();

       
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void loadData(){
        loadingHelper.showLoading("Loading");
        new ComicsController().getAll(new ComicsCallback() {
            @Override
            public void onSuccess(ArrayList<Comics> shops) {
                loadingHelper.dismissLoading();
                recyclerView.setAdapter(new ComicsAdapter(shops, ComicsActivity.this));
            }

            @Override
            public void onFail(String msg) {
                loadingHelper.dismissLoading();
            }
        });
    }
}
