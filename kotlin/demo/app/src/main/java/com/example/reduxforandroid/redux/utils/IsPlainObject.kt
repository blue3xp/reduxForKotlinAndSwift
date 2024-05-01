package com.example.reduxforandroid.redux.utils

internal fun isPlainObject(obj: Any): Boolean = obj !is Function<*>
