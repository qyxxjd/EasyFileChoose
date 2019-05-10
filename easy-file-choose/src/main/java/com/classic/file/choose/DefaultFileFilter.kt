package com.classic.file.choose

import java.io.File
import java.io.FileFilter

/**
 * 默认文件过滤器
 *
 * - 不显示隐藏文件夹
 * - 不显示隐藏文件
 *
 * @author Classic
 * @version v1.0, 2019/3/27 4:25 PM
 */
@Suppress("unused")
class DefaultFileFilter : FileFilter {
    override fun accept(file: File): Boolean {
        return !file.name.startsWith(".")
    }
}
