package uk.co.disciplemedia.testable

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import uk.co.disciplemedia.testable.ForTesting

/**
 * Encapsulates creating a fragment with a single argument, and retrieving the argument later
 * @param factory the ctor of the fragment class, e.g.: ::FooFragment
 * Supports Parcelable, String, Long
 * @property bundler can be swapped out with a mock for unit testing:
 *
 *   TestableFragmentFactory.bundler = { mock<Bundle>() }
 *
 */
object TestableFragmentFactory {
    val ARG = "TestableFragmentFactory.arg"

    @ForTesting var bundler: () -> Bundle = { Bundle() }

    fun <F : Fragment, A : Parcelable> newInstance(factory: () -> F, arg: A) = factory().apply {
        arguments = bundler().apply { putParcelable(ARG, arg) }
    }

    fun <F : Fragment, A : Long> newInstance(factory: () -> F, arg: A) = factory().apply {
        arguments = bundler().apply { putLong(ARG, arg) }
    }

    fun <F : Fragment, A : String> newInstance(factory: () -> F, arg: A) = factory().apply {
        arguments = bundler().apply { putString(ARG, arg) }
    }

    fun <A : Parcelable> getParcelable(f: Fragment): A = f.arguments?.getParcelable(ARG)!!

    fun getLong(f: Fragment): Long = f.arguments?.getLong(ARG)!!

    fun getString(f: Fragment): Long = f.arguments?.getLong(ARG)!!

}