package com.example.task_final_g7.data.local

import junit.framework.TestCase
import org.junit.Test
import org.mockito.Mock

class UserTest : TestCase(){

    fun validateUser(user: User) : Boolean {
        if (user.username!!.isNotEmpty() && user.email!!.isNotEmpty()) {
            return true
        }
        return false
    }
    fun validateUser1(user: User) : Boolean {
        if (user.password!!.isNotEmpty() && user.fullName!!.isNotEmpty()) {
            return true
        }
        return false
    }
}