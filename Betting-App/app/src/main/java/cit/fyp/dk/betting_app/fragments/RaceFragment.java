package cit.fyp.dk.betting_app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cit.fyp.dk.betting_app.R;

/**
 * Created by davyk on 28/03/2017.
 */

public class RaceFragment extends Fragment{

    public RaceFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_race, container, false);
    }
}
