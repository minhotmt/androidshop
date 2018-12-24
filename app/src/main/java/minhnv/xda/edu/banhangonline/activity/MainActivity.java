package minhnv.xda.edu.banhangonline.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import minhnv.xda.edu.banhangonline.R;
import minhnv.xda.edu.banhangonline.adapter.LoaispAdapter;
import minhnv.xda.edu.banhangonline.adapter.RecyclerItemClickListener;
import minhnv.xda.edu.banhangonline.adapter.SanphamAdapter;
import minhnv.xda.edu.banhangonline.model.GioHang;
import minhnv.xda.edu.banhangonline.model.Loaisp;
import minhnv.xda.edu.banhangonline.model.Sanpham;
import minhnv.xda.edu.banhangonline.ultil.CheckConnection;
import minhnv.xda.edu.banhangonline.ultil.Serve;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;
    LoaispAdapter loaispAdapter;
    static TextView txtGioHang, txtThongBao;
    public static ArrayList<GioHang> mangGioHang;
    int id =0;
    String tenloaisp="";
    String hinhanhloaisp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if (CheckConnection.internetConnectionCheck(getApplicationContext())){
            Actionbar();
            ActionViewFlip();
            Getdulieuloaisp();
            Getdulieusanpham();
            CatchOnItemListView();

        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
            finish();
        }
    }


    private void CatchOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        if(CheckConnection.internetConnectionCheck(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Ban kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.internetConnectionCheck(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienthoaiActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Ban kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.internetConnectionCheck(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LaptopActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Ban kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.internetConnectionCheck(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienheActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Ban kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.internetConnectionCheck(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongTin.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Ban kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if(CheckConnection.internetConnectionCheck(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,Sentiment.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Ban kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void Getdulieusanpham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Serve.Duongdansp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response !=null){
                    int id =0;
                    String tensanpham="";
                    String hinhanhsanpham="";
                    Integer giasanpham=0;
                    String motasanpham="";
                    int idsanpham=0;
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tensanpham = jsonObject.getString("tensanpham");
                            hinhanhsanpham = jsonObject.getString("hinhanhsanpham");
                            giasanpham = jsonObject.getInt("giasanpham");
                            motasanpham = jsonObject.getString("motasanpham");
                            idsanpham = jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(id,tensanpham,giasanpham,motasanpham,hinhanhsanpham,idsanpham));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        sanphamAdapter.notifyDataSetChanged();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void Getdulieuloaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Serve.Duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response !=null){
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisanpham");
                            hinhanhloaisp = jsonObject.getString("hinhanhloaisanpham");
                            mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loaispAdapter.notifyDataSetChanged();
                    }
                    mangloaisp.add(new Loaisp(3,"Liên hệ","https://phodotrat.com/wp-content/uploads/2017/04/icon-tu-van.png"));
                    mangloaisp.add(new Loaisp(4,"Thông tin","http://nhomh-bav.byethost22.com/wp-content/uploads/2016/03/9333115-Blue-glossy-information-icon-Stock-Vector-info-1024x1024.jpg"));
                    mangloaisp.add(new Loaisp(4, "Sentiment analysis","https://store-images.s-microsoft.com/image/apps.44287.13510798886692701.91b1e6b4-5f2b-4276-bcc0-03214acdc0ca.6e453039-4795-4dc5-b7ef-0eb314bb9bdf?w=180&h=180&q=60"));

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlip() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn2.tgdd.vn/qcao/30_11_2017_18_48_15_Le-hoi-phu-kien-800-300.png");
        mangquangcao.add("http://noithatvanphongsme.com/wp-content/uploads/2016/09/banner9-1.jpg");
        mangquangcao.add("http://noithatvanphongsme.com/wp-content/uploads/2016/07/banner8.jpg");
        mangquangcao.add("http://noithatvanphongsme.com/wp-content/uploads/2016/07/banner6p.png");
        for (int i=0;i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon((R.drawable.ic_menu_white_18dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    //Tao function menu
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menutrangchu,menu);
        MenuItem iGioHang = menu.findItem(R.id.itGioHang);
        View giaoDienCustomGioHang = MenuItemCompat.getActionView(iGioHang);
        txtGioHang = giaoDienCustomGioHang.findViewById(R.id.txtSoLuongSanPhamGioHang);
        giaoDienCustomGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGioHang = new Intent(MainActivity.this, minhnv.xda.edu.banhangonline.activity.GioHang.class);
                startActivity(iGioHang);
            }
        });
        MenuItem iThongBao = menu.findItem(R.id.itThongBao);
        View giaoDienThongBao = MenuItemCompat.getActionView(iThongBao);
        txtThongBao = giaoDienThongBao.findViewById(R.id.txtSoLuongThongBao);
        txtThongBao.setText("5");
        giaoDienThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itb = new Intent(MainActivity.this, ThongBao.class);
                startActivity(itb);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itGioHang:
                Intent intent = new Intent(getApplicationContext(), minhnv.xda.edu.banhangonline.activity.GioHang.class);
                startActivity(intent);
            case R.id.itThongBao:
                Intent intent2 = new Intent(getApplicationContext(), ThongBao.class);
                startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }

    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflip);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        listView = (ListView) findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        //
        mangloaisp = new ArrayList<>();
        mangloaisp.add(new Loaisp(0,"Trang chủ","http://laptopgiasi.vn/wp-content/uploads/2017/09/icon-trang-chu-laptopgiasi.vn_.png"));
        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listView.setAdapter(loaispAdapter);
        //
        mangsanpham = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(mangsanpham,getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        recyclerView.setAdapter(sanphamAdapter);
        if (mangGioHang!=null){
        }else {
            mangGioHang = new ArrayList<GioHang>();
        }
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this,ChiTietSanPham.class);
                        intent.putExtra("thongtinsanpham",mangsanpham.get(position));
                        startActivity(intent);
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

    }
}
