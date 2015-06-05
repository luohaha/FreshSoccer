package com.example.root.freshsoccernews;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.FontAwesomeText;

/**
 * Created by YIxin on 15-4-1.
 */
public class HeadBar extends FrameLayout {

    private BootstrapButton leftButton;
    // private Button rightButton;
    private BootstrapButton rightButton;
    private BootstrapButton rightSecondButton;
    private BootstrapButton leftSecondButton;
    private TextView titleText;

    int sdk;
    public HeadBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        sdk = Build.VERSION.SDK_INT;
        LayoutInflater.from(context).inflate(R.layout.head_title, this);
        titleText = (TextView) findViewById(R.id.title_text);
        leftButton = (BootstrapButton) findViewById(R.id.button_left);
        rightButton = (BootstrapButton) findViewById(R.id.button_right);
        rightSecondButton = (BootstrapButton) findViewById(R.id.button_right_second);
        leftSecondButton = (BootstrapButton) findViewById(R.id.button_left_second);
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }
    public void setTitleVisiable() {
        titleText.setVisibility(View.VISIBLE);
    }
    public void setTitleNoVisiable() {
        titleText.setVisibility(View.GONE);
    }
    public void setTitleText(String text) {
        titleText.setText(text);
    }

    //class for setting left button for topbar
    public void setLeftButtonText(String text) {
        leftButton.setText(text);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setLeftButtonBackground(Drawable drawable) {
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            leftButton.setBackgroundDrawable(drawable);
        }
        else {
            leftButton.setBackground(drawable);
        }
    }
    public void setLeftButtonNoused() {
        leftButton.setClickable(false);
        leftButton.setVisibility(View.GONE);
    }
    public void setLeftButtonListener(OnClickListener l) {
        leftButton.setOnClickListener(l);
    }

    //class for setting right button in topbar
    public void setRightButtonText(String text) {
        rightButton.setText(text);
    }

    /*
    * stop show right button
    * */
    public void setRightButtonNoused() {
        rightButton.setClickable(false);
        rightButton.setVisibility(View.GONE);
    }
    /*
    * set right button listener
    * */
    public void setRightButtonListener(OnClickListener l) {
        rightButton.setOnClickListener(l);
    }
    /**
     * stop show right second button
     * */
    public void setRightSecondButtonNoused() {
        rightSecondButton.setClickable(false);
        rightSecondButton.setVisibility(View.GONE);
    }
    /**
     * set right second button's listener
     * */
    public void setRightSecondButtonListener(OnClickListener l) {
        rightSecondButton.setOnClickListener(l);
    }

    /**
     * stop show left second button
     * */
    public void setLeftSecondButtonNoused() {
        leftSecondButton.setClickable(false);
        leftSecondButton.setVisibility(View.GONE);
    }
    /**
     * set left second button's listener
     * */
    public void setLeftSecondButton(OnClickListener listener) {
        leftSecondButton.setOnClickListener(listener);
    }
}

