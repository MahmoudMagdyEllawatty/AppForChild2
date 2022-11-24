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
import com.app.games.adapters.ShortVideosAdapter;
import com.app.games.callback.ShortVideosCallback;
import com.app.games.controller.ShortVideoController;
import com.app.games.model.ShortVideo;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.SharedData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShortVideosActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    FloatingActionButton add;
    LoadingHelper loadingHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_products);
        add = findViewById(R.id.add);
        recyclerView = findViewById(R.id.list);
        recyclerView .setLayoutManager(new LinearLayoutManager(this));
        recyclerView .setItemAnimator(new DefaultItemAnimator());

        loadingHelper = new LoadingHelper(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedData.shortVideo = null;
                Intent intent = new Intent(ShortVideosActivity.this,AddShortVideoActivity.class);
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
        new ShortVideoController().getAll(new ShortVideosCallback() {
            @Override
            public void onSuccess(ArrayList<ShortVideo> shops) {
                loadingHelper.dismissLoading();
                recyclerView.setAdapter(new ShortVideosAdapter(shops,
                        ShortVideosActivity.this));
            }

            @Override
            public void onFail(String msg) {
                loadingHelper.dismissLoading();
            }
        });
    }
}
