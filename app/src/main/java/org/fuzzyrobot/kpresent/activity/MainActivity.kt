package org.fuzzyrobot.kpresent.activity

import android.os.Bundle
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import org.fuzzyrobot.k.addFragment
import org.fuzzyrobot.kpresent.CommentingFragment
import org.fuzzyrobot.kpresent.R
import org.fuzzyrobot.kpresent.application.ActivityComponent
import org.fuzzyrobot.kpresent.application.TheApp
import org.fuzzyrobot.kpresent.fragment.MainFragment

class MainActivity : RxAppCompatActivity() {

    private lateinit var commentingFragment: CommentingFragment
    val activityComponent: ActivityComponent = TheApp.forActivity(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            addFragment(R.id.container, MainFragment())

            commentingFragment = CommentingFragment()
            supportFragmentManager.beginTransaction().add(R.id.commenting_bar_container, commentingFragment).commit()
        } else {
            commentingFragment = supportFragmentManager.findFragmentById(R.id.commenting_bar_container) as CommentingFragment
        }

    }

}

