//
//  Assertions.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/4/19.
//

import Foundation

/// drop-in fatalError replacement for testing

/**
 Swift.fatalError wrapper for catching in tests

 - parameter message: Message to be wrapped
 - parameter file:    Calling file
 - parameter line:    Calling line
 */
func raiseFatalError(_ message: @autoclosure () -> String = "",
                     file: StaticString = #file, line: UInt = #line) -> Never {
    Assertions.fatalErrorClosure(message(), file, line)
    repeat {
        RunLoop.current.run()
    } while (true)
}

/// Stores custom assertions closures, by default it points to Swift functions. But test target can
/// override them.
class Assertions {
    static var fatalErrorClosure = swiftFatalErrorClosure
    static let swiftFatalErrorClosure: (String, StaticString, UInt) -> Void
        = { Swift.fatalError($0, file: $1, line: $2) }
}
