package com.journeydigitalpractical.app.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.journeydigitalpractical.app.R
import com.journeydigitalpractical.app.data.model.PostData
import com.journeydigitalpractical.app.databinding.ItemPostBinding
import java.util.*
import kotlin.collections.ArrayList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult


/**
 * Adapter for displaying Post list
 */
class PostAdapter(
    private val postList: MutableList<PostData>,
    private val onItemClick: (mData: PostData) -> Unit
) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>(), Filterable {
    // use this in base class to access items
   // protected var postFilterData = mData
    private var filterPostList:MutableList<PostData> = postList;
    init {
        filterPostList = postList;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = DataBindingUtil.bind<ItemPostBinding>(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBinding.tvPostTitle.text = filterPostList[position].title
        holder.mBinding.tvPostDesc.text = filterPostList[position].description

        holder.itemView.setOnClickListener {
            onItemClick(postList[position])
        }
    }

    override fun getItemCount(): Int {
        return filterPostList.size
    }


//filter data according to post search
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filterPostList = if (charSearch.isEmpty()) {
                    postList
                } else {
                    val resultList = ArrayList<PostData>()
                    for (row in postList) {
                        if (row.title.lowercase().contains(constraint.toString().lowercase())) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = postList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterPostList = results?.values as ArrayList<PostData>
                notifyDataSetChanged()
            }
        }
    }



}