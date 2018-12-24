package minhnv.xda.edu.banhangonline.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EmotionOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import minhnv.xda.edu.banhangonline.R;
import minhnv.xda.edu.banhangonline.model.DanhGia;
import minhnv.xda.edu.banhangonline.ultil.CheckConnection;
import minhnv.xda.edu.banhangonline.ultil.Serve;


public class DanhGiaSP extends AppCompatActivity {
    TextView txtidsp,txtNguoiDanhGia,txtDanhGia,txtSentiment,txtTieuDe,txtSao;
    Button btnSentiment;
    Intent intent;
    Toolbar toolbar;
    RatingBar ratingBar;
    int id =0;
    ArrayList<DanhGia> mangDG;

    private class WatsonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... textToSpeak) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txtSentiment.setText("Đang phân tích đánh giá ...");
                }
            });

            NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
                    NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
                    "ae9bbf46-ed1a-404a-8c18-26fcf426db44",
                    "CMGtOrukt3Uc");

            //String text = "Apples and Oranges" + "I love apples! I don't like oranges";
            String text = String.valueOf(txtDanhGia.getText());

            List<String> targets = new ArrayList<>();
            targets.add("Shirt");
            targets.add("This");
            targets.add("I");
            targets.add("It");
            targets.add("So");
            targets.add("Verry");

            EmotionOptions emotion= new EmotionOptions.Builder()
                    .targets(targets)
                    .build();

            Features features = new Features.Builder()
                    .emotion(emotion)
                    .build();

            AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                    .html(text)
                    .features(features)
                    .build();

            AnalysisResults response = service
                    .analyze(parameters)
                    .execute();
            System.out.println(response);

            if (response.getEmotion().getDocument().getEmotion().getJoy()>0.5){
                return "Chỉ số thích: "+String.valueOf(response.getEmotion().getDocument().getEmotion().getJoy())+
                        "\nChỉ số gét: "+String.valueOf(response.getEmotion().getDocument().getEmotion().getAnger())+
                        "\nSản phẩm được khách hàng yêu thích";
            }else {
                return "Chỉ số gét: "+String.valueOf(response.getEmotion().getDocument().getEmotion().getAnger())+
                        "\nSản phẩm khách hàng không yêu thích";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result!=null){
                txtSentiment.setText(result);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);
        txtidsp = (TextView) findViewById(R.id.txtID);
        txtNguoiDanhGia = (TextView) findViewById(R.id.txtTenKH);
        txtDanhGia = (TextView) findViewById(R.id.txtDanhGia);
        txtSentiment = (TextView) findViewById(R.id.txtSentiment);
        txtTieuDe = (TextView) findViewById(R.id.txtTieuDe);
        txtSao = (TextView) findViewById(R.id.txtSao);
        btnSentiment = (Button) findViewById(R.id.btnSentiment);
        toolbar = (Toolbar) findViewById(R.id.toolbardanhgia);
        ratingBar = (RatingBar) findViewById(R.id.rateBar);
        mangDG = new ArrayList<>();
        ActionToolbar();

        intent = getIntent();
        Bundle i = intent.getExtras();
        Integer idspd = (Integer) i.get("id");
        txtidsp.setText(String.valueOf(idspd));
        GetDataDanhGia(idspd);

        Log.i("MangDanhGia",String.valueOf(mangDG.size()));


        btnSentiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WatsonTask task = new WatsonTask();
                task.execute(new String[]{});

            }
        });

    }
    private void GetDataDanhGia(final int idspdg) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Serve.Duongdandanhgia, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("Json", response.toString());
                if (response !=null){
                    int idsp =0;
                    String tennguoidg="";
                    String noidung="";
                    int sao = 0;
                    String tieude ="";
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            idsp = jsonObject.getInt("id");
                            tennguoidg = jsonObject.getString("tennguoidanhgia");
                            noidung = jsonObject.getString("danhgia");
                            sao = jsonObject.getInt("sao");
                            tieude = jsonObject.getString("tieude");

                            mangDG.add(new DanhGia(idsp,tennguoidg,noidung,tieude,sao));
                            if (mangDG.size()>0){
                                for (int j=0;j < mangDG.size();j++){
                                    DanhGia dg = mangDG.get(j);
                                    if (dg.getId() == idspdg){
                                        txtDanhGia.setText(dg.getNoidung());
                                        txtNguoiDanhGia.setText(dg.getTennguoidanhgia());
                                        txtTieuDe.setText(dg.getTieude());
                                        txtSao.setText(String.valueOf(dg.getSao()));
                                    }
                                }
                            } else {
                                txtDanhGia.setText("Sản phẩm chưa có đánh giá nào!");
                            }

                            Log.i("DG", String.valueOf(mangDG.size()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
}
