//
//  CounterSlice.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/4/20.
//

import Foundation

struct CounterState : Equatable,CustomStringConvertible {
    var counter: Int = 0
    var isLoading: Bool = false
    var response: String = ""
    var error: String = ""
    var description: String {
            return "counter: \(counter) isLoading: \(isLoading)"
        }
}

struct CounterActionIncrease: Action {}
struct CounterActionDecrease: Action {}
struct CounterStartLoading: Action {}
struct CounterLoadingComplete: Action {}
struct CounterResponse: Action {
    var response:String = ""
    var error: String = ""
}


func counterReducer(action: Action, state: CounterState?) -> CounterState {
    var state = state ?? CounterState()
    switch action {
    case _ as CounterActionIncrease:
        state.counter += 1
    case _ as CounterActionDecrease:
        state.counter -= 1
    case _ as CounterStartLoading:
        state.isLoading = true
    case _ as CounterLoadingComplete:
        state.isLoading = false
    case _ as CounterLoadingComplete:
        state.isLoading = false
    case let action as CounterResponse:
        state.response = action.response
    default:
        break
    }
    return state
}
