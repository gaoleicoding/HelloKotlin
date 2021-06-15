package com.gl.kotlin.demo

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object ExtensionFunDemo {

    // 在fragment获取activity并将其cast成MainActivity。
    inline fun <reified T> Any.safeCast(action: T.() -> Unit) {
        if (this is T) {
            this.action()
        }
    }
    // ViewModel生成必须用给定的工厂方法。每次写::class.java我就觉得很烦，所以有了如下这个
    inline fun <reified VM : ViewModel> FragmentActivity.getViewModel(): VM {
        return ViewModelProvider(this).get(VM::class.java)
    }

}