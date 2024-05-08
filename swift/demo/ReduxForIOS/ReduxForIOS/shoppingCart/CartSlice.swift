//
//  CartSlice.swift
//  ReduxForIOS
//
//  Created by JackLi on 2024/5/7.
//

import Foundation

struct Product : Equatable,CustomStringConvertible {
    var id: String
    var name: String
    var price: String
    var quantity: Int
    var description: String {
            return "id: \(id) name: \(name) price: \(price) quantity: \(quantity)"
        }
}

struct ShoppingCart : Equatable,CustomStringConvertible {
    var id: String
    var items: [String]
    var description: String {
            return "id: \(id)"
        }
}

struct GetProductList : Action{
    var keyword: String
}

struct AddProductIntoCart : Action{
    var productId: String
    var shoppingCartId: String
}

struct RemoveProductIntoCart : Action{
    var productId: String
    var shoppingCartId: String
}

struct UpdateProductNumInCart : Action{
    var productId: String
    var num: Int
    var shoppingCartId: String
}

func productEntityReducer(action: Action, state: Entity<Product>?) -> Entity<Product> {
    var state = state ?? Entity<Product>(byId: [String : Product](), allIds: [])
    switch action {
    case let action as GetProductList:
        state = getProductListHandle(state: state, action: action)
    case let action as UpdateProductNumInCart:
        state = updateProductNumInCartHandle(state: state, action: action)
    default:
        break
    }
    return state
}

func shoppingCartEntityReducer(action: Action, state: Entity<ShoppingCart>?) -> Entity<ShoppingCart> {
    var state = state ?? Entity<ShoppingCart>(byId: [String : ShoppingCart](), allIds: [])
    switch action {
    case let action as AddProductIntoCart:
        state = addProductIntoCartHandle(state: state, action: action)
    case let action as RemoveProductIntoCart:
        state = removeProductIntoCartHandle(state: state, action: action)
    default:
        break
    }
    return state
}



func addProductIntoCartHandle(state: Entity<ShoppingCart>, action: AddProductIntoCart) -> Entity<ShoppingCart>{
    print("CartSlice - action productId: \(action.productId), shopping cartID: \(action.shoppingCartId)")
    var tmp = state.byId
    let shoppingCart = tmp[action.shoppingCartId]
    if shoppingCart == nil {
        return state
    } else {
        var itemList = shoppingCart!.items
        itemList.append(action.productId)
        let newShoppingCart = ShoppingCart(id: action.shoppingCartId, items: itemList)
        tmp[action.shoppingCartId] = newShoppingCart
        return Entity<ShoppingCart>(byId: tmp, allIds: state.allIds)
    }
}

func removeProductIntoCartHandle(state: Entity<ShoppingCart>, action: RemoveProductIntoCart) -> Entity<ShoppingCart>{
    print("CartSlice - action productId: \(action.productId), action shoppingCartId: \(action.shoppingCartId)")
    var tmp = state.byId
    let shoppingCart = tmp[action.shoppingCartId]
    if shoppingCart == nil {
        return state
    } else {
        var itemList = shoppingCart!.items
        itemList.removeAll { $0 == action.productId }
        let newShoppingCart = ShoppingCart(id: action.shoppingCartId, items: itemList)
        tmp[action.shoppingCartId] = newShoppingCart
        return Entity<ShoppingCart>(byId: tmp, allIds: state.allIds)
    }
}

func getProductListHandle(state: Entity<Product>, action: GetProductList) -> Entity<Product> {
    return Entity(byId: [
        "product1": Product(id: "product1", name: "user1", price: "20", quantity: 1),
        "product2": Product(id: "product2", name: "user2", price: "30", quantity: 2)
    ], allIds: ["product1", "product2"])
}

func replaceValueInMap(originalMap: [String: Product], key: String, newValue: Product) -> [String: Product] {
    var newMap = originalMap
    newMap[key] = newValue
    return newMap
}

func updateProductNumInCartHandle(state: Entity<Product>, action: UpdateProductNumInCart) -> Entity<Product>{
    print("CartSlice - action productId: \(action.productId), action num: \(action.num)")
    let tmp = state.byId
    let productEntity = tmp[action.productId]
    if productEntity == nil {
        return state
    } else {
        let newProduct = Product(id: action.productId, name: productEntity!.name, price: productEntity!.price, quantity: action.num)
        let newMap = replaceValueInMap(originalMap: tmp, key: action.productId, newValue: newProduct)
        return Entity<Product>(byId: newMap, allIds: state.allIds)
    }
}


