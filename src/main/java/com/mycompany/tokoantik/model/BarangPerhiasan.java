/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokoantik.model;

/**
 *
 * @author ASUS
 */

public class BarangPerhiasan extends Barang implements Authenticable {
    private String material;
    private double beratGram;

    public BarangPerhiasan(int id, String namaBarang, double harga, int stok, String material, double beratGram) {
        super(id, namaBarang, harga, stok);
        setMaterial(material);
        setBeratGram(beratGram);
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        if (material == null || material.trim().isEmpty()) {
            throw new IllegalArgumentException("Material tidak boleh kosong.");
        }
        this.material = material;
    }

    public double getBeratGram() {
        return beratGram;
    }

    public void setBeratGram(double beratGram) {
        if (beratGram <= 0) {
            throw new IllegalArgumentException("Berat harus lebih dari 0.");
        }
        this.beratGram = beratGram;
    }

    @Override
    public String getJenisBarang() {
        return "Perhiasan";
    }

    @Override
    public String getDetailAtribut() {
        return String.format("Mat: %-10s | Berat: %.1fg | Sertifikat: %s", material, beratGram, getKodeSertifikat());
    }

    @Override
    public String getKodeSertifikat() {
        return SERTIFIKAT_PREFIX + "JWL-" + getId();
    }

    @Override
    public boolean verifikasiKeaslian() {
        return beratGram > 0.5;
    }
}