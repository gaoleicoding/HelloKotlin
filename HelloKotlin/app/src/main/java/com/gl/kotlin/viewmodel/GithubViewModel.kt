package com.gl.kotlin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gl.kotlin.base.BaseViewModel
import com.gl.kotlin.model.Repo
import com.gl.kotlin.retrofit.GithubApi
import com.gl.kotlin.retrofit.RetrofitUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GithubViewModel : BaseViewModel() {

    private val TAG: String = javaClass.simpleName
    val reposLiveData = MutableLiveData<List<Repo>?>()

    fun getRepos() {
        viewModelScope.launch(Dispatchers.IO) {
            val repos =
                RetrofitUtil.instance.get(GithubApi::class.java).listReposKt("JakeWharton")
            reposLiveData.postValue(repos)

        }
    }


    fun getReposAsync() {
        viewModelScope.launch(Dispatchers.IO) {
            val one = async {
                RetrofitUtil.instance.get(GithubApi::class.java).listReposKt("JakeWharton")
            }
            val two = async {
                RetrofitUtil.instance.get(GithubApi::class.java).listReposKt("gaoleicoding")
            }
            Log.d(TAG, "launch:${one.await()[0].name}== ${two.await()[0].name}")
        }
    }
}