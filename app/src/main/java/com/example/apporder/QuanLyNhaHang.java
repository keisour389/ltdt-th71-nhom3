package com.example.apporder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuanLyNhaHang extends AppCompatActivity {
    Database database;
    ListView ListViewThucDon;
    ArrayList<ThucDon> arrayThucDon;
    ThucDonAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_nha_hang);

        ListViewThucDon = (ListView) findViewById(R.id.ListViewThucDon);
        arrayThucDon = new ArrayList<>();
        adapter = new ThucDonAdapter(this, R.layout.dong_thuc_don, arrayThucDon);


        ListViewThucDon.setAdapter(adapter);
        // Tạo database ghi chú
        database = new Database(this, "ThucDon.sqlite", null, 1);

        // Tạo bảng ThucDon
        database.QueryData("CREATE TABLE IF NOT EXISTS ThucDon(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenMon VARCHAR(200))");


        // Insert data
        //database.QueryData("INSERT INTO ThucDon VALUES(null, 'Món 2')");
        GetDataThucDon();
    }
    private void GetDataThucDon(){
        //Hiển thị
        Cursor dataThucDon = database.GetData("SELECT * FROM ThucDon");
        //Xóa mảng trước khi thêm để cập nhật lại
        arrayThucDon.clear();
        //duyệt dữ liệu
        while (dataThucDon.moveToNext()) {
            String Ten = dataThucDon.getString(1);
            String MoTa = dataThucDon.getString(1);
            //Toast.makeText(this, Ten, Toast.LENGTH_SHORT).show();

            int id = dataThucDon.getInt(0);
            arrayThucDon.add(new ThucDon(id, Ten, MoTa));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_mon, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        if(item.getItemId() == R.id.menuThem){
            DialogThem();
        }

        return super.onOptionsItemSelected(item);
    }
    public void DialogXoa(final String ten, final int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa món ăn " + ten + " không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                database.QueryData("DELETE FROM ThucDon WHERE Id = '"+ id +"' ");
                Toast.makeText(QuanLyNhaHang.this, "Đã xóa xong!", Toast.LENGTH_SHORT).show();
                GetDataThucDon();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();

    }
    public void DialogSua(String ten, final int id){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua_mon);

        final EditText editTextSuaTenMon = (EditText) dialog.findViewById(R.id.editTextSuaTenMon);
        Button ButtonSua = (Button) dialog.findViewById(R.id.ButtonSua);
        Button ButtonHuySua = (Button) dialog.findViewById(R.id.ButtonHuySua);

        // sửa lại editTextSuaTenMon
        editTextSuaTenMon.setText(ten);
        //Khi nhấn vào nút Sửa sẽ cập nhật tên món ăn mới
        ButtonSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String tenMoi = editTextSuaTenMon.getText().toString().trim();//trim dùng để bỏ khoảng trắng hai đầu
                database.QueryData("UPDATE ThucDon SET TenMon = '"+ tenMoi +"'  WHERE Id = '"+ id +"'  ");
                Toast.makeText(QuanLyNhaHang.this, "Đã sửa xong!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataThucDon();
            }
        });

        //Khi nhấn vào nút hủy sẽ đóng bảng dialog
        ButtonHuySua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void DialogThem(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_mon);

        final EditText editTextThemTenMon = (EditText) dialog.findViewById(R.id.editTextThemTenMon);
        //final EditText editTextMoTa = (EditText) dialog.findViewById(R.id.editTextMoTa);
        Button ButtonThem = (Button) dialog.findViewById(R.id.ButtonThem);
        Button ButtonHuyThem = (Button) dialog.findViewById(R.id.ButtonHuyThem);
        //Khi nhấn vào nút thêm thì sẽ thêm dữ liệu mới vào
        ButtonThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenma = editTextThemTenMon.getText().toString();
                //String mota = editTextMoTa.getText().toString();
                if (tenma.equals("")/* || mota.equals("")*/){
                    Toast.makeText(QuanLyNhaHang.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    database.QueryData("INSERT INTO ThucDon VALUES(null, '"+ tenma +"')");
                    //database.QueryData("INSERT INTO ThucDon VALUES(null, '"+ mota +"' ");
                    Toast.makeText(QuanLyNhaHang.this, "Đã thêm xong!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataThucDon();
                }
            }
        });

        //Khi nhấn vào nút hủy sẽ đóng bảng dialog
        ButtonHuyThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}
