/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokoantik.model;

/**
 *
 * @author ASUS
 */

public interface Authenticable {
    final String SERTIFIKAT_PREFIX = "CERT-MONARCH-";
    
    String getKodeSertifikat();
    boolean verifikasiKeaslian();
}

