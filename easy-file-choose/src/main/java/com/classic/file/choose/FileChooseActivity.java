package com.classic.file.choose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.classic.adapter.CommonRecyclerAdapter;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 文件选择页面
 *
 * @author Classic
 * @version v1.0, 2017/2/26 4:02 PM
 */
public class FileChooseActivity extends AppCompatActivity
        implements CommonRecyclerAdapter.OnItemClickListener, Toolbar.OnMenuItemClickListener {

    private static final int LOAD_FILES_FINISH = 1;
    private static final String PARAMS_TITLE = "title";
    private static final String PARAMS_PATH = "path";

    private Context mAppContext;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private TextView mPathView;

    private String mCurrentPath;
    private File mChooseFile;
    private FileAdapter mFileAdapter;
    private List<File> mCurrentFiles;
    private volatile boolean isFileProcessing;

    private final ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    private static final String ROOT_PATH = Environment.getExternalStorageDirectory()
                                                       .getAbsolutePath();
    private final HashMap<String, List<File>> mCache = new HashMap<>();

    public static void start(@NonNull Activity activity, String title, String rootPath,
                             int requestCode) {
        Intent intent = new Intent(activity, FileChooseActivity.class);
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra(PARAMS_TITLE, title);
        }
        if (!TextUtils.isEmpty(rootPath)) {
            intent.putExtra(PARAMS_PATH, rootPath);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    private final WeakHandler mHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == LOAD_FILES_FINISH) {
                if (((int) (msg.obj)) > 0) {
                    mFileAdapter.setChoosePosition(-1);
                    mPathView.setText(mCurrentPath);
                    mFileAdapter.replaceAll(mCurrentFiles);
                }
                isFileProcessing = false;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_choose);

        mAppContext = getApplicationContext();
        mToolbar = findViewById(R.id.file_choose_toolbar);
        mRecyclerView = findViewById(R.id.file_choose_rv);
        mPathView = findViewById(R.id.file_choose_path);
        initView();

        if (getIntent().hasExtra(PARAMS_TITLE)) {
            setTitle(getIntent().getStringExtra(PARAMS_TITLE));
        } else {
            setTitle(R.string.title);
        }
        mFileAdapter = new FileAdapter(mAppContext);
        mRecyclerView.setAdapter(mFileAdapter);
        mFileAdapter.setOnItemClickListener(this);
        mCurrentPath = getIntent().hasExtra(PARAMS_PATH) ?
                getIntent().getStringExtra(PARAMS_PATH) : ROOT_PATH;
        loadFiles(mCurrentPath);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_file_choose, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int i = menuItem.getItemId();
        if (i == R.id.action_finish) {
            if (null == mChooseFile) {
                Toast.makeText(mAppContext, R.string.noChooseFileHint, Toast.LENGTH_SHORT)
                     .show();
                return false;
            }

            Intent intent = new Intent();
            intent.putExtra(EasyFileChoose.INTENT_KEY_PATH, mChooseFile.getAbsolutePath());
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mCurrentPath.equals(ROOT_PATH)) {
            setResult(RESULT_CANCELED);
            super.onBackPressed();
        } else {
            mChooseFile = null;
            final String path = new File(mCurrentPath).getParentFile().getAbsolutePath();
            loadFiles(path);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setOnMenuItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mAppContext));
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, View view, int position) {
        if (position >= mCurrentFiles.size()) return;
        mFileAdapter.setChoosePosition(position);
        mChooseFile = mCurrentFiles.get(position);
        if (mChooseFile.isDirectory()) {
            loadFiles(mChooseFile.getAbsolutePath());
        }
    }

    private void loadFiles(@NonNull final String path) {
        Log.d("", "Load path: " + path);
        if (isFileProcessing) {
            return;
        }
        isFileProcessing = true;
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                List<File> files = null;
                if (mCache.containsKey(path)) {
                    files = mCache.get(path);
                } else {
                    File[] fileArray = getFiles(path);
                    if (null != fileArray && fileArray.length > 0) {
                        Log.d("", "File array length : " + fileArray.length);
                        files = Arrays.asList(fileArray);
                        if (null != EasyFileChoose.fileComparator()) {
                            Collections.sort(files, EasyFileChoose.fileComparator());
                        }
                        mCache.put(path, files);
                    }
                }
                if (null == files || files.size() == 0) {
                    isFileProcessing = false;
                    return;
                }
                final int size = files.size();
                mCurrentFiles = files;
                mCurrentPath = path;
                Message message = Message.obtain();
                message.what = LOAD_FILES_FINISH;
                message.obj = size;
                mHandler.sendMessage(message);
            }
        });
    }

    private File[] getFiles(@NonNull String path) {
        if (EasyFileChoose.fileFilter() != null) {
            return new File(path).listFiles(EasyFileChoose.fileFilter());
        } else if (EasyFileChoose.nameFilter() != null) {
            return new File(path).listFiles(EasyFileChoose.nameFilter());
        } else {
            return new File(path).listFiles();
        }
    }
}