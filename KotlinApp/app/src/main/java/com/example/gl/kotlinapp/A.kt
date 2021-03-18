package com.example.gl.kotlinapp

interface A {
    fun foo() {
        println("A")
    }    // 默认实现, 打印"A"

    fun bar()
}

interface B {
    fun foo() {
        println("B")
    }
}

// 多实现时，显式指定 super<A>.foo() 以去冲突
class D : A, B {
    override fun bar() {
    }

    override fun foo() {
        super<A>.foo()
        super<B>.foo()
    }
}