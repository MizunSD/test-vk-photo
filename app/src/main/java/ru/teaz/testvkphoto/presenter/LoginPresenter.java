package ru.teaz.testvkphoto.presenter;

public interface LoginPresenter extends MvpPresenter {

    void login(String... scopes);
}
