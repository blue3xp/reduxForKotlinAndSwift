package com.example.reduxforandroid

import android.util.Log
import com.example.reduxforandroid.counter.CounterState
import com.example.reduxforandroid.counter.counterReducer
import com.example.reduxforandroid.redux.Dispatcher
import com.example.reduxforandroid.redux.Middleware
import com.example.reduxforandroid.redux.Reducer
import com.example.reduxforandroid.redux.Store
import com.example.reduxforandroid.redux.applyMiddleware
import com.example.reduxforandroid.redux.combineReducers
import com.example.reduxforandroid.redux.createStore
import com.example.reduxforandroid.redux.middleware
import com.example.reduxforandroid.redux.middleware.createLoggerMiddleware
import com.example.reduxforandroid.redux.middleware.createThunkMiddleware
import com.example.reduxforandroid.redux.utils.ReduxPersistUtil
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


fun createPersistMiddleware(persistUtil: ReduxPersistUtil): Middleware<AppState> =
    { store ->
        { next: Dispatcher ->
            { action: Any ->
                next(action)
                // 在状态更新后保存状态
                val state = store.getState()
                persistUtil.saveState("reduxState", state)
            }
        }
    }


fun <State> createRehydrateMiddleware(persistUtil: ReduxPersistUtil): Middleware<State> =
    { store ->
        { next: Dispatcher ->
            { action: Any ->
                next(action)
                // 在初始化时从本地存储中加载状态
                val state = persistUtil.loadState("reduxState", AppState::class.java)
                if (state != null) {
                    store.dispatch(SetStateAction(state))
                }            }
        }
 }

data class SetStateAction(val state: AppState)

val appReducer: Reducer<AppState> = { state, action ->
    AppState(
        counterState = counterReducer(state.counterState, action),
        toDoState = toDoRootReducer(state.toDoState, action)
    )
}

val persistReducer: Reducer<AppState> = { state, action ->
    when (action) {
        is SetStateAction -> {
            // 在这里根据 SetStateAction 更新状态
            action.state
        }
        else -> state
    }
}

val rootReducer = combineReducers(appReducer, persistReducer )
val persistUtil = ReduxPersistUtil(MyApp.getAppContext())

val store = createStore(
    rootReducer, AppState(),
    applyMiddleware(createThunkMiddleware(),
        createLoggerMiddleware(),
        createPersistMiddleware(persistUtil),
        createRehydrateMiddleware(persistUtil))
);





