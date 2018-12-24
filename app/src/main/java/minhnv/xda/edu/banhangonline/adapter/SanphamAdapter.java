package minhnv.xda.edu.banhangonline.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import minhnv.xda.edu.banhangonline.R;
import minhnv.xda.edu.banhangonline.model.Sanpham;

/**
 * Created by MinhNguyen on 11/26/2017.
 */

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {

    ArrayList<Sanpham> sanphams;
    Context context;


    public SanphamAdapter(ArrayList<Sanpham> sanphams, Context context) {
        this.sanphams = sanphams;
        this.context = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sanpham,null);
        ItemHolder itemHolder = new ItemHolder(view);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Sanpham sanpham = sanphams.get(position);
        holder.txttensp.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasp.setText("Giá: " + decimalFormat.format(sanpham.getGiasp())+" Đ");
        Picasso.with(context).load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.error)
                .error(R.drawable.error)
                .into(holder.imghinhsp);

    }

    @Override
    public int getItemCount() {
        return (sanphams == null) ? 0 : sanphams.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhsp;
        public TextView txtgiasp,txttensp;

        public ItemHolder(View itemView){
            super(itemView);
            imghinhsp = itemView.findViewById(R.id.imgsanpham);
            txtgiasp = itemView.findViewById(R.id.txtgiasp);
            txttensp = itemView.findViewById(R.id.txttensp);

        }
    }

}
