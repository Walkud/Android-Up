package com.walkud.self.mvp.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.walkud.self.R
import com.walkud.self.mvp.base.BaseActivity
import com.walkud.self.mvp.presenter.FirstPresenter
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : BaseActivity() {

    val presenter by lazy { FirstPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_first)

        show_toast_btn.setOnClickListener(View.OnClickListener {
            presenter.handleClick()
        })

        presenter.init()
    }

}