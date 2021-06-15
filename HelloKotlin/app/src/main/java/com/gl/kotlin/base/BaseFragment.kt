package com.gl.kotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment() {
    val TAG = javaClass.simpleName
    lateinit var mViewModel: VM
    lateinit var mDataBinding: DB
    private var lazyLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDataBinding = DataBindingUtil.inflate(inflater, getContentViewId(), container, false)
        val view = mDataBinding.root
        initViewModel()
        initView()
        initData()
        return view
    }

    override fun onResume() {
        super.onResume()
        // 实现懒加载
        if (!lazyLoaded) {
            initData()
            lazyLoaded = true
        }
    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    abstract fun getContentViewId(): Int

    /**
     * 获取ViewModel的class
     */
    abstract fun viewModelClass(): Class<VM>

    /**
     * View初始化相关
     */
    open fun initView() {
        // Override if need
    }

    /**
     * 数据初始化相关
     */
    open fun initData() {
        // Override if need
    }


}
