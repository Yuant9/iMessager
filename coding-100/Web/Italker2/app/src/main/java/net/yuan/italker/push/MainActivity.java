package net.yuan.italker.push;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.yuan.italker.common.Common;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Common();
    }


}
