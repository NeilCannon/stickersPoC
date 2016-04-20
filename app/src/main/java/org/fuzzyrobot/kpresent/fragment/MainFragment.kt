package org.fuzzyrobot.kpresent.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.view.*
import org.fuzzyrobot.kpresent.R
import org.fuzzyrobot.kpresent.inflate

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val recycler = view?.main_recycler
        recycler?.layoutManager = LinearLayoutManager(context)
        recycler?.adapter = MainRecyclerAdapter()
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class MainRecyclerAdapter : RecyclerView.Adapter<Holder>() {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder? = Holder(inflate(R.layout.main_recycler_item, parent))

        override fun onBindViewHolder(holder: Holder?, position: Int) { }

        override fun getItemCount(): Int = 64

    }


}

