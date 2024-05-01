//
//  Store.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/4/19.
//

import Foundation

struct AppState: CustomStringConvertible{
    var counterState: CounterState = CounterState()
    var description: String {
            return "counterState: \(counterState)"
        }
}

func AppReducer(action: Action, state: AppState?) -> AppState {
    AppState(
        counterState: counterReducer(action: action, state: state?.counterState)
    )
}

// First, you create the middleware, which needs to know the type of your `State`.
let thunkMiddleware: Middleware<AppState> = createThunkMiddleware()
let logMiddleware: Middleware<AppState> = createLoggerMiddleware()

let store = Store<AppState>(reducer: AppReducer, state: AppState(), middleware: [thunkMiddleware,logMiddleware])



