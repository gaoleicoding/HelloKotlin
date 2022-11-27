package com.gl.kotlin.model


class User(var name: String, var age: Int, var id: String) {
//
//    override fun equals(other: Any?): Boolean {
//        if (this === other) {
//            return true
//        }
//        if (other is User) {
//            if (other.age == this.age && other.name === this.name && other.id === this.id) {
//                return true
//            }
//        }
//
//        return false
//    }
//
//    override fun hashCode(): Int {
//        var result = name.hashCode()
//        result = 31 * result + age
//        result = 31 * result + id.hashCode()
//        return result
//    }
}