package openstack.contributhon.com.openstackcontroller.nova;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import static openstack.contributhon.com.openstackcontroller.Config.MY_TAG;

public class ServerList {
    @SerializedName("servers")
    private ArrayList<ServerVO> servers;

    @SerializedName("server")
    private ServerVO server;

    public ArrayList<ServerVO> getServers() {
        return servers;
    }
    public ServerVO getDetailServer() {
        return server;
    }
}
