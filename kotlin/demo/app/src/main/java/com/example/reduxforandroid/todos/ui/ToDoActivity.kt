package com.example.reduxforandroid.todos.ui

import android.app.Activity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reduxforandroid.AppState
import com.example.reduxforandroid.R
import com.example.reduxforandroid.databinding.ActivityToDoBinding
import com.example.reduxforandroid.redux.StoreSubscription
import com.example.reduxforandroid.store
import com.example.reduxforandroid.todos.domain.AddTodo
import com.example.reduxforandroid.todos.domain.SetVisibilityFilter
import com.example.reduxforandroid.todos.domain.VisibilityFilter


//val store = createThreadSafeStore(::rootReducer, AppState())

class ToDoActivity : AppCompatActivity() {
    private lateinit var storeSubscription: StoreSubscription
    private lateinit var binding: ActivityToDoBinding
    private var adapter = TodoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storeSubscription = store.subscribe { render(store.state) }
        binding.btnAddTodo.setOnClickListener {
            val todoText = binding.etTodo.text.toString()
            binding.etTodo.text.clear()
            store.dispatch(AddTodo(todoText))
        }
        binding.btnSelectAll.setOnClickListener { store.dispatch(SetVisibilityFilter(
            VisibilityFilter.SHOW_ALL)) }
        binding.btnActive.setOnClickListener { store.dispatch(SetVisibilityFilter(VisibilityFilter.SHOW_ACTIVE)) }
        binding.btnCompleted.setOnClickListener { store.dispatch(SetVisibilityFilter(VisibilityFilter.SHOW_COMPLETED)) }

        binding.recyclerView.adapter = adapter

        render(store.state)
    }

    private fun render(state: AppState) {
        adapter.submitList(state.toDoState.visibleTodos)
        setFilterButtons(state.toDoState.visibilityFilter)
    }

    private fun setFilterButtons(visibilityFilter: VisibilityFilter) =
        when (visibilityFilter) {
            VisibilityFilter.SHOW_ALL -> {
                binding.btnSelectAll.isSelected = true
                binding.btnActive.isSelected = false
                binding.btnCompleted.isSelected = false
            }

            VisibilityFilter.SHOW_ACTIVE -> {
                binding.btnActive.isSelected = true
                binding.btnSelectAll.isSelected = false
                binding.btnCompleted.isSelected = false
            }

            VisibilityFilter.SHOW_COMPLETED -> {
                binding.btnCompleted.isSelected = true
                binding.btnSelectAll.isSelected = false
                binding.btnActive.isSelected = false
            }
        }
}