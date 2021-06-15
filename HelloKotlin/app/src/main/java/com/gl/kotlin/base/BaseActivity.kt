package com.gl.kotlin.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.gl.kotlin.fragment.ProgressDialogFragment

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {
    val TAG = javaClass.simpleName
    lateinit var mViewModel: VM
    lateinit var mDataBinding: DB
    private lateinit var progressDialogFragment: ProgressDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewDataBinding() //初始化 DataBinding
        initViewModel()//初始化 ViewModel
        initView(savedInstanceState)//初始化控件
        initData()//初始化数据
    }


    abstract fun getLayoutId(): Int //获取布局文件
    abstract fun viewModelClass(): Class<VM>//获取 ViewModel 类
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()

    private fun initViewDataBinding() {
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mDataBinding.lifecycleOwner = this
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    fun showProgressDialog(@StringRes message: Int) {
        if (!this::progressDialogFragment.isInitialized) {
            progressDialogFragment = ProgressDialogFragment.newInstance()
        }
        if (!progressDialogFragment.isAdded) {
            progressDialogFragment.show(supportFragmentManager, message, false)
        }
    }

    /**
     * 隐藏加载(转圈)对话框
     */
    fun dismissProgressDialog() {
        if (this::progressDialogFragment.isInitialized && progressDialogFragment.isVisible) {
            progressDialogFragment.dismissAllowingStateLoss()
        }
    }
}