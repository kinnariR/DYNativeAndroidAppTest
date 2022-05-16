package com.example.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.data.model.PostData
import com.example.app.databinding.ItemPostBinding

/**
 * Adapter for displaying Post list
 */
class PostAdapter(private val mData: List<PostData>, private val onItemClick: (mData: PostData) -> Unit) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = DataBindingUtil.bind<ItemPostBinding>(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBinding.tvPostTitle.text =  mData[position].title
        holder.mBinding.tvPostDesc.text =  mData[position].description

        holder.itemView.setOnClickListener {
            onItemClick(mData[position])
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }
}