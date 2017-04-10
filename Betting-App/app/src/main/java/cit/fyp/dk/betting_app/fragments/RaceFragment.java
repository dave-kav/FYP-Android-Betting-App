package cit.fyp.dk.betting_app.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.LoadDeepZoom;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cit.fyp.dk.betting_app.R;
import cit.fyp.dk.betting_app.activities.BetDetailActivity;
import cit.fyp.dk.betting_app.activities.LoginActivity;
import cit.fyp.dk.betting_app.activities.MainActivity;
import cit.fyp.dk.betting_app.domain.Bet;
import cit.fyp.dk.betting_app.domain.Customer;
import cit.fyp.dk.betting_app.domain.Horse;
import cit.fyp.dk.betting_app.domain.Race;

/**
 * Created by davyk on 28/03/2017.
 */

public class RaceFragment extends Fragment{

    private static final String apiUrl = "https://betting-app1.herokuapp.com/api/";

    private Customer customer;
    private Bet bet;
    private ArrayList<Race> races;
    private List<String> meetingArray;
    private List<String> racesArray;
    private List<String> horsesArray;
    private double stake;

    private Spinner meetingItems;
    private Spinner raceItems;
    private Spinner horseItems;
    private RelativeLayout betSection;
    private TextView balanceTv;
    private TextView costTv;
    private EditText stakeEt;
    private CheckBox checkBox;
    private AppCompatButton button;

    public RaceFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customer = ((MainActivity) this.getActivity()).getCustomer();
        races = ((MainActivity) this.getActivity()).getRaces();

        meetingArray =  new ArrayList<>();
        meetingArray.add("Select race meeting...");

        racesArray = new ArrayList<>();
        racesArray.add("Select race time...");

        horsesArray = new ArrayList<>();
        horsesArray.add("Select horse...");

