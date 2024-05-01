package com.example.reduxforandroid.redux.middleware

import android.util.Log
import com.example.reduxforandroid.redux.Dispatcher
import com.example.reduxforandroid.redux.Middleware

public fun <State> createLoggerMiddleware(extraArgument: Any? = null): Middleware<State> =
    { store ->
        { next: Dispatcher ->
            { action: Any ->
                Log.d("Redux", "dispatching $action") // 打印日志消息
                next(action)
                Log.d("Redux", "next state ${store.getState()}") // 打印日志消息
            }
        }
    }

