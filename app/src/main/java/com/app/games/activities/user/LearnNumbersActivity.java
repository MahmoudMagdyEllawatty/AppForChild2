package com.app.games.activities.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.games.R;
import com.app.games.utils.LetterHelper;
import com.app.games.utils.LocaleManager;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Locale;

public class LearnNumbersActivity extends AppCompatActivity
        implements android.view.View.OnTouchListener{

    ImageView imageView;
    Button next,previous,play;
    ArrayList<LetterHelper> letters;
    int index = 0;

    Canvas canvas;
    Paint paint;
    Matrix matrix;
    float downx = 0;
    float downy = 0;
    float upx = 0;
    float upy = 0;

    private Bitmap DrawBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint DrawBitmapPaint;
    RelativeLayout Rl;
    LearnNumbersActivity.CustomView View;
    Paint mPaint;

    private String baseSpeakURL = "https://ttsmp3.com/created_mp3/";

    private MediaPlayer mediaPlayer;

    Button speak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_numbers);
        Rl = findViewById(R.id.rel);
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setTitle(getString(R.string.learn_numbers));

        imageView = findViewById(R.id.image);


        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        play = findViewById(R.id.play);
        speak = findViewById(R.id.speak);


        play.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                try {
                    playAudio(letters.get(index).getUrl());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LearnNumbersActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL
                        ,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ar-JO");
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"tk.oryx.voice");
                startActivityForResult(intent,11);
            }
        });

        letters = new ArrayList<>();
        fillLetters();

        index = 0;
        setData();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index < (letters.size()-1))
                    index++;
                setData();
            }
        });


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index > 0)
                    index--;
                setData();
            }
        });
    }

    private void playAudio(String url) throws Exception
    {
        killMediaPlayer();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(baseSpeakURL+url);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    private void killMediaPlayer() {
        if(mediaPlayer!=null) {
            try {
                mediaPlayer.release();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        killMediaPlayer();
    }

    private void setData(){
        imageView.setImageResource(letters.get(index).getSource());
        if(index == 0){
            previous.setVisibility(View.GONE);
        }else{
            previous.setVisibility(View.VISIBLE);
        }

        if(index == letters.size() -1){
            next.setVisibility(View.GONE);
        }else{
            next.setVisibility(View.VISIBLE);
        }

        if(View != null){
            Rl.removeView(View);
        }
        View = new LearnNumbersActivity.CustomView(this);

        Rl.addView(View);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(getResources()
                .getColor(android.R.color.holo_green_dark));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(100);


//        Display currentDisplay = getWindowManager().getDefaultDisplay();
//        float dw = currentDisplay.getWidth();
//        float dh = currentDisplay.getHeight();
//
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),letters.get(index))
//                .copy(Bitmap.Config.ARGB_8888, true);
//        canvas = new Canvas(bitmap);
//        paint = new Paint();
//        paint.setColor(Color.BLACK);
//        paint.setStrokeWidth(20);
//        imageView.setImageBitmap(bitmap);
//
//        imageView.setOnTouchListener(this);

    }


    private void fillLetters(){
        letters.add(new LetterHelper(R.mipmap.one,"63481a968add784aa6ac244ff4ac059b.mp3","واحد"));
        letters.add(new LetterHelper(R.mipmap.two,"89280178e332c87d7af1381e48ff9aec.mp3","اثنان"));
        letters.add(new LetterHelper(R.mipmap.three,"049b2435a620cf056859b1e04a6bc293.mp3","ثلاثه"));
        letters.add(new LetterHelper(R.mipmap.four,"a3a87f85db5d3a5da7961b19e4f0662b.mp3","اربعه"));
        letters.add(new LetterHelper(R.mipmap.five,"445b0b81c736dc40e80c3ef3fe59e454.mp3","خمسه"));
        letters.add(new LetterHelper(R.mipmap.six,"aa4201533c3a57815f86e758d7fc51f2.mp3","سته"));
        letters.add(new LetterHelper(R.mipmap.seven,"cf22285b494989ff04f1fbb815f972d0.mp3","سبعه"));
        letters.add(new LetterHelper(R.mipmap.eight,"dcac32c644ea31f49cd6b7760e59286e.mp3","ثمانيه"));
        letters.add(new LetterHelper(R.mipmap.nine,"8af9365db85d00437299d46941c1793e.mp3","تسعه"));
        letters.add(new LetterHelper(R.mipmap.zero,"2452d9624d6515a24462128ffeea0894.mp3","صفر"));
    }





    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downx = event.getX();
                downy = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                upx = event.getX();
                upy = event.getY();
                canvas.drawCircle(downx, downy, 20, paint);
                imageView.invalidate();
                downx = upx;
                downy = upy;
                break;
            case MotionEvent.ACTION_UP:
                upx = event.getX() ;
                upy = event.getY() ;
                canvas.drawCircle(downx, downy, 20, paint);
                imageView.invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }

    public class CustomView extends View {

        @SuppressWarnings("deprecation")
        public CustomView(Context c) {

            super(c);
            Display Disp = getWindowManager().getDefaultDisplay();
            DrawBitmap = Bitmap.createBitmap(Disp.getWidth(), Disp.getHeight(),
                    Bitmap.Config.ARGB_4444);

            mCanvas = new Canvas(DrawBitmap);

            mPath = new Path();
            DrawBitmapPaint = new Paint(Paint.DITHER_FLAG);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            setDrawingCacheEnabled(true);
            canvas.drawBitmap(DrawBitmap, 0, 0, DrawBitmapPaint);
            canvas.drawPath(mPath, mPaint);
            canvas.drawRect(mY, 0, mY, 0, DrawBitmapPaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);

            mCanvas.drawPath(mPath, mPaint);

            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            float[] eventXY = new float[] {eventX, eventY};

            Matrix invertMatrix = new Matrix();
            ((ImageView)imageView).getImageMatrix().invert(invertMatrix);

            invertMatrix.mapPoints(eventXY);
            int x1 = Integer.valueOf((int)eventXY[0]);
            int y1 = Integer.valueOf((int)eventXY[1]);

            Drawable imgDrawable = ((ImageView)imageView).getDrawable();
            Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();

            //Limit x, y range within bitmap
            if(x1 < 0){
                x1 = 0;
            }else if(x1 > bitmap.getWidth()-1){
                x1 = bitmap.getWidth()-1;
            }

            if(y1 < 0){
                y1 = 0;
            }else if(y1 > bitmap.getHeight()-1){
                y1 = bitmap.getHeight()-1;
            }

            int touchedRGB = bitmap.getPixel(x1, y1);

            //   Toast.makeText(StudentLettersLearningActivity.this, "touched color: " + "#" + Integer.toHexString(touchedRGB), Toast.LENGTH_SHORT).show();


            if (!Integer.toHexString(touchedRGB).equals("ffffffff")){
                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(LearnNumbersActivity.this, "Please,Draw in white area", Toast.LENGTH_SHORT).show();
                setData();
                return true;
            }

            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 11 && resultCode == RESULT_OK){
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(result.get(0).equals(letters.get(index).getCorrectPronounce())){
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
//                try {
//                    playAudio("31b99ffa0d41e1ebbc4854d74e7bf52d.mp3");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }else{
                Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
//                try {
//                    playAudio("83f8ca27e0f8363939778cab01be576b.mp3");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

}