package com.app.games.activities.user;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.games.R;
import com.app.games.activities.LoginActivity;
import com.app.games.activities.SplashActivity;
import com.app.games.utils.CircleTransform;
import com.app.games.utils.LocaleManager;
import com.app.games.utils.SharedData;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.intellij.lang.annotations.Language;

import java.util.ArrayList;
import java.util.Locale;

public class UserMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedData.type = 2;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_comics, R.id.nav_videos, R.id.nav_memory,R.id.nav_dashboard)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ImageView imageView = navigationView.getHeaderView(0).findViewById(R.id.imageView);
        TextView textView = navigationView.getHeaderView(0).findViewById(R.id.name);

        if(SharedData.user.getImageURL() != null){
            if(!SharedData.user.getImageURL().equals("")){
                Picasso.get()
                        .load(SharedData.user.getImageURL())
                        .transform(new CircleTransform())
                        .into(imageView);
            }
        }

        textView.setText(SharedData.user.getName());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        showTipDialog();
    }

    private void showTipDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(UserMainActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_tip, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        final ImageButton dialogBtnClose = dialogView.findViewById(R.id.btn_close);
        final TextView etxtTitle = dialogView.findViewById(R.id.text);
        final TextView title = dialogView.findViewById(R.id.title);


        title.setText(R.string.tip_title);
        etxtTitle.setText(R.string.tip);


        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();



        dialogBtnClose.setOnClickListener(v -> alertDialog.dismiss());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            SharedData.type = 1;
            Intent intent = new Intent(UserMainActivity.this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }else if(item.getItemId() == R.id.action_language){
            String language = LocaleManager.getLanguagePref(this);
            if(language.equals("ar")){
                setNewLocale(this,LocaleManager.ENGLISH);
            }else{
                setNewLocale(this, LocaleManager.Arabic);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void loadLocale() {
        String language = LocaleManager.getLanguagePref(this);
        changeLang(language);
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

    }



    private void setNewLocale(AppCompatActivity mContext, @LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(this, language);
        Intent intent = mContext.getIntent();
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}