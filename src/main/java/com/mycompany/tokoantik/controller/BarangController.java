/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokoantik.controller;

/**
 *
 * @author ASUS
 */
import com.mycompany.tokoantik.dao.BarangDao;
import com.mycompany.tokoantik.dao.BarangDaoImpl;
import com.mycompany.tokoantik.model.Barang;
import com.mycompany.tokoantik.model.BarangAntik;
import com.mycompany.tokoantik.model.BarangPerhiasan;
import java.util.ArrayList;

public class BarangController {
    private final BarangDao barangDao;

    public BarangController() {
        this.barangDao = new BarangDaoImpl();
    }

    public Barang tambahBarang(String nama, double harga, int stok, String asal, int tahun) {
        BarangAntik b = new BarangAntik(0, nama, harga, stok, asal, tahun);
        barangDao.create(b);
        return b;
    }

    public Barang tambahBarang(String nama, double harga, int stok, String material, double berat) {
        BarangPerhiasan b = new BarangPerhiasan(0, nama, harga, stok, material, berat);
        barangDao.create(b);
        return b;
    }

    public ArrayList<Barang> getDaftarBarang() {
        return barangDao.getAll();
    }

    public Barang cariBarang(int id) {
        return barangDao.getById(id);
    }

    public ArrayList<Barang> cariBarang(String keyword) {
        return barangDao.getByKeyword(keyword);
    }

    public void updateBarang(Barang b) {
        barangDao.update(b);
    }

    public boolean hapusBarang(int id) {
        if (barangDao.getById(id) != null) {
            barangDao.delete(id);
            return true;
        }
        return false;
    }
}