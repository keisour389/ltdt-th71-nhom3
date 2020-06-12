package com.example.lalafood.Shippers.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.lalafood.API.OrdersFoodAPI;
import com.example.lalafood.API.Req.Users;
import com.example.lalafood.API.Req.UsersData;
import com.example.lalafood.FragmentLanguage;
import com.example.lalafood.R;
import com.example.lalafood.SQLite.DatabaseContext;
import com.example.lalafood.Login.Activity.MainLogin;
import com.example.lalafood.Shippers.Activity.ShippersMainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentOption extends Fragment {
    //Database
    DatabaseContext databaseContext;
    View view;
    //Các đối tượng dùng chung
    TextView userName;
    TextView name;
    TextView createdDate;
    Button changeLanguage;
    Button logout;
    //Intent
    private String account;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_option, container, false);
        //Get account
        databaseContext = new DatabaseContext(getActivity(), "Local", null, 1);
        account = getAccountFromDatabase();
        //Ánh xạ
        userName = (TextView) view.findViewById(R.id.account);
        name = (TextView) view.findViewById(R.id.name);
        createdDate = (TextView) view.findViewById(R.id.createdDate);
        changeLanguage = (Button) view.findViewById(R.id.changeLanguagesLable);
        logout = (Button) view.findViewById(R.id.logout);
        //Khởi tạo thông tin cá nhân
        getShipperByAccount(account);
        //Đổi ngôn ngữ
        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ShippersMainActivity)getActivity()).changeLanguage();
            }
        });
        //Đăng xuất
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               logoutAlert(); //Thông báo
            }
        });
        return view;
    }
    public void getShipperByAccount(final String account)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        //Khởi tạo retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://android-food-api.herokuapp.com/") //Lấy URL của HOST
                .addConverterFactory(GsonConverterFactory.create(gson)) //Sử dụng file JSON
                .build();
        OrdersFoodAPI ordersFoodAPI = retrofit.create(OrdersFoodAPI.class); //Khởi tạo các controller

        //Shipper
        Call<UsersData> call = ordersFoodAPI.getUserByAccount(account); //id này là request
        call.enqueue(new Callback<UsersData>() {
            @Override
            public void onResponse(Call<UsersData> call, Response<UsersData> response) {
                //Không tìm thấy
                if (!response.isSuccessful()) {
                    Log.d("Response", "Failed");
                    return;
                }
                //Tìm thấy
                Log.d("Response", "Success");
                Users[] orderShipperByAccount = response.body().getUsersList(); //Tìm thấy đơn hàng theo Id
                //Gán
                //Luôn luôn lấy index 0 vì chỉ có 1 id tìm được duy nhất
                try
                {
                    userName.setText(orderShipperByAccount[0].getUserName());
                    name.setText(orderShipperByAccount[0].getLastName() + " " + orderShipperByAccount[0].getFirstName());
                    createdDate.setText(orderShipperByAccount[0].getCreatedDate());
                }
                catch (Exception ex)
                {
                    userName.setText("NULL");
                    name.setText("NULL");
                    createdDate.setText("NULL");
                }
            }
            @Override
            public void onFailure(Call<UsersData> call, Throwable t) {
                Log.d("Response", "Error");
            }
        });
    }

    //Tạo thông báo khi đăng xuất
    public void logoutAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.sign_out))
                .setMessage(R.string.signout_confirm_question)
                .setPositiveButton(getString(R.string.confirm_no_caps), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), MainLogin.class); //Chuyển về trang đăng xuất
                        startActivity(intent);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
        });
        builder.show();
        //Drop table khi logout, để nhận giá trị mới
        databaseContext.QueryData("DROP TABLE ShipperAccount;");
    }
    //Get account from local database
    private String getAccountFromDatabase()
    {
        //Database
        String result = "";
        Cursor getData = databaseContext.GetData
                ("SELECT *\n" +
                        "FROM ShipperAccount");
        Log.d("GetCount", String.valueOf(getData.isFirst()));
        while(getData.moveToNext()) //The first row
        {
            result = getData.getString(0);//Account nằm cột 1
            Log.d("AccountDatabase", result);
        }
        return result;
    }


}