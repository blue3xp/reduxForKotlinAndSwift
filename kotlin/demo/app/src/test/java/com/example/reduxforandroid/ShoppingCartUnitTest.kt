package com.example.reduxforandroid

import com.example.reduxforandroid.shoppingcart.domain.GetProductList
import org.junit.Assert
import org.junit.Test

class ShoppingCartUnitTest {

    @Test
    fun getProductListHandle_isCorrect() {

        store.dispatch(GetProductList("test"))
        val listSize = store.getState().entities.productEntity.allIds.size
        Assert.assertEquals(2, listSize)
    }
}