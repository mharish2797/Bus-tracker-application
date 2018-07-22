package bus.track.bustracker;

/**
 * Created by HP on 31-03-2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        //Toast.makeText(this, "Network Project by \nHarish M (2014503517)\nTeja Surya (2014503550) \nTharani Tharan (2014503551)", Toast.LENGTH_LONG).show();

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
