package org.fuzzyrobot.kpresent

import android.view.LayoutInflater
import android.view.View
import org.fuzzyrobot.kpresent.fragment.MediaBarFragment
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito

class MediaBarFragmentTest {

    @Before fun setUp() {

    }

    @Test fun test() {

        val fragment = MediaBarFragment()

        fragment.onCreate(null)
        val inflater = mock<LayoutInflater>()
        val view = mock<View>()
        given(inflater.inflate(R.layout.fragment_media_bar, null, false)).willReturn(view)

        val ret = fragment.onCreateView(inflater, null, null)

        assertSame(ret, view)
    }
}

inline fun <reified T : Any> mock(): T {
    return Mockito.mock(T::class.java)
}
