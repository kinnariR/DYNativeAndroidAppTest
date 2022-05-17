package com.journeydigitalpractical.app.ui.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.journeydigitalpractical.app.data.model.CommentData
import com.journeydigitalpractical.app.data.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Handle business logic for Comment List
 */
class CommentViewModel(private val mRepo: Repository) : ViewModel() {
    //  private lateinit var mBinding: ActivityHomeBinding
    private var compositeDisposable: CompositeDisposable? = null
    var response = MutableLiveData<MutableList<CommentData>>()
    var errorMessage = MutableLiveData<String>()
    var hasError = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()


    init {
        compositeDisposable = CompositeDisposable()
    }


    @SuppressLint("CheckResult")
    fun fetchComments(postId:Int) {
        loading.value = true
        val mDisposable = mRepo.fetchComments(postId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object :
                DisposableSingleObserver<MutableList<CommentData>>() {
                override fun onSuccess(t: MutableList<CommentData>) {
                    response.value = t
                    loading.value = false
                    hasError.value = false
                }

                override fun onError(e: Throwable) {
                    loading.value = false
//                    errorMessage.value =
//                        mBinding.root.context.getString(R.string.error_fetching_user_data)
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