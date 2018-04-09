package com.walkud.self.mvp.presenter

import com.walkud.self.mvp.base.MvpPresenter
import com.walkud.self.mvp.model.MainModel
import com.walkud.self.mvp.ui.FirstActivity

class FirstPresenter(view: FirstActivity) : MvpPresenter<MainModel>(view) {

    fun init() {
    }

    fun handleClick() {
        model.request()
        view.forward(FirstActivity::class.java)
    }
}