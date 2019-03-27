package com.classic.file.choose;

import java.io.File;
import java.io.FileFilter;

/**
 * 默认文件过滤器(过滤掉隐藏文件、文件夹)
 *
 * @author Classic
 * @version v1.0, 2019/3/27 4:25 PM
 */
public final class DefaultFileFilter implements FileFilter {

    @Override
    public boolean accept(File file) {
        return !file.getName().startsWith(".");
    }
}
