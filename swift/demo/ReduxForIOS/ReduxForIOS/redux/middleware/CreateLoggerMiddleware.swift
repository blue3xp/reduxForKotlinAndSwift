//
//  CreateLoggerMiddleware.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/4/20.
//

import Foundation

public func createLoggerMiddleware<State>() -> Middleware<State> {
    return { _, getState in
        return { next in
            return { action in
                let structType = Mirror(reflecting: action).subjectType
                let structName = String(describing: structType)
                NSLog("Redux dispatching \(structName)")
                next(action)
                NSLog("Redux next state \(String(describing: getState()))")
            }
        }
    }
}

