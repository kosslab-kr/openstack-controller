package openstack.contributhon.com.openstackcontroller.nova;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ServerList {
    @SerializedName("servers")
    private ArrayList<ServerVO> servers;

    public ArrayList<ServerVO> getServers() {
        return servers;
    }
}
