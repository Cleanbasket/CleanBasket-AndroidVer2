package com.bridge4biz.laundry.cleanbasket_androidver2.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;

/**
 * Created by gingeraebi on 2016. 8. 3..
 */
public class AnimationUtil {

    private Context context;
    private WindowManager windowManager;
    private int windowHeight;
    private int windowWidth;

    public AnimationUtil(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.windowHeight = windowManager.getDefaultDisplay().getHeight();
        this.windowWidth = windowManager.getDefaultDisplay().getWidth();
    }

    public void moveViewToRightAndGone(View view) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, windowWidth, 0, 0);
        translateAnimation.setDuration(300);
        view.startAnimation(translateAnimation);
        view.setVisibility(View.GONE);
        Log.e("애니메이션","오른쪽으로 움직인다.");
    }

    public void moveViewToLeftAndGone(View view) {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, -windowWidth, 0, 0);
        translateAnimation.setDuration(300);
        view.startAnimation(translateAnimation);
        view.setVisibility(View.GONE);
        Log.e("애니메이션","오른쪽으로 움직인다.");
    }

    public void moveViewToRightAndShow(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation translateAnimation = new TranslateAnimation(-windowWidth, 0, 0, 0);
        translateAnimation.setDuration(300);
        view.startAnimation(translateAnimation);

        Log.e("애니메이션","오른쪽으로 움직인다.");
    }

    public void moveViewToLeftAndShow(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation translateAnimation = new TranslateAnimation(windowWidth, 0, 0, 0);
        translateAnimation.setDuration(300);
        view.startAnimation(translateAnimation);

        Log.e("애니메이션","오른쪽으로 움직인다.");
    }

}
