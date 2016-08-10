package ru.teaz.testvkphoto.presenter.impl;

import android.app.Activity;

import ru.teaz.testvkphoto.domain.net.ServerApi;
import ru.teaz.testvkphoto.domain.net.ServerApiFactory;
import ru.teaz.testvkphoto.presenter.LoginPresenter;
import ru.teaz.testvkphoto.ui.interfaces.LoginView;
import ru.teaz.testvkphoto.ui.interfaces.MvpView;

public class LoginPresenterImpl implements LoginPresenter{

    private LoginView mView;
    private final ServerApi mServerApi = ServerApiFactory.create();

    @Override
    public void bindView(MvpView view) {
        if (view instanceof LoginView) {
            mView = (LoginView) view;
        } else {
            throw new IllegalArgumentException("View must be instance of LoginView");
        }
    }

    @Override
    public void login(String... scopes) {
        mServerApi.login((Activity) mView, scopes);
    }
}
