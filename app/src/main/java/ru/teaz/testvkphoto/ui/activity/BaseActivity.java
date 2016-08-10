package ru.teaz.testvkphoto.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.teaz.testvkphoto.presenter.MvpPresenter;
import ru.teaz.testvkphoto.presenter.PresenterFactory;
import ru.teaz.testvkphoto.ui.interfaces.MvpView;
import ru.teaz.testvkphoto.utils.Logger;

public abstract class BaseActivity<V extends MvpView, P extends MvpPresenter> extends AppCompatActivity {

    private P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(getLocalClassName() + " created");
        presenter = PresenterFactory.create((V) this);
        presenter.bindView((V) this);
    }

    P getPresenter() {
        return presenter;
    }
}
