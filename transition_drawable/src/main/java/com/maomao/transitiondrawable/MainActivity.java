package com.maomao.transitiondrawable;

import android.content.res.Resources;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ToggleButton;

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

        final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.tb);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionDrawable drawable = (TransitionDrawable) img_transition.getDrawable();
                if (toggleButton.isChecked()) {
                    drawable.startTransition(500);
                } else {
                    drawable.reverseTransition(500);
                }
            }
        });
    }


}
