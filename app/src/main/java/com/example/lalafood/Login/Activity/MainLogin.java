package com.example.lalafood.Login.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lalafood.Customers.Activity.CustomerMainSearch;
import com.example.lalafood.Helper.LocaleHelper;
import com.example.lalafood.R;
import com.example.lalafood.SQLite.DatabaseContext;
import com.example.lalafood.Shippers.Activity.ShippersMainActivity;

public class MainLogin extends AppCompatActivity {
    public static final String LOGIN_TYPE_ID = "LOGIN_TYPE_ID";
    private Context context = this;
    //Các biến dùng chung
    ActionBar actionBar;
    private Button customersLogin;
    private Button shippersLogin;
    //database
    private String shipperAccount;
    private String customerAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        //Get account
        shipperAccount = getShipperAccountFromDatabase();
        //Ánh xạ
        actionBar = getSupportActionBar();
        //Ẩn action bar
        actionBar.hide();
        //Kiểm tra sign in hay sign up
        chooseLoginType();
    }
    public void chooseLoginType() {
        //Ánh xạ
        actionBar = getSupportActionBar();
        customersLogin = (Button) findViewById(R.id.customersLogin);
        shippersLogin = (Button) findViewById(R.id.shippersLogin);
        //Choose
        //1: Customers, 2: Shippers
        customersLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xử lí cho khách hàng
                Intent intent;
                intent = new Intent(context, CustomerMainSearch.class);
                intent.putExtra(LOGIN_TYPE_ID, 1);
                startActivity(intent);
            }
        });
        shippersLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(shipperAccount != "") //Nếu có tài khoản dưới database
                {
                    intent = new Intent(context, ShippersMainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    intent = new Intent(context, SignInAndSignUp.class);
                    intent.putExtra(LOGIN_TYPE_ID, 2);
                    startActivity(intent);
                }
            }
        });
    }
    //Get shipper account from local database
    private String getShipperAccountFromDatabase()
    {
        String result = "";
        //Database
        DatabaseContext databaseContext = new DatabaseContext(this, "Local", null, 1);
        //Tạo bảng
        databaseContext.QueryData("CREATE TABLE IF NOT EXISTS ShipperAccount(Account NVARCHAR(50), Password NVARCHAR (50))");
        Cursor getData = databaseContext.GetData
                ("SELECT *\n" +
                        "FROM ShipperAccount");
        Log.d("GetCount", String.valueOf(getData.isFirst()));
        while(getData.moveToNext()) //The first row
        {
            result = getData.getString(0);//Account nằm cột 1
            Log.d("ShipperAccount", result);
        }
        return result;
    }
    //Đổi ngôn ngữ
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
