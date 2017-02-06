package com.aya.vectordrawable;

import android.os.Bundle;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeTextViewImageTint();
    }

    private void changeTextViewImageTint() {
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_cat_black_24dp);  // これをそのまま使うと猫のvector画像全部色が変わっちゃう
        Drawable wrapDrawable = DrawableCompat.wrap(drawable.mutate().getConstantState().newDrawable());  // だからクローンを作ってあげて、
        DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(this, R.color.colorAccent));  //  色を変えてあげて、
        TextView textView = (TextView) findViewById(R.id.text_view_tint);
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(wrapDrawable, null, null, null);  // textViewの左側にdrawableつけてあげればできるよっ
    }

}
