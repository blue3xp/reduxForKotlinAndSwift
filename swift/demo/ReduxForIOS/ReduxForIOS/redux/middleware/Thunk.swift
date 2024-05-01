//
//  Thunk.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/4/20.
//

import Foundation

public struct Thunk<State>: Action {
    let body: (_ dispatch: @escaping DispatchFunction, _ getState: @escaping () -> State?) -> Void
    public init(body: @escaping (
        _ dispatch: @escaping DispatchFunction,
        _ getState: @escaping () -> State?) -> Void) {
        self.body = body
    }
}

