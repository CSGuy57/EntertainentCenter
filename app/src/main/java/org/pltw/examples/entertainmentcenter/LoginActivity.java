package org.pltw.examples.entertainmentcenter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private Button btLogin;
    private Button btSignup;
    private EditText etName;
    private EditText etEmailAddress;
    private EditText etPassword;
    private TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLogin = findViewById(R.id.bt_login);
        etName = findViewById(R.id.et_name);
        etEmailAddress = findViewById(R.id.et_email_address);
        etPassword = findViewById(R.id.et_password);
        tvSignup = findViewById(R.id.tv_signup);
        btSignup = findViewById(R.id.bt_signup);

        Backendless.initApp(this,
                getString(R.string.be_app_id),
                getString(R.string.be_android_api_key));

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btLogin.getVisibility() == View.VISIBLE) {
                    btLogin.setVisibility(View.GONE);
                    etName.setVisibility(View.VISIBLE);
                    btSignup.setVisibility(View.VISIBLE);
                    tvSignup.setText(R.string.cancel_sign_up);
                } else {
                    btLogin.setVisibility(View.VISIBLE);
                    etName.setVisibility(View.GONE);
                    btSignup.setVisibility(View.GONE);
                    tvSignup.setText(R.string.sign_me_up);
                }
            }
        });

        etPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN) {
                    btLogin.performClick();
                    return true;
                }

                return false;
            }
        });

        btSignup.setOnClickListener(new SignUpOnClickListener());
        btLogin.setOnClickListener(new LoginOnClickListener());
    }

    private void warnUser(String message) {
        /* warn the user of the problem */
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(message);
        builder.setTitle(R.string.authentication_error_title);
        builder.setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String validateEmailPassword(String email, String password) {
        if(!email.contains("@") || !email.contains(".")) {
            return getString(R.string.invalid_email_format_error);
        } else if (password.length() < 6) {
            return getString(R.string.password_length_error);
        } else if (password.equals(email)) {
            return getString(R.string.password_email_same_error);
        }

        return "";
    }

    private class LoginOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String userEmail = etEmailAddress.getText().toString();
            String password = etPassword.getText().toString();

            userEmail = userEmail.trim();
            password = password.trim();

            String validated = validateEmailPassword(userEmail, password);

            if (!userEmail.isEmpty() &&!password.isEmpty()) {
                final ProgressDialog pDialog = ProgressDialog.show(LoginActivity.this,
                        getString(R.string.progress_title),
                        getString(R.string.logging_in_message),
                        true);

                Backendless.UserService.login(userEmail, password, new AsyncCallback<BackendlessUser>() {
                    public void handleResponse(BackendlessUser user) {
                        // user has been logged in
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    public void handleFault(BackendlessFault fault) {
                        pDialog.dismiss();
                        // login failed, to get the error code call fault.getCode()
                        warnUser(fault.getMessage());
                    }
                });
            } else {
                warnUser(getString(R.string.empty_field_login_error));
            }
        }
    }

    private class SignUpOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String userEmail = etEmailAddress.getText().toString();
            String password = etPassword.getText().toString();
            String name = etName.getText().toString();

            userEmail = userEmail.trim();
            password = password.trim();
            name = name.trim();

            if (!userEmail.isEmpty() &&!password.isEmpty() && !name.isEmpty()) {
                String validated = validateEmailPassword(userEmail, password);

                if(validated.isEmpty()){
                    /* register the user in Backendless */
                    BackendlessUser user = new BackendlessUser();
                    user.setEmail(userEmail);
                    user.setProperty("name", name);
                    user.setPassword(password);

                    final ProgressDialog pDialog = ProgressDialog.show(LoginActivity.this,
                            getString(R.string.progress_title),
                            getString(R.string.creating_new_account_message),
                            true);

                    Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                        public void handleResponse(BackendlessUser registeredUser) {
                            // user has been registered and now can login
                            Log.i(TAG, "User registered in Backendless.");
                            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this,
                                    "User was successfully registered!",
                                    Toast.LENGTH_LONG).show();
                        }

                        public void handleFault(BackendlessFault fault) {
                            Log.e(TAG, fault.getDetail());
                            // an error has occurred, the error code can be retrieved with fault.getCode()
                            pDialog.dismiss();
                        }
                    });
                } else {
                    warnUser(validated);
                }
            }
            else {
                /* warn the user of the problem */
                warnUser(getString(R.string.empty_field_signup_error));
            }
        }
    }
}
