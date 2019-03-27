package com.classic.file.choose;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.lang.ref.WeakReference;
import java.util.Comparator;

/**
 * 文件选择
 *
 * @author Classic
 * @version v1.0, 2017/2/26 4:02 PM
 */
public class EasyFileChoose {
    static final String INTENT_KEY_PATH = "path";
    private String mTitle;
    private String mDefaultPath;
    private WeakReference<Activity> mActivity;
    private static FilenameFilter sNameFilter;
    private static FileFilter sFileFilter = new DefaultFileFilter();
    private static Comparator<File> sFileComparator = new DefaultFileComparator();

    public static String getPath(Intent intent) {
        return null == intent || !intent.hasExtra(INTENT_KEY_PATH) ?
            "" : intent.getStringExtra(INTENT_KEY_PATH);
    }

    static FilenameFilter nameFilter() {
        return sNameFilter;
    }

    static FileFilter fileFilter() {
        return sFileFilter;
    }

    static Comparator<File> fileComparator() {
        return sFileComparator;
    }

    private EasyFileChoose(Builder builder) {
        mTitle = builder.title;
        mDefaultPath = builder.path;
        mActivity = new WeakReference<>(builder.activity);
        if (null != builder.nameFilter) sNameFilter = builder.nameFilter;
        if (null != builder.fileFilter) sFileFilter = builder.fileFilter;
        if (null != builder.fileComparator) sFileComparator = builder.fileComparator;
    }

    @SuppressWarnings("unused")
    public void choose(int requestCode) {
        Activity activity = mActivity.get();
        if (activity != null) {
            FileChooseActivity.start(activity, mTitle, mDefaultPath, requestCode);
        }
    }

    @SuppressWarnings("unused")
    public static final class Builder {
        private String title;
        private String path;
        private Activity activity;
        private FilenameFilter nameFilter;
        private FileFilter fileFilter;
        private Comparator<File> fileComparator;

        public Builder() {}

        public Builder title(@NonNull String val) {
            title = val;
            return this;
        }

        public Builder path(@NonNull String val) {
            path = val;
            return this;
        }

        public Builder activity(@NonNull Activity val) {
            activity = val;
            return this;
        }

        public Builder filenameFilter(@NonNull FilenameFilter val) {
            nameFilter = val;
            fileFilter = null;
            return this;
        }

        public Builder fileFilter(@NonNull FileFilter val) {
            fileFilter = val;
            nameFilter = null;
            return this;
        }

        public Builder fileComparator(@NonNull Comparator<File> val) {
            fileComparator = val;
            return this;
        }

        public EasyFileChoose build() {return new EasyFileChoose(this);}
    }
}
