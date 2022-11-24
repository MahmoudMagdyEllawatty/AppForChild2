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
import android.speech.tts.TextToSpeech;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.games.R;
import com.app.games.utils.LetterHelper;
import com.app.games.utils.LocaleManager;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Locale;

public class LearnLettersActivity extends AppCompatActivity
        implements android.view.View.OnTouchListener, TextToSpeech.OnInitListener {

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
    CustomView View;
    Paint mPaint;

    private String baseSpeakURL = "https://ttsmp3.com/created_mp3/";

    private MediaPlayer mediaPlayer;

    Button speak;
    TextToSpeech t1;
    RadioGroup group;
    RadioButton arabicLetters,englishLetters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_numbers);
        Rl = findViewById(R.id.rel);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setTitle(getString(R.string.learn_letters));


        group = findViewById(R.id.group);
        group.setVisibility(android.view.View.VISIBLE);
        arabicLetters = findViewById(R.id.arabic_letters);
        englishLetters = findViewById(R.id.english_letters);

        arabicLetters.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    letters = new ArrayList<>();
                    fillLetters();

                    index = 0;
                    setData();
                }
            }
        });


        englishLetters.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    letters = new ArrayList<>();
                    fillEnglishLetters();

                    index = 0;
                    setData();
                }
            }
        });

        imageView = findViewById(R.id.image);


        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        play = findViewById(R.id.play);
        speak = findViewById(R.id.speak);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                try {
                    playAudio(letters.get(index).getUrl());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LearnLettersActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                if(arabicLetters.isChecked()){
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL
                            ,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ar-JO");
                    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"tk.oryx.voice");
                    startActivityForResult(intent,11);
                }else if(englishLetters.isChecked()){
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL
                            ,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    startActivityForResult(intent,11);
                }

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

        t1 = new TextToSpeech(this,this);
    }

    private void playAudio(String url) throws Exception
    {
        if(arabicLetters.isChecked()){
            killMediaPlayer();

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(baseSpeakURL+url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }else if(englishLetters.isChecked()){
            t1.speak(url,TextToSpeech.QUEUE_FLUSH,null,"");
        }

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
    protected void onPause() {
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        killMediaPlayer();
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
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
        View = new CustomView(this);

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
        letters.add(new LetterHelper(R.mipmap.a,"9bd675bfe77c34335706da82fab804ee.mp3","الف"));
        letters.add(new LetterHelper(R.mipmap.b,"05d9918647aaeda7b6c4ace85291b270.mp3","باء"));
        letters.add(new LetterHelper(R.mipmap.c,"79d20f9ee7eec65f952a9bdfccc69c1e.mp3","تاء"));
        letters.add(new LetterHelper(R.mipmap.d,"ae48c27db5b22e31249dc21cfdd4848f.mp3","ثاء"));
        letters.add(new LetterHelper(R.mipmap.e,"bcaeec92a9aadf3d775d91a92dfc3e4b.mp3","جيم"));
        letters.add(new LetterHelper(R.mipmap.f,"f0f24596d1094fbdbcede197fbab9e6b.mp3","حاء"));
        letters.add(new LetterHelper(R.mipmap.g,"c2e02c919c100ec843a19f31b9cfa39c.mp3","خاء"));
        letters.add(new LetterHelper(R.mipmap.h,"b71b3dec8c38457f286a882ff32d83fb.mp3","دال"));
        letters.add(new LetterHelper(R.mipmap.i,"b09e436c5fe927502fe6c77b6e974b9f.mp3","ذال"));
        letters.add(new LetterHelper(R.mipmap.j,"e9aecfc53dceb4c5d2f4705abe989158.mp3","راء"));
        letters.add(new LetterHelper(R.mipmap.k,"133d122e01e2c6e8fd7bf572b8195b10.mp3","زي"));
        letters.add(new LetterHelper(R.mipmap.l,"7ebe770864e7aec6207fb16950293676.mp3","سين"));
        letters.add(new LetterHelper(R.mipmap.m,"d707f20f41859d99d3714ec97d9140db.mp3","شين"));
        letters.add(new LetterHelper(R.mipmap.n,"b25fcb1901becc916b157b7bedc4e735.mp3","صاد"));
        letters.add(new LetterHelper(R.mipmap.o,"af52c8a1ec63569c7bd9daf88e5047d6.mp3","ضاد"));
        letters.add(new LetterHelper(R.mipmap.p,"701767f44d56687b836daff0eeb03038.mp3","طاء"));
        letters.add(new LetterHelper(R.mipmap.q,"89bbe912275d6b5a0edc16befd02eedb.mp3","ظاء"));
        letters.add(new LetterHelper(R.mipmap.r,"1d6a1c727b6c9474429229cba661bd7b.mp3","عين"));
        letters.add(new LetterHelper(R.mipmap.s,"67a29152b961f5f17a56c4b558438a32.mp3","غين"));
        letters.add(new LetterHelper(R.mipmap.v,"5dfc0415911860a735278f72cc52fbd7.mp3","فاء"));
        letters.add(new LetterHelper(R.mipmap.u,"49e035cc4e7c86d3dfa73877a6788789.mp3","قاف"));
        letters.add(new LetterHelper(R.mipmap.t,"5acc7ba766149e429270abc8da82db72.mp3","كاف"));
        letters.add(new LetterHelper(R.mipmap.x,"8c0907bcba7bfb41fda42f595b50592d.mp3","لام"));
        letters.add(new LetterHelper(R.mipmap.w,"265daad4177f953a6ca712121ca57fd0.mp3","ميم"));
        letters.add(new LetterHelper(R.mipmap.y,"ee3b6d7c1559609e6dccece2e0262d62.mp3","نون"));
        letters.add(new LetterHelper(R.mipmap.zz,"fa0a406e2f248e238124b3e934bbf357.mp3","هاء"));
        letters.add(new LetterHelper(R.mipmap.z,"0b8c156da81c732cc6f966e50452418d.mp3","واو"));
        letters.add(new LetterHelper(R.mipmap.zzz,"adb76f7a6628420dd85aaf9281ec8510.mp3","ياء"));
    }

    private void fillEnglishLetters(){
        letters.add(new LetterHelper(R.mipmap.a1,"a","a"));
        letters.add(new LetterHelper(R.mipmap.b1,"b","b"));
        letters.add(new LetterHelper(R.mipmap.c1,"c","c"));
        letters.add(new LetterHelper(R.mipmap.d1,"d","d"));
        letters.add(new LetterHelper(R.mipmap.e1,"e","e"));
        letters.add(new LetterHelper(R.mipmap.f1,"f","f"));
        letters.add(new LetterHelper(R.mipmap.g1,"g","g"));
        letters.add(new LetterHelper(R.mipmap.h1,"h","h"));
        letters.add(new LetterHelper(R.mipmap.i1,"i","i"));
        letters.add(new LetterHelper(R.mipmap.j1,"j","j"));
        letters.add(new LetterHelper(R.mipmap.k1,"k","k"));
        letters.add(new LetterHelper(R.mipmap.l1,"l","l"));
        letters.add(new LetterHelper(R.mipmap.m1,"m","m"));
        letters.add(new LetterHelper(R.mipmap.n1,"n","n"));
        letters.add(new LetterHelper(R.mipmap.o1,"o","o"));
        letters.add(new LetterHelper(R.mipmap.p1,"p","p"));
        letters.add(new LetterHelper(R.mipmap.q1,"q","q"));
        letters.add(new LetterHelper(R.mipmap.r1,"r","r"));
        letters.add(new LetterHelper(R.mipmap.s1,"s","s"));
        letters.add(new LetterHelper(R.mipmap.t1,"t","t"));
        letters.add(new LetterHelper(R.mipmap.u1,"u","u"));
        letters.add(new LetterHelper(R.mipmap.v1,"v","v"));
        letters.add(new LetterHelper(R.mipmap.w1,"w","w"));
        letters.add(new LetterHelper(R.mipmap.x1,"x","x"));
        letters.add(new LetterHelper(R.mipmap.y1,"y","y"));
        letters.add(new LetterHelper(R.mipmap.z1,"z","z"));
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

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result = t1.setLanguage(Locale.US);

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Init Failed", Toast.LENGTH_SHORT).show();
        }
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
                Toast.makeText(LearnLettersActivity.this, "Please,Draw in white area", Toast.LENGTH_SHORT).show();
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