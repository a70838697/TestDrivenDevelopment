package com.casper.testDrivenDevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImageViewMainActivity extends AppCompatActivity {

    private Button buttonPrevious,buttonNext;
    private ImageView imageViewFunny;
    private int[] imageIDArray={R.drawable.funny_1,R.drawable.funny_2
            ,R.drawable.funny_3,R.drawable.funny_4,R.drawable.funny_5
            ,R.drawable.funny_6
    };
    private int imageIDArrayCurrentIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_main);

        buttonPrevious= findViewById(R.id.button_previous);
        buttonNext=findViewById(R.id.button_next);
        imageViewFunny=findViewById(R.id.image_view_funny);

        buttonPrevious.setOnClickListener(new MyButtonClickListener());
        buttonNext.setOnClickListener(new MyButtonClickListener());

    }

    private class MyButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if(view==buttonNext)
            {
                imageIDArrayCurrentIndex=(imageIDArrayCurrentIndex+1)%imageIDArray.length;
            }
            else {
                imageIDArrayCurrentIndex=(imageIDArrayCurrentIndex+imageIDArray.length-1)%imageIDArray.length;
            }
            imageViewFunny.setImageResource(imageIDArray[imageIDArrayCurrentIndex]);
        }
    }
}
