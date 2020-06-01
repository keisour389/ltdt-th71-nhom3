package com.example.shippers.Login.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.shippers.R;

public class SignInAndSignUp extends AppCompatActivity {
    //Intent
    public static final String LOGIN_TYPE_ID = "LOGIN_TYPE_ID";
    public final static String IS_LOGIN = "IS_LOGIN";
    private Context context = this;
    //Các biến dùng chung
    Button signIn;
    Button signUp;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_and_sign_up);
        //Ánh xạ
        actionBar = getSupportActionBar();
        signIn = (Button) findViewById(R.id.signIn);
        signUp = (Button) findViewById(R.id.signUp);
//        //Hide action bar title
//        actionBar.hide();
//        //Hide action bar status
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set title null
        actionBar.setTitle("");
        final Intent putValue = getIntent();
        //Click
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginPhoneNumber.class);
                intent.putExtra(LOGIN_TYPE_ID, putValue.getIntExtra(MainLogin.LOGIN_TYPE_ID, 0));
                startActivity(intent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreatePhoneNumber.class);
                intent.putExtra(LOGIN_TYPE_ID, putValue.getIntExtra(MainLogin.LOGIN_TYPE_ID, 0));
                startActivity(intent);
            }
        });
        //Nút quay lại
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    //Set up nút quay lại
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        //Nút quay lại
        if(id == android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
