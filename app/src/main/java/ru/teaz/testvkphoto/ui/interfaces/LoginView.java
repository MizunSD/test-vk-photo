package ru.teaz.testvkphoto.ui.interfaces;

public interface LoginView extends MvpView{

    void showError(String errorMessage);
    void onSuccessLogin();
}
