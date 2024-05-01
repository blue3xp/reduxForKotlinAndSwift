package com.example.reduxforandroid.redux

/**
 * A *reducer* is a function that accepts
 * an accumulation and a value and returns a new accumulation. They are used
 * to reduce a collection of values down to a single value
 *
 * In Redux, the accumulated value is the state object, and the values being
 * accumulated are actions. Reducers calculate a new state given the previous
 * state and an action. They must be *pure functions*—functions that return
 * the exact same output for given inputs. They should also be free of
 * side-effects. This is what enables exciting features like hot reloading and
 * time travel.
 *
 * Reducers are the most important concept in Redux.
 *
 * *Do not put API calls into reducers.*
 *
 * An *action* is a plain object that represents an intention to change the
 * state. Actions are the only way to get data into the store. Any data,
 * whether from UI events, network callbacks, or other sources such as
 * WebSockets needs to eventually be dispatched as actions.
 *
 * use class or data class to implement Action
 *
 * State is data, we can use any data structure for state
*/

public typealias TypedReducer<State, Action> = (state: State, action: Action) -> State
public typealias Reducer<State> = TypedReducer<State, in Any>

/**
 * A *dispatching function* (or simply *dispatch function*) is a function that
 * accepts an action or an async action; it then may or may not dispatch one
 * or more actions to the store.
 *
 * We must distinguish between dispatching functions in general and the base
 * `dispatch` function provided by the store instance without any middleware.
 *
 * The base dispatch function *always* synchronously sends an action to the
 * store's reducer, along with the previous state returned by the store, to
 * calculate a new state. It expects actions to be plain objects ready to be
 * consumed by the reducer.
 *
 * Middleware wraps the base dispatch function. It allows the dispatch
 * function to handle async actions in addition to actions. Middleware may
 * transform, delay, ignore, or otherwise interpret actions or async actions
 * before passing them to the next middleware.
 *
 * @template A The type of things (actions or otherwise) which may be
 *   dispatched.
 *
 * The Redux store has a method called dispatch.
 * The only way to update the state is to call store.dispatch() and pass in an action object.
 * The store will run its reducer function and save the new state value inside,
 * and we can call getState() to retrieve the updated value
 */
public typealias TypedDispatcher<Action> = (Action) -> Any
public typealias Dispatcher = TypedDispatcher<Any>


public typealias GetState<State> = () -> State
public typealias StoreSubscriber = () -> Unit
public typealias StoreSubscription = () -> Unit


/**
 * A store is an object that holds the application's state tree.
 * There should only be a single store in a Redux app, as the composition
 * happens on the reducer level.
 *
 */
public interface TypedStore<State, Action> {
    /**
     * Reference to the underlying untyped store
     */
    public val store: Store<State>

    /**
     * Reads the state tree managed by the store.
     *
     * @returns The current state tree of your application.
     */
    public val getState: GetState<State>

    /**
     * Dispatches an action. It is the only way to trigger a state change.
     *
     * The `reducer` function, used to create the store, will be called with the
     * current state tree and the given `action`. Its return value will be
     * considered the **next** state of the tree, and the change listeners will
     * be notified.
     *
     * The base implementation only supports plain object actions. If you want
     * to dispatch a Promise, an Observable, a thunk, or something else, you
     * need to wrap your store creating function into the corresponding
     * middleware. For example, see the documentation for the `redux-thunk`
     * package. Even the middleware will eventually dispatch plain object
     * actions using this method.
     *
     */
    public var dispatch: TypedDispatcher<Action>

