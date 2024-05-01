//
//  Middleware.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/4/19.
//


public typealias DispatchFunction = (Action) -> Void
public typealias Middleware<State> = (@escaping DispatchFunction, @escaping () -> State?)
    -> (@escaping DispatchFunction) -> DispatchFunction
