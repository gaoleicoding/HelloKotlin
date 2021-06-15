package com.gl.kotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.gl.kotlin.R
import com.gl.kotlin.model.CityEntity
import com.gl.kotlin.util.KotlinUtil.isBold


class HotCityAdapter(var context: Context, var list: List<CityEntity>) : androidx.recyclerview.widget.RecyclerView.Adapter<HotCityAdapter.MyViewHolder>() {
    internal var listener: OnItemClickListener? = null

    fun changeList(list: List<CityEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_hot_city, null)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val bean = list[position]
        holder.tv_city.text = bean.location
        holder.root_view.setOnClickListener { listener?.onItemClick(holder.root_view, position) }

        val item = list[position]

        /*
          内联函数之with
          适用于调用同一个类的多个方法时，可以省去类名重复，直接调用类的方法即可，经常用于Android中RecyclerView中onBinderViewHolder中
           */
        with(item) {
            holder.tv_city.text = location
            holder.tv_max.text = tmp_max
            holder.tv_min.text = tmp_min
        }
        /*
          内联扩展函数之run
          适用于let,with函数任何场景。因为run函数是let,with两个函数结合体，准确来说它弥补了let函数在函数体内必须使用it参数替代对象，在run函数中可以像with函数一样可以省略，
          直接访问实例的公有属性和方法，另一方面它弥补了with函数传入对象判空问题，在run函数中可以像let函数一样做判空处理
          */
        list[position]?.run {
            holder.tv_city.text = location
            holder.tv_max.text = tmp_max
            holder.tv_min.text = tmp_min

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class MyViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        var tv_city: TextView
        var tv_max: TextView
        var tv_min: TextView
        var root_view: RelativeLayout


        init {
            tv_city = view.findViewById(R.id.tv_city)
            tv_max = view.findViewById(R.id.tv_max)
            tv_min = view.findViewById(R.id.tv_min)
            root_view = view.findViewById(R.id.root_view)
            tv_city.isBold()
        }

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, postion: Int)
    }

}