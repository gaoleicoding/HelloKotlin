package com.gl.kotlin.util

class ReflectionUtil {


    class User {
        var userName: String = "Czh"
            get() = field
            set(value) {
                field = value
            }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            //方式一
            var user = User()

            //获取属性对象
            var userName = User::userName
            println(userName.get(user))
            //设置属性值
            userName.set(user, "James")
            //获取属性值
            println(userName.get(user))

            //方式二
            //利用Java反射机制获取getUserName方法
            var getName = User::class.java.getMethod("getUserName")

            //利用Java反射机制获取setUserName方法
            var setName = User::class.java.getMethod("setUserName", java.lang.String().javaClass)
            //设置属性值
            setName.invoke(user, "Harden")
            //获取属性值
            println(getName.invoke(user))
        }
    }
}