package com.journeydigitalpractical.app.ui.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.journeydigitalpractical.app.R
import com.journeydigitalpractical.app.data.model.PostData
import com.journeydigitalpractical.app.data.repository.Repository
import com.journeydigitalpractical.app.databinding.ActivityPostBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Handle business logic for Post List
 */
class PostViewModel(private val mRepo: Repository) : ViewModel() {
    private lateinit var mBinding: ActivityPostBinding
    private var compositeDisposable: CompositeDisposable? = null
    var response = MutableLiveData<MutableList<PostData>>()
    var errorMessage = MutableLiveData<String>()
    var hasError = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()


    init {
        compositeDisposable = CompositeDisposable()
    }


    @SuppressLint("CheckResult")
    fun fetchPosts() {
        loading.value = true
        val mDisposable = mRepo.fetchPosts().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object :
                DisposableSingleObserver<MutableList<PostData>>() {
                override fun onSuccess(t: MutableList<PostData>) {
                    response.value = t
                    loading.value = false
                    hasError.value = false
                }

                override fun onError(e: Throwable) {
                        loading.value = false
                    errorMessage.value =
                        mBinding.root.context.getString(R.string.error_fetching_post_data)
                    hasError.value = true
                }
            })

        compositeDisposable?.add(mDisposable)
    }


    override fun onCleared() {
        compositeDisposable?.clear()
        compositeDisposable = null
        super.onCleared()
    }
}