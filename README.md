# Easy file choose

## Dependency

```gradle
implementation 'com.classic.file.choose:easy-file-choose:0.2'
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