package ru.teaz.testvkphoto.presenter;

import ru.teaz.testvkphoto.presenter.impl.LoginPresenterImpl;
import ru.teaz.testvkphoto.presenter.impl.PhotoGridPresenterImpl;
import ru.teaz.testvkphoto.ui.interfaces.LoginView;
import ru.teaz.testvkphoto.ui.interfaces.MvpView;
import ru.teaz.testvkphoto.ui.interfaces.PhotoGridView;

public class PresenterFactory {

    public static <P extends MvpPresenter> P create(MvpView view) {

        if (view instanceof LoginView) {
            return (P) new LoginPresenterImpl();
        } else {
            if (view instanceof PhotoGridView) {
                return (P) new PhotoGridPresenterImpl();
            } else {
                throw new IllegalArgumentException("View doesn't implement supported interface");
            }
        }
    }
}
