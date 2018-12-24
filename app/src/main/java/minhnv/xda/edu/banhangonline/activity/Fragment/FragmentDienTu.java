package minhnv.xda.edu.banhangonline.activity.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import minhnv.xda.edu.banhangonline.R;
import minhnv.xda.edu.banhangonline.adapter.SanphamAdapter;
import minhnv.xda.edu.banhangonline.model.Sanpham;
import minhnv.xda.edu.banhangonline.ultil.CheckConnection;
import minhnv.xda.edu.banhangonline.ultil.Serve;


/**
 * Created by MinhNguyen on 12/18/2017.
 */

public class FragmentDienTu extends Fragment{

    RecyclerView recyclerView;
    SanphamAdapter sanphamAdapter;
    ArrayList<Sanpham> mangsanpham;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dientu,container,false);
        recyclerView = getActivity().findViewById(R.id.recycle);
        mangsanpham = new ArrayList<Sanpham>();
        Getdulieusanpham();
//        mangsanpham.add(new Sanpham(1,"Dien thoai",95000,"ok","http://xemanhdep.com/wp-content/uploads/2017/05/anh-girl-xinh-viet-nam-2017-390x250.jpg",2));
//        mangsanpham.add(new Sanpham(2,"Dien thoai",95000,"ok","http://xemanhdep.com/wp-content/uploads/2017/05/anh-girl-xinh-viet-nam-2017-390x250.jpg",2));
//        mangsanpham.add(new Sanpham(3,"Dien thoai",95000,"ok","http://xemanhdep.com/wp-content/uploads/2017/05/anh-girl-xinh-viet-nam-2017-390x250.jpg",2));
        sanphamAdapter = new SanphamAdapter(mangsanpham,getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity().getApplicationContext(),3));
        recyclerView.setAdapter(sanphamAdapter);

        return view;
    }
    private void Getdulieusanpham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                CheckConnection.ShowToast_Short(getActivity(),error.toString());
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
    }

}
