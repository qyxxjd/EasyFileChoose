package com.classic.file.choose

import java.io.File
import java.io.FileFilter

/**
 * 文件类型过滤器
 *
 * - 仅显示指定的后缀文件
 *
 * @author Classic
 * @version v1.0, 2019/3/27 4:02 PM
 */
@Suppress("unused")
class FileTypeFilter(private val fileSuffix: String) : FileFilter {
    override fun accept(file: File): Boolean {
        return file.isDirectory || file.name.toLowerCase().endsWith(fileSuffix.toLowerCase())
    }
}
