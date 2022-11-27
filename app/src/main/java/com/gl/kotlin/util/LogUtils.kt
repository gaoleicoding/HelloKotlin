package com.gl.kotlin.util

import android.util.Log
import com.gl.kotlin.BuildConfig

/**
 * 日志输出控制类 (Description)
 */
object LogUtils {
    /** 日志输出级别NONE  */
    private const val LEVEL_NONE = 0

    /** 日志输出级别V  */
    private const val LEVEL_VERBOSE = 1

    /** 日志输出级别D  */
    private const val LEVEL_DEBUG = 2

    /** 日志输出级别I  */
    private const val LEVEL_INFO = 3

    /** 日志输出级别W  */
    private const val LEVEL_WARN = 4

    /** 日志输出级别E  */
    private const val LEVEL_ERROR = 5

    /** 日志输出时的TAG  */
    private const val mTag = "QiFu"

    /** 是否允许输出log  */
    @JvmField
    val mDebuggable = if (BuildConfig.DEBUG) LEVEL_ERROR else LEVEL_NONE

    /** 以级别为 d 的形式输出LOG  */
    fun v(tag: String?, msg: String) {
        if (mDebuggable >= LEVEL_VERBOSE) {
            Log.v(tag, msg)
        }
    }

    /** 以级别为 d 的形式输出LOG  */
    fun d(tag: String?, msg: String) {
        if (mDebuggable >= LEVEL_DEBUG) {
            Log.d(tag, msg)
        }
    }

    /** 以级别为 i 的形式输出LOG  */
    fun i(tag: String?, msg: String) {
        if (mDebuggable >= LEVEL_INFO) {
            Log.i(tag, msg)
        }
    }

    /** 以级别为 w 的形式输出LOG  */
    fun w(tag: String?, msg: String) {
        if (mDebuggable >= LEVEL_WARN) {
            Log.w(tag, msg)
        }
    }

    /** 以级别为 e 的形式输出LOG  */
    fun e(tag: String?, msg: String) {
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(tag, msg)
        }
    }
    //--------------------------------------------------
    /** 以级别为 d 的形式输出LOG  */
    fun v(msg: String) {
        if (mDebuggable >= LEVEL_VERBOSE) {
            Log.v(mTag, msg)
        }
    }

    /** 以级别为 d 的形式输出LOG  */
    fun d(msg: String) {
        if (mDebuggable >= LEVEL_DEBUG) {
            Log.d(mTag, msg)
        }
    }

    /** 以级别为 i 的形式输出LOG  */
    fun i(msg: String) {
        if (mDebuggable >= LEVEL_INFO) {
            Log.i(mTag, msg)
        }
    }

    /** 以级别为 w 的形式输出LOG  */
    fun w(msg: String) {
        if (mDebuggable >= LEVEL_WARN) {
            Log.w(mTag, msg)
        }
    }

    /** 以级别为 e 的形式输出LOG  */
    fun e(msg: String) {
        if (mDebuggable >= LEVEL_ERROR) {
            Log.e(mTag, msg)
        }
    }
}