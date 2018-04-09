package com.walkud.self.mvp.presenter

import com.walkud.self.mvp.base.MvpPresenter
import com.walkud.self.mvp.model.MainModel
import com.walkud.self.mvp.ui.NavActivity

class NavPresenter(view: NavActivity) : MvpPresenter<MainModel>(view) {

    fun init() {
//        view.showToast("NavPresenter 初始化 ")
//        model.request()
    }

    fun handleClick() {
        model.request()
//        view.forward(FirstActivity::class.java)
//        view.showToast("NavPresenter handleClick")
//        view?.showToast("")
    }
}