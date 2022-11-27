package com.gl.kotlin.activity

import android.os.Bundle
import android.util.Log
import com.gl.kotlin.R
import com.gl.kotlin.base.BaseActivity
import com.gl.kotlin.databinding.ActivityRetrofitBinding
import com.gl.kotlin.viewmodel.WanAndroidViewModel
import com.gl.kotlin.util.Utils

internal class RetrofitActivity : BaseActivity<WanAndroidViewModel, ActivityRetrofitBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_retrofit

    override fun viewModelClass() = WanAndroidViewModel::class.java

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initData() {
        getBanners()
        getArticles()
    }

    fun getBanners() {
        mViewModel.banners.observe(this, {
            val banners = it
            Log.d(TAG, "banners.size: ${banners?.size}")
            Utils.showToast("banners.size: ${banners?.size}")
        })
        mViewModel.getBanners()
    }

    fun getArticles() {
        mViewModel.homeArticles.observe(this, {
            val articleList = it
            Log.d(TAG, "articleList.size: ${articleList?.size}")
            Utils.showToast("articleList.size: ${articleList?.size}")
        })
        mViewModel.getArticles("kotlin")
    }

}