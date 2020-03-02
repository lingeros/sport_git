package ling.entity;

public class UserData {
    private String user_id;
    private String user_name;
    private String user_sex;
    private String user_phone;

    public UserData() {
    }

    public UserData(String user_id, String user_name, String user_sex, String user_phone) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_sex = user_sex;
        this.user_phone = user_phone;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_sex='" + user_sex + '\'' +
                ", user_phone='" + user_phone + '\'' +
                '}';
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
}
