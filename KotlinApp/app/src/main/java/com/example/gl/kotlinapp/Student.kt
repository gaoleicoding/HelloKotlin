package com.example.gl.kotlinapp


/**
 *Kotlin的构造函数分为主构造器（primary constructor）和次级构造器（secondary constructor）
 *当constructor关键字没有注解和可见性修饰符作用于它时，constructor关键字可以省略（当然，如果有这些修饰时，是不能够省略的，
 *并且constructor关键字位于修饰符后面
 */
class Student(name: String, age: Int) {
    var age = 0
    var name = ""

//    constructor(name: String, age: Int) {
//        this.age = age
//        this.name = name
//        println("secondary init person")
//    }

    init {
        this.age = age
        this.name = name
        println("init person")
    }

//    override fun hashCode(): Int {
//        val sb = StringBuilder()
//        sb.append(age)
//        sb.append(name)
//        val charArr = sb.toString().toCharArray()
//        var hash = 0
//
//        for (c in charArr) {
//            hash = hash * 131 + c.toInt()
//        }
//        return hash
//    }
//
//    override fun equals(obj: Any?): Boolean {
//        if (this === obj) {
//            return true
//        }
//
//        if (obj is Student) {
//            if (obj.age.equals(this.age) && obj.name.equals(this.name)) {
//                return true
//            }
//        }
//
//        return false
//    }
}