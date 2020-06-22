package com.example.lalafood.Login.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lalafood.Helper.LocaleHelper;
import com.example.lalafood.R;
import com.google.android.material.textfield.TextInputEditText;

import static com.example.lalafood.R.string.no_empty_password;


public class CreatePassword extends AppCompatActivity {
    //
    public final static String ACCOUNT = "ACCOUNT";
    public final static String PASSWORD = "PASSWORD";
    public static final String LOGIN_TYPE_ID = "LOGIN_TYPE_ID";
    //Các biến dùng chung
    private Context context = this;
    private Button btnTiepTheoDangKyForm;
    private TextInputEditText txtEMatKhauDangKy, txtENhapLaiMatKhauDangKy;
    private ActionBar actionBar;
    //Intent
    private String account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        //Ánh xạ
        actionBar = getSupportActionBar();
        btnTiepTheoDangKyForm = (Button) findViewById(R.id.btnTiepTheoDangKyForm);
        txtEMatKhauDangKy = (TextInputEditText) findViewById(R.id.txtEMatKhauDangKy);
        txtENhapLaiMatKhauDangKy = (TextInputEditText) findViewById(R.id.txtENhapLaiMatKhauDangKy);

        //
        btnTiepTheoDangKyForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent putValue = getIntent();
                //Test
                Intent test = getIntent();
                Log.d("Login phone", String.valueOf(putValue.getIntExtra(CreatePhoneNumber.LOGIN_TYPE_ID, 0)));
                if (kiemTraRong() == true && kiemTraGiongNhau() == true) {
                    Intent TV = new Intent(context, FormCreateAccount.class);
                    //Account Type ID
                    TV.putExtra(LOGIN_TYPE_ID, putValue.getIntExtra(CreatePhoneNumber.LOGIN_TYPE_ID, 0));
                    //Account
                    TV.putExtra(ACCOUNT, putValue.getStringExtra(CreatePhoneNumber.ACCOUNT));
                    //Password
                    TV.putExtra(PASSWORD, txtEMatKhauDangKy.getText().toString()); //Gửi mật khẩu đi
                    startActivity(TV);
                }

            }
        });
        //Báo lỗi cho ô Mật khẩu
        txtEMatKhauDangKy.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtEMatKhauDangKy.getText().toString().isEmpty()) {
                    txtEMatKhauDangKy.setError(getText(R.string.no_empty_password));
                }
            }
        });

        //Báo lỗi cho ô Nhập lại mật khẩu
        txtENhapLaiMatKhauDangKy.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtENhapLaiMatKhauDangKy.getText().toString().isEmpty()) {
                    txtENhapLaiMatKhauDangKy.setError(getText(R.string.no_empty_password));
                }
            }
        });
        //Nút quay lại
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Set title
        actionBar.setTitle(getText(R.string.create_account));
    }

    // Kiểm tra 2 ô dữ liệu có rỗng hay 0.
    private boolean kiemTraRong() {
        if (!txtEMatKhauDangKy.getText().toString().isEmpty() && !txtENhapLaiMatKhauDangKy.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean kiemTraGiongNhau() {
        if (txtEMatKhauDangKy.getText().toString().equals(txtENhapLaiMatKhauDangKy.getText().toString())) {
            return true;
        } else {
            return false;
        }
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
    //Đổi ngôn ngữ
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
