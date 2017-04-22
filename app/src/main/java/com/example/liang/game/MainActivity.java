package com.example.liang.game;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Ondraw(this));

    }

}
