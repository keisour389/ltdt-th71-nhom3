package com.example.lalafood.Customers.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

//import com.example.fragments.FragmentNgonNgu;
//import com.example.helper.LocaleHelper;

import com.example.lalafood.API.OrdersFoodAPI;
import com.example.lalafood.API.Req.Users;
import com.example.lalafood.API.Req.UsersData;
import com.example.lalafood.Customers.Adapter.CategoryAdapter;
import com.example.lalafood.Customers.Adapter.CustomPagerAdapter;
import com.example.lalafood.FragmentLanguage;
import com.example.lalafood.Helper.LocaleHelper;
import com.example.lalafood.Login.Activity.CreatePhoneNumber;
import com.example.lalafood.Login.Activity.LoginPhoneNumber;
import com.example.lalafood.Login.Activity.MainLogin;
import com.example.lalafood.R;
import com.example.lalafood.SQLite.DatabaseContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CustomerMainSearch extends AppCompatActivity {
    //
    public static final String SEARCH = "SEARCH";
    public static final String LOGIN_TYPE_ID = "LOGIN_TYPE_ID";
    private GridView categoryGroup;//Khởi tạo gridview
    //Các biến dùng chung
    private Context context = this;
    private Cursor dataSqlite;
    private LinearLayout customerMain;
    private TextView customerAddress;
    private SearchView search_bar;
    private Button buttonOptions;
    //Các biến Option
    private View includeCustomerOptions;
    private LinearLayout customerLoginIn;
    private LinearLayout customerDontLoginIn;
    private Button buttonCustomerSignIn;
    private Button buttonCustomerSignUp;
    private TextView customerNameInOptions;
    private FrameLayout customerLogout;
    private FrameLayout customerChangeLanguage;
    private Button closeCustomerOptions;
    private LinearLayout customerChooseOptions;
    //Database
    private String customerAccount;
    // Tên category
    Integer[] imgId = {
            R.drawable.salad ,  R.drawable.milktea,
            R.drawable.chicken, R.drawable.koreanfood,
            R.drawable.foodbox, R.drawable.cake,
            R.drawable.noodle, R.drawable.streetfood,
    };
    //Hình category

    public enum  CustomPagerEnum{
        RED(R.string.red, R.layout.image_slide_1),
        BLUE(R.string.blue, R.layout.image_slide_2),
        ORANGE(R.string.orange, R.layout.image_slide_3);

        private int mTitleResId;
        private int mLayoutResId;

        CustomPagerEnum(int titleResId, int layoutResId) {
            mTitleResId = titleResId;
            mLayoutResId = layoutResId;
        }

        public int getTitleResId() {
            return mTitleResId;
        }

        public int getLayoutResId() {
            return mLayoutResId;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_search_main);
        //Ánh xạ
        customerMain = findViewById(R.id.customerMain);
        customerAddress = findViewById(R.id.customerAddress);
        search_bar = findViewById(R.id.search_bar);
        buttonOptions = findViewById(R.id.buttonOptions);
        //Ánh xạ Options
        includeCustomerOptions = findViewById(R.id.customerOptions);
        customerLoginIn = includeCustomerOptions.findViewById(R.id.customerLoginIn);
        customerDontLoginIn = includeCustomerOptions.findViewById(R.id.customerDontLoginIn);
        buttonCustomerSignIn = includeCustomerOptions.findViewById(R.id.buttonCustomerSignIn);
        buttonCustomerSignUp = includeCustomerOptions.findViewById(R.id.buttonCustomerSignUp);
        customerNameInOptions = includeCustomerOptions.findViewById(R.id.customerNameInOptions);
        customerLogout = includeCustomerOptions.findViewById(R.id.customerLogout);
        customerChangeLanguage = includeCustomerOptions.findViewById(R.id.customerChangeLanguage);
        closeCustomerOptions = includeCustomerOptions.findViewById(R.id.closeCustomerOptions);
        customerChooseOptions = includeCustomerOptions.findViewById(R.id.customerChooseOptions);
        //Ẩn action bar
        getSupportActionBar().hide();
        //Khởi tạo viewpager
        ViewPager viewpager = findViewById(R.id.viewpager_promotions);
        viewpager.setAdapter(new CustomPagerAdapter(this));
        //Các intent
        Context mContext = getApplicationContext();
        //Bind các category vào layout
        CategoryAdapter adapter = new CategoryAdapter(this, imgId);
        categoryGroup = findViewById(R.id.category_group);
        categoryGroup.setAdapter(adapter);
        categoryGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, CustomerSearchedRestaurant.class);
                switch (position)
                {
                    case 0: //Salad
                        intent.putExtra(SEARCH, "Salad");
                        Log.d("Query", "Salad");
                        break;
                    case 1: //Trà sữa
                        intent.putExtra(SEARCH, "Trà sữa");
                        Log.d("Query", "Trà sữa");
                        break;
                    case 2: //Gà rán
                        intent.putExtra(SEARCH, "Gà rán");
                        Log.d("Query", "Gà rán");
                        break;
                    case 3: //Đồ ăn Hàn
                        intent.putExtra(SEARCH, "Đồ ăn Hàn");
                        Log.d("Query", "Đồ ăn Hàn");
                        break;
                    case 4: //Cơm phần
                        intent.putExtra(SEARCH, "Cơm phần");
                        Log.d("Query", "Cơm phần");
                        break;
                    case 5: //Bánh ngọt
                        intent.putExtra(SEARCH, "Bánh ngọt");
                        Log.d("Query", "Bánh ngọt");
                        break;
                    case 6: //Bún phở
                        intent.putExtra(SEARCH, "Bún phở");
                        Log.d("Query", "Bún phở");
                        break;
                    case 7: //Ăn vặt
                        intent.putExtra(SEARCH, "Ăn vặt");
                        Log.d("Query", "Ăn vặt");
                        break;
                }
                startActivity(intent);
                if (position == 0){
//                    Intent rest = new Intent(TrangChuChuaTimKiem.this, TrangChuTimKiemNhaHang.class);
//                    startActivity(rest);
                }
            }
        });
