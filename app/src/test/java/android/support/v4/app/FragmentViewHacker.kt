package android.support.v4.app

import android.view.View

// set the Fragment's view
fun Fragment.hackView(view: View) {
    this.mView = view
}