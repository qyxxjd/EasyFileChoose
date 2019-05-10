package com.classic.file.choose

import android.app.Activity
import android.content.Intent
import android.os.Environment
import java.io.File
import java.io.FileFilter
import java.io.FilenameFilter
import java.util.Comparator

/**
 * 文件选择
 *
 * @author Classic
 * @version v1.0, 2017/2/26 4:02 PM
 */
@Suppress("unused")
object EasyFileChoose {
    private var directoryIcon: Int = R.drawable.ic_easy_file_choose_directory
    private var fileIcon: Int = R.drawable.ic_easy_file_choose_file
    private var selectedColor: Int = R.color.EasyFileChooseSelected
    private var transparentColor: Int = R.color.EasyFileChooseTransparent
    internal const val INTENT_KEY_PATH = "path"
    internal var defaultPath: String = Environment.getExternalStorageDirectory().absolutePath
    internal var title: Int = R.string.easy_file_choose_title
    internal var nameFilter: FilenameFilter? = null
    internal var fileFilter: FileFilter? = null
    internal var comparator: Comparator<File> = DefaultFileComparator()

    /**
     * 设置起始路径
     */
    fun setPath(value: String): EasyFileChoose {
        defaultPath = value
        return this
    }

    /**
     * 设置标题
     */
    fun setTitle(resId: Int): EasyFileChoose {
        title = resId
        return this
    }

    /**
     * 设置文件图标
     */
    fun setFileIcon(resId: Int): EasyFileChoose {
        fileIcon = resId
        return this
    }

    /**
     * 设置目录图标
     */
    fun setDirectoryIcon(resId: Int): EasyFileChoose {
        directoryIcon = resId
        return this
    }

    /**
     * 设置文件选中的背景颜色
     */
    fun setSelectedBackgroundColor(resId: Int): EasyFileChoose {
        selectedColor = resId
        return this
    }

    /**
     * 设置文件名称过滤器
     *
     * > 文件过滤器、文件名称过滤器两者只能设置其一
     * > 两个都设置时，以文件过滤器为准
     */
    fun setFileNameFilter(value: FilenameFilter): EasyFileChoose {
        nameFilter = value
        return this
    }

    /**
     * 设置文件过滤器
     *
     * > 文件过滤器、文件名称过滤器两者只能设置其一
     * > 两个都设置时，以文件过滤器为准
     */
    fun setFileFilter(value: FileFilter): EasyFileChoose {
        fileFilter = value
        return this
    }

    /**
     * 自定义文件排序
     */
    fun setComparator(value: Comparator<File>): EasyFileChoose {
        comparator = value
        return this
    }

    /**
     * 开始选择文件
     */
    fun choose(activity: Activity, requestCode: Int) {
        FileChooseActivity.start(activity, requestCode)
    }

    /**
     * 获取选择的路径
     */
    fun getPath(intent: Intent): String {
        return if (intent.hasExtra(INTENT_KEY_PATH)) intent.getStringExtra(INTENT_KEY_PATH) else ""
    }

    internal fun icon(file: File): Int = if (file.isDirectory) directoryIcon else fileIcon

    internal fun backgroundColor(selected: Boolean): Int {
        return if (selected) selectedColor else transparentColor
    }
}
