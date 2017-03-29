package cit.fyp.dk.betting_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import cit.fyp.dk.betting_app.Domain.Customer;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final String apiUrl = "https://betting-app1.herokuapp.com/api/";

    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    private TextView signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = (EditText) findViewById(R.id.input_username);
        passwordText = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        signupLink = (TextView) findViewById(R.id.link_signup);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String username = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        Ion.with(getApplicationContext())
                .load(apiUrl + "login")
                .setBodyParameter("userName", username)
                .setBodyParameter("password", password)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            JSONObject json = new JSONObject(result);    // Converts the string "result" to a JSONObject
                            String json_result = json.getString("result"); // Get the string "result" inside the Json-object
                            if (json_result.equalsIgnoreCase("ok")){ // Checks if the "result"-string is equals to "ok"
                                // Result is "OK"
                                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show(); // This will show the user what went wrong with a toast
                            } else {
                                // Result is NOT "OK"
                                String error = json.getString("error");
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show(); // This will show the user what went wrong with a toast
                                Intent to_main = new Intent(getApplicationContext(), MainActivity.class); // New intent to MainActivity
                                startActivity(to_main); // Starts MainActivity
                                finish(); // Add this to prevent the user to go back to this activity when pressing the back button after we've opened MainActivity
                            }
                        } catch (JSONException j){
                            // This method will run if something goes wrong with the json, like a typo to the json-key or a broken JSON.
                            j.printStackTrace();
                        }
                    }
                });


        new android.os.Handler().postDelayed(new Runnable() {
                public void run() {
                    // On complete call either onLoginSuccess or onLoginFailed
                    onLoginSuccess();
                    // onLoginFailed();
                    progressDialog.dismiss();
                }
            }, 3000
        );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here

                this.finish();
            }
        }
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (username.isEmpty()) {
            emailText.setError("enter a valid username");
            valid = false;
        } else {
            emailText.setError(null);
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
