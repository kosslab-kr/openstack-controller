package openstack.contributhon.com.openstackcontroller;

import io.realm.RealmObject;

public class SessionVO extends RealmObject {
    public int id;
    public String host;
    public String domain;
    public String user;
    public String passwd;
    public String date;

    public void setAll(int id, String host, String domain, String user, String passwd, String date){
        this.id = id;
        this.host = host;
        this.domain = domain;
        this.user = user;
        this.passwd = passwd;
        this.date = date;
    }
}
