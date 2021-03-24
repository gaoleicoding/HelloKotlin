package com.gl.kotlin.util

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gl.kotlin.entity.User

object KotlinUtil {

    // by lazy真正做到了声明的同时也指定了延迟初始化时的行为，在属性被第一次被使用的时候能自动初始化。
    // 但这些功能是要为此付出一丢丢代价的。
    val TAG: String by lazy {
        javaClass.simpleName
    }
    private val USER: User by lazy {
        User("xiaoming", 12, "456")
    }

    //该方法是于静态公共的
    fun getResult(): String {
        return "test"
    }


    fun testRange() {
        //想遍历1-100的数值可以这样写
        for (index in 1..100) {
            print(index)
        }
        //如果想倒序遍历就该使用标准库中定义的downTo()函数
        for (index in 100 downTo 1) {
            print(index)
        }
        //遍历一个数组/列表，想同时取出下标和元素：
        val array = arrayOf("a", "b", "c")
        for ((index, e) in array.withIndex()) {
            println("下标=$index----元素=$e")
        }
        //我们还可以使用step来定义间隙，代码只会遍历到1、3
        for (i in 1..4 step 2) {
            Log.d(TAG, "i---------" + i)
        }
        //如果想创建一个开区间，可以使用until,就等同于0..3，不包括最后的
        for (i in 0 until 4) {
            Log.d(TAG, "i---------" + i)
        }
        for (i in 10 downTo 0) {
            Log.d(TAG, "i---------" + i)
        }

        val range: IntRange = 1..5
        //等同于0..4，不包括最后的
        val range2: IntRange = 1 until 5

        sum(arg1 = 1, arg2 = 2)

        hello(1, 2, 3, 4, 5)


    }

    fun judgeIfNull(str1: String, str2: String?, str3: String?, user: User?) {

        val str: String = "123"
        //如果?:左边的值不为空返回左边的值，如果为空返回""
        val t: String = str ?: ""
        val s1 = str as? String ?: "not String"
        //如果s为null则会抛出空指针异常，并且异常会指向使用!!的这一行
        val s: String = user!!.toString()
        //如果s为null则会抛出空指针异常
        println(s)
        Log.d(TAG, "length1=" + str1.length)
        // s?.length 就相当于 if(str2!=null) str2.length else null
        Log.d(TAG, "length2=" + str2?.length)
        Log.d(TAG, "length3=" + str3?.length)
        val name = user?.name ?: return

        user.let {
            //输出name}
            println(it.name)
        }
    }

    fun judgeEqual() {
        val a = 12
        /*在Kotlin中，=== 表示比较对象地址，== 表示比较两个值大小*/
        val a1: Int = a
        val a2: Int = a
        println(a1 == a2)   //true
        println(a1 === a2)  //false
    }

    fun judgeWhen() {

        val score = 100
        when (score) {
            in 0..59 -> print("不及格")
            in 60..79 -> print("及格")
            in 80..89 -> print("良好")
            in 90..100 -> print("优秀")
            else -> print("信息有误")     // 效果和if中的else, switch中的default一样
        }
        // 这里能够看到, switch中是不支持a > b这种写法的, 而when中可以
        val a = 3
        val b = 4
        val max = when (a > b) {
            true -> a
            false -> b

        }
        print(max)
    }

    fun testCallback() {
        // 对象表达式（Object Expression）,实现匿名内部类
        val callBack = object : CallBack {
            override fun onFailure() {
                Log.d(TAG, "onFailure---------")
            }

            override fun onSuccess() {
                Log.d(TAG, "onSuccess---------")
            }

        }
        callBack.onSuccess()
    }

    //methodB定义在methodA的方法体中，即methodB被称为局部方法或局部函数
    //methodB只能在methodA中方法调用
    //methodB在methodA方法外调用，会引起编译错误
    fun methodA() {
        fun methodB() {

        }
        methodB() //valid
    }
    //methodB() invalid

    //扩展函数调用
    fun invokeExtension(textView: TextView) {
        textView.isBold()
        textView.leftMargin = 30
    }

    //扩展函数定义
    fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

    // 扩展属性
    fun TextView.isBold() = this.apply {
        paint.isFakeBoldText = true
    }

    //扩展属性
    var TextView.leftMargin: Int
        get():Int {
            return (layoutParams as ViewGroup.MarginLayoutParams).leftMargin
        }
        set(value) {
            (layoutParams as ViewGroup.MarginLayoutParams).leftMargin = value
        }

    /*方法不用中括号直接返回结果*/
    fun sum(arg1: Int, arg2: Int) = arg1 + arg2

    /* vararg 关键字，参数长度可变化*/
    fun hello(vararg ints: Int) {
        ints.forEach(::println)
    }

    fun main(args: Array<String>) {
        val result = "testLet".also {
            println(it.length)
            1000
        }
        println(result)
    }


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
}