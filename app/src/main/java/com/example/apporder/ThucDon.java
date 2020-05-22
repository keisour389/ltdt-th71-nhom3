package com.example.apporder;

public class ThucDon {
    private int id;
    private String TenMon;
    private String MoTa;

    public ThucDon(int id, String tenMon, String moTa) {
        this.id = id;
        TenMon = tenMon;
        MoTa = moTa;
    }

    public int getId() {
        return id;
    }

    public String getTenMon() {
        return TenMon;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTenMon(String tenMon) {
        TenMon = tenMon;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }
}
