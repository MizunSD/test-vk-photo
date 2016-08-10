package ru.teaz.testvkphoto.ui.activity;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ru.teaz.testvkphoto.R;
import ru.teaz.testvkphoto.presenter.LoginPresenter;
import ru.teaz.testvkphoto.ui.interfaces.LoginView;
import ru.teaz.testvkphoto.utils.Logger;
import ru.teaz.testvkphoto.utils.SharedPreferencesManager;

public class MainActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView{

    @InjectView(R.id.button_sign_in)
    Button buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccessLogin() {
        startActivity(new Intent(this, ContentActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {

            @Override
            public void onResult(VKAccessToken res) {
                Logger.d("Auth success: " + res.accessToken);
                SharedPreferencesManager.saveAccessToken(getApplicationContext(), res);

                onSuccessLogin();
                finish();
            }

            @Override
            public void onError(VKError error) {
                Logger.e("Auth failed: " + error.toString());
                showError(error.toString());
            }

        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.button_sign_in)
    public void signIn() {
        getPresenter().login(VKScope.PHOTOS);
    }
}
