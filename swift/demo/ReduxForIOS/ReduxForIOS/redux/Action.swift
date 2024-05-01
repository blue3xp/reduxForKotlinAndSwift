//
//  Action.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/4/19.
//

public protocol Action { }

/// Initial Action that is dispatched as soon as the store is created.
/// Reducers respond to this action by configuring their initial state.
public struct ReSwiftInit: Action {}
