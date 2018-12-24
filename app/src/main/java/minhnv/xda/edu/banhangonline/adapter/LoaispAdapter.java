package minhnv.xda.edu.banhangonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import minhnv.xda.edu.banhangonline.R;
import minhnv.xda.edu.banhangonline.model.Loaisp;

/**
 * Created by MinhNguyen on 11/26/2017.
 */

public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp> loaispArrayList;
    Context context;

    public LoaispAdapter(ArrayList<Loaisp> loaispArrayList, Context context) {
        this.loaispArrayList = loaispArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return loaispArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return loaispArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        TextView txtloaisp;
        ImageView imgloaisp;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.row_listview_loaisp,null);
            viewHolder.imgloaisp = view.findViewById(R.id.imageviewloaisp);
            viewHolder.txtloaisp = view.findViewById(R.id.textviewloaisp);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();

        }
        Loaisp loaisp = (Loaisp) getItem(i);
        viewHolder.txtloaisp.setText(loaisp.getTenloaisp());
        Picasso.with(context).load(loaisp.getHinhanhloaisp())
                .placeholder(R.drawable.error)
                .error(R.drawable.error)
                .into(viewHolder.imgloaisp);
        return view;
    }
}
