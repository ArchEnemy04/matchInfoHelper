package com.alacrity.matchInfoHelper

interface EventHandler<T> {
    fun obtainEvent(event: T)
}