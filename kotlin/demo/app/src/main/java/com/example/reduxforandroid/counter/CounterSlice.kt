package com.example.reduxforandroid.counter

import com.example.reduxforandroid.redux.Reducer

data class CounterState(
    val counter: Int = 0,
    val isLoading: Boolean = false,
    val response:String = "",
    val error: String = "",
)

class Increment
class Decrement
class StartLoading
class LoadingComplete

data class FetchReponse(val reponse: String, val error: String)

/**
 * This is a reducer, a pure function with (state, action) -> state signature.
 * It describes how an action transforms the state into the next state.
 *
 * The shape of the state is up to you: it can be a primitive, an array, an object,
 * Usually this will be a data class.  The copy method is useful for creating the new state.
 * In this contrived example, we are just using an Int for the state.
 *
 * In this example, we use a `when` statement and type checking, but other methods are possible,
 * such as a 'type' string field, or delegating the reduction to a method on the action objects.
 */
//val reducer: Reducer<Int> = { state, action ->
//    when (action) {
//        is Increment -> state + 1
//        is Decrement -> state - 1
//        else -> state
//    }
//}

val counterReducer: Reducer<CounterState> = { state, action ->
    when (action) {
        is StartLoading -> state.copy(isLoading = true)
        is LoadingComplete -> state.copy(isLoading = false)
        is Increment -> state.copy(counter = state.counter + 1)
        is Decrement -> state.copy(counter = state.counter - 1)
        is FetchReponse -> state.copy(
            response = action.reponse,
            error = action.error
        )
        else -> state
    }
}
