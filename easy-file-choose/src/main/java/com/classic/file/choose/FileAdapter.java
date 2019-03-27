package com.classic.file.choose;

import android.content.Context;

import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;

import java.io.File;

/**
 * 文件列表适配器
 *
 * @author Classic
 * @version v1.0, 2017/2/26 4:02 PM
 */
class FileAdapter extends CommonRecyclerAdapter<File> {

    private int mChoosePosition = -1;

    FileAdapter(Context context) {
        super(context, R.layout.item_file, null);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onUpdate(BaseAdapterHelper helper, File item, int position) {
        helper.setText(R.id.item_file_name, item.getName())
              .setImageResource(R.id.item_file_icon,
                                item.isDirectory() ? R.drawable.ic_folder : R.drawable.ic_file)
              .setBackgroundColor(R.id.item_file_layout,
                                  mChoosePosition == position ? helper.getView()
                                                                      .getResources()
                                                                      .getColor(
                                                                          R.color.divider) : 0);
    }

    void setChoosePosition(int choosePosition) {
        if (mChoosePosition != -1) {
            notifyItemChanged(mChoosePosition);
        }
        if (choosePosition != -1) {
            notifyItemChanged(choosePosition);
        }
        if (mChoosePosition != choosePosition) {
            mChoosePosition = choosePosition;
        }
    }
}
