package openstack.contributhon.com.openstackcontroller.neutron;

import com.google.gson.annotations.SerializedName;

public class NetworkVO {
    @SerializedName("name")
    public String name;
    public String provider;
    public String status;
}
