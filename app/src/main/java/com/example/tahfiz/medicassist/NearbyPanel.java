package com.example.tahfiz.medicassist;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tahfiz.medicassist.NearbyPlaces.NearbyPlacesActivity;
import com.example.tahfiz.medicassist.NearbyPlaces.NearbyRepo;
import com.example.tahfiz.medicassist.NearbyPlaces.PlaceData;

/**
 * Created by tahfiz on 5/4/2016.
 */
public class NearbyPanel extends Fragment {

    private Button nearbyActivty;
    private NearbyRepo nearbyRepo;
    private TextView nearbyTxtView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nearby_panel,container,false);

        nearbyActivty = (Button) view.findViewById(R.id.nextActivity);

        nearbyTxtView = (TextView) view.findViewById(R.id.nearbyTxtView);

        nearbyRepo = new NearbyRepo(getActivity());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (nearbyRepo.getNearestPlace() != null){
            PlaceData placeData = nearbyRepo.getNearestPlace();
            nearbyTxtView.setText(placeData.getDistance());
        }

        nearbyActivty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),NearbyPlacesActivity.class));
            }
        });
    }
}
