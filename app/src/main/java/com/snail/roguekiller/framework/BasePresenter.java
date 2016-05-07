package com.snail.roguekiller.framework;

/**
 * Created by personal on 16/5/7.
 */
public class BasePresenter<T> {

    protected T mTarget;

    BasePresenter(T target) {
        this.mTarget = target;
    }

}
