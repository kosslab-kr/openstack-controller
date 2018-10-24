package openstack.contributhon.com.openstackcontroller;

import java.util.Date;
import io.realm.RealmObject;

public class SessionVO extends RealmObject {
    public int id;
    public String name;
    public String address;
    public String domain;
    public String user;
    public String passwd;
    public Date date;

    public void setAll(int id, String name, String address, String domain, String user, String passwd){
        this.id = id;
        this.name = name;
        this.address = address;
        this.domain = domain;
        this.user = user;
        this.passwd = passwd;
    }

    public void setDate(Date date){
        this.date  = date;
    }
}
