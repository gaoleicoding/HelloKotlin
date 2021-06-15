package com.gl.kotlin.fragment

import android.widget.Toast
import com.gl.kotlin.R
import com.gl.kotlin.base.BaseFragment
import com.gl.kotlin.databinding.FragmentHomeBinding
import com.gl.kotlin.viewmodel.WanAndroidViewModel

class HomeFragment : BaseFragment<WanAndroidViewModel, FragmentHomeBinding>() {


    override fun initData() {

        mViewModel.banners.observe(viewLifecycleOwner, {
            val content: List<String> = it!!.map { banner ->
                banner.title
            }
            mDataBinding.textView.text = content.toTypedArray().contentToString()
        })
        mViewModel.toastMsg.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
//        viewModel.hotKeys.observe(this, {
//            val content: List<String> = it.map { key ->
//                key.name
//            }
//            binding.text.text = content.toTypedArray().contentToString()
//        })
//        viewModel.viewModelSequenceRequest()
    }


    override fun getContentViewId(): Int = R.layout.fragment_home

    override fun viewModelClass(): Class<WanAndroidViewModel> = WanAndroidViewModel::class.java

}