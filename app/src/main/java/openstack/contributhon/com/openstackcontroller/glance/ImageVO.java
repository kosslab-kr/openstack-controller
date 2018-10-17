package openstack.contributhon.com.openstackcontroller.glance;

import com.google.gson.annotations.SerializedName;

public class ImageVO {
    @SerializedName("name")
    public String name;
    public String disk_format;
    public String status;
    public String id;
}
