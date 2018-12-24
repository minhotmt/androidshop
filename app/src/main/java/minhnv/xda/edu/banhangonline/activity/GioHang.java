package minhnv.xda.edu.banhangonline.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.text.DecimalFormat;
import minhnv.xda.edu.banhangonline.R;
import minhnv.xda.edu.banhangonline.adapter.GiohangAdapter;
import minhnv.xda.edu.banhangonline.ultil.CheckConnection;

public class GioHang extends AppCompatActivity {
    static TextView txtTongTien;
    TextView txtThongBao;
    Button btnThanhToan,btnTiepTuc,btnAdd,btnMinus;
    ListView lvGiohang;
    GiohangAdapter giohangAdapter;
    Toolbar toolbarGiohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        AnhXa();
        ActionToolbar();
        CheckData();
        EvenUntil();
        CatchOnItemLV();
        CatchClickButton();
    }

    private void CatchClickButton() {
        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(intent);
//                MainActivity.txtGioHang.setText(String.valueOf(MainActivity.mangGioHang.size()));
                finish();
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.mangGioHang.size()<=0){
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Giỏ hàng của bạn không có sản phẩm nào!");
                } else {
                    Intent intent = new Intent(getApplicationContext(),ThongTinKhachHang.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void CatchOnItemLV() {
        lvGiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn chắc chắn muốn xóa!");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (MainActivity.mangGioHang.size()<0){
                            txtThongBao.setVisibility(View.VISIBLE);

                        }else {
                            MainActivity.mangGioHang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EvenUntil();
                            if (MainActivity.mangGioHang.size()<=0){
                                txtThongBao.setVisibility(View.VISIBLE);
                            } else {
                                txtThongBao.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EvenUntil();
                            }

                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        giohangAdapter.notifyDataSetChanged();
                        EvenUntil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EvenUntil() {
        long tongtien=0;
        for (int i=0;i<MainActivity.mangGioHang.size();i++){
            tongtien += MainActivity.mangGioHang.get(i).getGiasp();

        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText("Giá: " + decimalFormat.format(tongtien)+" Đ");
    }

    private void CheckData() {
            if (MainActivity.mangGioHang.size()<=0){
                giohangAdapter.notifyDataSetChanged();
                txtThongBao.setVisibility(View.VISIBLE);
                txtTongTien.setText("0 Đ");
                lvGiohang.setVisibility(View.INVISIBLE);
            } else {
                giohangAdapter.notifyDataSetChanged();
                txtThongBao.setVisibility(View.INVISIBLE);
                lvGiohang.setVisibility(View.VISIBLE);

            }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarGiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        txtThongBao = (TextView) findViewById(R.id.txtThongBao);
        txtTongTien = (TextView) findViewById(R.id.txtTongTien);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnThanhToan = (Button) findViewById(R.id.btnThanhToanGioHang);
        btnTiepTuc = (Button) findViewById(R.id.btnTiepTucMuaHang);
        lvGiohang = (ListView) findViewById(R.id.lvGioHang);
        toolbarGiohang = (Toolbar) findViewById(R.id.toolbargiohang);
        giohangAdapter = new GiohangAdapter(MainActivity.mangGioHang,getApplicationContext());
        lvGiohang.setAdapter(giohangAdapter);

    }
}
