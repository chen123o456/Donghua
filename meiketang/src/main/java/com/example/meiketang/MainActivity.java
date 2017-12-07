package com.example.meiketang;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView aihaozhe;
    private ImageView meiyiren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        aihaozhe = (ImageView) findViewById(R.id.aihaozhe);
        meiyiren = (ImageView) findViewById(R.id.meiyiren);

        ObjectAnimator meiyirenX = ObjectAnimator.ofFloat(meiyiren,"translationX",0f,-150f);
        ObjectAnimator aihaozheX = ObjectAnimator.ofFloat(aihaozhe,"translationX",0f,150f);
        meiyirenX.start();
        aihaozheX.start();
    }

    public void meiyiren(View view){

    }

    public void aihaozhe(View view){

    }
}
