package minhnv.xda.edu.banhangonline.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import minhnv.xda.edu.banhangonline.activity.Fragment.FragmentChuongTrinhKhuyenMai;
import minhnv.xda.edu.banhangonline.activity.Fragment.FragmentDienTu;
import minhnv.xda.edu.banhangonline.activity.Fragment.FragmentNoiBat;
import minhnv.xda.edu.banhangonline.activity.Fragment.FragmentPhuKien;

/**
 * Created by MinhNguyen on 12/18/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> listFragment = new ArrayList<Fragment>();
    List<String> titleFragment = new ArrayList<String>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        listFragment.add(new FragmentNoiBat());
        listFragment.add(new FragmentChuongTrinhKhuyenMai());
        listFragment.add(new FragmentDienTu());
        listFragment.add(new FragmentPhuKien());

        titleFragment.add("Nổi bật");
        titleFragment.add("Chương trình khuyến mãi");
        titleFragment.add("Điện tử");
        titleFragment.add("Phụ kiện");

    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleFragment.get(position);
    }
}
