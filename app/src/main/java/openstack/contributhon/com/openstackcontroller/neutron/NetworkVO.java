package openstack.contributhon.com.openstackcontroller.neutron;

import com.google.gson.annotations.SerializedName;

public class NetworkVO{
    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;
    public String provider;
    public String status;
    public String dns_domain;
    public String admin_state_up;
    public String ipv4_address_scope;
    public String ipv6_address_scope;
    public String l2_adjacency;
    public String mtu;
    public String port_security_enabled;
    public String project_id;
    public String qos_policy_id;
    public String created_at;
    public String updated_at;
}
