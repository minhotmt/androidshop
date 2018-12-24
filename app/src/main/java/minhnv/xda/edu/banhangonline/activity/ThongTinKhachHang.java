package minhnv.xda.edu.banhangonline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;
import minhnv.xda.edu.banhangonline.R;
import minhnv.xda.edu.banhangonline.ultil.CheckConnection;
import minhnv.xda.edu.banhangonline.ultil.Serve;

public class ThongTinKhachHang extends AppCompatActivity {
    EditText edtTen,edtEmail,edtSDT,edtDiaChi;
    Button btnExit,btnXacNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        Anhxa();
        EvenClickButton();
    }

    private void EvenClickButton() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ten = edtTen.getText().toString().trim();
                final String sdt = edtSDT.getText().toString().trim();
                final String email = edtEmail.getText().toString().trim();
                final String diachi = edtDiaChi.getText().toString().trim();
                if (ten.length()>0 && sdt.length()>0 && email.length()>0 && diachi.length()>0){
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Serve.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang",madonhang);
                            if (Integer.parseInt(madonhang.toString())>0){
                                RequestQueue request = Volley.newRequestQueue(getApplicationContext());
                                StringRequest string = new StringRequest(Request.Method.POST, Serve.Duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("0")==false){
                                            MainActivity.mangGioHang.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn đã thêm giỏ hàng thành công");
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng");
                                        }else {
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Thông tin giỏ hàng của bạn đã bị lỗi");
                                            finish();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i=0;i<MainActivity.mangGioHang.size();i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.mangGioHang.get(i).getIdsp());
                                                jsonObject.put("tensanpham",MainActivity.mangGioHang.get(i).getTensp());
                                                jsonObject.put("giasanpham",MainActivity.mangGioHang.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham",MainActivity.mangGioHang.get(i).getSoluongsp());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);

                                        }
                                        HashMap<String,String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                request.add(string);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("email",email);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("diachi",diachi);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Hãy kiểm tra lại dữ liệu");
                }

            }

        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChi);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSDT = (EditText) findViewById(R.id.edtSDTKH);
        edtTen = (EditText) findViewById(R.id.edtTenKH);
        btnExit = (Button) findViewById(R.id.btnTrove);
        btnXacNhan = (Button) findViewById(R.id.btnXacNhan);
    }
}
