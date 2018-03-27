package com.app.concertbud.concertbuddies.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.concertbud.concertbuddies.AppControllers.BaseActivity;
import com.app.concertbud.concertbuddies.CustomUI.AdjustableImageView;
import com.app.concertbud.concertbuddies.Helpers.AppUtils;
import com.app.concertbud.concertbuddies.Helpers.StringUtils;
import com.app.concertbud.concertbuddies.Networking.Responses.User;
import com.app.concertbud.concertbuddies.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.logo_image)
    AdjustableImageView mLogoImage;

    @BindView(R.id.fb_button)
    LoginButton mFbLoginButton;

    @BindView(R.id.login_button)
    Button mCustomLoginButton;

    private Unbinder unbinder;

    private CallbackManager mCallbackManager;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        unbinder = ButterKnife.bind(this);

        mCallbackManager = CallbackManager.Factory.create();

        mAuth = FirebaseAuth.getInstance();

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
                AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // TODO: Update Firebase database with facebook login info (Server or Client side's job??)
                            Log.e(TAG, "signInWithCredential succeeds");
                            Profile profile = Profile.getCurrentProfile();
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("Users").setValue(mAuth.getUid());
                            mDatabase.child("Users").child(mAuth.getUid()).setValue(
                                    new User(profile.getName(), profile.getId())
                            );
                        }
                    }
                });
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
        AppUtils.startNewActivityAndFinish(this, LoginActivity.this,
                SignUpActivity.class);
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
