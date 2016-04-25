package uk.co.disciplemedia.testable

import android.os.Bundle
import android.support.v4.app.Fragment
import org.fuzzyrobot.kpresent.CommentingState
import org.fuzzyrobot.kpresent.mock
import org.junit.Test
import org.junit.Assert.*

class FooFragment : Fragment() {

}


class TestableFragmentFactoryTest {

    @Test fun test() {

        val args = mock<Bundle>()
        TestableFragmentFactory.bundler = { args }
        val f = TestableFragmentFactory.newInstance(::FooFragment, CommentingState.STICKERS)
        val arg = TestableFragmentFactory.getParcelable<CommentingState>(f)
        assertEquals(CommentingState.STICKERS, arg)
    }

}
