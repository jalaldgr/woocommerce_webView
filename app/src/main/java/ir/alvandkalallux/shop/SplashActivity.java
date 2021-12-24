package ir.alvandkalallux.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashActivity extends AppCompatActivity {
    ImageView logo ;
    LinearLayoutCompat titleLaout;
    Animation topanim,btmanim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.SplashImgView);
        titleLaout = findViewById(R.id.SplashTitleLayout);

        topanim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        btmanim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        //set animation to image and text
        logo.setAnimation(topanim);
        titleLaout.setAnimation(btmanim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        },1500);
    }
}