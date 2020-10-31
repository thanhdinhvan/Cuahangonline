package com.example.cuahangthietbionline.model;

import java.io.Serializable;

public class Giohang implements Serializable {
    public int idsanpham;
    public String tensp;
    public long Gia;
    public String hinhAnhSanPham;
    public int soLuong;
    public Giohang() {

    }



    public Giohang(int idsanpham, String tensp, long gia, String hinhAnhSanPham, int soLuong) {
        this.idsanpham = idsanpham;
        this.tensp = tensp;
        Gia = gia;
        this.hinhAnhSanPham = hinhAnhSanPham;
        this.soLuong = soLuong;
    }

    public int getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(int idsanpham) {
        this.idsanpham = idsanpham;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public long getGia() {
        return Gia;
    }

    public void setGia(long gia) {
        Gia = gia;
    }

    public String getHinhAnhSanPham() {
        return hinhAnhSanPham;
    }

    public void setHinhAnhSanPham(String hinhAnhSanPham) {
        this.hinhAnhSanPham = hinhAnhSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
