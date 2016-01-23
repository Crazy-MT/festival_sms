package com.maomao.transitiondrawable;

import android.content.res.Resources;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView img_transition ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = this.getResources();
        TransitionDrawable transition = (TransitionDrawable)
                res.getDrawable(R.drawable.transitiondrawable);


        img_transition = (ImageView) findViewById(R.id.img_transition);
        img_transition.setImageDrawable(transition);
        transition.startTransition(3000);
    }
}
