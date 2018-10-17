package openstack.contributhon.com.openstackcontroller.neutron;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import openstack.contributhon.com.openstackcontroller.glance.ImageVO;

public class NetworkList {
    @SerializedName("networks")
    private ArrayList<NetworkVO> networks;
    public ArrayList<NetworkVO> getNetworks() {
        return networks;
    }
}
