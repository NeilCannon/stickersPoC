package org.fuzzyrobot.k

import android.os.Bundle
import android.os.Parcelable
import java.util.*

/**
 * Created by neil on 31/03/2016.
 */

interface EnterExit {
    fun enter() {}
    fun exit() {}
}

class StateActions<T : Parcelable>(var state:T) : SavesState {

    init {
        println("StateEvents($state)")
    }

    private val eventsMap: MutableMap<T, EnterExitEvents> = HashMap()

    fun moveTo(newState: T) {
        state = transition(state, newState)
    }

    fun moveTo(f:(current:T)->T) {
        moveTo(f(state))
    }

    fun move(ifCurrentState: T, newState: T) {
        if (state == ifCurrentState) {
            state = transition(state, newState)
        }
    }

    // copied from "map literals"
    // @see kotlin.collections.hashMapOf
    fun move(vararg pairs: Pair<T,T>) {
        pairs.forEach { move(it.first, it.second) }
    }

    fun whenEnter(state: T, f: () -> Unit) {
        getEnterExitEvents(state).enterEvents.add(f)
    }

    fun whenExit(state: T, f: () -> Unit) {
        getEnterExitEvents(state).exitEvents.add(f)
    }

    fun whenEnterExit(state: T, presenter: Aspect) {
        getEnterExitEvents(state).enterEvents.add( { presenter.add()})
        getEnterExitEvents(state).exitEvents.add( { presenter.remove()})
    }

    fun whenEnterExit(state: T, f: (enter: Boolean) -> Unit) {
        println("whenEnterExit(f(enter:Boolean)")
        getEnterExitEvents(state).enterEvents.add( { f(true)})
        getEnterExitEvents(state).exitEvents.add( { f(false)})
    }

    private fun getEnterExitEvents(state: T): EnterExitEvents {
        return eventsMap.getOrPut(state) { EnterExitEvents() }
    }

    fun enter(state: T) {
        println("enter $state")
        eventsMap[state]?.enterEvents?.fire()
    }

    fun exit(state: T) {
        println("exit $state")
        eventsMap[state]?.exitEvents?.fire()
    }


    private fun transition(oldState: T, newState: T): T {
        println("$oldState -> $newState")
        if (newState == oldState) {
            return oldState
        }
        exit(oldState)
        enter(newState)
        return newState
    }

    override fun saveState(outState: Bundle?) {
        println("saveState")
        outState?.putParcelable(StateActions::class.qualifiedName, state)
    }

    override fun loadState(inState: Bundle?) {
        println("loadState")
        if (inState != null) {
            val state:T = inState.getParcelable(StateActions::class.qualifiedName)
            moveTo(state)
        }
    }
}


private class EnterExitEvents {
    val enterEvents = Events()
    val exitEvents = Events()
}

private class Events {
    private val listeners: MutableList<() -> Unit> = ArrayList()

    fun add(f: () -> Unit) {
        listeners.add(f)
    }

    fun fire() {
        listeners.forEach {
//            println("fire: $it")
            it()
        }
    }
}