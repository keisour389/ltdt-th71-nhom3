package com.example.shippers.Login.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.shippers.API.OrdersFoodAPI;
import com.example.shippers.API.Req.CreateUserData;
import com.example.shippers.API.Req.Users;
import com.example.shippers.Common.Account;
import com.example.shippers.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormCreateAccount extends AppCompatActivity {
    //Intent
    private String account = "";
    private String password = "";
    private int accountTypeId;
    private String accountType = "";
    private Context context = this;
    //Các biến dùng chung
    private ActionBar actionBar;
    private TextInputEditText txtEHo;
    private TextInputEditText txtETen;
    private TextInputEditText txtEDiaChi;
    private Button btnNgaySinh;
    private Button btnDangKy;
    private RadioButton radioButtonNam;
    private RadioButton radioButtonNu;
    private EditText edTEmail;
    //Các biến kiểm tra
    private boolean kiemTraChonNgay = false;
    private Users users;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_create_account);

        //Ánh xạ
        txtEHo = (TextInputEditText) findViewById(R.id.txtEHo);
        txtETen = (TextInputEditText) findViewById(R.id.txtETen);
        btnNgaySinh = (Button) findViewById(R.id.button);
        txtEDiaChi = (TextInputEditText) findViewById(R.id.txtEDiaChi);
        edTEmail = (EditText) findViewById(R.id.edTEmail);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        radioButtonNam = (RadioButton) findViewById(R.id.radioButtonNam);
        radioButtonNu = (RadioButton) findViewById(R.id.radioButtonNu);
        actionBar = getSupportActionBar();


        //Lấy tài khoản và mật khẩu
        Intent intent = getIntent();
        account = intent.getStringExtra(CreatePassword.ACCOUNT);
        password = intent.getStringExtra(CreatePassword.PASSWORD);
        accountTypeId = intent.getIntExtra(CreatePassword.LOGIN_TYPE_ID, 0);
        accountType = new Account().getAccountTypeById(accountTypeId);
        //

        //Click đăng kí
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users = new Users();
                if (kiemTraRong() == true && kiemTraCheckBox() == true  && kiemTraChonNgay == true) {
                    //Nhận value
                    setValuesToUsers(); //set các value to biến user
                    createUser(users);
                    //Khi click xong sẽ chuyển activity theo response
                }

            }
        });

        Log.d("Create", account);
        Log.d("Create Pass", password);
        Log.d("Create Type", String.valueOf(accountTypeId));

        // Bắt sự kiện khi click vào ô Ngày sinh
        btnNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });

        //Báo lỗi cho ô Họ người dùng
        txtEHo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtEHo.getText().toString().isEmpty()) {
                    txtEHo.setError("VUI LÒNG KHÔNG ĐỂ TRỐNG !");
                }
            }
        });
        //Báo lỗi cho ô Tên người dùng
        txtETen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtETen.getText().toString().isEmpty()) {
                    txtETen.setError("VUI LÒNG KHÔNG ĐỂ TRỐNG !");
                }
            }
        });

        //Báo lỗi cho ô Địa chỉ người dùng
        txtEDiaChi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtEDiaChi.getText().toString().isEmpty()) {
                    txtEDiaChi.setError("VUI LÒNG KHÔNG ĐỂ TRỐNG !");
                }
            }
        });

        //Báo lỗi cho ô Email người dùng
        edTEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (edTEmail.getText().toString().isEmpty()) {
                    edTEmail.setError("VUI LÒNG KHÔNG ĐỂ TRỐNG !");
                }
            }
        });
        //Nút quay lại
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Set title
        actionBar.setTitle("Tạo tài khoản");

    }

    private boolean KiemTraRong(){
        if(!txtEHo.getText().toString().isEmpty() && !txtETen.getText().toString().isEmpty()
            && txtEDiaChi.getText().toString().isEmpty() && edTEmail.getText().toString().isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    private void ChonNgay(){
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        // Gọi vào lịch có sẵn
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Chọn ngày tháng cần chọn, nếu không có sẽ được mặc định theo ngày tháng hiện tại
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                btnNgaySinh.setText(simpleDateFormat.format(calendar.getTime()));


                kiemTraChonNgay = true;
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private boolean kiemTraRong() {
        if (!txtEHo.getText().toString().isEmpty() && !txtETen.getText().toString().isEmpty()
                && !txtEDiaChi.getText().toString().isEmpty() && !edTEmail.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    private boolean kiemTraCheckBox(){
        if (radioButtonNam.isChecked() || radioButtonNu.isChecked()){
            return true;
        } else {
            return false;
        }
    }
    //Create Users
    public void createUser(Users users)
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
        //Khởi tạo retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://android-food-api.herokuapp.com/") //Lấy URL của HOST
                .addConverterFactory(GsonConverterFactory.create(gson)) //Sử dụng file JSON
                .build();
        OrdersFoodAPI ordersFoodAPI = retrofit.create(OrdersFoodAPI.class); //Khởi tạo các controller
        Call<CreateUserData> call = ordersFoodAPI.createUser(users);
        call.enqueue(new Callback<CreateUserData>() {
            @Override
            public void onResponse(Call<CreateUserData> call, Response<CreateUserData> response) {
                //Không tìm thấy
                if (!response.isSuccessful()) {
                    Log.d("Response", "Failed");
                    return;
                }
                //Tìm thấy
                Log.d("Response", "Success");
                Toast.makeText(context, "Chúc mừng bạn tạo tài khoản thành công.", Toast.LENGTH_LONG).show();
                Intent TV = new Intent(context, MainLogin.class);
                startActivity(TV);
            }
            //Các trường hợp failed chưa giải quyết:
            //-Mail trùng
            @Override
            public void onFailure(Call<CreateUserData> call, Throwable t) {
                Toast.makeText(context, "Đã xảy ra lội bất thường.", Toast.LENGTH_LONG).show();
                Log.d("Response", "Error");
            }
        });
    }
    //Hàm dùng để chuyển các text vào class Users
    private void setValuesToUsers()
    {
        //Format today
        Date date = new Date(); //Ngày hôm này
        String today = new SimpleDateFormat("yyyy-MM-dd").format(date);
        //Format birthday
        String birthDay = btnNgaySinh.getText().toString();
        String[] birthDaySplit = birthDay.split("/");
        String birthDayString = birthDaySplit[2] + "-" + birthDaySplit[1] + "-" + birthDaySplit[0];
        users.setUserName(account); //tài khoản
        users.setPassword(password); //Mật khẩu
        //Các thông tin còn lại
        users.setLastName(txtEHo.getText().toString());
        users.setFirstName(txtETen.getText().toString());
        users.setBirthDate(birthDayString);
        Log.d("birthDayString", birthDayString);
        if(radioButtonNam.isChecked())
        {
            users.setGender(radioButtonNam.getText().toString());
        }
        else
        {
            users.setGender(radioButtonNu.getText().toString());
        }
        Log.d("users.getGender", users.getGender());
        users.setAddress(txtEDiaChi.getText().toString());
        users.setEmail(edTEmail.getText().toString());
        //Các thông tin hệ thống tạo
        users.setTypeUserId(accountType);
        users.setStatus("Bình thường"); //Tạm thời chưa để hơn 10 kí tự
        users.setCreatedDate(today);
    }
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
