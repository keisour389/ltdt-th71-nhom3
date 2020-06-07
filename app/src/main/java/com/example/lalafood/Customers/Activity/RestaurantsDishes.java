package com.example.lalafood.Customers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lalafood.API.OrdersFoodAPI;
import com.example.lalafood.API.Req.CreateOrderData;
import com.example.lalafood.API.Req.Dishes;
import com.example.lalafood.API.Req.DishsData;
import com.example.lalafood.API.Req.OrderDetails;
import com.example.lalafood.API.Req.OrderDetailsCreateData;
import com.example.lalafood.API.Req.Orders;
import com.example.lalafood.Customers.Adapter.BillCustomerAdapter;
import com.example.lalafood.Customers.Adapter.FoodAdapter;
import com.example.lalafood.Login.Activity.CreatePhoneNumber;
import com.example.lalafood.Login.Activity.LoginPhoneNumber;
import com.example.lalafood.R;
import com.example.lalafood.SQLite.DatabaseContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantsDishes extends AppCompatActivity {
    public final static String ORDER_ID = "ORDER_ID";
    public final static String LOGIN_TYPE_ID = "LOGIN_TYPE_ID";

    private Context context = this;
    //Các biến dùng chung
    private String restaurantId;
    private String restaurantName;
    private String restaurantImg;
    private String restaurantAddress;
    private Cursor dataSqlite;
    private ActionBar actionBar;
    private Activity contextActivity = this;
    private ListView foodsRestaurantList;
    private ImageView imageRestaurantBanner;
    private TextView restaurantNameInDishes;
    private LinearLayout pickingOrder;
    private FrameLayout totalFrame;
    private TextView dishTotalPrice;
    private Button foodBag;

    //Các biến include amount
    private Button buttonCloseAmountFrame;
    private View includeAmountView;
    private Button buttonReduce;
    private Button buttonIncrease;
    private TextView dishAmount;
    private TextView foodNameInFragment;
    private TextView dishAmountInTotal;
    private Button buttonAddDish;

    //Các biến include order
    private View includeOrderView;
    private Button returnRestaurant;
    private ListView customerOrderListInOrder;
    private Button customerOrderComplete;
    private TextView customerNumberPhoneInOrder;
    private TextView customerNoteInOrder;
    private TextView customerAddressInOrder;

    //Các biến lưu giá trị các món
    private ArrayList<String> foodNameList = new ArrayList<>();
    private ArrayList<Integer> foodAmountList = new ArrayList<>();
    private ArrayList<Integer> foodPriceList = new ArrayList<>();

    private ArrayList<String> foodId = new ArrayList<>();

    //Các biến lưu giá trị đơn hàng
    private int orderId;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //Nút quay lại
        if(id == android.R.id.home)
        {
            //Kết thúc hiện tại
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_dishes);
        //Ánh xạ
        actionBar = getSupportActionBar();
        foodsRestaurantList = (ListView) findViewById(R.id.foodsRestaurantList);
        imageRestaurantBanner = (ImageView) findViewById(R.id.imageRestaurantBanner);
        restaurantNameInDishes = (TextView) findViewById(R.id.restaurantNameInDishes);
        pickingOrder = (LinearLayout) findViewById(R.id.pickingOrder);
        dishAmountInTotal = (TextView) findViewById(R.id.dishAmountInTotal);
        totalFrame = (FrameLayout) findViewById(R.id.totalFrame);
        dishTotalPrice = (TextView) findViewById(R.id.dishTotalPrice);
        foodBag = (Button) findViewById(R.id.foodBag);

        //Ánh xạ biến include amount
        includeAmountView = (View) findViewById(R.id.setAmountFrame);
        buttonCloseAmountFrame = (Button) includeAmountView.findViewById(R.id.buttonCloseAmountFrame);
        buttonReduce = (Button) includeAmountView.findViewById(R.id.buttonReduce);
        buttonIncrease = (Button) includeAmountView.findViewById(R.id.buttonIncrease);
        dishAmount = (TextView) includeAmountView.findViewById(R.id.dishAmount);
        foodNameInFragment = (TextView) includeAmountView.findViewById(R.id.foodNameInFragment);
        buttonAddDish = (Button) includeAmountView.findViewById(R.id.buttonAddDish);

        //Ánh xạ biến include order
        includeOrderView = (View) findViewById(R.id.customerOrderLayout);
        returnRestaurant = (Button) findViewById(R.id.returnRestaurant);
        customerOrderListInOrder = (ListView) findViewById(R.id.customerOrderListInOrder);
        customerOrderComplete = (Button) findViewById(R.id.customerOrderComplete);
        customerNumberPhoneInOrder = (TextView) findViewById(R.id.customerNumberPhoneInOrder);
        customerNoteInOrder = (TextView) findViewById(R.id.customerNoteInOrder);
        customerAddressInOrder = (TextView) findViewById(R.id.customerAddressInOrder);
        //Nút quay lại
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Lấy mã và địa chỉ nhà hàng
        Intent intent = getIntent();
        restaurantId = intent.getStringExtra(CustomerSearchedRestaurant.RESTAURANT_ID);
        restaurantName = intent.getStringExtra(CustomerSearchedRestaurant.RESTAURANT_NAME);
        restaurantImg = intent.getStringExtra(CustomerSearchedRestaurant.RESTAURANT_IMG);
        restaurantAddress = intent.getStringExtra(CustomerSearchedRestaurant.RESTAURANT_ADDRESS);
        Log.d("restaurantAddress", restaurantAddress);
        //Set ảnh banner
        Picasso.get().load(restaurantImg).into(imageRestaurantBanner, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                imageRestaurantBanner.setBackgroundResource(R.drawable.restaurant_banner);
            }
        });
        //Set tên nhà hàng
        restaurantNameInDishes.setText(restaurantName);
        //Lấy tất cả món ăn theo mã nhà hàng
        getDishByRestaurantId(restaurantId);
        //Xử lí các layout ở include
        //Food Amount
        //Close
        buttonCloseAmountFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Quay lại menu chính
                returnStatusOrder();
            }
        });
        //Reduce
        buttonReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(dishAmount.getText().toString());
                if(amount > 0)
                {
                    amount -= 1;
                }
                dishAmount.setText(String.valueOf(amount));
            }
        });
        //Increase
        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = Integer.parseInt(dishAmount.getText().toString());
                amount += 1;
                dishAmount.setText(String.valueOf(amount));
            }
        });
        //Foodbag
        foodBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mở giỏ hàng lên
                includeOrderView.setVisibility(View.VISIBLE);
                //Ẩn nhà hàng
                pickingOrder.setVisibility(View.GONE);
                //Ẩn thanh tổng cộng
                totalFrame.setVisibility(View.GONE);
                //Triển adapter lên list
                BillCustomerAdapter billCustomerAdapter = new BillCustomerAdapter(contextActivity,
                        foodNameList, foodAmountList, foodPriceList);
                customerOrderListInOrder.setAdapter(billCustomerAdapter);
                //Set text address
                while (dataSqlite.moveToNext())
                {
                    customerAddressInOrder.setText(dataSqlite.getString(3)); //Địa chỉ cột 4
                }
            }
        });
        //Return dish in restaurant
        returnRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Đóng giỏ hàng
                includeOrderView.setVisibility(View.GONE);
                //Mở nhà hàng
                pickingOrder.setVisibility(View.VISIBLE);
                //Mở thanh thanh tổng cộng, nếu đơn hàng hiện tại có 1 món
                if(Integer.parseInt(dishAmountInTotal.getText().toString()) > 0)
                {
                    totalFrame.setVisibility(View.VISIBLE);
                }
            }
        });
        //Create Order
        customerOrderComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String now = simpleDateFormat.format(date);
                //Khởi tạo đối tượng
                Orders orders = new Orders();
                //Các đối tượng hệ thống gán
                orders.setName("Đơn hàng LaLaFood");
                orders.setCreatedDate(now);
                orders.setStatus("Nhận hàng");
                orders.setCustomerId("NULL");//Test
                orders.setShipperId("NULL");
                orders.setAdminId("NULL");
                orders.setPickUpAddress(restaurantAddress);
                orders.setShippingCost(20000); //Chưa xử lí được nên để mặc định
                //Tự tạo
                orders.setPhoneNumber(customerNumberPhoneInOrder.getText().toString());
                orders.setNote(customerNoteInOrder.getText().toString());
                orders.setShipAddress(customerAddressInOrder.getText().toString());
                //Tạo đơn hàng
                createOrder(orders);
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
    //Lấy hết các món ăn về (Test)
    String foodPicking;
    private void getAllDishes()
    {
        //Orders
        Call<DishsData> call = ordersFoodAPI.getAllDishes(); //id này là request
        call.enqueue(new Callback<DishsData>() {
            ArrayList<String> foodName = new ArrayList<>();
            ArrayList<Integer> foodPrice = new ArrayList<>();
            ArrayList<String> foodUnit = new ArrayList<>();
            Dishes[] allDishes;
            //
            int fPrice = 0;
            @Override
            public void onResponse(Call<DishsData> call, Response<DishsData> response) {
                //Không tìm thấy
                if (!response.isSuccessful()) {
                    Log.d("Response", "Failed");
                    return;
                }
                //Tìm thấy
                Log.d("Response", "Success");
                allDishes = response.body().getDishesList(); //Tìm thấy đơn hàng theo Id
                //Gán
                Log.d("allRestaurant.length", String.valueOf(allDishes.length));
                if(allDishes.length > 0) //Xử lí rỗng
                {
                    for(Dishes res: allDishes)
                    {
                        foodName.add(res.getName());//URL trong ghi chú
                        foodPrice.add(res.getPrice());
                        foodUnit.add(res.getUnit());
                    }
                }
                //Phần xử lí result khi lấy được
                //Triển khai adapter
                FoodAdapter foodAdapter = new FoodAdapter(contextActivity, foodName,
                        foodPrice, foodUnit);
                foodsRestaurantList.setAdapter(foodAdapter);
                //Keo dai list theo view
                setListViewHeightBasedOnChildren(foodsRestaurantList);
                //Chọn món
                foodsRestaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Xét trạng thái đăng nhập
                        dataSqlite = getCustomerFromDatabase();//Lấy data từ SQLite
                        Log.d("dataSqlite", String.valueOf(dataSqlite.getCount()));
                        if(dataSqlite.getCount() == 0) //Nếu chưa đăng nhập
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(contextActivity);
                            builder.setTitle("Bạn chưa đăng nhập")
                                    .setMessage("Đăng nhập hoặc đăng kí ngay nào !!!")
                                    .setPositiveButton("Đăng kí", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(context, CreatePhoneNumber.class);
                                            intent.putExtra(LOGIN_TYPE_ID, 1);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("Đăng nhập", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(context, LoginPhoneNumber.class);
                                            intent.putExtra(LOGIN_TYPE_ID, 1);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNeutralButton("Lúc khác", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            builder.show();
                        }
                        else
                        {
                            //Ẩn chọn món
                            pickingOrder.setVisibility(View.GONE);
                            //Ẩn thanh tổng cộng
                            totalFrame.setVisibility(View.GONE);
                            //Chọn số lượng
                            includeAmountView.setVisibility(View.VISIBLE);
                            //Set tên món
                            foodNameInFragment.setText(allDishes[position].getName());
                            //Lấy giá tiền của món
                            fPrice = allDishes[position].getPrice();
                            //Lưu tạm món đang chọn
                            foodPicking = allDishes[position].getDishId();
                        }

                    }
                });
                //Thêm món
                buttonAddDish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Điều chỉnh số lượng
                        int amount = Integer.parseInt(dishAmount.getText().toString());
                        int amountTotal = Integer.parseInt(dishAmountInTotal.getText().toString());
                        //Điều chỉnh giá tiền
                        int priceTotalOneDish = Integer.parseInt(dishTotalPrice.getText().toString());

                        //Tổng số lượng sẽ cộng dồn
                        amountTotal += amount; //Cộng dồn
                        dishAmountInTotal.setText(String.valueOf(amountTotal));
                        //Điều chỉnh giá tiền
                        //Số lượng * giá = tổng
                        priceTotalOneDish += fPrice * amount;
                        dishTotalPrice.setText(String.valueOf(priceTotalOneDish));
                        //Quay lại menu chính
                        returnStatusOrder();
                        //Thêm các món vào list
                        foodNameList.add(foodNameInFragment.getText().toString()); //Tên món
                        foodAmountList.add(amount); //Add số lượng của một món
                        foodPriceList.add(fPrice * amount); //Lấy tổng tiền của 1 món đã đặt

                        //Thêm mã món ăn vào list
                        foodId.add(foodPicking);
                    }
                });
            }
            @Override
            public void onFailure(Call<DishsData> call, Throwable t) {
                Log.d("Response", "Error");
            }
        });
    }
    //Get dish by id
    private void getDishByRestaurantId(String restaurantId)
    {
        //Orders
        //Hàm này bị sai
        Call<DishsData> call = ordersFoodAPI.getDishesByRestaurantId(restaurantId); //id này là request
        call.enqueue(new Callback<DishsData>() {
            ArrayList<String> foodName = new ArrayList<>();
            ArrayList<Integer> foodPrice = new ArrayList<>();
            ArrayList<String> foodUnit = new ArrayList<>();
            Dishes[] allDishes;
            int fPrice = 0;
            @Override
            public void onResponse(Call<DishsData> call, Response<DishsData> response) {
                //Không tìm thấy
                if (!response.isSuccessful()) {
                    Log.d("Response", "Failed");
                    return;
                }
                //Tìm thấy
                Log.d("Response", "Success");
                allDishes = response.body().getDishesList(); //Tìm thấy đơn hàng theo Id
                //Gán
                Log.d("allRestaurant.length", String.valueOf(allDishes.length));
                if(allDishes.length > 0) //Xử lí rỗng
                {
                    for(Dishes res: allDishes)
                    {
                        foodName.add(res.getName());//URL trong ghi chú
                        foodPrice.add(res.getPrice());
                        foodUnit.add(res.getUnit());
                    }
                }
                //Phần xử lí result khi lấy được
                //Triển khai adapter
                FoodAdapter foodAdapter = new FoodAdapter(contextActivity, foodName,
                        foodPrice, foodUnit);
                foodsRestaurantList.setAdapter(foodAdapter);
                //Keo dai list theo view
                setListViewHeightBasedOnChildren(foodsRestaurantList);
                //Chọn món
                foodsRestaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Xét trạng thái đăng nhập
                        dataSqlite = getCustomerFromDatabase();//Lấy data từ SQLite
                        if(dataSqlite.getCount() == 0) //Nếu chưa đăng nhập
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(contextActivity);
                            builder.setTitle("Bạn chưa đăng nhập")
                                    .setMessage("Đăng nhập hoặc đăng kí ngay nào !!!")
                                    .setPositiveButton("Đăng kí", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(context, CreatePhoneNumber.class);
                                            intent.putExtra(LOGIN_TYPE_ID, 1);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("Đăng nhập", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(context, LoginPhoneNumber.class);
                                            intent.putExtra(LOGIN_TYPE_ID, 1);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNeutralButton("Lúc khác", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            builder.show();
                        }
                        else
                        {
                            //Ẩn chọn món
                            pickingOrder.setVisibility(View.GONE);
                            //Ẩn thanh tổng cộng
                            totalFrame.setVisibility(View.GONE);
                            //Chọn số lượng
                            includeAmountView.setVisibility(View.VISIBLE);
                            //Set tên món
                            foodNameInFragment.setText(allDishes[position].getName());
                            //Lấy giá tiền của món
                            fPrice = allDishes[position].getPrice();
                            //Lưu tạm món đang chọn
                            foodPicking = allDishes[position].getDishId();
                        }
                    }
                });
                //Thêm món
                buttonAddDish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Điều chỉnh số lượng
                        int amount = Integer.parseInt(dishAmount.getText().toString());
                        int amountTotal = Integer.parseInt(dishAmountInTotal.getText().toString());
                        //Điều chỉnh giá tiền
                        int priceTotalOneDish = Integer.parseInt(dishTotalPrice.getText().toString());

                        //Tổng số lượng sẽ cộng dồn
                        amountTotal += amount; //Cộng dồn
                        dishAmountInTotal.setText(String.valueOf(amountTotal));
                        //Điều chỉnh giá tiền
                        //Số lượng * giá = tổng
                        priceTotalOneDish += fPrice * amount;
                        dishTotalPrice.setText(String.valueOf(priceTotalOneDish));
                        //Quay lại menu chính
                        returnStatusOrder();
                        //Thêm các món vào list
                        foodNameList.add(foodNameInFragment.getText().toString()); //Tên món
                        foodAmountList.add(amount); //Add số lượng của một món
                        foodPriceList.add(fPrice * amount); //Lấy tổng tiền của 1 món đã đặt

                        //Thêm mã món ăn vào list
                        foodId.add(foodPicking);
                    }
                });

            }
            @Override
            public void onFailure(Call<DishsData> call, Throwable t) {
                Log.d("Response", "Error");
            }
        });
    }
    //Trả về trạng thái đặt hàng
    private void returnStatusOrder()
    {
        //Chọn món
        pickingOrder.setVisibility(View.VISIBLE);
        //Ẩn số lượng
        includeAmountView.setVisibility(View.GONE);

        if(Integer.parseInt(dishAmountInTotal.getText().toString()) == 0)
        {
            //Ẩn thanh tổng cộng
            totalFrame.setVisibility(View.GONE);
        }
        else
        {
            //Mở thanh tổng cộng
            totalFrame.setVisibility(View.VISIBLE);
        }

        //Xóa số lượng hiện tại
        dishAmount.setText("1");
    }
    //Tạo đơn hàng
    private void createOrder(final Orders orders)
    {
        //Create Order
        Call<CreateOrderData> call = ordersFoodAPI.createOrders(orders); //id này là request
        call.enqueue(new Callback<CreateOrderData>() {
            Orders createSuccess;
            @Override
            public void onResponse(Call<CreateOrderData> call, Response<CreateOrderData> response) {
                //Không tìm thấy
                if (!response.isSuccessful()) {
                    Log.d("Response", "Failed");
                    return;
                }
                //Tìm thấy
                Log.d("Response", "Success");
                createSuccess = response.body().getOrder(); //Tìm thấy đơn hàng theo Id
                //Gán
                Log.d("orderId", String.valueOf(createSuccess.getOrderId()));
                //TODO: Wait...
                //Phần xử lí result khi lấy được
                //Trả về orderId để tiếp tục tạo chi tiết đơn hàng
                orderId = createSuccess.getOrderId();
                //Tạo chi tiết đơn hàng
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String now = simpleDateFormat.format(date);
                for(int position = 0; position < foodNameList.size(); position++) //Gán từ món vào chi tiết đơn hàng
                {
                    //Khởi tạo
                    OrderDetails orderDetails = new OrderDetails();
                    //Gán
                    orderDetails.setOrderId(orderId); //Lấy orderId
                    orderDetails.setDishId(foodId.get(position));
                    orderDetails.setAmount(foodAmountList.get(position));
                    orderDetails.setCreatedDate(now);
                    //Tạo chi tiết đơn hàng
                    createOrderDetails(orderDetails);
                }
                //Tạo CSDL dưới SQLite
                createOrderDetailInSQLite();
                //Chuyển qua trang order thành công
                Intent orderComplete = new Intent(contextActivity, CustomerOrderCompleted.class);
                orderComplete.putExtra(ORDER_ID, createSuccess.getOrderId());
                startActivity(orderComplete);

            }
            @Override
            public void onFailure(Call<CreateOrderData> call, Throwable t) {
                Log.d("Response", "Error");
            }
        });
    }
    private void createOrderDetails(OrderDetails orderDetails)
    {
        //Order Details
        Call<OrderDetailsCreateData> call = ordersFoodAPI.createOrderDetails(orderDetails); //id này là request
        call.enqueue(new Callback<OrderDetailsCreateData>() {
            Orders result; //Kết quả trả về
            OrderDetails orderDetails;
            @Override
            public void onResponse(Call<OrderDetailsCreateData> call, Response<OrderDetailsCreateData> response) {
                //Không tìm thấy
                if (!response.isSuccessful()) {
                    Log.d("Response", "Failed");
                    return;
                }
                //Tìm thấy
                Log.d("Response", "Success");
                orderDetails = response.body().getOrderDetails(); //Tìm thấy đơn hàng theo Id
                //Phần xử lí result khi lấy được
                Log.d("OrderID", String.valueOf(orderDetails.getOrderId()));
                Log.d("DishID", String.valueOf(orderDetails.getOrderId()));
//                Intent intent = getIntent();
//                ArrayList<String> Test = new ArrayList<>();
//                intent.putExtra("A", Test);
            }
            @Override
            public void onFailure(Call<OrderDetailsCreateData> call, Throwable t) {
                Log.d("Response", "Error");
            }
        });
    }
    //Cho list view keo dai ra
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    DatabaseContext databaseContext = new DatabaseContext(this, "Local", null, 1);
    //Create database in SQLite
    private void createOrderDetailInSQLite()
    {
        //Tạo table
        databaseContext.QueryData("CREATE TABLE IF NOT EXISTS Bill(Id INTEGER PRIMARY KEY AUTOINCREMENT, OrderId NVARCHAR(10), DishId NVARCHAR(20)," +
                " Amount INTEGER, FoodName NVARCHAR(255), FoodPriceByAmount INTEGER)");
        for(int i = 0; i < foodNameList.size(); i++)
        {
            Log.d("Index", String.valueOf(i));
            databaseContext.QueryData("INSERT INTO Bill VALUES(null,'" + String.valueOf(orderId) + "', " +
                    "'" + foodId.get(i) + "', '"+ foodAmountList.get(i) +"', '" + foodNameList.get(i) + "', " +
                    "'" + foodPriceList.get(i) + "')");
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
    //Thêm Fragment
//    private void addFragmentGetDish()
//    {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        FragmentGetDish fragmentGetDish = new FragmentGetDish();
//        fragmentTransaction.add(R.id.fragmentGetDish, fragmentGetDish);
//        fragmentTransaction.commit();
//    }
}
