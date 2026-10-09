/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokoantik.dao;

/**
 *
 * @author ASUS
 */

import com.mycompany.tokoantik.dao.BarangDao;
import com.mycompany.tokoantik.config.DatabaseConfig;
import com.mycompany.tokoantik.model.Barang;
import com.mycompany.tokoantik.model.BarangAntik;
import com.mycompany.tokoantik.model.BarangPerhiasan;
import com.mycompany.tokoantik.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;

public class BarangDaoImpl implements BarangDao {

    @Override
    public void create(Barang barang) {
        String sql = "INSERT INTO barang (nama_barang, harga, stok, jenis, asal_negara, tahun_pembuatan, material, berat_gram) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, barang.getNamaBarang());
            stmt.setDouble(2, barang.getHarga());
            stmt.setInt(3, barang.getStok());

            if (barang instanceof BarangAntik ba) {
                stmt.setString(4, "Antik");
                stmt.setString(5, ba.getAsalNegara());
                stmt.setInt(6, ba.getTahunPembuatan());
                stmt.setNull(7, Types.VARCHAR);
                stmt.setNull(8, Types.DOUBLE);
            } else if (barang instanceof BarangPerhiasan bp) {
                stmt.setString(4, "Perhiasan");
                stmt.setNull(5, Types.VARCHAR);
                stmt.setNull(6, Types.INTEGER);
                stmt.setString(7, bp.getMaterial());
                stmt.setDouble(8, bp.getBeratGram());
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal menambahkan barang: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Barang> getAll() {
        ArrayList<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToBarang(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data barang: " + e.getMessage());
        }
        return list;
    }

    @Override
    public Barang getById(int id) {
        String sql = "SELECT * FROM barang WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBarang(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal mencari barang berdasarkan ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Barang> getByKeyword(String keyword) {
        ArrayList<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang WHERE LOWER(nama_barang) LIKE ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + keyword.toLowerCase() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToBarang(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal mencari barang berdasarkan kata kunci: " + e.getMessage());
        }
        return list;
    }

    @Override
    public void update(Barang barang) {
        String sql = "UPDATE barang SET nama_barang = ?, harga = ?, stok = ?, asal_negara = ?, tahun_pembuatan = ?, material = ?, berat_gram = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, barang.getNamaBarang());
            stmt.setDouble(2, barang.getHarga());
            stmt.setInt(3, barang.getStok());

            if (barang instanceof BarangAntik ba) {
                stmt.setString(4, ba.getAsalNegara());
                stmt.setInt(5, ba.getTahunPembuatan());
                stmt.setNull(6, Types.VARCHAR);
                stmt.setNull(7, Types.DOUBLE);
            } else if (barang instanceof BarangPerhiasan bp) {
                stmt.setNull(4, Types.VARCHAR);
                stmt.setNull(5, Types.INTEGER);
                stmt.setString(6, bp.getMaterial());
                stmt.setDouble(7, bp.getBeratGram());
            }

            stmt.setInt(8, barang.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal mengupdate barang: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM barang WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal menghapus barang: " + e.getMessage());
        }
    }

    private Barang mapResultSetToBarang(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nama = rs.getString("nama_barang");
        double harga = rs.getDouble("harga");
        int stok = rs.getInt("stok");
        String jenis = rs.getString("jenis");

        if ("Antik".equalsIgnoreCase(jenis)) {
            String asal = rs.getString("asal_negara");
            int tahun = rs.getInt("tahun_pembuatan");
            return new BarangAntik(id, nama, harga, stok, asal, tahun);
        } else {
            String material = rs.getString("material");
            double berat = rs.getDouble("berat_gram");
            return new BarangPerhiasan(id, nama, harga, stok, material, berat);
        }
    }
}