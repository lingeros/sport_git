package ling.entity;

/**
 * admin管理员实例
 */

public class Admin {
    //管理员密码
    private String admin_key;
    //管理员名字
    private String admin_name;

    public Admin(String admin_name,String admin_key ) {
        this.admin_key = admin_key;
        this.admin_name = admin_name;
    }

    public Admin() {
    }

    @Override
    public String toString() {
        return "Admin{" +
                "admin_key='" + admin_key + '\'' +
                ", admin_name='" + admin_name + '\'' +
                '}';
    }

    public String getAdmin_key() {
        return admin_key;
    }

    public void setAdmin_key(String admin_key) {
        this.admin_key = admin_key;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }
}
