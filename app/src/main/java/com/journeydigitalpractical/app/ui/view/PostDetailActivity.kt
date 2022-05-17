package com.journeydigitalpractical.app.ui.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.MenuItem
import android.view.View
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.journeydigitalpractical.app.R
import com.journeydigitalpractical.app.data.model.CommentData
import com.journeydigitalpractical.app.databinding.ActivityPostDetailBinding
import com.journeydigitalpractical.app.db.PostDb
import com.journeydigitalpractical.app.ui.adapter.CommentAdapter
import com.journeydigitalpractical.app.ui.adapter.PostAdapter
import com.journeydigitalpractical.app.ui.base.BaseActivity
import com.journeydigitalpractical.app.ui.viewModel.CommentViewModel
import com.journeydigitalpractical.app.utils.ConnectionChecker
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.activity_post_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>() {
    private val mCommentViewModel: CommentViewModel by viewModel()
    override fun layoutId() = R.layout.activity_post_detail

    private lateinit var mDb: PostDb
    lateinit var commentAdapter: CommentAdapter

    override fun onViewReady() {
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.purple_700
                )
            )
        )
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        mDb = PostDb.getDatabaseClient(this)
        var title = ""
        var id = 0
        if (intent?.extras != null) {
            title = intent.getStringExtra("PostTitle").toString()
            id = intent.getIntExtra("PostId", 0)
            supportActionBar?.title =  title

        }


        fetchPostComments(id)

         //Search icon for searchView
        val searchIcon =
            searchComments.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.BLACK)

        //cancel icon for searchView
        val cancelIcon =
            searchComments.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.BLACK)
        cancelIcon.setOnClickListener {
            searchComments.setQuery("", false)
        }

        //text for searchView
        val textView = searchComments.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        textView.setTextColor(Color.BLACK)
        searchComments.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                commentAdapter.filter.filter(query, Filter.FilterListener {
                })
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                commentAdapter.filter.filter(newText);
                if(commentAdapter.filterCommentList.isEmpty())
                {
                    tvCommentError.visibility = View.VISIBLE
                    tvCommentError.setText(getString(R.string.str_no_comment))

                }
                else
                {
                    tvCommentError.visibility = View.GONE

                }
                return false
            }

        })


        /**
         *  observeData using ViewModel
         */

        mCommentViewModel.apply {
            observeLoading()
            observeError()
            observeResponse()
        }
    }

    /**
     * Fetch Post Comment according to network connection or from database when netweok is not available
     */

    private fun fetchPostComments(id: Int) {
        if (ConnectionChecker.checkConnection(this)) {
            mCommentViewModel.fetchComments(id)
        } else {
            val commentData = mDb.commentDao().getAll(id)
            mCommentViewModel.response.postValue(commentData)
        }
    }
    /**
     *  observeResponse from api
     */

    private fun observeResponse() {
        mCommentViewModel.apply {
            response.observe(this@PostDetailActivity, Observer<MutableList<CommentData>> {
                showHideLoading(false)

                if (it != null && it.isNotEmpty()) {
                    mDb.commentDao().deleteAll()
                    mDb.commentDao().insertAll(it)

                }

                rvComments.layoutManager = LinearLayoutManager(this@PostDetailActivity)
                commentAdapter = CommentAdapter(it) { }
                rvComments.adapter= commentAdapter
            })
        }
    }


    private fun observeLoading() {
        mCommentViewModel.loading.observe(this@PostDetailActivity, Observer<Boolean> {
            showHideLoading(it)
        })
    }

    /**
     *  observeError from api if there is any error occur
     */
    private fun observeError() {
        mCommentViewModel.apply {
            hasError.observe(this@PostDetailActivity, Observer<Boolean> {
                showHideLoading(false)
                if (it && errorMessage.value != null && errorMessage.value != "") {
                    showHideError(true)
                    mBinding.tvCommentError.text = errorMessage.value
                }
            })
        }
    }

    private fun showHideLoading(isShow: Boolean) {
        mBinding.pbLoading.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showHideError(isShow: Boolean) {
        mBinding.tvCommentError.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    // And override this method
    override fun onNavigateUp(): Boolean {
        finish()
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                //Write your logic here
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}