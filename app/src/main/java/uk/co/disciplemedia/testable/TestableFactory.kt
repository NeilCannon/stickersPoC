package uk.co.disciplemedia.testable

import uk.co.disciplemedia.testable.ForTesting

/**
 * Used when class A instantiates class B, but we'd like to make B a mock during testing.
 * @param factory constructs a new T. Called lazily & cached when 'instance' is first accessed
 *
 * Usage:
 *
 * class A {
 *   val b = TestableFactory<B>() { B("some param") }
 *
 *   fun useB() {
 *     b.instance.foo()
 *   }
 * }
 *
 * class ATest {
 *   @Test fun test() {
 *     val a = A()
 *     a.b.newInstance = { mock() }
 *     a.useB()
 *   }
 * }
 *
 */
class TestableFactory<T>(factory: () -> T) {
    val instance: T by lazy { newInstance() }
    @ForTesting var newInstance: () -> T = { factory() }
}