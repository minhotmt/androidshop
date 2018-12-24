package minhnv.xda.edu.banhangonline.ultil;

import android.app.Activity;
import android.bluetooth.BluetoothA2dp;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by MinhNguyen on 11/26/2017.
 */

public class CheckConnection {
    public static boolean internetConnectionCheck(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobilde = false;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo){
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobilde = true;
        }
        return haveConnectedMobilde || haveConnectedWifi;
    }
    public static void ShowToast_Short(Context context,String thongbao){
        Toast.makeText(context,thongbao,Toast.LENGTH_LONG).show();
    }
}
