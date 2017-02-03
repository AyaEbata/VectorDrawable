package com.aya.vectordrawable;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.TextView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeTextViewImageTint();

        ImageView imageView = (ImageView) findViewById(R.id.animated_vector_drawable);
        imageView.setOnClickListener(view -> startAnimation(imageView));
    }

    private void changeTextViewImageTint() {
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_cat_black_24dp);  // これをそのまま使うと猫のvector画像全部色が変わっちゃう
        Drawable wrapDrawable = DrawableCompat.wrap(drawable.mutate().getConstantState().newDrawable());  // だからクローンを作ってあげて、
        DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(this, R.color.colorAccent));  //  色を変えてあげて、
        TextView textView = (TextView) findViewById(R.id.text_view_tint);
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(wrapDrawable, null, null, null);  // textViewの左側にdrawableつけてあげればできるよっ
    }

    private void startAnimation(ImageView animatedVectorDrawable) {
        // ごめん、丸パクリ
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // ImageViewがselect状態かどうかで判定してセットするAnimatedDrawableを決める
            int drawableResId = animatedVectorDrawable.isSelected()
                    ? R.drawable.avd_pause_to_play
                    : R.drawable.avd_play_to_pause;

            // Drawableをセットする。setImageResource()だと動かないので注意
            Drawable drawable = getDrawable(drawableResId);
            animatedVectorDrawable.setImageDrawable(drawable);

            if (drawable instanceof Animatable) {
                Animatable animatable = (Animatable) drawable;
                // アニメーション中だったらいったん止める
                if (animatable.isRunning()) {
                    animatable.stop();
                }
                // アニメーションを開始
                animatable.start();
            }

            // 次にタップしたら逆方向のアニメーションを実行するためにselect状態を切り替える
            animatedVectorDrawable.setSelected(!animatedVectorDrawable.isSelected());
        }
    }

}
