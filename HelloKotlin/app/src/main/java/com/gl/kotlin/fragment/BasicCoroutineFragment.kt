package com.gl.kotlin.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gl.kotlin.databinding.FragmentBasicCoroutineBinding
import com.gl.kotlin.retrofit.MainViewModel

class BasicCoroutineFragment : Fragment() {

    private val binding: FragmentBasicCoroutineBinding by lazy {
        FragmentBasicCoroutineBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        viewModel.banners.observe(this, {
            val content: List<String> = it.map { banner ->
                banner.title
            }
            binding.text.text = content.toTypedArray().contentToString()
        })
        viewModel.toastMsg.observe(this, {
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

}