package com.journeydigitalpractical.app.ui.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.journeydigitalpractical.app.data.model.PostData
import com.journeydigitalpractical.app.db.PostDb
import com.journeydigitalpractical.app.ui.adapter.PostAdapter
import com.journeydigitalpractical.app.ui.base.BaseActivity
import com.journeydigitalpractical.app.ui.viewModel.PostViewModel
import com.journeydigitalpractical.app.utils.ConnectionChecker
import com.journeydigitalpractical.app.R
import com.journeydigitalpractical.app.databinding.ActivityPostBinding
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.activity_post_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class PostActivity : BaseActivity<ActivityPostBinding>() {
    private val mPostViewModel: PostViewModel by viewModel()
    override fun layoutId() = R.layout.activity_post

    private lateinit var mDb: PostDb
    lateinit var adapter: PostAdapter

    override fun onViewReady() {
        supportActionBar?.title = getString(R.string.posts)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.purple_700
                )
            )
        )

        mDb = PostDb.getDatabaseClient(this)

        fetchPostData()

        //Search icon for searchView
        val searchIcon =
            searchPosts.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.BLACK)

        //cancel icon for searchView
        val cancelIcon =
            searchPosts.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.BLACK)
        cancelIcon.setOnClickListener {
            searchPosts.setQuery("", false)
        }

        //text for searchView
        val textView = searchPosts.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
        textView.setTextColor(Color.BLACK)
        searchPosts.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter.filter(query, Filter.FilterListener {
                })
                if(adapter.filterPostList.isEmpty())
                {
                    tvError.visibility = View.VISIBLE
                    tvError.setText(getString(R.string.str_no_comment))
                }
                else{
                    tvError.visibility = View.GONE
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText);
                if(adapter.filterPostList.isEmpty())
                {
                    tvError.visibility = View.VISIBLE
                    tvError.setText(getString(R.string.str_no_comment))
                }
                else{
                    tvError.visibility = View.GONE
                }
                return false
            }

        })


        mPostViewModel.apply {
            observeLoading()
            observeError()
            observeResponse()
        }

    }

    /**
     * Fetch Post Data from api or database when network is not available
     */

    private fun fetchPostData() {
        if (ConnectionChecker.checkConnection(this)) {
            mPostViewModel.fetchPosts()
        } else {
            val listData = mDb.postDao().getAll()
            mPostViewModel.response.postValue(listData)
        }
    }

    /**
     * viewModel observeResponse from api
     */
    private fun observeResponse() {
        mPostViewModel.apply {
            response.observe(this@PostActivity, Observer<MutableList<PostData>> {
                showHideLoading(false)

                if (it != null && it.isNotEmpty()) {
                    mDb.postDao().deleteAll()
                    mDb.postDao().insertAll(it)

                }
                rvPosts.layoutManager = LinearLayoutManager(this@PostActivity)
                adapter = PostAdapter(it) { navigateToNextScreen(it.title, it.id) }
                rvPosts.adapter = adapter

            })
        }
    }

    private fun navigateToNextScreen(postTitle: String, postId: Int) {
        val intent: Intent = Intent(this@PostActivity, PostDetailActivity::class.java)
        intent.putExtra("PostTitle", postTitle)
        intent.putExtra("PostId", postId)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right,
            R.anim.slide_out_left);
    }

    private fun observeLoading() {
        mPostViewModel.loading.observe(this@PostActivity, Observer<Boolean> {
            showHideLoading(it)
        })
    }

    /**
     * viewModel observeError from api if there is any error occur
     */
    private fun observeError() {
        mPostViewModel.apply {
            hasError.observe(this@PostActivity, Observer<Boolean> {
                showHideLoading(false)
                if (it && errorMessage.value != null && errorMessage.value != "") {
                    showHideError(true)
                    mBinding.tvError.text = errorMessage.value
                }
            })
        }
    }

    private fun showHideLoading(isShow: Boolean) {
        mBinding.pbLoading.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showHideError(isShow: Boolean) {
        mBinding.tvError.visibility = if (isShow) View.VISIBLE else View.GONE
    }


}

