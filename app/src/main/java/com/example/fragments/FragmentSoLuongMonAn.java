package com.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.FoodOrder.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSoLuongMonAn extends DialogFragment {

    public FragmentSoLuongMonAn() {
        // Required empty public constructor
    }

    public static FragmentSoLuongMonAn newInstance(){
        return new FragmentSoLuongMonAn();
    }

    private void closeFragment(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View foodCountFrag = inflater.inflate(R.layout.fragment_food_count, container, false);
        Button buttonConfirmCount = foodCountFrag.findViewById(R.id.button_confirm_count);
        buttonConfirmCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });
        return foodCountFrag;
    }
}
