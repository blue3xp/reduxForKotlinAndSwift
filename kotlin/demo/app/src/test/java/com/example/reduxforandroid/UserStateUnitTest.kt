package com.example.reduxforandroid

import com.example.reduxforandroid.shoppingcart.domain.GetProductList
import com.example.reduxforandroid.shoppingcart.domain.SetName
import org.junit.Assert
import org.junit.Test

class UserStateUnitTest {

    @Test
    fun setUserNameHandle_isCorrect() {

        store.dispatch(SetName("test"))
        val name = store.getState().userState.name
        Assert.assertEquals("test", name)
    }
}