//
//  StoreSubscriber.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/4/19.
//

public protocol AnyStoreSubscriber: AnyObject {
    // swiftlint:disable:next identifier_name
    func _newState(state: Any)
}

public protocol StoreSubscriber: AnyStoreSubscriber {
    associatedtype StoreSubscriberStateType

    func newState(state: StoreSubscriberStateType)
}

extension StoreSubscriber {
    // swiftlint:disable:next identifier_name
    public func _newState(state: Any) {
        if let typedState = state as? StoreSubscriberStateType {
            newState(state: typedState)
        }
    }
}