    /**
     * Adds a change listener. It will be called any time an action is
     * dispatched, and some part of the state tree may potentially have changed.
     * You may then call `getState()` to read the current state tree inside the
     * callback.
     *
     * You may call `dispatch()` from a change listener, with the following
     * caveats:
     *
     * 1. The subscriptions are snapshotted just before every `dispatch()` call.
     * If you subscribe or unsubscribe while the listeners are being invoked,
     * this will not have any effect on the `dispatch()` that is currently in
     * progress. However, the next `dispatch()` call, whether nested or not,
     * will use a more recent snapshot of the subscription list.
     *
     * 2. The listener should not expect to see all states changes, as the state
     * might have been updated multiple times during a nested `dispatch()` before
     * the listener is called. It is, however, guaranteed that all subscribers
     * registered before the `dispatch()` started will be called with the latest
     * state by the time it exits.
     *
     * @param listener A callback to be invoked on every dispatch.
     * @returns A function to remove this change listener.
     */
    public val subscribe: (StoreSubscriber) -> StoreSubscription

    /**
     * Replaces the reducer currently used by the store to calculate the state.
     *
     * You might need this if your app implements code splitting and you want to
     * load some of the reducers dynamically. You might also need this if you
     * implement a hot reloading mechanism for Redux.
     *
     * @param nextReducer The reducer for the store to use instead.
     */
    public val replaceReducer: (TypedReducer<State, Action>) -> Unit

    /**
     * Current store state
     */
    public val state: State get() = getState()
}

/**
 * Main redux storage container for a given [State]
 */
public typealias Store<State> = TypedStore<State, Any>

/**
 * A store creator is a function that creates a Redux store. Like with
 * dispatching function, we must distinguish the base store creator,
 * `createStore(reducer, preloadedState)` exported from the Redux package, from
 * store creators that are returned from the store enhancers.
 *
 */
public typealias StoreCreator<State> = (
    reducer: Reducer<State>,
    initialState: State,
    enhancer: Any?
) -> Store<State>

/**
 * Take a store creator and return a new enhanced one
 */
public typealias StoreEnhancer<State> = (StoreCreator<State>) -> StoreCreator<State>

/**
 * A middleware is a higher-order function that composes a dispatch function
 * to return a new dispatch function. It often turns async actions into
 * actions.
 *
 * Middleware is composable using function composition. It is useful for
 * logging actions, performing side effects like routing, or turning an
 * asynchronous API call into a series of synchronous actions.
 *
 */
public typealias Middleware<State> = (store: Store<State>) -> (next: Dispatcher) -> (action: Any) -> Any

/**
 * Convenience function for creating a [Middleware]
 * usage:
 *    val myMiddleware = middleware { store, next, action -> doStuff() }
 */
public fun <State> middleware(dispatch: (Store<State>, next: Dispatcher, action: Any) -> Any): Middleware<State> =
    { store ->
        { next ->
            { action: Any ->
                dispatch(store, next, action)
            }
        }
    }

/**
 * Convenience function for creating a [TypedReducer]
 * usage:
 *   sealed class LoginScreenAction
 *   data class LoginComplete(val user: User): LoginScreenAction()
 *
 *   val loginReducer = typedReducer<AppState, LoginAction> { state, action ->
 *       when(action) {
 *           is LoginComplete -> state.copy(user = action.user)
 *       }
 *   }
 *
 *   sealed class FeedScreenAction
 *   data class FeedLoaded(val items: FeedItems): FeedScreenAction
 *   data class FeedLoadError(val msg: String): FeedScreenAction
 *
 *   val feedReducer = typedReducer<AppState, FeedScreeAction> { state, action ->
 *       when(action) {
 *          is FeedLoaded -> state.copy(feedItems = action.items)
 *          is FeedLoadError -> state.copy(errorMsg = action.msg)
 *       }
 *   }
 *
 *   val rootReducer = combineReducers(loginReducer, feedReducer)
 *   val store = createStore(rootReducer, AppState())
 *      **or**
 *   val store = createThreadSafeStore(rootReducer, AppState())
 */
public inline fun <State, reified Action> typedReducer(
    crossinline reducer: TypedReducer<State, Action>
): Reducer<State> = { state, action ->
    when (action) {
        is Action -> reducer(state, action)
        else -> state
    }
}


