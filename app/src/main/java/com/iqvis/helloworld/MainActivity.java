package com.iqvis.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.testfairy.TestFairy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestFairy.begin(this, "3dde51e1d2d95ae6d8d1a36d423078e3eba65f9f");
    }
}
