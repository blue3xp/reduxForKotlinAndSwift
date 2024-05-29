package com.example.reduxforandroid

import com.example.reduxforandroid.counter.CounterState
import com.example.reduxforandroid.counter.counterReducer
import com.example.reduxforandroid.redux.Reducer
import com.example.reduxforandroid.redux.applyMiddleware
import com.example.reduxforandroid.redux.combineReducers
import com.example.reduxforandroid.redux.createStore
import com.example.reduxforandroid.redux.middleware.createLoggerMiddleware
import com.example.reduxforandroid.redux.middleware.createThunkMiddleware
import com.example.reduxforandroid.shoppingcart.domain.Product
import com.example.reduxforandroid.shoppingcart.domain.ShoppingCart
import com.example.reduxforandroid.shoppingcart.domain.UserState
import com.example.reduxforandroid.shoppingcart.domain.productEntityReducer
import com.example.reduxforandroid.shoppingcart.domain.shoppingCartEntityReducer
import com.example.reduxforandroid.shoppingcart.domain.userReducer
import com.example.reduxforandroid.todos.domain.DataListReducer
import com.example.reduxforandroid.todos.domain.ToDoState
import com.example.reduxforandroid.todos.domain.toDoRootReducer
import com.example.reduxforandroid.todos.domain.visibilityFilterReducer

data class Entity<State>(
    val byId: Map<String, State>,
    val allIds: List<String>
)

/**
 * Entire state tree for the app.
 */
data class Entities(
    val productEntity: Entity<Product> = Entity(byId = mapOf(),
        allIds = listOf()),
    val shoppingCartEntity: Entity<ShoppingCart> = Entity(byId = mapOf(
        "shoppingCart1" to ShoppingCart(
            id = "shoppingCart1",
            items = listOf(),
        ),
    ),
        allIds = listOf("shoppingCart1"))
)
data class AppState(
    val counterState: CounterState = CounterState(),
    val toDoState: ToDoState = ToDoState(),
    val userState: UserState = UserState(),
    val entities: Entities = Entities()
)

val entitiesRootReducer: Reducer<Entities> = { state, action ->
    Entities(
        productEntity = productEntityReducer(state.productEntity, action),
        shoppingCartEntity = shoppingCartEntityReducer(state.shoppingCartEntity, action)
    )
}

val appReducer: Reducer<AppState> = { state, action ->
    AppState(
        counterState = counterReducer(state.counterState, action),
        toDoState = toDoRootReducer(state.toDoState, action),
        userState = userReducer(state.userState,action),
        entities = entitiesRootReducer(state.entities, action)
    )
}


val store = createStore(
    appReducer, AppState(),
    applyMiddleware(createThunkMiddleware(), createLoggerMiddleware())
);
