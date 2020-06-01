package com.example.shippers.Login.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.shippers.R;
import com.example.shippers.SQLite.DatabaseContext;
import com.example.shippers.ShippersMainActivity;

public class MainLogin extends AppCompatActivity {
    public static final String LOGIN_TYPE_ID = "LOGIN_TYPE_ID";
    private Context context = this;
    //Các biến dùng chung
    ActionBar actionBar;
    private Button customersLogin;
    private Button shippersLogin;
    //intent
    private String account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        //Get account
        account = getAccountFromDatabase();
        //Ánh xạ
        actionBar = getSupportActionBar();
        //set title
        actionBar.setTitle("Quay lại");
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
                //Chưa xử lí cho khách hàng
                Intent intent;
                intent = new Intent(context, SignInAndSignUp.class);
                intent.putExtra(LOGIN_TYPE_ID, 1);
                startActivity(intent);
            }
        });
        shippersLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(account != "") //Nếu có tài khoản dưới database
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
    //Get account from local database
    private String getAccountFromDatabase()
    {
        String result = "";
        //Database
        DatabaseContext databaseContext = new DatabaseContext(this, "Local", null, 1);
        //Tạo bảng
        databaseContext.QueryData("CREATE TABLE IF NOT EXISTS AccountTable(Account NVARCHAR(50), Password NVARCHAR (50))");
        Cursor getData = databaseContext.GetData
                ("SELECT *\n" +
                        "FROM AccountTable");
        Log.d("GetCount", String.valueOf(getData.isFirst()));
        while(getData.moveToNext()) //The first row
        {
            result = getData.getString(0);//Account nằm cột 1
            Log.d("AccountDatabase", result);
        }
        return result;
    }
}
