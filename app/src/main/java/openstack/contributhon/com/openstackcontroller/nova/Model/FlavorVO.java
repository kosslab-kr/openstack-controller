package openstack.contributhon.com.openstackcontroller.nova.Model;

import com.google.gson.annotations.SerializedName;

public class FlavorVO {
    @SerializedName("id")
    public String id;
    public String name;
    public String ram;
    public String swap;
    public String vcpus;
    public String rxtx_factor;
    public String disk;
    public String description;
}
