package com.gl.kotlin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gl.kotlin.base.BaseViewModel
import com.gl.kotlin.model.Repo
import com.gl.kotlin.retrofit.ApiGithub
import com.gl.kotlin.retrofit.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GithubViewModel : BaseViewModel() {

    private val TAG: String = javaClass.simpleName
    val reposLiveData = MutableLiveData<List<Repo>?>()
    var apiGithub: ApiGithub = RetrofitManager.create(ApiGithub::class.java)

    fun getRepos() {
        viewModelScope.launch(Dispatchers.IO) {
            val repos = apiGithub.listReposKt("JakeWharton")
            reposLiveData.postValue(repos)

        }
    }


    fun getReposAsync() {
        viewModelScope.launch(Dispatchers.IO) {
            val one = async {
                apiGithub.listReposKt("JakeWharton")
            }
            val two = async {
                apiGithub.listReposKt("gaoleicoding")
            }
            Log.d(TAG, "launch:${one.await()[0].name}== ${two.await()[0].name}")
        }
    }
}