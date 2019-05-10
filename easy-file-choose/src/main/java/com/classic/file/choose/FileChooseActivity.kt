package com.classic.file.choose

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.easy_file_choose_activity.easy_file_choose_path
import kotlinx.android.synthetic.main.easy_file_choose_activity.easy_file_choose_rv
import kotlinx.android.synthetic.main.easy_file_choose_activity.easy_file_choose_toolbar
import java.io.File
import java.util.Arrays
import java.util.Collections
import java.util.HashMap
import java.util.concurrent.Executors



/**
 * 文件选择页面
 *
 * @author Classic
 * @version v1.0, 2017/2/26 4:02 PM
 */
internal class FileChooseActivity : AppCompatActivity(), ItemClickListener, Toolbar.OnMenuItemClickListener {
    private val executor = Executors.newSingleThreadExecutor()
    private val innerAdapter: InnerAdapter = InnerAdapter(this)
    private val cache = HashMap<String, List<File>>()

    private lateinit var appContext: Context
    private var currentPath: String? = null
    private var chooseFile: File? = null
    private var currentFiles: List<File>? = null
    @Volatile
    private var isFileProcessing: Boolean = false

    private val handler = WeakHandler(Handler.Callback { msg ->
        if (msg.what == LOAD_FILES_FINISH) {
            if (msg.obj as Int > 0) {
                innerAdapter.setChoosePosition(-1)
                easy_file_choose_path.text = currentPath
                innerAdapter.submitList(currentFiles!!)
            }
            isFileProcessing = false
        }
        true
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.easy_file_choose_activity)
        setTitle(EasyFileChoose.title)
        appContext = applicationContext
        easy_file_choose_toolbar.apply {
            setSupportActionBar(this)
            val actionBar = supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            setOnMenuItemClickListener(this@FileChooseActivity)
        }
        easy_file_choose_rv.apply {
            layoutManager = LinearLayoutManager(appContext)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = innerAdapter
        }
        currentPath = EasyFileChoose.defaultPath
        loadFiles(currentPath!!)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.easy_file_choose_menu, menu)
        return true
    }

    override fun onMenuItemClick(menuItem: MenuItem): Boolean {
        val i = menuItem.itemId
        if (i == R.id.action_finish) {
            if (null == chooseFile) {
                Toast.makeText(appContext, R.string.easy_file_choose_empty_hint, Toast.LENGTH_SHORT)
                    .show()
                return false
            }
            val intent = Intent()
            intent.putExtra(EasyFileChoose.INTENT_KEY_PATH, chooseFile!!.absolutePath)
            setResult(Activity.RESULT_OK, intent)
            finish()
            return true
        }
        return false
    }

    override fun onBackPressed() {
        if (currentPath == ROOT_PATH) {
            setResult(Activity.RESULT_CANCELED)
            super.onBackPressed()
        } else {
            chooseFile = null
            val path = File(currentPath!!).parentFile.absolutePath
            loadFiles(path)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(holder: RecyclerView.ViewHolder, file: File, position: Int) {
        if (position >= currentFiles!!.size) return
        innerAdapter.setChoosePosition(position)
        chooseFile = currentFiles!![position]
        if (chooseFile!!.isDirectory) {
            loadFiles(chooseFile!!.absolutePath)
        }
    }

    private fun loadFiles(path: String) {
        Log.d(TAG, "Load path: $path")
        if (isFileProcessing) {
            return
        }
        isFileProcessing = true
        executor.submit(Runnable {
            var files: List<File>? = null
            if (cache.containsKey(path)) {
                files = cache[path]
            } else {
                val fileArray = getFiles(path)
                if (null != fileArray && fileArray.isNotEmpty()) {
                    Log.d(TAG, "File array length : " + fileArray.size)
                    files = Arrays.asList(*fileArray)
                    Collections.sort(files!!, EasyFileChoose.comparator)
                    cache[path] = files
                }
            }
            if (null == files || files.isEmpty()) {
                isFileProcessing = false
                return@Runnable
            }
            val size = files.size
            currentFiles = files
            currentPath = path
            val message = Message.obtain()
            message.what = LOAD_FILES_FINISH
            message.obj = size
            handler.sendMessage(message)
        })
    }

    private fun getFiles(path: String): Array<File>? {
        return when {
            EasyFileChoose.fileFilter != null -> File(path).listFiles(EasyFileChoose.fileFilter)
            EasyFileChoose.nameFilter != null -> File(path).listFiles(EasyFileChoose.nameFilter)
            else -> File(path).listFiles()
        }
    }

    companion object {
        private val TAG = FileChooseActivity::class.java.simpleName
        private const val LOAD_FILES_FINISH = 1
        private val ROOT_PATH = Environment.getExternalStorageDirectory().absolutePath

        internal fun start(activity: Activity, requestCode: Int) {
            activity.startActivityForResult(Intent(activity, FileChooseActivity::class.java), requestCode)
        }
    }
}