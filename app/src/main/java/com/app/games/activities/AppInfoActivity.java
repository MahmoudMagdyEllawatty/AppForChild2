package com.app.games.activities;

import android.os.Build;
import android.os.Bundle;

import com.app.games.R;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class AppInfoActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setButtonBackVisible(false);
        setButtonNextVisible(false);
        setButtonCtaVisible(true);
        setButtonCtaTintMode(BUTTON_CTA_TINT_MODE_BACKGROUND);


        setPageScrollDuration(500);

        setButtonBackFunction(BUTTON_BACK_FUNCTION_BACK);
        setButtonNextFunction( BUTTON_NEXT_FUNCTION_NEXT_FINISH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setPageScrollInterpolator(android.R.interpolator.fast_out_slow_in);
        }
        addSlide(new SimpleSlide.Builder()
                .title("All In OneApp")
                .description("Here We Learn,Play and Have Fun")
                .image(R.mipmap.logo)
                .background(R.color.white)
                .backgroundDark(R.color.white)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("Read Comics")
                .description("You can Pick Your Favorite Comic and read it")
                .image(R.mipmap.comics)
                .background(R.color.white)
                .backgroundDark(R.color.white)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("Watch Videos")
                .description("You can Watch Various Videos")
                .image(R.mipmap.videos)
                .background(R.color.white)
                .backgroundDark(R.color.white)
                .scrollable(false)
                .build());
        autoplay(2500, INFINITE);
    }


}