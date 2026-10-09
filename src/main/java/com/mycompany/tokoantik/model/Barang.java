/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokoantik.model;

/**
 *
 * @author ASUS
 */

public abstract class Barang {
    protected final int id;
    protected String namaBarang;
    protected double harga;
    protected int stok;

    public Barang(int id, String namaBarang, double harga, int stok) {
        this.id = id;
        setNamaBarang(namaBarang);
        setHarga(harga);
        setStok(stok);
    }

    public final int getId() {
        return id;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        if (namaBarang == null || namaBarang.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama barang tidak boleh kosong.");
        }
        this.namaBarang = namaBarang;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        if (harga < 0) {
            throw new IllegalArgumentException("Harga tidak boleh negatif.");
        }
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif.");
        }
        this.stok = stok;
    }

    public abstract String getJenisBarang();

    public abstract String getDetailAtribut();

    @Override
    public String toString() {
        return String.format("%-4d %-25s %-15s Rp%-15.0f %-6d | %s", 
                id, namaBarang, getJenisBarang(), harga, stok, getDetailAtribut());
    }
}