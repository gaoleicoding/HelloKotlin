package com.example.gl.kotlinapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView;
    lateinit var student: Student
    lateinit var city_recyclerview: RecyclerView
    lateinit var hotCityAdapter: HotCityAdapter
    lateinit var hotCityList: List<CityAddBean>
    val cities: Array<CityAddBean> = arrayOf(CityAddBean("北京", "10", "20"), CityAddBean("上海", "10", "20"), CityAddBean("广州", "10", "20"), CityAddBean("深圳", "10", "20"), CityAddBean("杭州", "10", "20"), CityAddBean("郑州", "10", "20"))

    val TAG: String = "TAG_MainActivity"

    //lateinit 只用于变量 var，而 lazy 只用于常量 val
    val lazyValue: String by lazy {
        println("computed!")
        "Hello"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        city_recyclerview = findViewById(R.id.city_recyclerview)

        textView.setText("Hello Kotlin")

        // 内联扩展函数之let
        // let扩展函数的实际上是一个作用域函数，当你需要去定义一个变量在一个特定的作用域范围内，
        // let函数的是一个不错的选择；let函数另一个作用就是可以避免写一些判断null的操作。
        textView?.let {
            it.setTextColor(resources.getColor(R.color.colorPrimary))
            it.setTextIsSelectable(true)
        }


        textView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        textView.setOnClickListener({ view: View ->
            Utils.showToast("i click", false)
            //todo
        })
        //如果以上代码中的view参数没有使用到的话，可以直接把data去掉
        textView.setOnClickListener({
            Utils.showToast("i click", false)
            //todo
        })
        //由于setEventListener这个函数只有一个参数，可以直接省略圆括号
        textView.setOnClickListener {
            //todo
        }
        test()
    }

    fun test() {
        student = Student("xiaoming", 12)
        student.name
        student.age

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

        var score = 100
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

        //内联扩展函数之also
        //also函数的结构实际上和let很像唯一的区别就是返回值的不一样，let是以闭包的形式返回，返回函数体内最后一行的值，
        // 而also函数返回的则是传入对象的本身
        val result = "testLet".also {
            println(it.length)
            1000
        }
        println(result)

        initHotCityRecyclerView()

        //扩展函数调用
        textView.isBold()
        /*在Kotlin中，=== 表示比较对象地址，== 表示比较两个值大小*/
        val a1: Int? = a
        val a2: Int? = a
        println(a1 == a2)   //true
        println(a1 === a2)  //false

        var str: String = "123"
        val t: String = str ?: "" //如果?:左边的值不为空返回左边的值，如果为空返回""
        str as? String ?: "not String"
        var s: String = str!!  //如果s为null则会抛出空指针异常，并且异常会指向使用!!的这一行
        println(s)//如果s为null则会抛出空指针异常
    }

    /*
  methodB定义在methodA的方法体中，即methodB被称为局部方法或局部函数
  methodB只能在methodA中方法调用
  methodB在methodA方法外调用，会引起编译错误
  */
    fun methodA() {
        fun methodB() {

        }
        methodB() //valid
    }

    //methodB() invalid
    //扩展函数定义
    fun TextView.isBold() = this.apply {
        paint.isFakeBoldText = true
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

    private fun initHotCityRecyclerView() {
        hotCityList = cities.toList() as ArrayList<CityAddBean>
        hotCityAdapter = HotCityAdapter(this, hotCityList)
        val gridLayoutManager = GridLayoutManager(this, 3)
        city_recyclerview.layoutManager = gridLayoutManager
        city_recyclerview.adapter = hotCityAdapter
        hotCityAdapter.setOnItemClickListener(object : HotCityAdapter.OnItemClickListener {
            override fun onItemClick(view: View, postion: Int) {
                val bean = hotCityList[postion]
                Utils.showToast(bean.location, false)
            }
        })

    }
}
