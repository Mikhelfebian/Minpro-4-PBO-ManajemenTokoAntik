/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokoantik.view;

/**
 *
 * @author ASUS
 */

import com.mycompany.tokoantik.controller.BarangController;
import com.mycompany.tokoantik.model.Barang;
import com.mycompany.tokoantik.model.BarangAntik;
import com.mycompany.tokoantik.model.BarangPerhiasan;
import com.mycompany.tokoantik.util.Validator;

import java.util.ArrayList;
import java.util.Scanner;

public class MainView {
    private final BarangController controller;
    private final Scanner sc;

    public MainView() {
        controller = new BarangController();
        sc = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            tampilkanMenu();
            int pilihan = Validator.inputPilihanMenu(sc, "Pilih menu (1-6): ", 1, 6);

            switch (pilihan) {
                case 1 -> tambahBarang();
                case 2 -> tampilkanSemuaBarang();
                case 3 -> cariBarang();
                case 4 -> updateBarang();
                case 5 -> hapusBarang();
                case 6 -> {
                    System.out.println("\nTerima kasih telah menggunakan Monarch Antiqu'e System!");
                    running = false;
                }
            }
            System.out.println();
        }
    }

    private void tampilkanMenu() {
        System.out.println("===========================================");
        System.out.println("    TOKO BARANG ANTIK - MONARCH ANTIQU'E   ");
        System.out.println("===========================================");
        System.out.println("1. Tambah Barang");
        System.out.println("2. Tampilkan Semua Barang");
        System.out.println("3. Cari Barang");
        System.out.println("4. Update Barang (Tekan Enter jika data tidak diubah)");
        System.out.println("5. Hapus Barang");
        System.out.println("6. Keluar");
        System.out.println("===========================================");
    }

    private void tambahBarang() {
        System.out.println("\n=== TAMBAH BARANG ===");
        System.out.println("1. Barang Antik Umum");
        System.out.println("2. Barang Perhiasan");
        int jenis = Validator.inputPilihanMenu(sc, "Pilih jenis barang (1-2): ", 1, 2);

        String nama = Validator.inputString(sc, "Nama barang: ");
        double harga = Validator.inputDoubleMin(sc, "Harga Rp: ", 0);
        int stok = Validator.inputIntMin(sc, "Stok barang: ", 0);

        if (jenis == 1) {
            String asal = Validator.inputString(sc, "Asal Negara: ");
            int tahun = Validator.inputInt(sc, "Tahun Pembuatan: ");
            controller.tambahBarang(nama, harga, stok, asal, tahun);
        } else {
            String material = Validator.inputString(sc, "Material: ");
            double berat = Validator.inputDoubleMin(sc, "Berat dalam gram: ", 0.1);
            controller.tambahBarang(nama, harga, stok, material, berat);
        }
        System.out.println("-> Data barang berhasil ditambahkan!");
    }

    private void tampilkanSemuaBarang() {
        System.out.println("\n=== DAFTAR BARANG ANTIK & PERHIASAN ===");
        if (controller.getDaftarBarang().isEmpty()) {
            System.out.println("Belum ada data barang.");
            return;
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-4s %-25s %-15s %-17s %-6s | %s%n", "ID", "Nama Barang", "Jenis", "Harga", "Stok", "Detail & Sertifikat Keaslian");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        for (Barang b : controller.getDaftarBarang()) {
            System.out.println(b);
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
    }

    private void cariBarang() {
        System.out.println("\n=== CARI BARANG ===");
        System.out.println("1. Cari berdasarkan ID");
        System.out.println("2. Cari berdasarkan Nama/Keyword");
        int opsi = Validator.inputPilihanMenu(sc, "Pilih metode pencarian (1-2): ", 1, 2);

        if (opsi == 1) {
            int id = Validator.inputIntMin(sc, "Masukkan ID barang: ", 1);
            Barang b = controller.cariBarang(id);
            if (b == null) {
                System.out.println("-> Barang dengan ID " + id + " tidak ditemukan.");
            } else {
                System.out.println("-> Barang Ditemukan:\n" + b);
            }
        } else {
            String kw = Validator.inputString(sc, "Masukkan kata kunci nama: ");
            ArrayList<Barang> hasil = controller.cariBarang(kw);
            if (hasil.isEmpty()) {
                System.out.println("-> Tidak ada barang yang cocok dengan kata kunci '" + kw + "'.");
            } else {
                System.out.println("-> Hasil Pencarian:");
                for (Barang b : hasil) {
                    System.out.println(b);
                }
            }
        }
    }

    private void updateBarang() {
        System.out.println("\n=== UPDATE BARANG ===");
        tampilkanSemuaBarang();
        int id = Validator.inputIntMin(sc, "Masukkan ID barang yang ingin diupdate: ", 1);
        Barang b = controller.cariBarang(id);

        if (b == null) {
            System.out.println("-> Barang dengan ID " + id + " tidak ditemukan.");
            return;
        }

        System.out.println("\n*Catatan: Tekan [ENTER] jika tidak ingin mengubah nilai lama.");
        String nama = Validator.inputStringOptional(sc, "Nama barang baru [" + b.getNamaBarang() + "]: ", b.getNamaBarang());
        double harga = Validator.inputDoubleOptional(sc, "Harga baru Rp [" + b.getHarga() + "]: ", b.getHarga());
        int stok = Validator.inputIntOptional(sc, "Stok baru [" + b.getStok() + "]: ", b.getStok());

        b.setNamaBarang(nama);
        b.setHarga(harga);
        b.setStok(stok);

        if (!(b instanceof BarangAntik barangAntik)) {
            if (b instanceof BarangPerhiasan barangPerhiasan) {
                String material = Validator.inputStringOptional(sc, "Material baru [" + barangPerhiasan.getMaterial() + "]: ", barangPerhiasan.getMaterial());
                double berat = Validator.inputDoubleOptional(sc, "Berat baru (gram) [" + barangPerhiasan.getBeratGram() + "]: ", barangPerhiasan.getBeratGram());
                barangPerhiasan.setMaterial(material);
                barangPerhiasan.setBeratGram(berat);
            }
        } else {
            String asal = Validator.inputStringOptional(sc, "Asal negara baru [" + barangAntik.getAsalNegara() + "]: ", barangAntik.getAsalNegara());
            int tahun = Validator.inputIntOptional(sc, "Tahun pembuatan baru [" + barangAntik.getTahunPembuatan() + "]: ", barangAntik.getTahunPembuatan());
            barangAntik.setAsalNegara(asal);
            barangAntik.setTahunPembuatan(tahun);
        }

        System.out.println("-> Barang berhasil diupdate.");

        controller.updateBarang(b);
        System.out.println("-> Barang berhasil diupdate.");
    }

    private void hapusBarang() {
        System.out.println("\n=== HAPUS BARANG ===");
        tampilkanSemuaBarang();
        int id = Validator.inputIntMin(sc, "Masukkan ID barang yang ingin dihapus: ", 1);
        Barang b = controller.cariBarang(id);

        if (b == null) {
            System.out.println("-> Barang dengan ID " + id + " tidak ditemukan.");
            return;
        }

        String konfirmasi = Validator.inputString(sc, "Yakin ingin menghapus '" + b.getNamaBarang() + "'? (y/n): ");
        if (konfirmasi.equalsIgnoreCase("y")) {
            controller.hapusBarang(id);
            System.out.println("-> Barang berhasil dihapus.");
        } else {
            System.out.println("-> Penghapusan dibatalkan.");
        }
    }
}