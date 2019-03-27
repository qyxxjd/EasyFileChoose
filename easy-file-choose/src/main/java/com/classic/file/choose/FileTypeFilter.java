package com.classic.file.choose;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileFilter;

/**
 * 文件类型过滤器
 *
 * @author Classic
 * @version v1.0, 2019/3/27 4:02 PM
 */
@SuppressWarnings("unused")
public class FileTypeFilter implements FileFilter {
    private final String mFileSuffix;

    public FileTypeFilter(@NonNull String suffix) {
        mFileSuffix = suffix;
    }

    @Override
    public boolean accept(File file) {
        return !file.getName().startsWith(".") &&
            (file.isDirectory() ||
            file.getName().toLowerCase().endsWith(mFileSuffix.toLowerCase()));
    }
}
