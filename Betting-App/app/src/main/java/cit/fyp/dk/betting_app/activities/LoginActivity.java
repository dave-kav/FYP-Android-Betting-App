package cit.fyp.dk.betting_app.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cit.fyp.dk.betting_app.domain.Bet;
import cit.fyp.dk.betting_app.domain.Customer;
import cit.fyp.dk.betting_app.R;

public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final String apiUrl = "https://betting-app1.herokuapp.com/api/";
    private Customer customer;

    private EditText usernameText;
    private EditText passwordText;
    private Button loginButton;
    private TextView signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText = (EditText) findViewById(R.id.input_username);
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
            loginButton.setEnabled(true);
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        //login
        Ion.with(getApplicationContext())
                .load(apiUrl + "login")
                .setBodyParameter("username", username)
                .setBodyParameter("password", password)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            JSONObject json = new JSONObject(result);    // Converts the string "result" to a JSONObject
                            String jsonResult = json.getString("result"); // Get the string "result" inside the Json-object
                            if (jsonResult.equalsIgnoreCase("ok")){
                                String customerJson = json.getJSONObject("customer").toString();
                                Gson gson = new Gson();
                                customer = gson.fromJson(customerJson, Customer.class);

                                String betsJson = json.getJSONArray("bets").toString();
                                List<Bet> bets = gson.fromJson(betsJson, new TypeToken<List<Bet>>(){}.getType());
                                customer.setBets(bets);
                                for (Bet b: customer.getBets())
                                    Log.d(TAG, b.toString());
                            } else {
                                String error = json.getString("error");
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException j){
                            Toast.makeText(getApplicationContext(), "Sorry, cannot login right now!", Toast.LENGTH_LONG).show();
                        }
                    }
                });


        new android.os.Handler().postDelayed(new Runnable() {
                                                 public void run() {
                                                     // On complete call either onLoginSuccess or onLoginFailed
                                                     if (customer != null)
                                                         onLoginSuccess();
                                                     else
                                                         loginButton.setEnabled(true);
                                                     progressDialog.dismiss();
                                                 }
                                             }, 3000
        );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getBaseContext(), "Successful signup, please login", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("customer", customer);
        mainActivity.putExtras(b);
        startActivity(mainActivity);
        finish();
    }

    public boolean validate() {
        boolean valid = true;

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        if (username.isEmpty()) {
            usernameText.setError("enter a valid username");
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
