package com.example.tahfiz.medicassist;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by tahfiz on 20/4/2016.
 */
public class UserProfileFragment extends Fragment {

    private Spinner selectedAge;
    private TextInputLayout inputLayoutName;
    private EditText inputName;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile_fragment,container,false);

        selectedAge = (Spinner) view.findViewById(R.id.select_age);

        inputLayoutName = (TextInputLayout) view.findViewById(R.id.input_layout_name);

        inputName = (EditText) view.findViewById(R.id.input_name);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        selectedAge.setOnItemSelectedListener(new ItemSelectedListener());
    }

    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String item = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
