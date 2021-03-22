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

    fun lambdaCode() {

        // 一、回调函数的Kotin的lambda的简化
        // new 一个线程 匿名类写法
        val runnable1 = object : Runnable {
            override fun run() {
                println("I'm an anonymous class")
            }
        }
        // 函数写法, 略像js
        val runnable2 = fun() {
            println("I'm a function")
        }
        // lambda写法1
        val runnable3 = Runnable { ->
            println("I'm a Lambda")
        }
        // lambda写法2
        val runnable4 = { println("I'm a Lambda") }
        Thread(runnable4).start()
    }

    fun inlineFunctionCode() {

        // 二、内联扩展函数之let
        // let扩展函数的实际上是一个作用域函数，当你需要去定义一个变量在一个特定的作用域范围内，let函数的是一个不错的选择；
        // let函数另一个作用就是可以避免写一些判断null的操作。
        val result1 = "testLet".let {
            println(it.length)
            1000
        }
        println(result1)

        // 三、内联函数之with
        // with函数和前面的几个函数使用方式略有不同，因为它不是以扩展的形式存在的。它是将某对象作为函数的参数，
        // 在函数块内可以通过 this 指代该对象。返回值为函数块的最后一行或指定return表达式。

        val user2 = User("Kotlin", 1, "123")
        val result2 = with(user2) {
            println("my name is $name, I am $age years old, my phone number is $id")
            1000
        }
        println("result: $result2")

        // 四、内联扩展函数之run
        // run函数实际上可以说是let和with两个函数的结合体，run函数只接收一个lambda函数为参数，
        // 以闭包形式返回，返回值为最后一行的值或者指定的return的表达式。

        val user3 = User("Kotlin", 1, "789")

        val result3 = user3.run {
            println("my name is $name, I am $age years old")
            1000
        }
        println("result: $result3")

        // 五、内联扩展函数之apply
        // also函数的结构实际上和let很像唯一的区别就是返回值的不一样，let是以闭包的形式返回，返回函数体内最后一行的值，
        // 而also函数返回的则是传入对象的本身
        val result = "testLet".also {
            println(it.length)
            1000
        }
        println(result)
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

        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        list.filter { it % 2 == 0 }             // 取偶数
                .map { it * it }               // 平方
                .sortedDescending()         // 降序排序
                .take(3)                    // 取前 3 个
                .forEach { println(it) }    // 遍历, 打印
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


    fun higherFunctioCode() {
        /*1、高阶函数*/
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8)
        println(numbers.filter(
                fun(x: Int): Boolean {
                    return x > 5
                }
        ))
        /*2、匿名函数*/
        val numbers2 = listOf(1, 2, 3, 4, 5, 6, 7, 8)
        println(numbers2.filter(
                fun(x: Int): Boolean {
                    return x > 5
                }
        ))
        /* 3、Lambda表达式*/
        val numbers3 = listOf(1, 2, 3, 4, 5, 6, 7, 8)
        println(numbers3.filter { it > 5 })
        for (a in numbers3) {
            println(numbers3.filter { it > a })
        }

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