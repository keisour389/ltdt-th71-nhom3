package com.example.shippers.login.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shippers.API.OrdersFoodAPI;
import com.example.shippers.API.Req.Users;
import com.example.shippers.API.Req.UsersData;
import com.example.shippers.R;
import com.example.shippers.SQLite.DatabaseContext;
import com.example.shippers.ShippersMainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPassword extends AppCompatActivity {
    //Database
    DatabaseContext databaseContext = new DatabaseContext(this, "Local", null, 1);
    //
    public final static String ACCOUNT = "ACCOUNT";
    //Context
    private Context context = this;
    //Các biến dùng chung
    private ActionBar actionBar;
    private Button btnDangNhap;
    private TextInputEditText txtEMatKhau;
    //Các biến gửi bằng intent
    private String account = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);
        //Ánh xạ
        actionBar = getSupportActionBar();
        txtEMatKhau = (TextInputEditText) findViewById(R.id.txtEMatKhauKH);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);

        //Báo lỗi cho ô Mật khẩu khách hàng
        txtEMatKhau.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtEMatKhau.getText().toString().isEmpty()) {
                    txtEMatKhau.setError("VUI LÒNG KHÔNG ĐỂ TRỐNG !");
                }
            }
        });
        //Nút quay lại
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Set title
        actionBar.setTitle("Đăng nhập");
        //Nhận giá trị từ intent
        Intent intent = getIntent();
        account = intent.getStringExtra(LoginPhoneNumber.ACCOUNT);
        //Đăng nhập thành công
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassword(account, txtEMatKhau.getText().toString());
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
        if (!txtEMatKhau.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    //passwordToggleEnable là hiển thị password cho người dùng nhìn thấy hoặc không nhìn thấy
    //hintEnable trong TextInputLayout là để không bị mất đi chữ đang hiện thị mà đưa nó lên trên
    //Kiểm số điện thoại
    public void checkPassword(String account, final String password)
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

                if(userAccount.length > 0) //Kiểm tra rỗng
                {
                    //Tạm thời get theo user name, chờ access token
                    if(!userAccount[0].getPassword().equals(password)) //Không sử dụng toán tử
                     {
                        Toast.makeText(context, "Sai mật khẩu. Vui lòng nhập lại", Toast.LENGTH_LONG).show();
                     }
                    else
                    {
                        //Nếu đúng thì đến bước tiếp
                        Intent TV = new Intent(context, ShippersMainActivity.class);
                        handleDatabase(databaseContext, userAccount[0].getUserName(), userAccount[0].getPassword());
//                        TV.putExtra(ACCOUNT, userAccount[0].getUserName());
                        startActivity(TV); //Đăng nhập thành công
                    }
                }
                else
                {
                    //
                }
            }
            @Override
            public void onFailure(Call<UsersData> call, Throwable t) {
                Log.d("Response", "Error");
            }
        });
    }
    //Handle Database
    private void handleDatabase(DatabaseContext databaseContext, String account, String password)
    {
        //Tạo bảng
        databaseContext.QueryData("CREATE TABLE IF NOT EXISTS AccountTable(Account NVARCHAR(50), Password NVARCHAR (50))");

        //Check
        Cursor getData = databaseContext.GetData
                ("SELECT *\n" +
                        "FROM AccountTable\n" +
                        "WHERE Account = \"" + account + "\""); //Đây là câu lệnh SQL chuẩn
        if(getData.getCount() == 0) //The first row
        {
            //Insert Data
            databaseContext.QueryData("INSERT INTO AccountTable VALUES('" + account + "', '" + password + "')");
        }
        Log.d("GetCount", String.valueOf(getData.getCount()));
        int i = 0;
        while(getData.moveToNext())
        {
            String a = getData.getString(0);
            Log.d("GetAccount", a);
            i++;
        }
        Log.d("Total", String.valueOf(i));
    }
}
