package com.app.games.activities.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.app.games.R;
import com.app.games.utils.LoadingHelper;
import com.app.games.utils.LocaleManager;
import com.app.games.utils.SharedData;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.button.MaterialButton;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;


public class UserReadComicActivity extends AppCompatActivity {

    String url = "";

    LoadingHelper loadingHelper;

    WebView pdfView;
    MaterialButton show_questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_read_comic);

        loadingHelper = new LoadingHelper(this);
        //loadingHelper.showLoading("Loading File");
        pdfView = findViewById(R.id.pdfView);

        show_questions = findViewById(R.id.show_questions);
        show_questions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedData.question_section = 1;
                Intent intent = new Intent(UserReadComicActivity.this,
                        UserQuestionTypes.class);
                startActivity(intent);
            }
        });

        url = SharedData.comics.getPdfURL();
        WebSettings settings = pdfView.getSettings();
        settings.setSupportZoom(true);
        settings.setJavaScriptEnabled(true);
        try {
            url= URLEncoder.encode(url,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        pdfView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url="+url);

        //new RetrivePDFfromUrl().execute(url);

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // create an async task class for loading pdf file from URL.
    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            loadingHelper.dismissLoading();
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
           // pdfView.fromStream(inputStream).load();
        }
    }


}