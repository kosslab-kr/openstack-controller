package openstack.contributhon.com.openstackcontroller.nova.Model;

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
    public String user_id;
    public String key_name;
    public Fault fault;

    public class Fault{
        public String message;
    }
}
