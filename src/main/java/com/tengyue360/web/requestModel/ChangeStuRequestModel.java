package com.tengyue360.web.requestModel;

public class ChangeStuRequestModel extends BaseRequestModel {


    private String id;//学员id

    private String phone; //家长电话




    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "ChangeStuRequestModel{" +
                "id='" + id + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
