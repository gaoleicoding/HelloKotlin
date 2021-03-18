package com.example.gl.kotlinapp

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object KotlinTest2 {

    fun main(args: Array<String>) = runBlocking {
        val jobs = List(1_000_000) {
            launch() {
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