//        getCustomerByAccount("0704617688");//Lấy thông tin từ API
        //Xét trạng thái đăng nhập
        dataSqlite = getCustomerFromDatabase();//Lấy data từ SQLite
        //customerAccount = getCustomerAccountFromDatabase();
        if(dataSqlite.getCount() > 0) //Đã đăng nhập trước đó
        {
            //Gán text vào address
            while (dataSqlite.moveToNext())
            {
                customerAddress.setText(dataSqlite.getString(3));//Địa chỉ cột 4
                customerNameInOptions.setText(dataSqlite.getString(2) + " " + dataSqlite.getString(1));
            }
            //Mở trạng thái đã đăng nhập
            customerLoginIn.setVisibility(View.VISIBLE);
            //Ẩn trạng thái chưa đăng nhập
            customerDontLoginIn.setVisibility(View.GONE);
            //Thanh chọn cài đặt sẽ được mở
            customerChooseOptions.setVisibility(View.VISIBLE);
            //Gán tên người sử dụng

        }
        else
        {
            //Thanh chọn cài đặt sẽ ẩn
            customerChooseOptions.setVisibility(View.GONE);
            //Ẩn trạng thái đã đăng nhập
            customerLoginIn.setVisibility(View.GONE);
            //Mở trạng thái chưa đăng nhập
            customerDontLoginIn.setVisibility(View.VISIBLE);
            //Chưa có địa chỉ
            customerAddress.setText(R.string.not_signin);//Địa chỉ cột 4
        }
        //Tìm kiếm
        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //Khi bấm tìm kiếm
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(context, CustomerSearchedRestaurant.class);
                intent.putExtra(SEARCH, query);
                Log.d("Query", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //Options
        buttonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hiện cài đặt
                includeCustomerOptions.setVisibility(View.VISIBLE);
                //Ẩn trang chủ
                customerMain.setVisibility(View.GONE);
            }
        });
        //Chuyển đổi ngôn ngữ
        customerChangeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage();
            }
        });
        //Đăng xuất
        customerLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logout
                logoutAlert();
            }
        });
        //Đóng cài đặt
        closeCustomerOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ẩn cài đặt
                includeCustomerOptions.setVisibility(View.GONE);
                //Hiện trang chủ
                customerMain.setVisibility(View.VISIBLE);
            }
        });
        //Xử lí khi khách hàng chưa đăng nhập
        final Intent putValue = getIntent();
        //Đăng nhập
        buttonCustomerSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginPhoneNumber.class);
                intent.putExtra(LOGIN_TYPE_ID, 1);
                startActivity(intent);
            }
        });

        //Đăng kí
        buttonCustomerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreatePhoneNumber.class);
                intent.putExtra(LOGIN_TYPE_ID,1);
                startActivity(intent);
            }
        });
    }

    Gson gson = new GsonBuilder().serializeNulls().create();
    //Khởi tạo retrofit
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://android-food-api.herokuapp.com/") //Lấy URL của HOST
            .addConverterFactory(GsonConverterFactory.create(gson)) //Sử dụng file JSON
            .build();
    OrdersFoodAPI ordersFoodAPI = retrofit.create(OrdersFoodAPI.class); //Khởi tạo các controller
    public void getCustomerByAccount(String account)
    {
        Call<UsersData> call = ordersFoodAPI.getUserByAccount(account); //id này là request
        call.enqueue(new Callback<UsersData>() {
            Users[] users;
            @Override
            public void onResponse(Call<UsersData> call, Response<UsersData> response) {
                //Không tìm thấy
                if (!response.isSuccessful()) {
                    Log.d("Update", "Failed");
                    return;
                }
                //Tìm thấy
                users = response.body().getUsersList();
                //Ghi DL xuống SQLite
                setCustomerToDatabase(users[0].getUserName(), users[0].getFirstName(),
                        users[0].getLastName(), users[0].getAddress()); //Lưu dữ liệu vào data base
                dataSqlite = getCustomerFromDatabase();//Lấy data từ SQLite
                //Gán text vào address
                while (dataSqlite.moveToNext())
                {
                    customerAddress.setText(dataSqlite.getString(3));//Địa chỉ cột 4
                }
                Log.d("Update", "Success");
            }
            @Override
            public void onFailure(Call<UsersData> call, Throwable t) {
                Log.d("Update", "Error");
            }
        });
    }
    DatabaseContext databaseContext = new DatabaseContext(this, "Local", null, 1);
    //Get account from local database
    private void setCustomerToDatabase(String account, String firstName, String lastName,
                                         String address)
    {
        //Tạo bảng
        databaseContext.QueryData("CREATE TABLE IF NOT EXISTS CustomerAccount(Account NVARCHAR(10), FirstName NVARCHAR (30), " +
                "LastName NVARCHAR (50), Address NVARCHAR (255))");

        //Check
        Cursor getData = databaseContext.GetData
                ("SELECT *\n" +
                        "FROM CustomerAccount\n" +
                        "WHERE Account = \"" + account + "\""); //Đây là câu lệnh SQL chuẩn
        if(getData.getCount() == 0) //The first row
        {
            //Insert Data
            databaseContext.QueryData("INSERT INTO CustomerAccount VALUES('" + account + "', '" + firstName + "', " +
                    "'" + lastName + "', '" + address + "')");
        }
        Log.d("GetCount", String.valueOf(getData.getCount()));
        int i = 0;
        while(getData.moveToNext())
        {
            String a = getData.getString(0);
            Log.d("GetAccount", a);
            i++;
        }
    }
    //Get customer from database
    private Cursor getCustomerFromDatabase()
    {
        //Tạo bảng
        databaseContext.QueryData("CREATE TABLE IF NOT EXISTS CustomerAccount(Account NVARCHAR(10), FirstName NVARCHAR (30), " +
                "LastName NVARCHAR (50), Address NVARCHAR (255))");
        Cursor getData = databaseContext.GetData
                ("SELECT *\n" +
                        "FROM CustomerAccount"); //Đây là câu lệnh SQL chuẩn
        return getData;
    }
    //Tạo thông báo khi đăng xuất
    public void logoutAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getText(R.string.sign_out) + ".")
                .setMessage(getText(R.string.signout_confirm_question))
                .setPositiveButton(R.string.confirm_no_caps, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, MainLogin.class); //Chuyển về trang đăng xuất
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
        //Drop table khi logout, để nhận giá trị mới
        databaseContext.QueryData("DROP TABLE CustomerAccount;");
    }
    //Get customer account from local database
    private String getCustomerAccountFromDatabase()
    {
        String result = "";
        //Database
        DatabaseContext databaseContext = new DatabaseContext(this, "Local", null, 1);
        //Tạo bảng
        databaseContext.QueryData("CREATE TABLE IF NOT EXISTS CustomerAccount(Account NVARCHAR(50), Password NVARCHAR (50))");
        Cursor getData = databaseContext.GetData
                ("SELECT *\n" +
                        "FROM CustomerAccount");
        Log.d("GetCount", String.valueOf(getData.isFirst()));
        while(getData.moveToNext()) //The first row
        {
            result = getData.getString(0);//Account nằm cột 1
            Log.d("CustomerAccount", result);
        }
        return result;
    }

    public void changeLanguage()
    {
        DialogFragment langFrag = FragmentLanguage.newInstance();
        langFrag.show(getSupportFragmentManager(), "tag");
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){ // Inflate menu
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch(item.getItemId()){
//            case R.id.action_change_address:
//                return true;
//            case R.id.action_change_language:
//                DialogFragment langFrag = FragmentNgonNgu.newInstance();
//                langFrag.show(getSupportFragmentManager(), "tag");
//                return true;
//            case R.id.action_check_order:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
//
//    public void languageSelect(String lang)
//    {
//        Locale locale = new Locale(lang); //Chọn locale theo biến lang
//        Configuration config = new Configuration();
//        config.locale = locale; //Set locale cho activity
//        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//        //Cập nhật ngôn ngữ đã chọn ở trên
//        Intent intent = new Intent(this, CustomerMainSearch.class); // Khởi động lại activity
//        startActivity(intent);
//    }

}
