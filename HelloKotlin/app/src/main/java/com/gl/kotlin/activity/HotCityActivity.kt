package com.gl.kotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.gl.kotlin.model.CityEntity
import com.gl.kotlin.adapter.HotCityAdapter
import com.gl.kotlin.util.Utils
import com.gl.kotlin.util.Utils.toast
import com.gl.kotlin.databinding.ActivityRecyclerBinding
import java.util.*

class HotCityActivity : AppCompatActivity() {


    lateinit var hotCityAdapter: HotCityAdapter
    lateinit var hotCityList: List<CityEntity>

    private val cities: Array<CityEntity> by lazy {
        arrayOf(CityEntity("北京", "10", "20"), CityEntity("上海", "10", "20"), CityEntity("广州", "10", "20"), CityEntity("深圳", "10", "20"), CityEntity("杭州", "10", "20"), CityEntity("郑州", "10", "20"))
    }

    private val mBinding: ActivityRecyclerBinding by lazy {
        ActivityRecyclerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initHotCityRecyclerView()
    }

    private fun initHotCityRecyclerView() {
        hotCityList = cities.toList() as ArrayList<CityEntity>
        hotCityAdapter = HotCityAdapter(this, hotCityList)
        val gridLayoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 3)
        mBinding.cityRecyclerview.layoutManager = gridLayoutManager
        mBinding.cityRecyclerview.adapter = hotCityAdapter
        hotCityAdapter.setOnItemClickListener(object : HotCityAdapter.OnItemClickListener {
            override fun onItemClick(view: View, postion: Int) {
                val bean = hotCityList[postion]
                Utils.showToast(bean.location, false)
                toast(bean.location)
            }
        })

    }
}
