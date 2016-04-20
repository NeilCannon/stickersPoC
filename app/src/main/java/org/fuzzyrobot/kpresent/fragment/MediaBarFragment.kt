package org.fuzzyrobot.kpresent.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.fuzzyrobot.kpresent.R

class MediaBarFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_media_bar, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    }


}

