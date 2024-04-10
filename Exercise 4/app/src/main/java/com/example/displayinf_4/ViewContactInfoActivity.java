package com.example.displayinf_4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ViewContactInfoActivity extends Activity {
    TextView nameTxtView, emailTxtView, projectTxtView;

    Button finishBtn;
    private View.OnClickListener mClickFinishListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactinfo);

        nameTxtView = (TextView) findViewById(R.id.nameTxtView);
        emailTxtView = (TextView) findViewById(R.id.emailTxtView);
        projectTxtView = (TextView) findViewById(R.id.projectTxtView);

        finishBtn = (Button) findViewById(R.id.finishBtn);
        finishBtn.setOnClickListener(mClickFinishListener);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("nameKey");
        String email = bundle.getString("emailKey");
        String project = bundle.getString("projectKey");

        nameTxtView.setText(name);
        emailTxtView.setText(email);
        projectTxtView.setText(project);
    }
}
