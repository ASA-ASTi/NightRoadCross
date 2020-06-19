package com.asaasti.nightroadcross;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.animation.ValueAnimator;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity
        implements GestureDetector.OnGestureListener
{

    private AlertDialog DialogueBox;
    private ImageButton imageButton;
    private ImageView imageView,imageView2;
    private GestureDetectorCompat mDetector;
    private int brightness=0;
    private Context context;
    //Keys
    private static String db="NightTrafficCross";
    private static String Orientation="Orientation";
    private static String Agreement="Agreement";
    private static String Tutorial="Tutorial";
    private SharedPreferences sharedPreferences;
    private MainActivity Agent;

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialise
        Agent=new MainActivity();
        Agent.imageButton=findViewById(R.id.help);
        Agent.imageView=findViewById(R.id.show_);
        Agent.imageView2=findViewById(R.id.show_2);

        Agent.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.System.putInt(context.getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS, brightness);
                Intent r=new Intent(MainActivity.this,Help.class);
                startActivity(r);
                finish();
            }
        });
        //SCREEN_BRIGHTNESS

        context = getApplicationContext();
        if(checkSystemWritePermission()){
            try {
                brightness=android.provider.Settings.System.getInt(
                        getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
                Settings.System.putInt(context.getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS, 255);
            }
            catch (Exception e){

            }

        }
        Agent.sharedPreferences=getSharedPreferences(db, MODE_PRIVATE);
        Agent.editor = Agent.sharedPreferences.edit();
        Agent.imageView.setScaleX(LoadOrientation());
        Agent.imageView2.setScaleX(LoadOrientation());

        if(LoadOrientation()==1){
            Animation(1.0f,0.0f);
        }
        else {
            Animation(0.0f,1.0f);
        }
        mDetector = new GestureDetectorCompat(this,this);
        if(LoadLoadAgreement()==0){
            Agreement();
        }
        Theme();

    }



    //Orientation
    private int LoadOrientation( ){
        return Agent.sharedPreferences.getInt(Orientation,1);
    }
    //LoadAgreement
    private int LoadLoadAgreement( ){
        return Agent.sharedPreferences.getInt(Agreement,0);

    }

    //LoadTutorial
    private int LoadTutorial( ){
        return Agent.sharedPreferences.getInt(Tutorial,0);
    }
    //ThemeChanges
    private void Theme( ){
        int k=Agent.sharedPreferences.getInt("theme",1);
        LinearLayout bg=(LinearLayout)findViewById(R.id.bg_);
        switch (k){
            case 1:

                Agent.imageView.setImageDrawable(getResources().getDrawable(R.drawable.trafficlight));
                Agent.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.trafficlight));

                bg.setBackgroundColor(Color.YELLOW);
                break;
            case 2:
                Agent.imageView.setImageDrawable(getResources().getDrawable(R.drawable.trafficlight2));
                Agent.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.trafficlight2));

                bg.setBackgroundColor(Color.BLACK);
                break;
            case 3:
                Agent.imageView.setImageDrawable(getResources().getDrawable(R.drawable.trafficlight3));
                Agent.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.trafficlight3));

                bg.setBackgroundColor(Color.BLUE);
                break;
            case 4:
                Agent.imageView.setImageDrawable(getResources().getDrawable(R.drawable.trafficlight4));
                Agent.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.trafficlight4));

                bg.setBackgroundColor(Color.RED);
                break;
        }


    }

    //Animation For App
    private void Animation(float a,float b){
        final ValueAnimator animator = ValueAnimator.ofFloat(a, b);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1200L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = Agent.imageView.getWidth();
                final float translationX = width * progress;
                Agent.imageView.setTranslationX(translationX);
                Agent.imageView2.setTranslationX(translationX - width);
            }
        });
        animator.start();

    }
    //Changing Brighness On FocusChange
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
            if (checkSystemWritePermission()) {
                try {

                    Settings.System.putInt(context.getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS, 255);
                } catch (Exception e) {

                }
            }
        }
        else {
            if (checkSystemWritePermission()) {


                try {

                    Settings.System.putInt(context.getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS, brightness);
                    //Toast.makeText(MainActivity.this,"Focus",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                }
            }

        }



    }
    //Hiding System UI
    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    //Checking Permission
    private boolean checkSystemWritePermission( ) {
        boolean retVal = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(this);
            // Log.d(TAG, "Can Write Settings: " + retVal);
            if(retVal){
                // Toast.makeText(this, "Write allowed :-)", Toast.LENGTH_LONG).show();
            }else{
                // Toast.makeText(this, "Write not allowed :-(", Toast.LENGTH_LONG).show();
                FragmentManager fm = getFragmentManager();
                // PopupWritePermission dialogFragment = new PopupWritePermission();
                // dialogFragment.show(fm, getString(R.string.popup_writesettings_title));
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivity(intent);
            }
        }
        return retVal;
    }
    @Override
    public void onShowPress(MotionEvent event) {
        // Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }
    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        //Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }
    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        //Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        return true;
    }
    @Override
    public void onLongPress(MotionEvent event) {
        //Log.d(DEBUG_TAG, "onLongPress: " + event.toString());

        //Changing Orientation
        if(LoadOrientation()==-1){
            Agent.imageView.setScaleX(1);
            Agent.imageView2.setScaleX(1);
            Animation(1.0f,0.0f);
            Agent.editor.putInt(Orientation,1);
            Agent.editor.commit();

        }
        else {
            Agent.imageView.setScaleX(-1);
            Agent.imageView2.setScaleX(-1);
            Animation(0.0f,1.0f);
            Agent.editor.putInt(Orientation,-1);
            Agent.editor.commit();
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }
    @Override
    public boolean onDown(MotionEvent event) {
        // Log.d(DEBUG_TAG,"onDown: " + event.toString());
        return true;
    }
    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        //Log.d(DEBUG_TAG, "onScroll: " + event1.toString() + event2.toString());

        return true;
    }

    //Agreement DialogBox
    private void Agreement()
    {

        AlertDialog.Builder builder0=new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.agreementlayout, null);
        Button Agree=dialogLayout.findViewById(R.id.ia);
        Agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agent.editor.putInt("Agreement",1);
                Agent.editor.commit();
                DialogueBox.dismiss();

                if(LoadTutorial()==0){

                    //Tutorial Code
                    AlertDialog.Builder builder0=new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogLayout = inflater.inflate(R.layout.tutoriallayout, null);
                    builder0.setView(dialogLayout);
                    builder0.setCancelable(true);
                    DialogueBox=builder0.create();

                    DialogueBox.getWindow().setGravity(Gravity.CENTER);
                    DialogueBox.show();

                }
            }
        });


        builder0.setView(dialogLayout);
        builder0.setCancelable(false);
        DialogueBox=builder0.create();

        DialogueBox.getWindow().setGravity(Gravity.CENTER);
        DialogueBox.show();

    }

}










