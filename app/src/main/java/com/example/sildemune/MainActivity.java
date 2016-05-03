package com.example.sildemune;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.sildemune.slidemunu.MySlidemenu;

public class MainActivity extends AppCompatActivity {
    MySlidemenu mySlidemenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySlidemenu = (MySlidemenu)findViewById(R.id.myslidemune);
        findViewById(R.id.btntoggle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySlidemenu.toggle();
            }
        });
    }
}
