package com.example.reduxforandroid.todos.domain

import com.example.reduxforandroid.redux.Reducer

fun DataListReducer(state: List<Todo>, action: Any) =
    when (action) {
        is AddTodo -> state + Todo(action.text, id = state.size)
        is ToggleTodo -> state.mapIndexed { index, todo ->
            if (index == action.index) {
                todo.copy(completed = !todo.completed)
            } else {
                todo
            }
        }
        else -> state
    }

val toDoRootReducer: Reducer<ToDoState> = { state, action ->
    ToDoState(
        todos = DataListReducer(state.todos, action),
        visibilityFilter = visibilityFilterReducer(state.visibilityFilter, action)
    )
}

fun visibilityFilterReducer(state: VisibilityFilter, action: Any) =
    when (action) {
        is SetVisibilityFilter -> action.visibilityFilter
        else -> state
    }
