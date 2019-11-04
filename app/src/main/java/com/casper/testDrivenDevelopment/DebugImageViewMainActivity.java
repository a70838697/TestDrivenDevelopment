package com.casper.testDrivenDevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DebugImageViewMainActivity extends AppCompatActivity {

    private Button buttonPrevious = (Button)findViewById(R.id.button_previous), buttonNext =(Button) findViewById(R.id.button_hello_en);
    private ImageView imageViewFunny = (ImageView)findViewById(R.id.image_view_funny);
    private int[] imageIDArray = {R.drawable.funny_1, R.drawable.funny_2
            , R.drawable.funny_3, R.drawable.funny_4, R.drawable.funny_5
            , R.drawable.funny_6
    };
    private int imageIDArrayCurrentIndex;

    public DebugImageViewMainActivity(int iTest) {
        imageIDArrayCurrentIndex = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_main);

        buttonPrevious.setOnClickListener(new MyButtonClickListener());
        buttonNext.setOnClickListener(new MyButtonClickListener());

    }

    private class MyButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (((Button) view).getText() == "下一个") {
                imageIDArrayCurrentIndex ++;
            } else {
                imageIDArrayCurrentIndex --;
            }
            imageViewFunny.setImageResource(imageIDArray[imageIDArrayCurrentIndex]);
        }
    }
}
