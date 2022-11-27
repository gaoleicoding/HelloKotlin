package com.gl.kotlin.retrofit

class ApiException(var code: Int, override var message: String) : RuntimeException()