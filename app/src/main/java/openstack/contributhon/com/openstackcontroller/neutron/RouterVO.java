package openstack.contributhon.com.openstackcontroller.neutron;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RouterVO {
    public String name;
    public String revision_number;
    public String status;
    public String id;
    @SerializedName("routes")
    public ArrayList<Routes> routes;

    public String flavor_id;
    public String ha;
    public String created_at;
    public String updated_at;
    public String project_id;
    public String tenant_id;
    public String service_type_id;
    public String description;

    public class Routes{
        public String destination;
        public String nexthop;
    }
}
