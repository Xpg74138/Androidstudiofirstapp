package com.example.first;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent=getIntent();
        String str=intent.getStringExtra("abc");
        TextView tv=findViewById(R.id.textView4);
        tv.setText(str);
    }
}
