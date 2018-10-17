package openstack.contributhon.com.openstackcontroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import okhttp3.ResponseBody;
import openstack.contributhon.com.openstackcontroller.Keystone.IKeystone;
import openstack.contributhon.com.openstackcontroller.Keystone.KeyStone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static openstack.contributhon.com.openstackcontroller.Config.cHost;
import static openstack.contributhon.com.openstackcontroller.Config.cToken;
import static openstack.contributhon.com.openstackcontroller.Config.cUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