        stake = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_race, container, false);

        // hide sections to be shown upon selection from previous spinner
        raceItems = (Spinner) view.findViewById(R.id.races_spinner);
        raceItems .setVisibility(View.INVISIBLE);

        horseItems = (Spinner) view.findViewById(R.id.horses_spinner);
        horseItems.setVisibility(View.INVISIBLE);

        betSection = (RelativeLayout) view.findViewById(R.id.bet_section);
        betSection.setVisibility(View.INVISIBLE);
        balanceTv = (TextView) view.findViewById(R.id.bet_account_balance);
        costTv = (TextView) view.findViewById(R.id.total_cost);
        stakeEt = (EditText) view.findViewById(R.id.new_bet_stake);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        button = (AppCompatButton) view.findViewById(R.id.btn_place_bet);
        button.setVisibility(View.INVISIBLE);

        // load all race meetings into spinner
        getRaceMeetings();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, meetingArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meetingItems = (Spinner) view.findViewById(R.id.meeting_spinner);
        meetingItems.setAdapter(adapter);

        //first spinner
        meetingItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (meetingItems.getSelectedItemPosition() == 0) {
                    // hide views
                    raceItems .setVisibility(View.INVISIBLE);
                    horseItems.setVisibility(View.INVISIBLE);
                    betSection.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.INVISIBLE);
                } else {
                    raceItems.setSelection(0);

                    // load races for selected meeting into spinner and display the spinner
                    getRacesByMeeting(meetingItems.getSelectedItem().toString());

                    raceItems.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, racesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raceItems.setAdapter(adapter);

        //second spinner
        raceItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (raceItems.getSelectedItemPosition() == 0) {
                    horseItems.setVisibility(View.INVISIBLE);
                    betSection.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.INVISIBLE);
                } else {
                    horseItems.setSelection(0);

                    // load races for selected meeting into spinner and display the spinner
                    getHorsesByRace(raceItems.getSelectedItem().toString());
                    horseItems.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, horsesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        horseItems.setAdapter(adapter);

        //third spinner
        horseItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (horseItems.getSelectedItemPosition() == 0) {
                    betSection.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.INVISIBLE);
                } else {
                    balanceTv.setText(String.format("Available Balance: \u20ac%.2f", customer.getCredit()));
                    betSection.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        stakeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getView().getParent().requestChildFocus(stakeEt, stakeEt);
                if (!charSequence.toString().equals("")) {
                    stake = Double.parseDouble(charSequence.toString());
                    costTv.setText(String.format("Total Cost: \u20ac%.2f", stake));
                    button.setVisibility(View.VISIBLE);
                } else {
                    costTv.setText("Total Cost: \u20ac0.00");
                    button.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stake != 0) {
                    if (checkBox.isChecked())
                        stake = stake * 2;
                    else {
                        stake = stake / 2;
                        stakeEt.setText(String.format("%.2f",stake));
                    }
                    costTv.setText(String.format("Total Cost: \u20ac%.2f", stake));
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stake > customer.getCredit())
                    stakeEt.setError("You do not have enough credit to place this bet!");
                else {
                    //do request to place bet
                    final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                            R.style.Theme_AppCompat_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Placing Bet...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    // send bet object
                    Ion.with(getContext())
                            .load(apiUrl + "bet/new")
                            .setBodyParameter("username", customer.getUsername())
                            .setBodyParameter("stake", stake + "")
                            .setBodyParameter("eachway", checkBox.isChecked() ? "true" : "false")
                            .setBodyParameter("horse", horseItems.getSelectedItem().toString())
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result) {
                                    try {
                                        JSONObject json = new JSONObject(result);    // Converts the string "result" to a JSONObject
                                        Log.d("racefrag", json.toString());
                                        String jsonResult = json.getString("result"); // Get the string "result" inside the Json-object
                                        if (jsonResult.equalsIgnoreCase("ok")){
                                            String betJson = json.getJSONObject("bet").toString();
                                            Gson gson = new Gson();
                                            bet = gson.fromJson(betJson, Bet.class);
                                        } else {
                                            Toast.makeText(getContext(), "error", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException j){
                                        Toast.makeText(getContext(), "Sorry, cannot place bet right now!", Toast.LENGTH_LONG).show();
                                        j.printStackTrace();
                                    }
                                }
                            });


                    new android.os.Handler().postDelayed(new Runnable() {
                                                             public void run() {
                                                                 if (bet != null) {
                                                                     Toast.makeText(getContext(), "Bet Successfully Placed!", Toast.LENGTH_SHORT).show();

                                                                     Map wrapper = getHorseInRace(bet.getSelection());
                                                                     bet.setRace((Race)wrapper.get("race"));
                                                                     bet.setHorse((Horse)wrapper.get("horse"));

                                                                     Context context = getContext();
                                                                     Intent intent = new Intent(context, BetDetailActivity.class);
                                                                     intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, bet.getBetID());
                                                                     Bundle b = new Bundle();
                                                                     Log.d("RACEFRAG", customer.getBets().size() + "");
                                                                     customer.getBets().add(bet);
                                                                     customer.setCredit(customer.getCredit() - stake);
                                                                     ((MainActivity)getActivity()).setCustomer(customer);

                                                                     Log.d("RACEFRAG", customer.getBets().size() + "");
                                                                     b.putSerializable("customer", customer);
                                                                     intent.putExtras(b);

                                                                     meetingItems.setSelection(0);
                                                                     horseItems.setSelection(0);
                                                                     meetingItems.setSelection(0);
                                                                     raceItems.setSelection(0);
                                                                     checkBox.setSelected(false);
                                                                     stakeEt.setText("");
                                                                     stake = 0;
                                                                     raceItems .setVisibility(View.INVISIBLE);
                                                                     horseItems.setVisibility(View.INVISIBLE);
                                                                     betSection.setVisibility(View.INVISIBLE);
                                                                     button.setVisibility(View.INVISIBLE);
                                                                     progressDialog.dismiss();

                                                                     startActivityForResult(intent, 1);
                                                                 }
                                                                 else
                                                                     System.out.println(false);
                                                             }
                                                         }, 3000
                    );
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("RACEFRAG", "onActivityResult()");
        super.onActivityResult(requestCode, resultCode, data);
        ((MainActivity)getActivity()).updateFragments();
    }

    private void resetRaceArray() {
        racesArray.clear();
        racesArray.add("Select race time...");
    }

    private void resetHorseArray() {
        horsesArray.clear();
        horsesArray.add("Select horse...");
    }

    private void getRaceMeetings() {
        for (Race r: races) {
            boolean exists = false;
            for (String s: meetingArray) {
                if (r.getTrack().equals(s))
                    exists = true;
            }
            if (!exists)
                meetingArray.add(r.getTrack());
        }
    }

    private void getRacesByMeeting(String meeting) {
        resetRaceArray();

        for (Race r: races) {
            if (r.getTrack().equals(meeting)) {
                racesArray.add(r.getTime());
            }
        }
    }

    private Map<String, Object> getHorseInRace(String selectionID) {
        HashMap<String, Object> wrapper = new HashMap<>();
        for (Race r: races) {
            for (Horse h: r.getAllHorses()) {
                if (h.getSelectionID() == Integer.parseInt(selectionID)) {
                    wrapper.put("horse", h);
                    wrapper.put("race", r);
                }
            }
        }
        return wrapper;
    }

    private void getHorsesByRace(String time) {
        resetHorseArray();

        Race race = null;

        for (Race r: races) {
            if (r.getTime().equals(time)) {
                race = r;
                break;
            }
        }
        for (Horse h: race.getAllHorses()) {
            horsesArray.add(String.format("%s - %s", h.getNumber(), h.getName()));
        }
    }
}
