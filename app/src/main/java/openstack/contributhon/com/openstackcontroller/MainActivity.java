package openstack.contributhon.com.openstackcontroller;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import static openstack.contributhon.com.openstackcontroller.Config.*;

public class MainActivity extends AppCompatActivity {

    AlertDialog mUserinfoDialog;
    AlertDialog mSessionDialog;
    private Realm mRealm;
    private SessionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview = findViewById(R.id.session_list);
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        mRealm = Realm.getInstance(realmConfig);

        RealmResults<SessionVO> vos = mRealm.where(SessionVO.class).findAll();
        mAdapter = new SessionAdapter(vos);
        listview.setAdapter(mAdapter);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                itemDialog(pos);
                return true;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SessionVO item = mAdapter.getItem(position);
                checkConnection(item.host, item.domain, item.user, item.passwd, item.id);
            }
        });

    }

    @Override
    protected void onDestroy() {
        mRealm.close();
        super.onDestroy();
    }

    public void itemDialog(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_session, null);
        final TextView host = view.findViewById(R.id.session_host);
        host.setText(mAdapter.getItem(pos).host);

        final TextView edit = view.findViewById(R.id.session_edit);
        final TextView delete = view.findViewById(R.id.session_delete);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDialog(mAdapter.getItem(pos).id);
                mSessionDialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRealm.beginTransaction();
                SessionVO vo = mRealm.where(SessionVO.class)
                        .equalTo("id", mAdapter.getItem(pos).id)
                        .findFirst();
                vo.deleteFromRealm();
                mRealm.commitTransaction();
                mAdapter.notifyDataSetChanged();
                mSessionDialog.dismiss();

            }
        });

        builder.setView(view);
        mSessionDialog = builder.create();
        mSessionDialog.show();
    }

    public void userDialog(final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_userinfo, null);
        Button addButton = view.findViewById(R.id.btnAdd);

        final EditText host_et = view.findViewById(R.id.host);
        final EditText domain_et = view.findViewById(R.id.domain);
        final EditText user_et = view.findViewById(R.id.user);
        final EditText password_et = view.findViewById(R.id.password);

        if (id > 0) {
            final SessionVO vo = mRealm.where(SessionVO.class)
                    .equalTo("id", id)
                    .findFirst();
            host_et.setText(vo.host);
            domain_et.setText(vo.domain);
            user_et.setText(vo.user);
            password_et.setText(vo.passwd);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String host = host_et.getText().toString();
                final String domain = domain_et.getText().toString();
                final String user = user_et.getText().toString();
                final String password = password_et.getText().toString();

                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        if (id > 0) {
                            final SessionVO vo = mRealm.where(SessionVO.class)
                                    .equalTo("id", id)
                                    .findFirst();
                            vo.setAll(id, host, domain, user, password, null);
                            mRealm.insertOrUpdate(vo);
                        } else {
                            SessionVO vo = realm.createObject(SessionVO.class);
                            Number currentIdNum = realm.where(SessionVO.class).max("id");
                            vo.setAll((currentIdNum == null) ? 1 : currentIdNum.intValue() + 1,
                                    host, domain, user, password, null);
                        }
                    }
                });
                mUserinfoDialog.dismiss();
            }
        });
        builder.setView(view);
        mUserinfoDialog = builder.create();
        mUserinfoDialog.show();
    }

    private String checkURL(String s) {
        if (!s.startsWith("http://"))
            s = "http://" + s;
        if (s.endsWith("/"))
            s = s.substring(0, s.length() - 1);
        return s;
    }

    private void checkConnection(final String host_, final String domain, final String user, final String password, final int id) {
        final String host = checkURL(host_);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IKeystone keystone = retrofit.create(IKeystone.class);
        Call<ResponseBody> call = keystone.getToken(KeyStone.getToken(domain, user, password));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    cToken = response.headers().get("X-Subject-Token");
                    cUser = user;
                    cHost = host;
                    Intent intent = new Intent(MainActivity.this,NavigationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else
                    Toast.makeText(getBaseContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addSession(View view) {
        userDialog(0);
    }
}
