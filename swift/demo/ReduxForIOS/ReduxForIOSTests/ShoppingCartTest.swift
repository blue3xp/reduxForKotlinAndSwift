//
//  ShoppingCartTest.swift
//  ReduxForIOSTests
//
//  Created by JackLi on 2024/5/8.
//

import XCTest
@testable import ReduxForIOS

final class ShoppingCartTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {
        store.dispatch(GetProductList(keyword: "ss"))
        let count = store.state.entities.productEntity.allIds.count
        XCTAssert(count == 2, "GetProductList expected")
    }

//    func testPerformanceExample() throws {
//        // This is an example of a performance test case.
//        self.measure {
//            // Put the code you want to measure the time of here.
//        }
//    }

}
