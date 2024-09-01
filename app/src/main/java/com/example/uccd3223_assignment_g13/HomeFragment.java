package com.example.uccd3223_assignment_g13;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class HomeFragment extends Fragment {

    public HomeFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        Spinner date_sel = (Spinner) getView().findViewById(R.id.date_selected);
        String[] date_arr = {"a", "b", "c"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, date_arr);
        date_sel.setAdapter(adapter);
    }
}