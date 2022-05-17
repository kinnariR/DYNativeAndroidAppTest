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
    /**
     * Initialize filter list for filter post data
     */
    var filterPostList: MutableList<PostData> = postList;


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
            onItemClick(filterPostList[position])
        }
    }

    override fun getItemCount(): Int {
        return filterPostList.size
    }


    /**
     * Filter Data according to Post Search
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                val data = if (charSearch.isEmpty()) {
                    filterPostList
                } else {
                    val resultList =
                        postList.filter { it.title.lowercase().contains(charSearch, true) }
                            .toMutableList()
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = data
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterPostList = results?.values as ArrayList<PostData>
                notifyDataSetChanged()
            }
        }
    }


}