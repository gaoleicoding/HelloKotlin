package com.gl.kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.knowledge.adapter.ItemAdapter
import com.gl.kotlin.R
import com.gl.kotlin.databinding.ActivityMainBinding
import com.gl.kotlin.entity.DataUser
import com.gl.kotlin.entity.User
import com.gl.kotlin.util.KotlinUtil


class MainActivity : AppCompatActivity() {

    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val items = arrayOf(
            "FlowActivity",
            "EncryptActivity"
    )
    private val activities = arrayOf<Class<*>>(
            FlowActivity::class.java,
            HotCityActivity::class.java
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("info", "MainActivity--onCreate()")
        setContentView(mBinding.root)

        mBinding.textView.setText("Hello Kotlin")

        // 内联扩展函数之let
        // let扩展函数的实际上是一个作用域函数，当你需要去定义一个变量在一个特定的作用域范围内，
        // let函数的是一个不错的选择；let函数另一个作用就是可以避免写一些判断null的操作。
        mBinding.textView.let {
            it.setTextColor(resources.getColor(R.color.colorPrimary))
            it.setTextIsSelectable(true)
        }

        //由于setEventListener这个函数只有一个参数，可以直接省略圆括号
        mBinding.textView.setOnClickListener {
            KotlinUtil.judgeIfNull("gao", null, "null", null)
        }

        val layoutManager = LinearLayoutManager(this)
        mBinding.recyclerview.layoutManager = layoutManager
        val itemAdapter = ItemAdapter(this, items)
        mBinding.recyclerview.adapter = itemAdapter
        mBinding.recyclerview.addItemDecoration(
                DividerItemDecoration(this@MainActivity,
                        DividerItemDecoration.VERTICAL
                )
        )
        itemAdapter.setOnItemClickLitener(object : ItemAdapter.OnItemClickLitener {
            override fun onItemClick(v: View) {
                val position = mBinding.recyclerview.getChildAdapterPosition(v)
                startActivity(Intent(this@MainActivity, activities[position]))
            }
        })

        var user: User = User("gao", 1, "123");
        var dataUser: DataUser = DataUser("gao", 1, "123");
        Log.i(TAG, "user: " + user.toString())
        Log.i(TAG, "dataUser: " + dataUser.toString())
    }


    override fun onStart() {
        super.onStart()
        Log.i(TAG, "MainActivity--onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "MainActivity--onResumed()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "MainActivity--onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "MainActivity--onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "MainActivity--onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "MainActivity--onDestroy()")
    }

    companion object {
        private const val TAG = "MainActivity"

    }
}