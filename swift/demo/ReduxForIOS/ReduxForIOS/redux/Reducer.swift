//
//  Reducer.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/4/19.
//

public typealias Reducer<ReducerStateType> =
    (_ action: Action, _ state: ReducerStateType?) -> ReducerStateType
