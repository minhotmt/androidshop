package minhnv.xda.edu.banhangonline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import minhnv.xda.edu.banhangonline.R;
import minhnv.xda.edu.banhangonline.model.Sanpham;

/**
 * Created by MinhNguyen on 12/1/2017.
 */

public class LaptopAdapter extends BaseAdapter {

    ArrayList<Sanpham> mangLaptop;
    Context context;

    public LaptopAdapter(ArrayList<Sanpham> mangLaptop, Context context) {
        this.mangLaptop = mangLaptop;
        this.context = context;
    }

    public class ViewHolder{
        public TextView txtTenlt,txtGialt,txtMotalt;
        public ImageView imglt;
    }

    @Override
    public int getCount() {
        return (mangLaptop == null) ? 0 : mangLaptop.size();
    }

    @Override
    public Object getItem(int i) {
        return mangLaptop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LaptopAdapter.ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new LaptopAdapter.ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.row_laptop,null);
            viewHolder.imglt = view.findViewById(R.id.imglt);
            viewHolder.txtTenlt = view.findViewById(R.id.txttenlt);
            viewHolder.txtGialt = view.findViewById(R.id.txtgialt);
            viewHolder.txtMotalt = view.findViewById(R.id.txtmotalt);
            view.setTag(viewHolder);
        }else {
            viewHolder = (LaptopAdapter.ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txtTenlt.setText(sanpham.getTensp());
        viewHolder.txtMotalt.setMaxLines(2);
        viewHolder.txtMotalt.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotalt.setText(sanpham.getMotasp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGialt.setText("Giá: " + decimalFormat.format(sanpham.getGiasp())+" Đ");
        Picasso.with(context).load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.error)
                .error(R.drawable.error)
                .into(viewHolder.imglt);
        return view;
    }

}
