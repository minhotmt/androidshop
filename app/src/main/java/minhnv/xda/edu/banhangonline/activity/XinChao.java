package minhnv.xda.edu.banhangonline.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import minhnv.xda.edu.banhangonline.R;

public class XinChao extends AppCompatActivity {


    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xin_chao);

        setSupportActionBar(toolbar);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                }catch (Exception e){

                }finally {
                    Intent iTrangChu = new Intent(getApplicationContext(), TrangChu.class);
                    startActivity(iTrangChu);
                }
            }
        });
        thread.start();
    }
}
