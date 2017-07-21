package com.classic.filechoose;

import android.content.Context;
import com.classic.adapter.BaseAdapterHelper;
import com.classic.adapter.CommonRecyclerAdapter;
import java.io.File;


class FileAdapter extends CommonRecyclerAdapter<File> {

    private int mChoosePosition = -1;

    FileAdapter(Context context) {
        super(context, R.layout.item_file, null);
    }

    @SuppressWarnings("deprecation")
    @Override public void onUpdate(BaseAdapterHelper helper, File item, int position) {
        helper.setText(R.id.item_file_name, item.getName())
              .setImageResource(R.id.item_file_icon,
                      item.isDirectory() ? R.drawable.ic_dir : R.drawable.ic_file)
                .setBackgroundColor(R.id.item_file_layout, mChoosePosition == position ?
                                                           helper.getView().getResources()
                                                               .getColor(R.color.divider):
                                                           0);
        //final View view = helper.getView(R.id.item_file_layout);
        //if(mChoosePosition != -1 && mChoosePosition == position){
        //    view.setBackgroundColor(view.getContext().getResources().getColor(R.color.divider));
        //} else {
        //    view.setBackgroundColor(-1);
        //}
    }

    void setChoosePosition(int choosePosition) {
        if(mChoosePosition != -1) {
            notifyItemChanged(mChoosePosition);
        }
        if(choosePosition != -1) {
            notifyItemChanged(choosePosition);
        }
        if(mChoosePosition!=choosePosition){
            mChoosePosition = choosePosition;
        }
    }
}
