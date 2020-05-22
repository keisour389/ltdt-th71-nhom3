package com.example.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.FoodOrder.R;
import com.example.helper.LocaleHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNgonNgu extends DialogFragment {
    private Button langEng;
    private Button langViet;

    public static FragmentNgonNgu newInstance(){
        return new FragmentNgonNgu();
    }

    public void Restart(Context ctx)
    {
        //geting activty from context
        Activity a = (Activity)ctx;
        //forcing activity to recreate
        a.recreate();
    }

    private void closeFragment(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         //Inflate the layout for this fragment
        View languageFrag =  inflater.inflate(R.layout.fragment_ngon_ngu, container, false);
        langEng = languageFrag.findViewById(R.id.lang_eng);
        langViet = languageFrag.findViewById(R.id.lang_viet);
//        LocaleHelper.onAttach(getActivity());
        langEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleHelper.setLocale(getActivity(), "en" );
                Restart(getActivity());
                closeFragment();
            }
        });

        langViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleHelper.setLocale(getActivity(), "vi" );
                Restart(getActivity());
                closeFragment();
            }
        });
        return languageFrag;
    }
}
