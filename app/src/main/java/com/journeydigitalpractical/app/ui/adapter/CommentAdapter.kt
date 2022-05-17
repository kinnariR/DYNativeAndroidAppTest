package com.journeydigitalpractical.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.journeydigitalpractical.app.R
import com.journeydigitalpractical.app.data.model.CommentData
import com.journeydigitalpractical.app.data.model.PostData
import com.journeydigitalpractical.app.databinding.ItemCommentBinding

/**
 * Adapter for displaying Comment list
 */
class CommentAdapter(
    private val commentList: MutableList<CommentData>,
    private val onItemClick: (mData: CommentData) -> Unit
) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>(), Filterable {

    /**
     * Initialize filter list for filter comment data
     */
    var filterCommentList: MutableList<CommentData> = commentList;

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = DataBindingUtil.bind<ItemCommentBinding>(itemView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return ViewHolder(mView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBinding.tvCommentTitle.text = filterCommentList[position].name
        holder.mBinding.tvComment.text = filterCommentList[position].comment

        holder.itemView.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return filterCommentList.size
    }

    /**
     * Filter data according to comment search
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                val data = if (charSearch.isEmpty()) {
                    filterCommentList
                } else {
                    val resultList =
                        commentList.filter {
                            it.comment.lowercase().contains(charSearch, true) || it.name.lowercase()
                                .contains(charSearch, true)
                        }
                            .toMutableList()
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = data
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterCommentList = results?.values as ArrayList<CommentData>
                notifyDataSetChanged()
            }
        }
    }
}