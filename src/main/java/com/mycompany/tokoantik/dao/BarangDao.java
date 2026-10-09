/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokoantik.dao;

/**
 *
 * @author ASUS
 */
import com.mycompany.tokoantik.model.Barang;
import java.util.ArrayList;

public interface BarangDao {
    void create(Barang barang);
    ArrayList<Barang> getAll();
    Barang getById(int id);
    ArrayList<Barang> getByKeyword(String keyword);
    void update(Barang barang);
    void delete(int id);
}