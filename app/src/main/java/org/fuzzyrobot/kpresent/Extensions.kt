package org.fuzzyrobot.kpresent

import android.content.Context
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest


fun Fragment.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(view?.windowToken, 0);
}

fun Fragment.showKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(view, 0);
}

fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(windowToken, 0);
}

fun View.showKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(this, 0);
}

fun Fragment.postDelayed(delayMillis : Long, f:()->Unit) {
    getView()?.postDelayed(Runnable { f() }, delayMillis)
}

//fun Fragment.inflate(layoutId: Int) : View {
//    return inflater?.inflate(R.layout.fragment_media_bar, container, false)
//}

fun inflate(layoutId: Int, parent: ViewGroup?): View {
    val inflater = LayoutInflater.from(parent!!.context)
    val view = inflater.inflate(layoutId, parent, false)
    return view
}

fun load(drawee: SimpleDraweeView?, url: String) {
    val controller = Fresco.newDraweeControllerBuilder()
            .setAutoPlayAnimations(true)
            .setImageRequest(ImageRequest.fromUri(url))
            .build()
    drawee?.controller = controller
}
