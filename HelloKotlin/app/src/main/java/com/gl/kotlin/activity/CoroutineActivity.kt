package com.gl.kotlin.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gl.kotlin.databinding.ActivityCoroutineBinding
import com.gl.kotlin.util.CoroutineUtil


class CoroutineActivity : AppCompatActivity() {

    private val mBinding: ActivityCoroutineBinding by lazy {
        ActivityCoroutineBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
    }

    fun startCoroutine(view: View) {
        CoroutineUtil.startCoroutine()
    }

    fun lifecycleCoroutine(view: View) {
        CoroutineUtil.lifecycleCoroutine(this)
    }

    fun startBlockingCoroutine(view: View) {
        CoroutineUtil.startBlockingCoroutine()
    }

    fun switchCoroutineThread(view: View) {
        CoroutineUtil.switchCoroutineThread()
    }


}