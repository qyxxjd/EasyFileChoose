# Easy file choose

## Dependency

```gradle
implementation 'com.classic.file.choose:easy-file-choose:0.3'
implementation 'com.android.support:recyclerview-v7:28.0.0'
```

## Simple

```java
public class Xxx {
    private static final int REQUEST_CODE = 101;

    public void choose() {
        new EasyFileChoose.Builder()
                .activity(this)
                .title("自定义标题")
                // 自定义文件排序规则
                .fileComparator(customComparator)
                // 文件名过滤 
                .filenameFilter(customFilenameFilter)
                // 文件过滤
                // 注意：FileFilter 和 FilenameFilter 仅支持设置其中一个。同时设置时，后设置的会覆盖前面设置的规则。
                .fileFilter(customFileFilter)
                .build()
                .choose(REQUEST_CODE);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // TODO
        }
    }
}
```

#### TODO

- 支持选择文件
- 支持选择目录
- 支持文件类型过滤
- 支持自定义路径
- 文件、目录排序