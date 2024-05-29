//
//  UserSlice.swift
//  ReduxForIOS
//
//  Created by Jack on 2024/5/29.
//

import Foundation

struct UserState: Equatable {
    var name: String = ""
    var age: Int = 0
    var email: String = ""
}

struct SetName: Action {
    let name: String
}

struct SetAge: Action {
    let age: Int
}

struct SetEmail: Action {
    let email: String
}



func userReducer(action: Action, state: UserState?) -> UserState {
    var state = state ?? UserState()
    switch action {
    case let action as SetName:
        state = setNameHandle(state: state, action: action)
    case let action as SetAge:
        state = setAgeHandle(state: state, action: action)
    case let action as SetEmail:
        state = setEmailHandle(state: state, action: action)
    default:
        break
    }
    return state
}

func setNameHandle(state: UserState, action: SetName) -> UserState {
    return UserState(name: action.name, age: state.age, email: state.email)
}

func setAgeHandle(state: UserState, action: SetAge) -> UserState {
    return UserState(name: state.name, age: action.age, email: state.email)
}

func setEmailHandle(state: UserState, action: SetEmail) -> UserState {
    return UserState(name: state.name, age: state.age, email: action.email)
}
