//
//  Synchronized.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/4/19.
//

import Foundation

/// An object that manages the execution of tasks atomically.
struct Synchronized<Value> {
    private let mutex = DispatchQueue(label: "com.redux.ios.synchronized", attributes: .concurrent)
    private var _value: Value
    init(_ value: Value) {
        self._value = value
    }
    /// Returns or modify the thread-safe value.
    var value: Value { return mutex.sync { return _value } }
    /// Submits a block for synchronous, thread-safe execution.
    mutating func value<T>(execute task: (inout Value) throws -> T) rethrows -> T {
        return try mutex.sync(flags: .barrier) { return try task(&_value) }
    }
}
