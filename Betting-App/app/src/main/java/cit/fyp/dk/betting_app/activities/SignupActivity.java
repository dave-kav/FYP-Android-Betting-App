package cit.fyp.dk.betting_app.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import cit.fyp.dk.betting_app.domain.Bet;
import cit.fyp.dk.betting_app.domain.Customer;
import cit.fyp.dk.betting_app.R;

public class SignupActivity extends Activity {
    private static final String TAG = "SignupActivity";
    private static final String apiUrl = "https://betting-app1.herokuapp.com/api/";

    private Customer customer;
    private Bet bet;

    private EditText firstNameText;
    private EditText lastNameText;
    private EditText usernameText;
    private EditText passwordText;
    private EditText dobText;
    private Button signupButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstNameText = (EditText) findViewById(R.id.input_first_name);
        lastNameText = (EditText) findViewById(R.id.input_last_name);
        usernameText = (EditText) findViewById(R.id.input_username);
        passwordText = (EditText) findViewById(R.id.input_password);
        dobText = (EditText) findViewById(R.id.input_dob);
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

        String firstName = firstNameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        String dob = dobText.getText().toString();
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        //signup
        Ion.with(getApplicationContext())
                .load(apiUrl + "/bet/new")

                .setBodyParameter("password", password)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            JSONObject json = new JSONObject(result);
                            String jsonResult = json.getString("result");
                            if (jsonResult.equalsIgnoreCase("ok")){
                                bet = new Bet();
                            } else {
                                String error = json.getString("error");
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException j){
                            Toast.makeText(getApplicationContext(), "Sorry, cannot sign-up right now!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (customer != null)
                            onSignupSuccess();
                        else
                            onSignupFailed();
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
        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String firstName = firstNameText.getText().toString();
        String lastName = firstNameText.getText().toString();
        String email = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String dob = dobText.getText().toString();

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

        if (dob.isEmpty()) {
            dobText.setError("Please enter a valid date of birth");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}