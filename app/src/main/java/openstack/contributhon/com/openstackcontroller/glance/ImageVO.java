package openstack.contributhon.com.openstackcontroller.glance;

import com.google.gson.annotations.SerializedName;

public class ImageVO {
    @SerializedName("name")
    public String name;
    public String disk_format;
    public String status;
    public String container_format;
    public String visibility;
    public String min_disk;
    public String created_at;
    public String size;
    public String id;
    public String os_hidden;
    public String virtual_size;
    public String updated_at;
}
