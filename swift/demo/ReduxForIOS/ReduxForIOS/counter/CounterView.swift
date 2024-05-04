//
//  CounterView.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/4/19.
//

import SwiftUI

struct CounterView: View {
    @State var countermodel = CounterModel()
       var body: some View {
           VStack {
               if countermodel.isLoading {
                    ProgressView("Loading...")
                          .padding()
               }
               Spacer()
               Image(systemName: "clock")
                   .imageScale(.large)
                   .foregroundStyle(.tint)
               Divider()
               Text("\(countermodel.counter)")
                   .padding()
               Divider()
               HStack {
                   Spacer()
                   Button("+1", action: {
                       store.dispatch(CounterActionIncrease())
                   })
                   Spacer()
                   Button("-1", action: {
                       store.dispatch(CounterActionDecrease())
                   })
                   Spacer()
               }
               Divider()
               Button("Increment if odd", action: {
                   if (store.state.counterState.counter % 2 != 0) {
                       store.dispatch(CounterActionIncrease())
                   }
               })
               Divider()
               Button("Double if odd", action: {
                   if (store.state.counterState.counter % 2 == 0) {
                       store.dispatch(CounterDouble())
                   }
               })
               Divider()
               Button("Async Increment", action: {
//                   DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) {
//                           store.dispatch(CounterActionIncrease())
//                   }
                   
                   let incrementThunk = Thunk<AppState> { dispatch, getState in
                       DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) {
                               dispatch(CounterActionIncrease())
                       }
                   }
                   store.dispatch(incrementThunk)
               })
               Spacer()
               Button("Mock Request", action: {
                   let networkRequest = Thunk<AppState>  { dispatch, getState in
                           dispatch(CounterStartLoading())
                           DispatchQueue.global().async {
                               Thread.sleep(forTimeInterval: 2)
                               DispatchQueue.main.async {
                                   dispatch(CounterLoadingComplete())
                               }
                           }
                       }
                   store.dispatch(networkRequest)
               })
               
           }
           .padding()
           .onAppear() {
               store.subscribe(countermodel) { $0.select { $0.counterState } }
           }
           .onDisappear(){
               store.unsubscribe(countermodel)
           }
       }
}
