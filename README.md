# Easy file choose

## Dependency

```gradle
implementation 'com.classic.file.choose:easy-file-choose:0.5'
implementation 'com.android.support:recyclerview-v7:28.0.0'
```

## Sample

```kotlin
class Sample {
    companion object {
        private const val REQUEST_CODE = 101
    }
    
    // 最简单的使用方法
    fun simple() {
        EasyFileChoose.choose(activity, REQUEST_CODE)
    }
    
    // 自定义
    fun custom() {
        EasyFileChoose
            // 标题
            .setTitle(R.string.custom_title)
            // 自定义起始路径
            .setPath(path)
            // 文件图标
            .setFileIcon(R.drawable.ic_custom_file)
            // 文件夹图标
            .setDirectoryIcon(R.drawable.ic_custom_folder)
            // 选中的背景颜色
            .setSelectedBackgroundColor(R.color.colorPrimary)
            // 文件名称过滤器
            .setFileNameFilter(filter)
            // 文件过滤器
            .setFileFilter(filter)
            // 文件排序
            .setComparator(comparator)
            .choose(this, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(this@MainActivity, "选择的文件：" + EasyFileChoose.getPath(data!!),
                Toast.LENGTH_SHORT)
                .show()
        }
    }
}
```


> 注意事项：
> `FileFilter` 和 `FilenameFilter` 仅支持设置其中一个，
> 两个都设置时，以 `FileFilter` 为准。


## Style

```xml
<!-- 自定义文件选择页面的主题 -->
<style name="EasyFileChoose.Theme">
    ...
</style>

<!-- 自定义路径文本样式 -->
<style name="EasyFileChoose.Path">
    ...
</style>

<!-- 自定义布局样式 -->
<style name="EasyFileChoose.ItemLayout">
    ...
</style>

<!-- 自定义图标样式 -->
<style name="EasyFileChoose.ItemIcon">
    ...
</style>

<!-- 自定义文件名称样式 -->
<style name="EasyFileChoose.ItemTitle">
    ...
</style>
```