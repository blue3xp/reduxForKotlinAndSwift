package com.example.reduxforandroid

import com.example.reduxforandroid.counter.CounterState
import com.example.reduxforandroid.counter.counterReducer
import com.example.reduxforandroid.redux.Reducer
import com.example.reduxforandroid.redux.applyMiddleware
import com.example.reduxforandroid.redux.combineReducers
import com.example.reduxforandroid.redux.createStore
import com.example.reduxforandroid.redux.middleware.createLoggerMiddleware
import com.example.reduxforandroid.redux.middleware.createThunkMiddleware
import com.example.reduxforandroid.todos.domain.DataListReducer
import com.example.reduxforandroid.todos.domain.ToDoState
import com.example.reduxforandroid.todos.domain.toDoRootReducer
import com.example.reduxforandroid.todos.domain.visibilityFilterReducer

/**
 * Entire state tree for the app.
 */
data class AppState(
    val counterState: CounterState = CounterState(),
    val toDoState: ToDoState = ToDoState()
)

//class AppDynamicState {
//    private val properties = mutableMapOf<String, Any>()
//
//    fun setProperty(name: String, value: Any) {
//        properties[name] = value
//    }
//
//    fun getProperty(name: String): Any? {
//        return properties[name]
//    }
//
//    fun removeProperty(name: String) {
//        properties.remove(name)
//    }
//}

val appReducer: Reducer<AppState> = { state, action ->
    AppState(
        counterState = counterReducer(state.counterState, action),
        toDoState = toDoRootReducer(state.toDoState, action)
    )
}


val store = createStore(
    appReducer, AppState(),
    applyMiddleware(createThunkMiddleware(), createLoggerMiddleware())
);
