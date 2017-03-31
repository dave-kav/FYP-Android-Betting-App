package cit.fyp.dk.betting_app.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cit.fyp.dk.betting_app.R;
import cit.fyp.dk.betting_app.activities.MainActivity;
import cit.fyp.dk.betting_app.domain.Customer;
import cit.fyp.dk.betting_app.domain.Horse;
import cit.fyp.dk.betting_app.domain.Race;

/**
 * Created by davyk on 28/03/2017.
 */

public class RaceFragment extends Fragment{

    private Customer customer;
    private ArrayList<Race> races;
    private List<String> meetingArray;
    private List<String> racesArray;
    private List<String> horsesArray;

    private Spinner meetingItems;
    private Spinner raceItems;
    private Spinner horseItems;
    private RelativeLayout racesSection;
    private RelativeLayout horsesSection;

    public RaceFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customer = ((MainActivity) this.getActivity()).getCustomer();
        races = ((MainActivity) this.getActivity()).getRaces();

        meetingArray =  new ArrayList<>();
        meetingArray.add("Select race meeting...");

        racesArray =  new ArrayList<>();
        racesArray.add("Select race time...");

        horsesArray =  new ArrayList<>();
        horsesArray.add("Select horse...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race, container, false);

        // hide sections to be shown upon selection from previous spinner
        racesSection = (RelativeLayout)view.findViewById(R.id.races_section);
        racesSection.setVisibility(View.INVISIBLE);

        horsesSection = (RelativeLayout)view.findViewById(R.id.horses_section);
        horsesSection .setVisibility(View.INVISIBLE);

        // load all race meetings into spinner
        getRaceMeetings();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, meetingArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meetingItems = (Spinner) view.findViewById(R.id.meeting_spinner);
        meetingItems.setAdapter(adapter);

        meetingItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (meetingItems.getSelectedItemPosition() == 0) {
                    Snackbar.make(view, "Select a race meeting from the dropdown", Snackbar.LENGTH_INDEFINITE).show();



                    // hide views
                    racesSection.setVisibility(View.INVISIBLE);
                    horsesSection.setVisibility(View.INVISIBLE);
                } else {
                    // load races for selected meeting into spinner and display the spinner
                    getRacesByMeeting(meetingItems.getSelectedItem().toString());

                    racesSection.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, racesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        raceItems = (Spinner) view.findViewById(R.id.races_spinner);
        raceItems.setAdapter(adapter);

        raceItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (raceItems.getSelectedItemPosition() == 0) {
                    Snackbar.make(view, "Select a race from the dropdown", Snackbar.LENGTH_INDEFINITE).show();
                    horsesSection.setVisibility(View.INVISIBLE);
                } else {
                    // load races for selected meeting into spinner and display the spinner
                    getHorsesByRace(raceItems.getSelectedItem().toString());

                    horsesSection.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, horsesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        horseItems = (Spinner) view.findViewById(R.id.horses_spinner);
        horseItems.setAdapter(adapter);

        return view;
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
        for (Race r: races) {
            if (r.getTrack().equals(meeting)) {
                racesArray.add(r.getTime());
            }
        }
    }

    private void getHorsesByRace(String time) {
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
