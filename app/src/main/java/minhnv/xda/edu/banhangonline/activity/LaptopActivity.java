package minhnv.xda.edu.banhangonline.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import minhnv.xda.edu.banhangonline.R;
import minhnv.xda.edu.banhangonline.adapter.LaptopAdapter;
import minhnv.xda.edu.banhangonline.model.Sanpham;
import minhnv.xda.edu.banhangonline.ultil.CheckConnection;
import minhnv.xda.edu.banhangonline.ultil.Serve;

public class LaptopActivity extends AppCompatActivity {

    Toolbar toolbarlt;
    LaptopAdapter laptopAdapter;
    ListView lvlt;
    ArrayList<Sanpham> manglt = new ArrayList<Sanpham>();
    Boolean loadingData = false;
    LaptopActivity.mHandler mHandler;
    Boolean limitdata = false;
    View footer;
    int id =0;
    int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        Anhxa();
        if (CheckConnection.internetConnectionCheck(getApplicationContext())){
            GetIdLoaisp();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
            finish();
        }

    }

    private void LoadMoreData() {
        lvlt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LaptopActivity.this,ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",manglt.get(i));
                startActivity(intent);
            }});
        lvlt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if (FirstItem+VisibleItem==TotalItem && TotalItem!=0 && loadingData == false && limitdata == false){
                    loadingData = true;
                    LaptopActivity.ThreadData thread = new LaptopActivity.ThreadData();
                    thread.start();
                }

            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String linkdt = Serve.Duongdanquan+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,linkdt, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response !=null && response.length() > 0 && limitdata == false){
                    lvlt.removeFooterView(footer);
                    int id =0;
                    String tendt="";
                    String hinhanhdt="";
                    Integer giadt=0;
                    String motadt="";
                    int iddtt=0;
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tendt = jsonObject.getString("tensanpham");
                            hinhanhdt = jsonObject.getString("hinhanhsanpham");
                            giadt = jsonObject.getInt("giasanpham");
                            motadt = jsonObject.getString("motasanpham");
                            iddtt = jsonObject.getInt("idsanpham");
                            manglt.add(new Sanpham(id,tendt,giadt,motadt,hinhanhdt,iddtt));
                            laptopAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    limitdata = true;
                    lvlt.removeFooterView(footer);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu");
                }

            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("idsanpham",String.valueOf(id));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void ActionToolbar() {
        setSupportActionBar(toolbarlt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlt.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    //Tao function menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutrangchu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuGioHang:
                Intent intent = new Intent(getApplicationContext(), minhnv.xda.edu.banhangonline.activity.GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void GetIdLoaisp() {
        id=getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham",id+"");

    }

    private void Anhxa() {
        toolbarlt = (Toolbar) findViewById(R.id.toolbarlaptop);
        laptopAdapter = new LaptopAdapter(manglt,getApplicationContext());
        lvlt = (ListView) findViewById(R.id.lvlaptop);
        lvlt.setAdapter(laptopAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footer = inflater.inflate(R.layout.progressbar,null);
        mHandler = new LaptopActivity.mHandler();

    }
    public class mHandler extends android.os.Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvlt.addFooterView(footer);
                    break;
                case 1:
                    GetData(++page);
                    loadingData = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
