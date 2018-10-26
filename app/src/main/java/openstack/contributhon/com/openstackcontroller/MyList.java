package openstack.contributhon.com.openstackcontroller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static openstack.contributhon.com.openstackcontroller.Config.*;


public class MyList extends Fragment {

    protected ListView mListView;
    protected AlertDialog mDialog;
    protected IRestApi mInterface;
    protected Retrofit mRetrofit;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected String mHost;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHost = cHost;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main,null);
        mListView = view.findViewById(R.id.list);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                getList();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mRetrofit = new Retrofit.Builder()
                .baseUrl(mHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mInterface = mRetrofit.create(IRestApi.class);

        getList();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setPostiion(position);
                cIsDetail = true;
                ((NavigationActivity)getActivity()).replaceFragment();
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                itemDialog(pos);
                return true;
            }
        });

        return view;
    }

    public void setPostiion(int position){}
    public void addDialog(){}
    public void itemDialog(int pos){}
    public void getList(){}
}
