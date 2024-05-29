package com.example.reduxforandroid.shoppingcart.domain

import android.util.Log
import com.example.reduxforandroid.Entity
import com.example.reduxforandroid.redux.Reducer

data class UserState(
    val name: String = "",
    val age: Int = 0,
    val email: String = ""
)

data class SetName(val name: String)
data class SetAge(val age: Int)
data class SetEmail(val email: String)

val userReducer: Reducer<UserState> = { state, action ->
    when (action) {
        is SetName -> setNameHandle(state,action)
        is SetAge -> setAgeHandle(state,action)
        is SetEmail -> setEmailHandle(state,action)
        else -> state
    }
}

fun setNameHandle(state: UserState, action: SetName):UserState{
    return state.copy(name = action.name)
}

fun setAgeHandle(state: UserState, action: SetAge):UserState{
    return state.copy(age = action.age)
}

fun setEmailHandle(state: UserState, action: SetEmail):UserState{
    return state.copy(email = action.email)
}

