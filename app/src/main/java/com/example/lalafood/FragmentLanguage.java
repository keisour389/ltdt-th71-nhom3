package com.example.lalafood;


import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.lalafood.Customers.Activity.CustomerMainSearch;
import com.example.lalafood.Helper.LocaleHelper;
import com.example.lalafood.Login.Activity.MainLogin;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLanguage extends DialogFragment {
    private Button langEng;
    private Button langViet;

    public static FragmentLanguage newInstance(){
        return new FragmentLanguage();
    }

    public void Restart(Context ctx)
    {
        //geting activty from context
        Activity a = (Activity)ctx;
        //forcing activity to recreate
        a.recreate();
    }

    public void RestartIntent(Intent intent)
    {
        getActivity().finish();
        startActivity(intent);
    }

    private void closeFragment(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         //Inflate the layout for this fragment
        View languageFrag =  inflater.inflate(R.layout.fragment_language, container, false);
        langEng = languageFrag.findViewById(R.id.lang_eng);
        langViet = languageFrag.findViewById(R.id.lang_viet);
        LocaleHelper.onAttach(getActivity());
        langEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleHelper.setLocale(getActivity(), "en" );
//                Restart(getActivity());
                Intent intent = new Intent(getActivity(), MainLogin.class);//Mở lại trang MainLogin
                RestartIntent(intent);
                closeFragment();
            }
        });

        langViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocaleHelper.setLocale(getActivity(), "vi" );
//                Restart(getActivity());
                Intent intent = new Intent(getActivity(), MainLogin.class);//Mở lại trang MainLogin
                RestartIntent(intent);
                closeFragment();
            }
        });
        return languageFrag;
    }
}
