package com.journeydigitalpractical.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.journeydigitalpractical.app.R
import com.journeydigitalpractical.app.data.model.CommentData
import com.journeydigitalpractical.app.databinding.ItemCommentBinding

/**
 * Adapter for displaying Comment list
 */
class CommentAdapter(private val mData: List<CommentData>, private val onItemClick: (mData: CommentData) -> Unit) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = DataBindingUtil.bind<ItemCommentBinding>(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBinding.tvCommentTitle.text =  mData[position].name
        holder.mBinding.tvComment.text =  mData[position].comment

        holder.itemView.setOnClickListener {
          //  onItemClick(mData[position])
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}