package com.classic.filechoose.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.classic.filechoose.EasyFileChoose;

public class MainActivity extends AppCompatActivity {

    private final int mRequestCode = 566;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void choose(View view) {
        new EasyFileChoose.Builder().activity(this)
                                    .title("自定义文件选择")
                                    .build()
                                    .choose(mRequestCode);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == mRequestCode && resultCode == RESULT_OK) {
            Toast.makeText(MainActivity.this, "选择的文件："+data.getStringExtra(EasyFileChoose.INTENT_KEY_PATH),
                           Toast.LENGTH_SHORT).show();
        }

    }
}
