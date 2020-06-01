package com.example.shippers.Login.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shippers.API.OrdersFoodAPI;
import com.example.shippers.API.Req.Users;
import com.example.shippers.API.Req.UsersData;
import com.example.shippers.Common.Account;
import com.example.shippers.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPhoneNumber extends AppCompatActivity {
    public static final String ACCOUNT = "ACCOUNT";
    //
    private Context context = this;
    private int accountTypeId;
    private String accountType;
    //Các biến dùng chung
    private ActionBar actionBar;
    private EditText editTextSdt;
    private Button btnTiepTheo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone_number);

        //Ánh xạ
        actionBar = getSupportActionBar();
        btnTiepTheo = (Button) findViewById(R.id.btnTiepTheo);
        editTextSdt = (EditText) findViewById(R.id.editTextSdt);
        //Nút quay lại
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Set title
        actionBar.setTitle("Đăng nhập");
        //Lấy AccountType
        Intent intent = getIntent();
        accountTypeId = intent.getIntExtra(SignInAndSignUp.LOGIN_TYPE_ID, 0); //Mặc định là 0
        accountType = new Account().getAccountTypeById(accountTypeId);

        //Login Phone Number Success Or Failed
        btnTiepTheo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kiemTraRong() == true) {
                    checkPhoneNumber(editTextSdt.getText().toString());
                }
            }
        });


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
    private boolean kiemTraRong() {
        if (!editTextSdt.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    //Kiểm số điện thoại
    public void checkPhoneNumber(final String account)
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
                Users[] userAccount = response.body().getUsersList(); //Tìm thấy đơn hàng theo Id
                //Gán
                //Luôn luôn lấy index 0 vì chỉ có 1 id tìm được duy nhất
                //Kiểm tra tài khoản và loại tài khoản
                if(userAccount.length > 0) //Kiểm tra rỗng
                {
                    Log.d("Account API", userAccount[0].getTypeUserId().toString());
                    Log.d("Account", accountType);
                    if(!userAccount[0].getUserName().equals(account) ||
                            !userAccount[0].getTypeUserId().equals(accountType)) //Không sử dụng toán tử
                    {
                        Toast.makeText(context, "Số điện thoại không tồn tại.", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //Nếu đúng thì đến bước tiếp
                        Intent TV = new Intent(context, LoginPassword.class);
                        TV.putExtra(ACCOUNT, account); //Gửi theo account đã tìm được
                        startActivity(TV); //Đăng nhập thành công
                    }
                }
                else
                {
                    Toast.makeText(context, "Số điện thoại này không tồn tại.", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<UsersData> call, Throwable t) {
                Log.d("Response", "Error");
            }
        });
    }
}
