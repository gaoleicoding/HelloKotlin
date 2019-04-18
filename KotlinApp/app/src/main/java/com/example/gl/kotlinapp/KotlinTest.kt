package com.example.gl.kotlinapp

object KotlinTest {


    @JvmStatic
    fun main(args: Array<String>) {

        val int1 = 1
        val int2 = 1
        val str1 = "abc"
        val str2 = "abc"

        var student1 = Student("guazi",30)
        var student2 = Student("guazi",30)

        println(int1 == int2)
        println(int1 === int2)
        println (str1 == str2)
        println(str1 === str2)
        println(student1 == student2)
        println(student1 === student2)
        println(student1.hashCode())
        println(student2.hashCode())

    }
}