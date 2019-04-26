package com.example.gl.kotlinapp

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking

object KotlinTest2 {



    fun main(args: Array<String>) = runBlocking {
        val jobs = List(1_000_000) {
            launch(CommonPool) {
                delay(10L)
                println(it)
            }
        }
        jobs.forEach { it.join() }
    }
//    fun main(args: Array<String>) {    val threadList=List(1_000_000){
//        Thread{
//            Thread.sleep(10L)
//            println(it)
//        }
//    }
//        threadList.forEach { it.start();it.join() }
//    }


}