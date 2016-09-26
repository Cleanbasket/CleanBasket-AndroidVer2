package com.bridge4biz.laundry.cleanbasket_androidver2.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bridge4biz.laundry.cleanbasket_androidver2.R;
import com.bridge4biz.laundry.cleanbasket_androidver2.activities.MainActivity;
import com.bridge4biz.laundry.cleanbasket_androidver2.activities.ServiceIntroActivity;
import com.bridge4biz.laundry.cleanbasket_androidver2.constants.Constants;
import com.bridge4biz.laundry.cleanbasket_androidver2.utils.AnimationUtil;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.google.gson.Gson;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.auth.SessionCallback;
import com.kakao.util.exception.KakaoException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Gson gson = new Gson();
    private AnimationUtil animationUtil;

    private Session session;
    private SessionCallback mySessionCallback;
    private Context context;


    @BindView(R.id.emailRegisterView)
    LinearLayout emailRegisterView;
    @BindView(R.id.emailRegisterEdit)
    EditText emailRegisterEdit;
    @BindView(R.id.passwordRegisterEdit)
    EditText passwordRegisterEdit;
    @BindView(R.id.acceptAllCheck)
    CheckBox acceptAllCheck;
    @BindView(R.id.acceptPrivacyCheck)
    CheckBox acceptPrivacyCheck;
    @BindView(R.id.acceptTermsCheck)
    CheckBox acceptTermsCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        this.context = this;

        animationUtil = new AnimationUtil(this);
        showIntro();

        mySessionCallback = new MySessionStatusCallback();

        Session.initialize(this, AuthType.KAKAO_TALK);
        session = Session.getCurrentSession();
        session.addCallback(mySessionCallback);

        acceptAllCheck.setChecked(false);
        acceptPrivacyCheck.setChecked(false);
        acceptTermsCheck.setChecked(false);

        acceptAllCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    acceptAllCheck.setChecked(false);
                }
                if (!isChecked) {
                    acceptAllCheck.setChecked(true);
                    acceptPrivacyCheck.setChecked(true);
                    acceptTermsCheck.setChecked(true);
                }
            }
        });

        acceptTermsCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (acceptAllCheck.isChecked()) {
                    acceptAllCheck.setChecked(false);
                }
                if (isChecked) {
                    acceptTermsCheck.setChecked(false);
                }
                if (!isChecked) {
                    acceptTermsCheck.setChecked(true);
                    if (acceptPrivacyCheck.isChecked()) {
                        acceptAllCheck.setChecked(true);
                    }
                }
            }
        });

        acceptPrivacyCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (acceptAllCheck.isChecked()) {
                    acceptAllCheck.setChecked(false);
                }
                if (isChecked) {
                    acceptPrivacyCheck.setChecked(false);
                } else {
                    acceptPrivacyCheck.setChecked(true);
                    if (acceptTermsCheck.isChecked()) {
                        acceptAllCheck.setChecked(true);

                    }
                }
            }
        });
    }

    private void showIntro() {
        Intent intent = new Intent(this, ServiceIntroActivity.class);
        startActivity(intent);
    }

    @BindView(R.id.basket)
    ImageView logo;
    @BindView(R.id.loginAndRegisterView)
    RelativeLayout loginAndRegisterView;
    @BindView(R.id.loginView)
    LinearLayout loginView;
    private boolean loginClicked = false;

    @OnClick(R.id.login)
    public void showLoginView() {
        animationUtil.moveViewToLeftAndGone(logo);
        animationUtil.moveViewToLeftAndGone(loginAndRegisterView);
        animationUtil.moveViewToLeftAndShow(loginView);
        loginClicked = true;
    }


    @BindView(R.id.registerView)
    LinearLayout registerView;
    private boolean registerClicked = false;

    @OnClick(R.id.register)
    public void showRegisterView() {
        animationUtil.moveViewToLeftAndGone(loginAndRegisterView);
        animationUtil.moveViewToLeftAndShow(registerView);
        registerClicked = true;
    }


    //로그인, 회원가입 버튼을 눌렀을때 뒤로가기
    @Override
    public void onBackPressed() {
        if (loginClicked) {
            animationUtil.moveViewToRightAndGone(loginView);
            animationUtil.moveViewToRightAndShow(logo);
            animationUtil.moveViewToRightAndShow(loginAndRegisterView);
            loginClicked = false;
        } else if (registerClicked) {
            animationUtil.moveViewToRightAndGone(registerView);
            animationUtil.moveViewToRightAndShow(loginAndRegisterView);
            registerClicked = false;
        } else if (showRegisterClicked) {
            animationUtil.moveViewToRightAndGone(emailRegisterView);
            animationUtil.moveViewToRightAndShow(registerView);
            animationUtil.moveViewToRightAndShow(logo);
            registerClicked = true;
            showRegisterClicked = false;
        } else {
            super.onBackPressed();
        }
    }

    @BindView(R.id.et_login_email)
    EditText etEmail;
    @BindView(R.id.et_login_password)
    EditText etPassword;

    @OnClick(R.id.emailLogin)
    public void login() {
        AuthManager authManager = new AuthManager();
        authManager.login(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();

                switch (jsonData.constant) {
                    case Constants.SESSION_EXPIRED:

                        break;
                    case Constants.EMAIL_ERROR:
                        Toast.makeText(LoginActivity.this, "존재하지 않는 이메일입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case Constants.PASSWORD_ERROR:
                        Toast.makeText(LoginActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case Constants.ACCOUNT_DISABLED:
                        Toast.makeText(LoginActivity.this, "승인되지 않은 사용자입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case Constants.SUCCESS:
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        break;
                }
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "인터넷 환경을 확인해주시길 바랍니다.", Toast.LENGTH_SHORT).show();
            }
        }, etEmail.getText().toString(), etPassword.getText().toString(), true, "");

    }


    @OnClick({R.id.kakaoLogin, R.id.kakaoRegisterButton})
    public void kakaoLogin() {
        session.getCurrentSession().open(AuthType.KAKAO_TALK, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data))
            return;
    }

    private class MySessionStatusCallback implements SessionCallback {
        @Override
        public void onSessionOpened() {
            Intent intent = new Intent(context, KakaoRegisterActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onSessionClosed(final KakaoException exception) {
        }

        @Override
        public void onSessionOpening() {
        }
    }

    @OnClick(R.id.emailRegisterButton)
    public void onEmailRegisterClicked() {
        if (!(acceptAllCheck.isChecked())) {
            Toast.makeText(this, "약관에 동의하세요.", Toast.LENGTH_LONG).show();
            return;
        }

        AuthManager authManager = new AuthManager();
        String email = emailRegisterEdit.getText().toString();
        if (email.isEmpty() || email == null) {
            Toast.makeText(this, "Email을 입력하세요.", Toast.LENGTH_LONG).show();
            return;
        }

        String password = passwordRegisterEdit.getText().toString();
        if (password.isEmpty() || password == null) {
            Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
            return;
        }

        authManager.signUpWithEmail(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                if (jsonData.constant == Constants.SUCCESS) {
                    animationUtil.moveViewToRightAndGone(emailRegisterView);
                    animationUtil.moveViewToRightAndShow(loginView);
                } else if (jsonData.constant == Constants.ACCOUNT_DUPLICATION) {
                    Toast.makeText(LoginActivity.this, "이미 등록된 이메일입니다.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        }, email, password);
    }


    private boolean showRegisterClicked = false;

    @OnClick(R.id.showEmailRegisterButton)
    public void showEmailRegister() {
        showRegisterClicked = true;
        registerClicked = false;


        animationUtil.moveViewToLeftAndGone(registerView);
        animationUtil.moveViewToLeftAndGone(logo);
        animationUtil.moveViewToLeftAndShow(emailRegisterView);
    }


}
