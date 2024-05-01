//
//  CounterModel.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/4/19.
//

import Foundation
import SwiftUI

@Observable
final class CounterModel: StoreSubscriber {
    var counter: Int = 0
    var isLoading: Bool = false
    func newState(state: CounterState) {
        counter = state.counter
        isLoading = state.isLoading
    }
}

