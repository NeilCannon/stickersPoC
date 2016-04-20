package org.fuzzyrobot.k

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity

fun FragmentActivity.addFragment(containerId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().add(containerId, fragment).commit()
}

fun Fragment.addFragment(containerId: Int, fragment: Fragment) {
    (activity as AppCompatActivity).addFragment(containerId, fragment)
}

fun FragmentActivity.removeFragment(containerId: Int) {
    val existing = supportFragmentManager.findFragmentById(containerId)
    if (existing != null) {
        supportFragmentManager.beginTransaction().remove(existing).commit()
    }
}

fun Fragment.removeFragment(containerId: Int) {
    (activity as AppCompatActivity).removeFragment(containerId)
}
