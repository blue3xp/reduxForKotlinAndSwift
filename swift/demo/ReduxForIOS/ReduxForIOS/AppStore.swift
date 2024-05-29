//
//  Store.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/4/19.
//

import Foundation

struct Entity<State> : CustomStringConvertible{
    let byId: [String: State]
    let allIds: [String]
    var description: String {
        return "byId: \(byId) allIds:\(allIds)"
    }
}

struct Entities : CustomStringConvertible{
    
    let productEntity: Entity<Product>
    let shoppingCartEntity: Entity<ShoppingCart>
    
    init(productEntity: Entity<Product> = Entity(byId: [:], allIds: []),
         shoppingCartEntity: Entity<ShoppingCart> = Entity(byId: ["shoppingCart1": ShoppingCart(id: "shoppingCart1", items: [])], allIds: ["shoppingCart1"])) {
        self.productEntity = productEntity
        self.shoppingCartEntity = shoppingCartEntity
    }
    
    var description: String {
        return "productEntity: \(productEntity) shoppingCartEntity:\(shoppingCartEntity)"
    }
}

struct AppState: CustomStringConvertible{
    var counterState: CounterState = CounterState()
    var userState: UserState = UserState()
    var entities: Entities = Entities()
    var description: String {
            return "counterState: \(counterState) entities:\(entities)"
        }
}

func entitiesRootReducer(action: Action, state: Entities?) -> Entities {
    Entities(
        productEntity: productEntityReducer(action: action, state: state?.productEntity),
        shoppingCartEntity:shoppingCartEntityReducer(action: action, state: state?.shoppingCartEntity)
    )
}

func AppReducer(action: Action, state: AppState?) -> AppState {
    AppState(
        counterState: counterReducer(action: action, state: state?.counterState),
        userState: userReducer(action: action, state: state?.userState),
        entities:entitiesRootReducer(action: action, state: state?.entities)
    )
}

// First, you create the middleware, which needs to know the type of your `State`.
let thunkMiddleware: Middleware<AppState> = createThunkMiddleware()
let logMiddleware: Middleware<AppState> = createLoggerMiddleware()

let store = Store<AppState>(reducer: AppReducer, state: AppState(), middleware: [thunkMiddleware,logMiddleware])



