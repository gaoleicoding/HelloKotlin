package com.example.gl.kotlinapp

/**
 *Kotlin的构造函数分为主构造器（primary constructor）和次级构造器（secondary constructor）
 *当constructor关键字没有注解和可见性修饰符作用于它时，constructor关键字可以省略（当然，如果有这些修饰时，是不能够省略的，
 *并且constructor关键字位于修饰符后面
*/
class Student(val name: String) {
     var age =0

    constructor(name: String, age: Int) : this(name) {
        this.age = age
        println("secondary init person")
    }

    init {
        println("init person")
    }
}