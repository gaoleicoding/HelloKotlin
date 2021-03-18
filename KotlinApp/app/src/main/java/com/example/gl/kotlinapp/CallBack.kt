package com.example.gl.kotlinapp

interface CallBack {

    fun onSuccess()
    fun onFailure()
    interface A {
        fun foo() {
            println("A")
        }    // 默认实现, 打印"A"

        fun bar()
    }
}