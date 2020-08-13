package com.example.medexexpress.Model;

import java.util.List;

public class Request {

    private String phone;
    private String address;
    private String name;
    private String total;
    private List<Order> fdodds;

    public Request() {
    }

    public Request(String phone, String address, String name, String total, List<Order> fdodds) {
        this.phone = phone;
        this.address = address;
        this.name = name;
        this.total = total;
        this.fdodds = fdodds;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFdodds() {
        return fdodds;
    }

    public void setFdodds(List<Order> fdodds) {
        this.fdodds = fdodds;
    }
}
