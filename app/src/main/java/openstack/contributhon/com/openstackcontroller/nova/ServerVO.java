package openstack.contributhon.com.openstackcontroller.nova;

import com.google.gson.annotations.SerializedName;

public class ServerVO {
    @SerializedName("name")
    public String name;
    public String status;
    public String power;
    public String id;

    public boolean isExpand = false;

}
