package com.example.reduxforandroid.counter

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.reduxforandroid.AppState
import com.example.reduxforandroid.databinding.ActivityCounterBinding
import com.example.reduxforandroid.redux.StoreSubscription
import com.example.reduxforandroid.redux.applyMiddleware
import com.example.reduxforandroid.redux.createStore
import com.example.reduxforandroid.redux.middleware.Thunk
import com.example.reduxforandroid.redux.middleware.createLoggerMiddleware
import com.example.reduxforandroid.redux.middleware.createThunkMiddleware
import com.example.reduxforandroid.redux.select.select
import com.example.reduxforandroid.redux.select.selectors
import com.example.reduxforandroid.store
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer
import kotlin.concurrent.timerTask



class CounterActivity : AppCompatActivity() {
    private lateinit var storeSubscription: StoreSubscription

    private lateinit var subscription: StoreSubscription
    private lateinit var multiSubscription: StoreSubscription

    private lateinit var binding: ActivityCounterBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCounterBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        storeSubscription = store.subscribe { render(store.state) }
        /**
         * API 有问题
         *
         * subscription = store.select({ it.counterState.isLoading }) {
         *             binding.loadingIndicator.visibility = if (store.state.counterState.isLoading) View.VISIBLE else View.GONE
         *  }
         */


        multiSubscription = store.selectors {
            select({ it.counterState.isLoading }) {
                binding.loadingIndicator.visibility = if (store.state.counterState.isLoading) View.VISIBLE else View.GONE
            }
            select({it.counterState.counter}){
                binding.txtLabel.text = "Clicked: ${state.counterState.counter} times"
            }
        }

        binding.btnIncrement.setOnClickListener { store.dispatch(Increment()) }
        binding.btnDecrement.setOnClickListener { store.dispatch(Decrement()) }
//        binding.btnAsync.setOnClickListener { incrementAsync() }
        binding.btnAsync.setOnClickListener { incrementAsync2() }
        binding.btnIncrementIfOdd.setOnClickListener { incrementIfOdd() }
        binding.btnDoubleIfEven.setOnClickListener { doubleIfEven() }
        binding.btnSend.setOnClickListener{store.dispatch(networkRequest())}
    }

    private fun render(state: CounterState) {
        binding.txtLabel.text = "Clicked: ${state.counter} times"
        binding.loadingIndicator.visibility = if (store.state.counterState.isLoading) View.VISIBLE else View.GONE
    }

    private fun incrementIfOdd() {
        if (store.state.counterState.counter % 2 != 0) {
            store.dispatch(Increment())
        }
    }

    private fun doubleIfEven() {
        if (store.state.counterState.counter % 2 == 0) {
            store.dispatch(Double())
        }
    }

    private fun incrementAsync() {
        Handler(mainLooper).postDelayed(
            {
                store.dispatch(Increment())
            },
            1000
        )
    }

    private fun incrementAsync2() {
        store.dispatch(incrementThunk());
    }

    private fun incrementThunk(): Thunk<AppState> = { dispatch, getState, _ ->
        Handler(mainLooper).postDelayed(
            {
                store.dispatch(Increment())
            },
            1000
        )
        getState()
    }

    /**
     * A fake network request thunk.  Just delays then dispatches a LoadingCompleteAction
     */
    fun networkRequest(): Thunk<CounterState> = { dispatch, getState, extraArg ->
        dispatch(StartLoading())
        GlobalScope.launch {
            delay(2000L)
            withContext(Dispatchers.Main) {
                dispatch(LoadingComplete())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        subscription()
        multiSubscription()
    }
}