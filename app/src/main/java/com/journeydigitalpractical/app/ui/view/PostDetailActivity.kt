package com.journeydigitalpractical.app.ui.view

import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.journeydigitalpractical.app.R
import com.journeydigitalpractical.app.data.model.CommentData
import com.journeydigitalpractical.app.databinding.ActivityPostDetailBinding
import com.journeydigitalpractical.app.db.PostDb
import com.journeydigitalpractical.app.ui.adapter.CommentAdapter
import com.journeydigitalpractical.app.ui.base.BaseActivity
import com.journeydigitalpractical.app.ui.viewModel.CommentViewModel
import com.journeydigitalpractical.app.utils.ConnectionChecker
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.activity_post_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class PostDetailActivity :  BaseActivity<ActivityPostDetailBinding>() {
    private val mCommentViewModel: CommentViewModel by viewModel()
    override fun layoutId() = R.layout.activity_post_detail

    private lateinit var mDb: PostDb
    override fun onViewReady() {
        supportActionBar?.title = getString(R.string.str_comment)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this,R.color.purple_700)))
        supportActionBar?.setIcon(R.drawable.ic_baseline_arrow_back_ios_24)

        mDb = PostDb.getDatabaseClient(this)
        var title =""
        var id=0
        if(intent?.extras != null){
            title = intent.getStringExtra("PostTitle").toString()
            id= intent.getIntExtra("PostId",0)
            tvPostTitle.setText(title)
        }


        if (ConnectionChecker.checkConnection(this)) {
            mCommentViewModel.fetchComments(id)
        } else {
            val listData = mDb.commentDao().getAll()
            if (listData.isNotEmpty()) {
                rvComments.layoutManager = LinearLayoutManager(this)
                rvComments.adapter = CommentAdapter(listData) {}
            } else {
                showHideError(true)
                mBinding.tvError.text = getString(R.string.error_no_internet_connection)
            }
        }

        mCommentViewModel.apply {
            observeLoading()
            observeError()
            observeResponse()
        }
    }

    private fun observeResponse() {
        mCommentViewModel.apply {
            response.observe(this@PostDetailActivity, Observer<MutableList<CommentData>> {
                if (it != null && it.isNotEmpty()) {
                    mDb.commentDao().deleteAll()
                    mDb.commentDao().insertAll(it)

                }
                rvComments.layoutManager = LinearLayoutManager(this@PostDetailActivity)
                rvComments.adapter = CommentAdapter(it) {  }
            })
        }
    }


    private fun observeLoading() {
        mCommentViewModel.loading.observe(this@PostDetailActivity, Observer<Boolean> {
            showHideLoading(it)
        })
    }

    private fun observeError() {
        mCommentViewModel.apply {
            hasError.observe(this@PostDetailActivity, Observer<Boolean> {
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