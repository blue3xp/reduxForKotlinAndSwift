//
//  ContentView.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/4/14.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        NavigationView{
            VStack {
                NavigationLink(destination: CounterView()) {
                                    Text("Jump Counter")
                                        .padding()
                                        .background(Color.blue)
                                        .foregroundColor(Color.white)
                                        .cornerRadius(10)
                                }
            }
            .padding()
        }
        .navigationBarTitle("Home Page")
        
    }
}

#Preview {
    ContentView()
}



