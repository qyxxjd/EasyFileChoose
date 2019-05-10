package com.classic.file.choose.demo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.classic.file.choose.EasyFileChoose

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // // 自定义
    // fun custom() {
    //     EasyFileChoose
    //         // 标题
    //         .setTitle(R.string.custom_title)
    //         // 自定义起始路径
    //         .setPath(path)
    //         // 文件图标
    //         .setFileIcon(R.drawable.ic_custom_file)
    //         // 文件夹图标
    //         .setDirectoryIcon(R.drawable.ic_custom_folder)
    //         // 选中的背景颜色
    //         .setSelectedBackgroundColor(R.color.colorPrimary)
    //         // 文件名称过滤器
    //         .setFileNameFilter(filter)
    //         // 文件过滤器
    //         .setFileFilter(filter)
    //         // 文件排序
    //         .setComparator(comparator)
    //         .choose(this, REQUEST_CODE)
    // }

    @Suppress("UNUSED_PARAMETER")
    fun choose(view: View) {
        EasyFileChoose
            .setTitle(R.string.custom_title)
            .setFileIcon(R.drawable.ic_custom_file)
            .setDirectoryIcon(R.drawable.ic_custom_folder)
            .setSelectedBackgroundColor(R.color.colorPrimary)
            .choose(this, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(this@MainActivity, "选择的文件：" + EasyFileChoose.getPath(data!!),
                Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        private const val REQUEST_CODE = 101
    }
}
