package com.koper.timetracker.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.ImageView;

import com.koper.timetracker.R;

/**
 * Created by Matus on 07.02.2016.
 */
public class CustomAnimationUtils {

    public static void animateCardViewBackgroundToColor(final CardView aTargetView, int aOldColor, final int aTargetColor) {
        final float[] from = new float[3],
                to = new float[3];


        Color.colorToHSV(aOldColor, from);   // from white
        Color.colorToHSV(aTargetColor, to);     // to red

        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);   // animate from 0 to 1
        anim.setDuration(600);                              // for 600 ms

        final float[] hsv = new float[3];                  // transition color
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Transition along each axis of HSV (hue, saturation, value)
                hsv[0] = from[0] + (to[0] - from[0]) * animation.getAnimatedFraction();
                hsv[1] = from[1] + (to[1] - from[1]) * animation.getAnimatedFraction();
                hsv[2] = from[2] + (to[2] - from[2]) * animation.getAnimatedFraction();

//                aTargetView.setBackgroundColor(Color.HSVToColor(hsv));
                aTargetView.setCardBackgroundColor(Color.HSVToColor(hsv));
            }
        });

        anim.start();
    }

    public static void animateBackgroundToColor(final View aTargetView, int aOldColor, final int aTargetColor) {
        final float[] from = new float[3],
                to = new float[3];

        Color.colorToHSV(aOldColor, from);   // from white
        Color.colorToHSV(aTargetColor, to);     // to red

        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);   // animate from 0 to 1
        anim.setDuration(600);                              // for 300 ms

        final float[] hsv = new float[3];                  // transition color
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Transition along each axis of HSV (hue, saturation, value)
                hsv[0] = from[0] + (to[0] - from[0]) * animation.getAnimatedFraction();
                hsv[1] = from[1] + (to[1] - from[1]) * animation.getAnimatedFraction();
                hsv[2] = from[2] + (to[2] - from[2]) * animation.getAnimatedFraction();

//                aTargetView.setBackgroundColor(Color.HSVToColor(hsv));
                aTargetView.setBackgroundColor(Color.HSVToColor(hsv));
            }
        });

        anim.start();
    }

    public static void animateEditTextHintColor(final EditText aTargetView, int aOldColor, final int aTargetColor) {
        final float[] from = new float[3],
                to = new float[3];

        Color.colorToHSV(aOldColor, from);   // from white
        Color.colorToHSV(aTargetColor, to);     // to red

        ValueAnimator anim = ValueAnimator.ofFloat(0, 1);   // animate from 0 to 1
        anim.setDuration(600);                              // for 300 ms

        final float[] hsv = new float[3];                  // transition color
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Transition along each axis of HSV (hue, saturation, value)
                hsv[0] = from[0] + (to[0] - from[0]) * animation.getAnimatedFraction();
                hsv[1] = from[1] + (to[1] - from[1]) * animation.getAnimatedFraction();
                hsv[2] = from[2] + (to[2] - from[2]) * animation.getAnimatedFraction();

//                aTargetView.setBackgroundColor(Color.HSVToColor(hsv));
                aTargetView.setHintTextColor(Color.HSVToColor(hsv));
            }
        });

        anim.start();
    }

    public static void animateToImageDrawable(final ImageView aTargetView, final Drawable aTargetDrawable) {
        ObjectAnimator mDownX = ObjectAnimator.ofFloat(aTargetView, "scaleX", 1f, 0f).setDuration(300);
        ObjectAnimator mDownY = ObjectAnimator.ofFloat(aTargetView, "scaleY", 1f, 0f).setDuration(300);
        ObjectAnimator mFadeOut = ObjectAnimator.ofFloat(aTargetView, "alpha", 1f, 0f).setDuration(250);

        final ObjectAnimator mUpX = ObjectAnimator.ofFloat(aTargetView, "scaleX", 0f, 1f).setDuration(300);
        final ObjectAnimator mUpY = ObjectAnimator.ofFloat(aTargetView, "scaleY", 0f, 1f).setDuration(300);

        mDownX.setInterpolator(new AnticipateOvershootInterpolator());
        mDownY.setInterpolator(new AnticipateOvershootInterpolator());
        mUpX.setInterpolator(new AnticipateOvershootInterpolator());
        mUpY.setInterpolator(new AnticipateOvershootInterpolator());

        mDownX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                aTargetView.setImageDrawable(aTargetDrawable);
                aTargetView.setAlpha(1f);
                AnimatorSet mScaleUp = new AnimatorSet();
                mScaleUp.play(mUpX).with(mUpY);
                mScaleUp.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        AnimatorSet mScaleDown = new AnimatorSet();

        mScaleDown.play(mDownX).with(mDownY).with(mFadeOut);
        mScaleDown.start();


//        Animation mOut = AnimationUtils.loadAnimation(aContext, android.R.anim.fade_out);
//        final Animation mIn = AnimationUtils.loadAnimation(aContext, android.R.anim.fade_in);
//
//        mOut.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {}
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                aTargetView.setImageDrawable(aTargetDrawable);
//                aTargetView.startAnimation(mIn);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {}
//        });
//
//        aTargetView.startAnimation(mOut);
    }

}
