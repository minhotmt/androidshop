package minhnv.xda.edu.banhangonline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import minhnv.xda.edu.banhangonline.R;
import minhnv.xda.edu.banhangonline.model.*;
import minhnv.xda.edu.banhangonline.model.GioHang;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbar;
    Spinner spinner;
    TextView txtTen,txtGia,txtMota;
    ImageButton btnThem;
    ImageView imgct;
    Button btnXemDG;
    int id =0;
    String tensp="";
    String hinhanhsp="";
    Integer giasp=0;
    String motasp="";
    int idsp=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        ActionToolbar();
        GetThongTin();
        CatchEvenSpiner();
        EnvenButton();
    }

    private void EnvenButton() {
        btnXemDG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DanhGiaSP.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (MainActivity.mangGioHang!= null && MainActivity.mangGioHang.size()>0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    Boolean exits = false;
                    for (int i=0;i<MainActivity.mangGioHang.size();i++){
                        if (MainActivity.mangGioHang.get(i).getIdsp()==id){
                            MainActivity.mangGioHang.get(i).setSoluongsp(MainActivity.mangGioHang.get(i).getSoluongsp()+sl);
                            if (MainActivity.mangGioHang.get(i).getSoluongsp()>10){
                                MainActivity.mangGioHang.get(i).setSoluongsp(10);
                            }
                            MainActivity.mangGioHang.get(i).setGiasp(MainActivity.mangGioHang.get(i).getSoluongsp()* giasp);
                            exits = true;
                        }
                    }
                    if (exits==false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi = soluong * giasp;
                        MainActivity.mangGioHang.add(new GioHang(id,tensp,giamoi,hinhanhsp,soluong));
                    }

                }else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giamoi = soluong * giasp;
                    MainActivity.mangGioHang.add(new GioHang(id,tensp,giamoi,hinhanhsp,soluong));
                }
                Intent intent = new Intent(getApplicationContext(), minhnv.xda.edu.banhangonline.activity.GioHang.class);
                startActivity(intent);
                MainActivity.txtGioHang.setText(String.valueOf(MainActivity.mangGioHang.size()));
            }
        });
    }

    private void CatchEvenSpiner() {
        Integer[] soluong = new Integer[] {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetThongTin() {

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getId();
        tensp = sanpham.getTensp();
        hinhanhsp = sanpham.getHinhanhsp();
        giasp = sanpham.getGiasp();
        motasp = sanpham.getMotasp();
        idsp = sanpham.getIdsp();
        txtTen.setText(tensp);
        txtMota.setText(motasp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGia.setText("Giá: " + decimalFormat.format(sanpham.getGiasp())+" Đ");
        Picasso.with(getApplicationContext()).load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.error)
                .error(R.drawable.error)
                .into(imgct);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarchitiet);
        txtGia = (TextView) findViewById(R.id.txtgiact);
        txtMota = (TextView) findViewById(R.id.txtmotact);
        txtTen = (TextView) findViewById(R.id.txttenct);
        btnThem = (ImageButton) findViewById(R.id.btnthemct);
        btnXemDG = (Button) findViewById(R.id.btnXemDanhGia);
        imgct = (ImageView) findViewById(R.id.imgct);
        spinner = (Spinner) findViewById(R.id.soluongct);

    }
}
