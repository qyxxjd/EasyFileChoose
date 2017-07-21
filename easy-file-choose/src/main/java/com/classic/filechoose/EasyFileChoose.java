package com.classic.filechoose;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * Created by classic on 2017/2/26.
 */

public class EasyFileChoose {

    public static final String INTENT_KEY_PATH = "path";

    private String                  mTitle;
    private String                  mRootPath;
    private WeakReference<Activity> mActivity;

    private EasyFileChoose(Builder builder) {
        mTitle = builder.title;
        mRootPath = builder.rootPath;
        mActivity = new WeakReference<>(builder.activity);
    }


    public void choose(int requestCode) {
        Activity activity = mActivity.get();
        if(activity != null) {
            FileChooseActivity.start(activity, mTitle, mRootPath, requestCode);
        }
    }


    public static final class Builder {
        private String title;
        private String rootPath;
        private Activity activity;

        public Builder() {}

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder rootPath(String val) {
            rootPath = val;
            return this;
        }

        public Builder activity(Activity val) {
            activity = val;
            return this;
        }

        public EasyFileChoose build() {return new EasyFileChoose(this);}
    }
}
