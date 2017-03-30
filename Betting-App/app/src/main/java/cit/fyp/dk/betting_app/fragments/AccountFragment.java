package cit.fyp.dk.betting_app.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cit.fyp.dk.betting_app.domain.Bet;
import cit.fyp.dk.betting_app.domain.Customer;
import cit.fyp.dk.betting_app.domain.Status;
import cit.fyp.dk.betting_app.activities.MainActivity;
import cit.fyp.dk.betting_app.R;

/**
 * Created by davyk on 28/03/2017.
 */

public class AccountFragment extends Fragment {

    private Customer customer;
    private static final String apiUrl = "https://betting-app1.herokuapp.com/api/";

    public AccountFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customer = ((MainActivity)this.getActivity()).getCustomer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

//        refreshBets();

        TextView usernameTV = (TextView) view.findViewById(R.id.user_name);
        usernameTV.setText(customer.getUsername());

        TextView nameTV = (TextView) view.findViewById(R.id.full_name);
        nameTV.setText(customer.getFirstName() + " " + customer.getLastName());

        TextView dobTV = (TextView) view.findViewById(R.id.dob);
        dobTV.setText(customer.getDOB().toString());

        TextView balanceTV = (TextView) view.findViewById(R.id.account_balance);
        balanceTV.setText("\u20ac" + customer.getCredit());

        TextView totalBetsTV = (TextView) view.findViewById(R.id.total_bets);
        totalBetsTV.setText(customer.getBets().size() + "");

        TextView openTV = (TextView) view.findViewById(R.id.open);
        openTV.setText(customer.getBetsByStatus(Status.OPEN).size() + "");

        TextView winnersTV = (TextView) view.findViewById(R.id.winners);
        winnersTV.setText(customer.getBetsByStatus(Status.WINNER).size() + "");

        TextView placedTV = (TextView) view.findViewById(R.id.placed);
        placedTV.setText(customer.getBetsByStatus(Status.PLACED).size() + "");

        TextView losersTV = (TextView) view.findViewById(R.id.losers);
        losersTV.setText(customer.getBetsByStatus(Status.LOSER).size() + "");

        return view;
    }

    private void refreshBets() {
        final ProgressDialog progressDialog = new ProgressDialog((this.getActivity()),
                R.style.Theme_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Reloading bets...");
        progressDialog.show();

        Ion.with(this.getActivity())
                .load(apiUrl + "bets/" + customer.getUsername())
                .setBodyParameter("username", customer.getUsername())
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            JSONObject json = new JSONObject(result);    // Converts the string "result" to a JSONObject
                            String jsonResult = json.getString("result"); // Get the string "result" inside the Json-object
                            if (jsonResult.equalsIgnoreCase("ok")){
                                Gson gson = new Gson();

                                String betsJson = json.getJSONArray("bets").toString();
                                List<Bet> bets = gson.fromJson(betsJson, new TypeToken<List<Bet>>(){}.getType());
                                customer.setBets(bets);
                            } else {
                                String error = json.getString("error");
                            }
                        } catch (JSONException j){
                            j.printStackTrace();
                        }
                    }
                });

        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 2000 );
    }
}
