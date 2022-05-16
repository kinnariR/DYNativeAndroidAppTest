package com.example.app.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.data.model.PostData
import com.example.app.db.PostDb
import com.example.app.ui.adapter.PostAdapter
import com.example.app.ui.base.BaseActivity
import com.example.app.ui.viewModel.PostViewModel
import com.example.app.utils.ConnectionChecker
import com.example.app.R
import com.example.app.databinding.ActivityPostBinding
import kotlinx.android.synthetic.main.activity_post.*
import org.koin.android.viewmodel.ext.android.viewModel

class PostActivity : BaseActivity<ActivityPostBinding>() {
    private val mPostViewModel: PostViewModel by viewModel()
    override fun layoutId() = R.layout.activity_post

    private lateinit var mDb: PostDb
    override fun onViewReady() {
        supportActionBar?.title = getString(R.string.posts)
        mDb = PostDb.getDatabaseClient(this)

        if (ConnectionChecker.checkConnection(this)) {
            mPostViewModel.fetchPosts()
        } else {
            val listData = mDb.postDao().getAll()
            if (listData.isNotEmpty()) {
                rvPosts.layoutManager = LinearLayoutManager(this)
                rvPosts.adapter = PostAdapter(listData) { navigateToNextScreen()  }
            } else {
                showHideError(true)
                mBinding.tvError.text = getString(R.string.error_no_internet_connection)
            }
        }

        mPostViewModel.apply {
            observeLoading()
            observeError()
            observeResponse()
        }
    }

    private fun observeResponse() {
        mPostViewModel.apply {
            response.observe(this@PostActivity, Observer<MutableList<PostData>> {
                if (it != null && it.isNotEmpty()) {
                    mDb.postDao().deleteAll()
                    mDb.postDao().insertAll(it)

                }
                rvPosts.layoutManager = LinearLayoutManager(this@PostActivity)
                rvPosts.adapter = PostAdapter(it) { navigateToNextScreen()  }
            })
        }
    }
    private fun navigateToNextScreen(){
        val intent: Intent = Intent(this@PostActivity, PostDetailActivity::class.java)
        startActivity(intent)
    }

    private fun observeLoading() {
        mPostViewModel.loading.observe(this@PostActivity, Observer<Boolean> {
            showHideLoading(it)
        })
    }

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