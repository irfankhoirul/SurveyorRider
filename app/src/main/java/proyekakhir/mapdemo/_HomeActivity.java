package proyekakhir.mapdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class _HomeActivity extends Activity {

    Button _act1_bt_login, _act1_bt_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity___home);

        _act1_bt_login = (Button)findViewById(R.id._act1_bt_login);
        _act1_bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //--Proses Login--//

                //--Get and Save User Data to User Class--//

                //--Show Main Menu if login Sukses//
                Intent i = new Intent (_HomeActivity.this,_MenuActivity.class);
                startActivity(i);
            }

        });

        _act1_bt_register = (Button)findViewById(R.id._act1_bt_register);
        _act1_bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i = new Intent (_HomeActivity.this,_RegisterActivity.class);
                startActivity(i);
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu___home, menu);
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
