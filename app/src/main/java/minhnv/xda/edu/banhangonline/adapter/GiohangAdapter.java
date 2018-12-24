package minhnv.xda.edu.banhangonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import minhnv.xda.edu.banhangonline.R;
import minhnv.xda.edu.banhangonline.activity.MainActivity;
import minhnv.xda.edu.banhangonline.model.GioHang;

/**
 * Created by MinhNguyen on 11/29/2017.
 */

public class GiohangAdapter extends BaseAdapter {
    ArrayList<GioHang> mangGioHang;
    Context context;

    public GiohangAdapter(ArrayList<GioHang> mangGioHang, Context context) {
        this.mangGioHang = mangGioHang;
        this.context = context;
    }

    @Override
    public int getCount() {
        return (mangGioHang == null) ? 0 : mangGioHang.size();
    }

    @Override
    public Object getItem(int i) {
        return mangGioHang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txtTenGioHang,txtGiaGiohang,txtTongGia;
        public Button btnAdd,btnMinus,btnValues;
        ImageView imgGioHang;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        GiohangAdapter.ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.row_giohang,null);
            viewHolder.imgGioHang = view.findViewById(R.id.imgGioHang);
            viewHolder.txtTenGioHang = view.findViewById(R.id.txtTenGioHang);
            viewHolder.txtGiaGiohang = view.findViewById(R.id.txtGiaGioHang);
            viewHolder.btnAdd = view.findViewById(R.id.btnAdd);
            viewHolder.btnMinus = view.findViewById(R.id.btnMinus);
            viewHolder.btnValues = view.findViewById(R.id.btnValues);
            view.setTag(viewHolder);
        }else {
            viewHolder = (GiohangAdapter.ViewHolder) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(i);
        viewHolder.txtTenGioHang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaGiohang.setText("Giá: " + decimalFormat.format(gioHang.getGiasp())+" Đ");
        Picasso.with(context).load(gioHang.getAnhsp())
                .placeholder(R.drawable.error)
                .error(R.drawable.error)
                .into(viewHolder.imgGioHang);
        viewHolder.btnValues.setText(gioHang.getSoluongsp()+"");
        int sl = Integer.parseInt(viewHolder.btnValues.getText().toString());
        if (sl>=10){
            viewHolder.btnAdd.setVisibility(View.INVISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        } else if (sl<=1){
            viewHolder.btnMinus.setVisibility(View.INVISIBLE);
        } else if (sl>=1){
            viewHolder.btnAdd.setVisibility(View.VISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        final ViewHolder finalViewHolder1 = viewHolder;
        final ViewHolder finalViewHolder2 = viewHolder;
        viewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluongmoinhat = Integer.parseInt(finalViewHolder.btnValues.getText().toString()) +1;
                int slht = MainActivity.mangGioHang.get(i).getSoluongsp();
                long giaht = MainActivity.mangGioHang.get(i).getGiasp();
                MainActivity.mangGioHang.get(i).setSoluongsp(soluongmoinhat);
                long giamoinhat = (giaht/slht) * soluongmoinhat;
                MainActivity.mangGioHang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder1.txtGiaGiohang.setText("Giá: " + decimalFormat.format(giamoinhat)+" Đ");
                minhnv.xda.edu.banhangonline.activity.GioHang.EvenUntil();
                if (soluongmoinhat>9){
                    finalViewHolder2.btnAdd.setVisibility(View.INVISIBLE);
                    finalViewHolder2.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder2.btnValues.setText(soluongmoinhat+"");
                } else {
                    finalViewHolder2.btnAdd.setVisibility(View.VISIBLE);
                    finalViewHolder2.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder2.btnValues.setText(soluongmoinhat+"");

                }


            }
        });
        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluongmoinhat = Integer.parseInt(finalViewHolder.btnValues.getText().toString()) -1;
                int slht = MainActivity.mangGioHang.get(i).getSoluongsp();
                long giaht = MainActivity.mangGioHang.get(i).getGiasp();
                MainActivity.mangGioHang.get(i).setSoluongsp(soluongmoinhat);
                long giamoinhat = (giaht/slht) * soluongmoinhat;
                MainActivity.mangGioHang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder1.txtGiaGiohang.setText("Giá: " + decimalFormat.format(giamoinhat)+" Đ");
                minhnv.xda.edu.banhangonline.activity.GioHang.EvenUntil();
                if (soluongmoinhat < 2){
                    finalViewHolder2.btnAdd.setVisibility(View.VISIBLE);
                    finalViewHolder2.btnMinus.setVisibility(View.INVISIBLE);
                    finalViewHolder2.btnValues.setText(soluongmoinhat+"");
                } else {
                    finalViewHolder2.btnAdd.setVisibility(View.VISIBLE);
                    finalViewHolder2.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder2.btnValues.setText(soluongmoinhat+"");

                }


            }
        });
        return view;
    }
}
