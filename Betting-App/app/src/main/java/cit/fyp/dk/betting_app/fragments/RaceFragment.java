package cit.fyp.dk.betting_app.fragments;

import android.os.Bundle;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cit.fyp.dk.betting_app.R;
import cit.fyp.dk.betting_app.activities.MainActivity;
import cit.fyp.dk.betting_app.domain.Customer;
import cit.fyp.dk.betting_app.domain.Race;

/**
 * Created by davyk on 28/03/2017.
 */

public class RaceFragment extends Fragment{

    private Customer customer;
    private ArrayList<Race> races;
    private Spinner meetingItems;
    private Spinner timeItems;
    private Spinner horseItems;

    public RaceFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customer = ((MainActivity) this.getActivity()).getCustomer();
        races = ((MainActivity) this.getActivity()).getRaces();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_race, container, false);

        // spinner for meetings
        List<String> meetingArray =  new ArrayList<>();
        meetingArray.add("Select race meeting...");

        for (Race r: races) {
            boolean exists = false;
            if (meetingArray.isEmpty())
                meetingArray.add(r.getTrack());
            else {
                for (String s: meetingArray) {
                    if (r.getTrack().equals(s))
                        exists = true;
                }
            }
            if (!exists)
                meetingArray.add(r.getTrack());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, meetingArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meetingItems = (Spinner) view.findViewById(R.id.meeting_spinner);
        meetingItems.setAdapter(adapter);

        meetingItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        List<String> timesArray =  new ArrayList<>();
        timesArray.add("Select race time...");
        timesArray.add("14:10");
        timesArray.add("14:40");
        timesArray.add("15:10");

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, timesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeItems = (Spinner) view.findViewById(R.id.races_spinner);
        timeItems.setAdapter(adapter);

        List<String> horsesArray =  new ArrayList<>();
        horsesArray.add("Select race time...");
        horsesArray.add("Shergar");
        horsesArray.add("Denman");
        horsesArray.add("Sprinter Sacre");

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, horsesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        horseItems = (Spinner) view.findViewById(R.id.horses_spinner);
        horseItems.setAdapter(adapter);




        return view;
    }
}
