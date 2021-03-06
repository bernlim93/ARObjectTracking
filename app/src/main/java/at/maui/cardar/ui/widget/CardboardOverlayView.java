package at.maui.cardar.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;

import java.lang.reflect.Type;

import at.maui.cardar.R;

public class CardboardOverlayView extends LinearLayout {
    private static final String TAG = CardboardOverlayView.class.getSimpleName();
    private final CardboardOverlayEyeView mLeftView;
    private final CardboardOverlayEyeView mRightView;
//    private AlphaAnimation mTextFadeAnimation;

    public CardboardOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(0, 0, 0, 0);

        mLeftView = new CardboardOverlayEyeView(context, attrs);
        mLeftView.setLayoutParams(params);
        addView(mLeftView);

        mRightView = new CardboardOverlayEyeView(context, attrs);
        mRightView.setLayoutParams(params);
        addView(mRightView);

        // Set some reasonable defaults.
        setDepthOffset(0.016f);
        setColor(Color.rgb(0, 0, 0));
        setVisibility(View.VISIBLE);

//        mTextFadeAnimation = new AlphaAnimation(1.0f, 0.0f);
//        mTextFadeAnimation.setDuration(3000);
    }

    public void show3DToast(String message) {
        setText(message);
        setTextAlpha(1f);
//        mTextFadeAnimation.setAnimationListener(new EndAnimationListener() {
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                setTextAlpha(0f);
//            }
//        });
//        startAnimation(mTextFadeAnimation);
    }

    public void show3DHUDItems(String message) {
        setHUDText(message);
        setHUDTextAlpha(1f);
    }

    public void show3DExplosion(int type) {
        setExplosion(type);
    }

    public void changeGun(int type) {
        setChangeGun(type);
    }

    private abstract class EndAnimationListener implements Animation.AnimationListener {
        @Override public void onAnimationRepeat(Animation animation) {}
        @Override public void onAnimationStart(Animation animation) {}
    }

    private void setDepthOffset(float offset) {
        mLeftView.setOffset(offset);
        mRightView.setOffset(-offset);
    }

    private void setText(String text) {
        mLeftView.setText(text);
        mRightView.setText(text);
    }

    private void setTextAlpha(float alpha) {
        mLeftView.setTextViewAlpha(alpha);
        mRightView.setTextViewAlpha(alpha);
    }

    private void setHUDText(String text) {
        mLeftView.setHUDText(text);
        mRightView.setHUDText(text);
    }

    private void setHUDTextAlpha(float alpha) {
        mLeftView.setHUDTextViewAlpha(alpha);
        mRightView.setHUDTextViewAlpha(alpha);
    }

    private void setExplosion(int type) {
        mLeftView.setViewExplosion(type);
        mRightView.setViewExplosion(type);
    }

    private void setChangeGun(int type) {
        mLeftView.setViewGun(type);
        mRightView.setViewGun(type);
    }

    private void setColor(int color) {
        mLeftView.setColor(color);
        mRightView.setColor(color);
    }

    /**
     * A simple view group containing some horizontally centered text underneath a horizontally
     * centered image.
     *
     * This is a helper class for CardboardOverlayView.
     */
    private class CardboardOverlayEyeView extends ViewGroup {
        private final ImageView imageView;
        private final ImageView explodeView;
        private final TextView textView;
        private final TextView HUDView;
        private float offset;

        public CardboardOverlayEyeView(Context context, AttributeSet attrs) {
            super(context, attrs);
            imageView = new ImageView(context, attrs);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setAdjustViewBounds(true);  // Preserve aspect ratio.
            addView(imageView);

            explodeView = new ImageView(context, attrs);
            explodeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            explodeView.setAdjustViewBounds(true);  // Preserve aspect ratio.
            addView(explodeView);


            textView = new TextView(context, attrs);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 8.0f);
            textView.setTypeface(Typeface.MONOSPACE, Typeface.NORMAL);
            textView.setGravity(Gravity.RIGHT);
            textView.setShadowLayer(3.0f, 0.0f, 0.0f, Color.DKGRAY);
            addView(textView);

            HUDView = new TextView(context, attrs);
            HUDView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 6.0f);
            HUDView.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
            HUDView.setGravity(Gravity.LEFT);
            HUDView.setShadowLayer(3.0f, 0.0f, 0.0f, Color.DKGRAY);
            addView(HUDView);
        }

        public void setColor(int color) {
            imageView.setImageResource(0);
            textView.setTextColor(color);
            HUDView.setTextColor(color);
        }

        public void setText(String text) {
            textView.setText(text);
        }

        public void setTextViewAlpha(float alpha) {
            textView.setAlpha(alpha);
        }

        public void setHUDText(String text) {
            HUDView.setText(text);
        }

        public void setHUDTextViewAlpha(float alpha) {
            HUDView.setAlpha(alpha);
        }

        public void setViewExplosion(int type) {
            if(type ==0)
                explodeView.setImageResource(R.drawable.info_card);
            else if(type==1)
                explodeView.setImageResource(0);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    explodeView.setImageResource(R.drawable.blank);
                }
            }, 500);
        }

        public void setViewGun(int type) {
            if(type==1)
                imageView.setImageResource(R.drawable.info_card);
            else if(type==0)
                imageView.setImageResource(0);
        }

        public void setOffset(float offset) {
            this.offset = offset;
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            // Width and height of this ViewGroup.
            final int width = right - left;
            final int height = bottom - top;

            // The size of the image, given as a fraction of the dimension as a ViewGroup. We multiply
            // both width and heading with this number to compute the image's bounding box. Inside the
            // box, the image is the horizontally and vertically centered.
            final float imageSize = 0.3f;

            // The fraction of this ViewGroup's height by which we shift the image off the ViewGroup's
            // center. Positive values shift downwards, negative values shift upwards.
            final float verticalImageOffset = -0.07f;

            // Vertical position of the text, specified in fractions of this ViewGroup's height.
            final float verticalTextPos = 0.52f;

            // Layout ImageView
            float imageMargin = (1.0f - imageSize) / 2.0f;
            float leftMargin = - 240 + (int) (width * (imageMargin + offset));
            float topMargin = - 330 + (int) (height * (imageMargin + verticalImageOffset) + 400);
            imageView.layout(
                    (int) leftMargin, (int) topMargin,
                    (int) (leftMargin + width * imageSize), (int) (topMargin + height * imageSize));

            // Explosion gif
            float exImageMargin = (1.0f - imageSize) / 2.0f;
            float exLeftMargin = (int) (width * (exImageMargin + offset));
            float exTopMargin = (int) (height * (exImageMargin + verticalImageOffset) + 400);
            explodeView.layout(
                    (int) exLeftMargin, (int) exTopMargin,
                    (int) (exLeftMargin + width * imageSize), (int) (exTopMargin + height * imageSize));

            // Layout TextView
            leftMargin = - 260 + offset * width;
            topMargin = height * verticalTextPos - 150;
            textView.layout(
                    (int) leftMargin, (int) topMargin,
                    (int) (leftMargin + width), (int) (topMargin + height * (1.0f - verticalTextPos)));

            // Layout HUDView
            leftMargin = 350 + offset * width;
            topMargin = height * verticalTextPos - 120;
            HUDView.layout(
                    (int) leftMargin, (int) topMargin,
                    (int) (leftMargin + width), (int) (topMargin + height * (1.0f - verticalTextPos)));
        }
    }
}