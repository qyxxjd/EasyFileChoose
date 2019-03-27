package com.classic.file.choose;

import java.io.File;
import java.util.Comparator;

/**
 * 默认文件排序
 *
 * @author Classic
 * @version v1.0, 2019/3/27 6:18 PM
 */
public final class DefaultFileComparator implements Comparator<File> {

    @Override
    public int compare(File o1, File o2) {
        if (o1.isDirectory() && o2.isFile())
            return -1;
        if (o1.isFile() && o2.isDirectory())
            return 1;
        return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
    }
}
