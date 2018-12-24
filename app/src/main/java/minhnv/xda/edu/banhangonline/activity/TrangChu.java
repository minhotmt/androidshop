package minhnv.xda.edu.banhangonline.activity;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import minhnv.xda.edu.banhangonline.R;
import minhnv.xda.edu.banhangonline.adapter.ViewPagerAdapter;

public class TrangChu extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    TextView txtGiohang;
    ViewPager viewPager;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    ExpandableListView expandableListView;
    public static ArrayList<GioHang> mangGioHang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        expandableListView = (ExpandableListView) findViewById(R.id.epMenu);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
           if (mangGioHang!=null){
        }else {
            mangGioHang = new ArrayList<GioHang>();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutrangchu,menu);
        MenuItem iGioHang = menu.findItem(R.id.itGioHang);
        View giaoDienCustomGioHang = MenuItemCompat.getActionView(iGioHang);
        txtGiohang = giaoDienCustomGioHang.findViewById(R.id.txtSoLuongSanPhamGioHang);
        giaoDienCustomGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGioHang = new Intent(TrangChu.this, GioHangMua.class);
                startActivity(iGioHang);
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        int id = item.getItemId();
        switch (id){
            case R.id.itTroGiup:
                Intent itTroGiup = new Intent(this, LienheActivity.class);
                startActivity(itTroGiup);
                break;
            case R.id.itThongTin:
                Intent iThongTin = new Intent(this, ThongTin.class);
                startActivity(iThongTin);
                break;
            case R.id.itSearch:
                Intent iTimKiem = new Intent(this, LienheActivity.class);
                startActivity(iTimKiem);
                break;
            case R.id.itDangNhap:
                Intent itDangNhap = new Intent(this, MainActivity.class);
                startActivity(itDangNhap);
                break;
        }
        return true;
    }
}
