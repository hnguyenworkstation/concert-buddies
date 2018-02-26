package com.app.concertbud.concertbuddies.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.concertbud.concertbuddies.AppControllers.BaseActivity;
import com.app.concertbud.concertbuddies.CustomUI.AdjustableImageView;
import com.app.concertbud.concertbuddies.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Getting base variables
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
