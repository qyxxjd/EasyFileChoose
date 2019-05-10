package com.classic.file.choose

import java.io.File
import java.util.Comparator

/**
 * 默认文件排序
 *
 * - 文件夹排在文件上面
 *
 * @author Classic
 * @version v1.0, 2019/3/27 6:18 PM
 */
class DefaultFileComparator : Comparator<File> {
    override fun compare(o1: File, o2: File): Int {
        if (o1.isDirectory && o2.isFile) return -1
        if (o1.isFile && o2.isDirectory) return 1
        return o1.name.toLowerCase().compareTo(o2.name.toLowerCase())
    }
}
