package com.example.apporder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class MatKhauKhachHang extends AppCompatActivity {
    private TextInputEditText txtEMatKhauKH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_khau_khach_hang);

        txtEMatKhauKH = (TextInputEditText) findViewById(R.id.txtEMatKhauKH);

        //Báo lỗi cho ô Mật khẩu khách hàng
        txtEMatKhauKH.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtEMatKhauKH.getText().toString().isEmpty()) {
                    txtEMatKhauKH.setError("VUI LÒNG KHÔNG ĐỂ TRỐNG !");
                }
            }
        });
    }
    private boolean kiemTraRong() {
        if (!txtEMatKhauKH.getText().toString().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    //passwordToggleEnable là hiển thị password cho người dùng nhìn thấy hoặc không nhìn thấy
    //hintEnable trong TextInputLayout là để không bị mất đi chữ đang hiện thị mà đưa nó lên trên
}
