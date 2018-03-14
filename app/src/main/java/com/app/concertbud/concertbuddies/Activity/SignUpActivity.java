package com.app.concertbud.concertbuddies.Activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.concertbud.concertbuddies.AppControllers.BaseActivity;
import com.app.concertbud.concertbuddies.CustomUI.AdjustableImageView;
import com.app.concertbud.concertbuddies.Helpers.AppUtils;
import com.app.concertbud.concertbuddies.Helpers.ImageLoader;
import com.app.concertbud.concertbuddies.R;
import com.facebook.Profile;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SignUpActivity extends BaseActivity {

    private final String TAG = SignUpActivity.class.getSimpleName();

    @BindView(R.id.logo_image)
    AdjustableImageView mLogoImage;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.user_name)
    TextView mUserFullName;
    @BindView(R.id.username_root)
    LinearLayout mUsernameView;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.update_info_btn)
    Button mContinueBtn;

    private Unbinder unbinder;
    private Profile fbProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Getting base variables
        unbinder = ButterKnife.bind(this);
        fbProfile = Profile.getCurrentProfile();

        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        if (fbProfile != null) {
            ImageLoader.loadCircleAdjustImageFromURI(mLogoImage, fbProfile.getProfilePictureUri(248, 248), mProgressBar);
            mUserFullName.setText(fbProfile.getFirstName() + " " + fbProfile.getLastName());
        }

        mContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "SignUp to Main", Toast.LENGTH_SHORT).show();
                AppUtils.startNewActivityAndFinish(getBaseContext(), SignUpActivity.this, MainActivity.class);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
