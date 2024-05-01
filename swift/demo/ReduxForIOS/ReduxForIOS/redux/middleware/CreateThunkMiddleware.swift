//
//  createThunkMiddleware.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/4/20.
//

import Foundation

public func createThunkMiddleware<State>() -> Middleware<State> {
    return { dispatch, getState in
        return { next in
            return { action in
                switch action {
                case let thunk as Thunk<State>:
                    thunk.body(dispatch, getState)
                default:
                    next(action)
                }
            }
        }
    }
}

