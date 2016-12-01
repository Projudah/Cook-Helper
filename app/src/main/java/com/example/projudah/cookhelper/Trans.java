package com.example.projudah.cookhelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static com.example.projudah.cookhelper.R.*;

/**
 * Created by Projudah on 2016-11-23.
 */
public class Trans{

    public static void out(RelativeLayout v,final Activity thiss, Class next){
        android.os.Handler h = new android.os.Handler();
        AlphaAnimation start = new AlphaAnimation(1.0f,0.0f);
        start.setDuration(700);
        start.setFillAfter(true);
        v.startAnimation(start);

        final Intent home = new Intent(thiss, next);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                thiss.startActivity(home);
                thiss.overridePendingTransition(0, 0);
                thiss.finish();
            }
        },700);
    }

    public static void outback(final RelativeLayout v,final Activity thiss, Class next){
        final int temp2 = layout.animation;
        android.os.Handler h = new android.os.Handler();
        AlphaAnimation start = new AlphaAnimation(1.0f,0.0f);
        start.setDuration(500);
        start.setFillAfter(true);
        v.startAnimation(start);

        final Intent home = new Intent(thiss, next);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                thiss.startActivity(home);
                thiss.overridePendingTransition(0, 0);


            }
        },500);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.clearAnimation();

            }
        },2000);
    }

    public static void back(RelativeLayout v,final Activity thiss){
        android.os.Handler h = new android.os.Handler();
        AlphaAnimation start = new AlphaAnimation(1.0f,0.0f);
        start.setDuration(1000);
        start.setFillAfter(false);
        v.startAnimation(start);

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                thiss.setContentView(R.layout.animation);
                LinearLayout page = (LinearLayout) thiss.findViewById(R.id.lay);
                AnimationDrawable animation = (AnimationDrawable) page.getBackground();
                animation.start();
            }
        },1000);
    }

    public static void animatein(final Activity thiss,final RelativeLayout Root){
        final RelativeLayout transition = new RelativeLayout(thiss);
        transition.setBackground(thiss.getDrawable(drawable.animatein));
        Root.addView(transition);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);
        transition.setLayoutParams(params);
        Root.bringChildToFront(transition);
        AnimationDrawable animation = (AnimationDrawable) transition.getBackground();
        animation.start();
        android.os.Handler h = new android.os.Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((ViewGroup)transition.getParent()).removeView(transition);
            }
        },540);
    }

    public static void fadein(LinearLayout Root) {
        Root.setVisibility(View.VISIBLE);
        //Root.setAlpha(0);
        AlphaAnimation start = new AlphaAnimation(0.0f, 1.0f);
        start.setDuration(500);
        start.setFillAfter(true);
        Root.startAnimation(start);
    }

    public static void refresh(final Activity thiss, Class next){
        final Intent home = new Intent(thiss, next);
        thiss.startActivity(home);
        thiss.overridePendingTransition(0, 0);
        thiss.finish();
    }

}
