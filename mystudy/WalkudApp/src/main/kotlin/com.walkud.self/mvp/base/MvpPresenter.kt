package com.walkud.self.mvp.base

import kotlin.reflect.KClass

/**
 * Mvp Presenter
 */
open class MvpPresenter<out M>(val view: BaseActivity) {

    //Model
    val model: M by lazy {
        (this::class.supertypes.first()
                .arguments.last().type!!.classifier as KClass<*>)
                .java.newInstance() as M
    }

    init {
    }
}