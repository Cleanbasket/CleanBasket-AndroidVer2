package com.bridge4biz.laundry.cleanbasket_androidver2.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.bridge4biz.laundry.cleanbasket_androidver2.activities.MainActivity;
import com.bridge4biz.laundry.cleanbasket_androidver2.constants.Constants;
import com.bridge4biz.laundry.cleanbasket_androidver2.utils.HashGenerator;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.JsonData;
import com.bridge4biz.laundry.cleanbasket_androidver2.vo.UserData;
import com.kakao.auth.APIErrorResult;
import com.kakao.usermgmt.LogoutResponseCallback;
import com.kakao.usermgmt.MeResponseCallback;
import com.kakao.usermgmt.SignupResponseCallback;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.UserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KakaoRegisterActivity extends AppCompatActivity {
    private static final String TAG = KakaoRegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }

    protected void redirectLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    protected void redirectMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * 가입 입력창의 정보를 모아서 가입 API를 호출한다.
     */
    private void signUp() {
        UserManagement.requestSignup(new SignupResponseCallback() {
            @Override
            public void onSuccess(final long userId) {
                signUpServer(userId);
            }

            @Override
            public void onSessionClosedFailure(final APIErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onFailure(final APIErrorResult errorResult) {
                String message = "failed to sign up. msg=" + errorResult;
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                redirectLoginActivity();
            }
        }, null);
    }

    private void signUpServer(final long userId) {
        UserData userData = new UserData();
        userData.email = String.valueOf(userId);
        userData.password = HashGenerator.makeHash(String.valueOf(userId));

        AuthManager authManager = new AuthManager();
        authManager.signUpWithKaKao(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                switch (jsonData.constant) {
                    case Constants.ACCOUNT_DUPLICATION:
                        login(userId);
                        break;

                    case Constants.ERROR:
                        logout();
                        break;

                    case Constants.SUCCESS:
                        login(userId);
                        break;
                }
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                logout();
            }
        }, userData);

    }

    private void login(final long userId) {

        AuthManager authManager = new AuthManager();
        authManager.login(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();

                switch (jsonData.constant) {
                    case Constants.SESSION_EXPIRED:
                        logout();
                        break;

                    case Constants.EMAIL_ERROR:
                        signUpServer(userId);
                        break;

                    case Constants.PASSWORD_ERROR:
                        logout();
                        break;

                    case Constants.ACCOUNT_DISABLED:
                        logout();
                        break;

                    case Constants.SUCCESS:
                        logoutKakao();
                        break;
                }
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        }, String.valueOf(userId), HashGenerator.makeHash(String.valueOf(userId)), true, "");

    }

    private void logout() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onSuccess(final long userId) {
                redirectLoginActivity();
            }

            @Override
            public void onFailure(final APIErrorResult apiErrorResult) {
                redirectLoginActivity();
            }
        });
    }

    private void logoutKakao() {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onSuccess(final long userId) {
                redirectMainActivity();
                finish();
            }

            @Override
            public void onFailure(final APIErrorResult apiErrorResult) {
                redirectLoginActivity();
            }
        });
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    private void requestMe() {
        try {
            UserManagement.requestMe(new MeResponseCallback() {
                @Override
                public void onSuccess(final UserProfile userProfile) {
                    Log.e("호출되나", "onSuccess");
                    login(userProfile.getId());
                }

                @Override
                public void onNotSignedUp() {
                    Log.e("호출되나", "onNotSignedUp");
                    signUp();
                }

                @Override
                public void onSessionClosedFailure(final APIErrorResult errorResult) {
                    Log.e("호출되나", "onSessionClosed");
                    redirectLoginActivity();
                }

                @Override
                public void onFailure(final APIErrorResult errorResult) {
                    redirectLoginActivity();
                }
            });
        } catch (IllegalStateException e) {
            Log.e(TAG, e.toString());
        }
    }
}
