package com.app.concertbud.concertbuddies.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.concertbud.concertbuddies.AppControllers.BaseActivity;
import com.app.concertbud.concertbuddies.CustomUI.AdjustableImageView;
import com.app.concertbud.concertbuddies.Helpers.AppUtils;
import com.app.concertbud.concertbuddies.Helpers.StringUtils;
import com.app.concertbud.concertbuddies.Networking.NetContext;
import com.app.concertbud.concertbuddies.Networking.Responses.CompleteFacebookUserResponse;
import com.app.concertbud.concertbuddies.Networking.Services.BackendServices;
import com.app.concertbud.concertbuddies.Networking.Services.FacebookServices;
import com.app.concertbud.concertbuddies.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.IOException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.logo_image)
    AdjustableImageView mLogoImage;

    @BindView(R.id.fb_button)
    LoginButton mFbLoginButton;

    @BindView(R.id.login_button)
    Button mCustomLoginButton;

    private Unbinder unbinder;

    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        unbinder = ButterKnife.bind(this);

        mCallbackManager = CallbackManager.Factory.create();

        initFbButton();
        initView();
    }


    private void initView() {
        AppUtils.loadImageFromDrawable(getResources().getDrawable(R.drawable.big_logo_image), mLogoImage);

        mCustomLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFbLoginButton.performClick();
            }
        });
    }


    private void initFbButton() {
        mFbLoginButton.setReadPermissions(Arrays.asList(StringUtils.getFbPermissions()));
        mFbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Logging In Success", Toast.LENGTH_SHORT).show();
                Log.d("HUONG", "loginsuccess");

                /* Update Backend */
                updateBackend(loginResult.getAccessToken().getToken());

                onFacebookLoginSuccessful(loginResult);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }
        });
    }

    private void onFacebookLoginSuccessful(LoginResult loginResults){
        Toast.makeText(getApplicationContext(), "Login to SignUp", Toast.LENGTH_SHORT).show();
        AppUtils.startNewActivityAndFinish(this, LoginActivity.this,
                SignUpActivity.class);
    }

    // <<<
    private void updateBackend(String token) {
        // Get user information
        CompleteFacebookUserResponse user = getUserInformation(token);

        // POST call to backend
        BackendServices services = NetContext.instance.create(BackendServices.class);
        // TODO: get dynamic DOB and gender
        services.postUser(user.getName(), "2012-05-03T00:00:00.00Z",
                "female", token)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Log.e(TAG, "Succesfully posted to DB");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
    }
    private CompleteFacebookUserResponse getUserInformation(String token) {
        FacebookServices services = NetContext.instance.create(FacebookServices.class);
        // TODO: update permission of facebook to also get DOB and gender
        String fields = "id,name,email";
        try {
            CompleteFacebookUserResponse response = services.getUserInformation(fields, token)
                    .execute().body();
            return response;
        } catch (IOException e) {
            // TODO: handle error
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
