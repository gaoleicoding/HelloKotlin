package com.gl.kotlin.util

import android.provider.Contacts.People




object GenericTypeUtil {


    fun main() {
        /**
         * 使用 Out (协变) 定义上界通配符
         * <out Number> 等价于 <? extends Number>
         */
        val ints: MutableList<out Number> = mutableListOf()
        /**
         * 可以返回
         */
        val number = ints.get(0)
        /**
         * 修改报错
         */
//        ints.add(0)
        /**
         * 使用 In(逆变) 定义下界通配符
         * <in Long> 等价于 <? super Long>
         */
        val longs: MutableList<in Long> = mutableListOf()
        /**
         * 使用 返回的是 Any 类型,也就是 Object
         */
        val l = longs.get(0)
        /**
         * 修改不报错,成功
         */
        longs.add(0)

        /**
         * <Long> 不变
         */
        val longs2: MutableList<Number> = mutableListOf()
        /**
         * 使用 返回的是 Any 类型,也就是 Object
         */
        val l2 = longs2.get(0)
        /**
         * 修改不报错,成功
         */
        longs2.add(0)
    }

}