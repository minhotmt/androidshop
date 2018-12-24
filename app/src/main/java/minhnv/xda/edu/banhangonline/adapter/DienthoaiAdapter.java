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
 * Created by MinhNguyen on 11/27/2017.
 */

public class DienthoaiAdapter extends BaseAdapter {

    ArrayList<Sanpham> mangDienthoai;
    Context context;

    public DienthoaiAdapter(ArrayList<Sanpham> mangDienthoai, Context context) {
        this.mangDienthoai = mangDienthoai;
        this.context = context;
    }

    public class ViewHolder{
        public TextView txtTendt,txtGiadt,txtMotadt;
        public ImageView imgDienthoai;
    }

    @Override
    public int getCount() {
        return (mangDienthoai == null) ? 0 : mangDienthoai.size();
    }

    @Override
    public Object getItem(int i) {
        return mangDienthoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.row_dienthoai,null);
            viewHolder.imgDienthoai = view.findViewById(R.id.imgdt);
            viewHolder.txtTendt = view.findViewById(R.id.txttendt);
            viewHolder.txtGiadt = view.findViewById(R.id.txtgiadt);
            viewHolder.txtMotadt = view.findViewById(R.id.txtmotadt);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txtTendt.setText(sanpham.getTensp());
        viewHolder.txtMotadt.setMaxLines(2);
        viewHolder.txtMotadt.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotadt.setText(sanpham.getMotasp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiadt.setText("Giá: " + decimalFormat.format(sanpham.getGiasp())+" Đ");
        Picasso.with(context).load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.error)
                .error(R.drawable.error)
                .into(viewHolder.imgDienthoai);
        return view;
    }

}
