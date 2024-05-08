package com.example.reduxforandroid.shoppingcart.domain

import android.util.Log
import com.example.reduxforandroid.Entity
import com.example.reduxforandroid.counter.CounterState
import com.example.reduxforandroid.counter.Decrement
import com.example.reduxforandroid.counter.Increment
import com.example.reduxforandroid.counter.LoadingComplete
import com.example.reduxforandroid.counter.StartLoading
import com.example.reduxforandroid.redux.Reducer
import com.example.reduxforandroid.todos.domain.AddTodo
import com.example.reduxforandroid.todos.domain.Todo
import com.example.reduxforandroid.todos.domain.ToggleTodo


data class Product(
    val id: String,
    val name: String,
    val price: String,
    val quantity: Int
)

data class ShoppingCart(
    val id: String,
    val items: List<String> //the list of product id
)



data class GetProductList(val keyword: String)

data class AddProductIntoCart(val productId: String,val shoppingCartId: String)

data class RemoveProductIntoCart(val productId: String,val shoppingCartId: String)

data class UpdateProductNumInCart(val productId: String,val num:Int,val shoppingCartId: String)

val productEntityReducer: Reducer<Entity<Product>> = { state, action ->
    when (action) {
        is GetProductList -> getProductListHandle(state,action)
        is UpdateProductNumInCart -> updateProductNumInCartHandle(state,action)
        else -> state
    }
}

val shoppingCartEntityReducer: Reducer<Entity<ShoppingCart>> = { state, action ->
    when (action) {
        is AddProductIntoCart -> addProductIntoCartHandle(state,action)
        is RemoveProductIntoCart -> removeProductIntoCartHandle(state,action)
        else -> state
    }
}

fun addProductIntoCartHandle(state: Entity<ShoppingCart>, action: AddProductIntoCart):Entity<ShoppingCart>{
    Log.d("CartSlice","action productId"+ action.productId + "shopping cartID"+ action.shoppingCartId )
    var tmp = state.byId.toMutableMap()
    var shoppingCart = tmp[action.shoppingCartId]
    if (shoppingCart == null)
        return state
    else{
        var itemList = shoppingCart.items.toMutableList()
        itemList.add(action.productId)
        val newShoppingCart = ShoppingCart(id = action.shoppingCartId, items = itemList)
        tmp[action.shoppingCartId] = newShoppingCart
        return state.copy(byId = tmp)
    }
}

fun removeProductIntoCartHandle(state: Entity<ShoppingCart>, action: RemoveProductIntoCart):Entity<ShoppingCart>{
    Log.d("CartSlice","action productId"+ action.productId + "action shoppingCartId" + action.shoppingCartId)
    var tmp = state.byId.toMutableMap()
    var shoppingCart = tmp[action.shoppingCartId]
    if (shoppingCart == null)
        return state
    else{
        var itemList = shoppingCart.items.toMutableList()
        itemList.remove(action.productId)
        val newShoppingCart = ShoppingCart(id = action.shoppingCartId, items = itemList)
        tmp[action.shoppingCartId] = newShoppingCart
        return state.copy(byId = tmp)
    }
}

fun getProductListHandle(state: Entity<Product>, action: GetProductList):Entity<Product>{
//    Log.d("CartSlice","action keyword"+ action.keyword)
    return state.copy(byId = mapOf(
        "product1" to Product(
            id = "product1",
            name = "user1",
            price = "20",
            quantity = 1
        ),
        "product2" to Product(
            id = "product2",
            name = "user2",
            price = "30",
            quantity = 2
        ),
    ),
        allIds = listOf("product1", "product2"));
}

fun replaceValueInMap(originalMap: Map<String, Product>, key: String, newValue: Product): Map<String, Product> {
    val newMap = originalMap.toMutableMap()
    newMap[key] = newValue
    return newMap
}

fun updateProductNumInCartHandle(state: Entity<Product>, action: UpdateProductNumInCart):Entity<Product>{
    Log.d("CartSlice","action productId"+ action.productId +" action num" + action.num)
    var tmp = state.byId
    var productEntity = tmp[action.productId]
    if (productEntity == null){
        return state;
    }
    else{
        val newProduct = Product(id = action.productId, name = productEntity.name, price = productEntity.price, quantity = action.num)
        val newMap = replaceValueInMap(tmp, action.productId, newProduct)
        return state.copy(byId = newMap);
    }

}







