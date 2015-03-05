package proyekakhir.mapdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class _MenuActivity extends Activity {

    Button _act3_bt_app1, _act3_bt_app2, _act3_bt_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity___menu);

        _act3_bt_app1 = (Button)findViewById(R.id._act3_bt_app1);
        _act3_bt_app1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent (_MenuActivity.this,_App1Activity.class);
                startActivity(i);
            }
        });

        _act3_bt_app2 = (Button)findViewById(R.id._act3_bt_app2);
        _act3_bt_app2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent (_MenuActivity.this,_App2Activity.class);
                startActivity(i);
            }
        });

        _act3_bt_setting = (Button)findViewById(R.id._act3_bt_setting);
        _act3_bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent (_MenuActivity.this,_SettingActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu___menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
