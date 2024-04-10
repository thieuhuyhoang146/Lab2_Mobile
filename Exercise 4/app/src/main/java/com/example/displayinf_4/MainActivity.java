package com.example.displayinf_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText nameEdTxt, emailEdTxt, projectEdTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdTxt = (EditText) findViewById(R.id.nameEdTxt);
        emailEdTxt = (EditText) findViewById(R.id.emailEdTxt);
        projectEdTxt = (EditText) findViewById(R.id.projectEdTxt);
    }

    public void onClick(View view){

        Intent iGetContactInfo = new Intent(getApplicationContext(), ViewContactInfoActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("nameKey", nameEdTxt.getText().toString());
        bundle.putString("emailKey", emailEdTxt.getText().toString());
        bundle.putString("projectKey", projectEdTxt.getText().toString());

        iGetContactInfo.putExtras(bundle);

        startActivity(iGetContactInfo);

    }
}