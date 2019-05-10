package com.classic.file.choose

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import java.io.File

/**
 * File adapter
 *
 * @author Classic
 * @version v1.0, 2019-05-10 14:40
 */
internal class InnerAdapter(private val listener: ItemClickListener) : ListAdapter<File, ItemHolder>(FileDiffCallback()) {
    private var choosePosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.easy_file_choose_item, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        getItem(position).let { file ->
            with(holder) {
                itemView.tag = file
                title.text = file.name
                icon.setImageResource(EasyFileChoose.icon(file))
                val color = EasyFileChoose.backgroundColor(choosePosition == position)
                itemLayout.setBackgroundColor(itemLayout.context.resources.getColor(color))
                itemLayout.setOnClickListener {
                    listener.onItemClick(this, file, adapterPosition)
                }
            }
        }
    }

    internal fun setChoosePosition(newChoosePosition: Int) {
        if (choosePosition != -1) {
            notifyItemChanged(choosePosition)
        }
        if (newChoosePosition != -1) {
            notifyItemChanged(newChoosePosition)
        }
        if (choosePosition != newChoosePosition) {
            choosePosition = newChoosePosition
        }
    }
}

internal interface ItemClickListener {
    fun onItemClick(holder: RecyclerView.ViewHolder, file: File, position: Int)
}

internal class FileDiffCallback : DiffUtil.ItemCallback<File>() {
    override fun areItemsTheSame(old: File, new: File): Boolean {
        return old.absolutePath == new.absolutePath
    }
    override fun areContentsTheSame(old: File, new: File): Boolean {
        return old.length() == new.length()
    }
}

internal class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
    val itemLayout: LinearLayout = view.findViewById(R.id.item_file_layout)
    val icon: ImageView = view.findViewById(R.id.item_file_icon)
    val title: TextView = view.findViewById(R.id.item_file_title)
}