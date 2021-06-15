package com.gl.kotlin.util

import com.gl.kotlin.model.User

object HigherFunctionUtil {


    fun invoke() {
        println("invoke----------------begin")
        println(lambda1(2, 3))
        println(lambda2.invoke(2, 3))
        println(anonymousFunction1.invoke(2, 3))
        println(anonymousFunction2.invoke(2, 3))
        println(method3(10) { num1: Int, num2: Int -> num1 * num2 })
        println(method4(10) { a, b -> a * b })
        val test = method(3)
        println(test())            //7
        println(test.invoke())     //8
        println(test())            //9
        println(test.invoke())     //10
        println("invoke----------------end")
    }

    //匿名函数（有fun，没名字）
    val anonymousFunction1: (Int, Int) -> Int = fun(a: Int, b: Int) = a + b
    //或者
    val anonymousFunction2 = fun(a: Int, b: Int) = a + b

    // lambda(有大括号)
    val lambda1: (Int, Int) -> Int = { a, b -> a + b }
    // 或者
    val lambda2 = { a: Int, b: Int -> a + b }

    // method 带 lambda
    fun method3(a: Int, b: (num1: Int, num2: Int) -> Int): Int {
        return a + b.invoke(3, 5)
    }
    //或者
    fun method4(a: Int, b: (num1: Int, num2: Int) -> Int): Int =
            a + b.invoke(3, 5)

    /**
     * 闭包，即是函数中包含函数，作用：一个是可以读取函数内部的变量，另一个就是让这些变量的值始终保持在内存中
     * 这里的函数我们可以包含（Lambda表达式，匿名函数，局部函数，对象表达式）。
     * 参数 b
     * 返回 匿名函数
     */
    fun method(b: Int): () -> Int {
        var a = 3
        return fun(): Int {
            a++
            return a + b
        }
    }

    fun runnableLambda() {

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

    fun inlineFunction() {

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

    fun streamCollection() {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        list.filter { it % 2 == 0 }             // 取偶数
                .map { it * it }               // 平方
                .sortedDescending()         // 降序排序
                .take(3)                    // 取前 3 个
                .forEach { println(it) }    // 遍历, 打印
    }

    fun forEach() {
        //定义一个list
        val list = listOf(1, 3, 4, 5, 6, 7, 43, 63)
        //再定义一个arrayList
        val newList = ArrayList<Int>()

        //一个集合的映射
        list.forEach {
            val newElement = it * 2 + 3
            newList.add(newElement)
        }

        newList.forEach(::println)
    }

    fun map() {
        //1.map:将List中每个元素转换成新的元素,并添加到一个新的List中,最后将新List返回
        arrayOf(1, 2, 3).map { i: Int -> i * 10 }.forEach(::println)
        /**
         * 打印输出
         * 10
         * 20
         * 30
         */
    }

    fun flatMap() {
        //2.flatMap:将数组中全部元素按顺序组成一个list
        //注意:lambda表达式中的参数类型可以不写.如:List<String>和IntRange
        listOf(listOf("a", "b"), listOf("c", "d")).flatMap { i: List<String> -> i.asIterable() }.forEach(::println)
        println("----------")
        arrayOf(1..3, 1..5).flatMap { i: IntRange -> i.asIterable() }.forEach(::println)
        /**
         * 打印输出
         * a
         * b
         * c
         * d
         * ----------
         * 1
         * 2
         * 3
         * 1
         * 2
         * 3
         * 4
         * 5
         */
    }

    fun fold() {
        /**
         * 3.fold:将集合中的元素依次冒泡组合,最终得到一个结果
         * 第一次执行时,由初始值10作为参数a,由集合中第0个元素作为参数b
         * 第二次执行时,第一次执行的返回值作为参数a,由集合中第1个元素作为参数b
         * 依次类推...
         * 最终将结果返回
         */
        val foldResult1 = arrayOf(1, 2, 3).fold(10, { a, b -> a + b })//计算过程为10+1+2+3,等于16
        val foldResult2 = arrayOf(1, 2, 3).fold(10, { a, b -> a * b })//计算过程为10*1*2*3,等于60
        println(foldResult1)
        println(foldResult2)
        /**
         * 打印输出
         * 16
         * 60
         */
    }

    fun reduce() {
        /**
         * 4.reduce:与fold类似,区别是reduce没有初始值
         */
        val reduceResult1 = arrayOf(1, 2, 3, 4).reduce { acc, i -> acc + i }//计算过程为1+2+3+4,等于10
        val reduceResult2 = arrayOf(1, 2, 3, 4).reduce { acc, i -> acc * i }//计算过程为1*2*3*4,等于24
        val reduceResult3 = arrayOf("a", "b", "c", "d").reduce { acc, i -> "$acc,$i" }
        println(reduceResult1)
        println(reduceResult2)
        println(reduceResult3)
        /**
         * 打印输出
         * 10
         * 24
         * a,b,c,d
         */
    }

    fun joinToString() {
        /**
         * 5.joinToString:为集合元素添加分隔符,组成一个新的字符串并返回
         */
        val joinToStringResult1 = arrayOf("a", "b", "c", "d").joinToString { i -> i }
        val joinToStringResult2 = arrayOf("a", "b", "c", "d").joinToString(separator = "#", prefix = "[前缀]", postfix = "[后缀]", limit = 3, truncated = "[省略号]") { i -> i }
        println(joinToStringResult1)
        println(joinToStringResult2)
        /**
         * 打印输出
         * a, b, c, d
         * [前缀]a#b#c#[省略号][后缀]
         */
    }

    fun filter() {
        /**
         * 6.filter:将中的元素遍历,把符合要求的元素添加到新的list中,并将新list返回
         */
        //根据元素值来过滤
        arrayOf(1, 2, 3, 0, 4).filter { i -> i >= 2 }.forEach(::println)
        println("----------")
        //根据索引来过滤
        arrayOf(1, 2, 3, 0, 4).filterIndexed { index, i -> index >= 2 }.forEach(::println)
        /**
         * 打印输出
         * 2
         * 3
         * 4
         * ----------
         * 3
         * 0
         * 4
         */
    }

    fun takeWhile() {
        /**
         * 7.takeWhile:遍历list中的元素,将符合要求的元素添加到新集合中
         * 注意:一旦遇到不符合要求的,直接终止
         */
        //注意:返回的集合中只有4和3,没有5,因为遇到2时,不符合要求,程序直接终止
        arrayOf(4, 3, 2, 5).takeWhile { i -> i > 2 }.forEach(::println)
        /**
         * 打印输出
         * 4
         * 3
         */
    }

}







