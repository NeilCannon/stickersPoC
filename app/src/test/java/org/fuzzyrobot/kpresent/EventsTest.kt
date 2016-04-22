package org.fuzzyrobot.kpresent

import android.os.Parcel
import android.os.Parcelable
import org.fuzzyrobot.k.StateActions
import org.junit.Test

class EventsTest {

    enum class State : Parcelable { ON {
    }, OFF;
        override fun writeToParcel(dest: Parcel?, flags: Int) {
        }

        override fun describeContents(): Int {
            return 0
        }
    }

    @Test fun test() {

        val stateEvents = StateActions<State>(State.OFF)

        stateEvents.whenEnter(State.ON) { println("ON") }
        stateEvents.whenExit(State.ON) { println("not ON") }
        stateEvents.whenEnter(State.OFF) { println("OFF") }

//        stateEvents.enter(State.ON)
//        stateEvents.exit(State.ON)
//        stateEvents.enter(State.OFF)

        var state = State.OFF
        stateEvents.enter(state)

//        state = stateEvents.move(state, State.ON)

//        state = stateEvents.transition(state, State.OFF)
    }


}
