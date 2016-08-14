package ru.teaz.testvkphoto.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.teaz.testvkphoto.presenter.MvpPresenter;
import ru.teaz.testvkphoto.presenter.PresenterFactory;
import ru.teaz.testvkphoto.ui.interfaces.MvpView;

public abstract class BaseActivity<V extends MvpView, P extends MvpPresenter> extends AppCompatActivity {

    private P presenter;
    protected static final String PHOTOS = "Photos";
    protected static final String POSITION = "Position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = PresenterFactory.create((V) this);
        presenter.bindView((V) this);
    }

    P getPresenter() {
        return presenter;
    }
}
