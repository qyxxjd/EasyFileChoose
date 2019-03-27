package com.classic.file.choose.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.classic.file.choose.EasyFileChoose;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void choose(View view) {
        new EasyFileChoose.Builder().activity(this)
                                    .title("自定义标题")
                                    // .fileFilter(new FileTypeFilter("zip"))
                                    .build()
                                    .choose(REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(MainActivity.this, "选择的文件：" + EasyFileChoose.getPath(data),
                           Toast.LENGTH_SHORT)
                 .show();
        }
    }
}
