package cit.fyp.dk.betting_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {
    private static final String TAG = "SignupActivity";

    private EditText firstNameText;
    private EditText lastNameText;
    private EditText usernameText;
    private EditText passwordText;
    private Button signupButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstNameText = (EditText) findViewById(R.id.input_first_name);
        lastNameText = (EditText) findViewById(R.id.input_last_name);
        usernameText = (EditText) findViewById(R.id.input_username);
        passwordText = (EditText) findViewById(R.id.input_password);
        signupButton = (Button) findViewById(R.id.btn_signup);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.Theme_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = firstNameText.getText().toString();
        String email = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String firstName = firstNameText.getText().toString();
        String lastName = firstNameText.getText().toString();
        String email = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        if (firstName.isEmpty() || firstName.length() < 3) {
            firstNameText.setError("at least 3 characters");
            valid = false;
        } else {
            firstNameText.setError(null);
        }

        if (lastName.isEmpty() || lastName.length() < 3) {
            lastNameText.setError("at least 3 characters");
            valid = false;
        } else {
            lastNameText.setError(null);
        }

        if (email.isEmpty()) {
            usernameText.setError("enter a valid user name");
            valid = false;
        } else {
            usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 8 || password.length() > 100) {
            passwordText.setError("between 8 and 100 characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}