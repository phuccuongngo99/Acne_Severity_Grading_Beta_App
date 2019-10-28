package com.example.deeplearning.camera_kit;

import android.app.Activity;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class PopupActivity extends Activity {
    public static String EXTRA_DIGIT;
    TextView textPred;
    Button saveButton;
    Button bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        saveButton = (Button) findViewById(R.id.button_save);
        bookButton = (Button) findViewById(R.id.button_book);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopupActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopupActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String digit = intent.getStringExtra(EXTRA_DIGIT);

        textPred = (TextView) findViewById(R.id.text_pred);
        textPred.setText("GRADE: "+digit);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.6),(int)(height*.5));


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }
}
