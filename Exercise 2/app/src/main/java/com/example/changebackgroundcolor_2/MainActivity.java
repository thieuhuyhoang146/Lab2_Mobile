package com.example.changebackgroundcolor_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onRadBtnClicked(View view){

        ConstraintLayout frameLayout = (ConstraintLayout) findViewById(R.id.frameLayout);

        switch (view.getId()){

            case R.id.redRadBtn:
                frameLayout.setBackgroundColor(Color.RED);
                break;
            case R.id.greenRadBtn:
                frameLayout.setBackgroundColor(Color.GREEN);
                break;
            case R.id.blueRadBtn:
                frameLayout.setBackgroundColor(Color.BLUE);
                break;
            case R.id.grayRadBtn:
                frameLayout.setBackgroundColor(Color.GRAY);
                break;
        }

    }
}