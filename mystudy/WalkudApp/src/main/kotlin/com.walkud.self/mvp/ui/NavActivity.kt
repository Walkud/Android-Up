package com.walkud.self.mvp.ui

import android.os.Bundle
import android.view.View
import com.walkud.self.R
import com.walkud.self.mvp.base.BaseActivity
import com.walkud.self.mvp.model.MainModel
import com.walkud.self.mvp.presenter.NavPresenter
import kotlinx.android.synthetic.main.activity_nav.*

class NavActivity : BaseActivity() {

    val presenter by lazy {
        NavPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)

        first_page_btn.setOnClickListener(View.OnClickListener {
            //            presenter.handleClick()
            MainModel().request()
//        view.forward(FirstActivity::class.java)
            showToast("NavPresenter handleClick")
        })

        presenter.handleClick()
    }
}