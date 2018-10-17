package openstack.contributhon.com.openstackcontroller.nova;

import com.google.gson.annotations.SerializedName;

public class ServerVO {
    @SerializedName("name")
    public String name;
    public String status;
    public String power;
    public String id;
    public String address;
    public String created;
    public String updated;
    public String accessIPv4;
    public String accessIPv6;
    public String image;
    public String user_id;
    public String key_name;
}
