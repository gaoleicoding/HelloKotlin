package com.example.gl.kotlinapp

import android.content.Context
import android.os.Looper
import android.widget.TextView
import android.widget.Toast

//该方法是于静态公共的
fun getResult(): String {
    return "test"
}

fun TextView.isBold() = this.apply {
    paint.isFakeBoldText = true
}

/**
 * 对象声明（Object Declaration），将类的声明和定义该类的单例对象结合在一起（即通过object就实现了单例模式）
 */
object Utils {

    fun showToast(content: String, isShort: Boolean) {
        try {
            val toast = Toast.makeText(CustomApplication.context, content, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG)
            toast.show()
        } catch (e: Exception) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare()
            Toast.makeText(CustomApplication.context, content, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
            Looper.loop()
        }

    }

    //扩展函数
    fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}