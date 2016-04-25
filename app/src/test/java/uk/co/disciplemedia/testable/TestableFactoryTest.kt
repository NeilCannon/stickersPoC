package uk.co.disciplemedia.testable

import org.junit.Test
import org.junit.Assert.*

class TestableFactoryTest {

    val tf = TestableFactory { "123" }

    @Test fun testNormalUsage() {
        assertEquals("123", tf.instance)

    }

    @Test fun testTestingUsage() {
        tf.newInstance = { "456" }
        assertEquals("456", tf.instance)
    }
}
