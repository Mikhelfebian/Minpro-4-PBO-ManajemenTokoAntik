# Dokumentasi Program Mini Project 4 PBO

## Sistem Manajemen Inventaris Toko Barang Antik (Monarch Antiqu'e) — Versi Database

---

### Informasi Mahasiswa

* **Nama** : Mikhel Febian
* **NIM** : 2509116056
* **Kelas** : B
* **Angkatan** : 2025
* **Mata Kuliah** : Pemrograman Berorientasi Objek
* **Tema Program** : Sistem Manajemen Inventaris Barang Antik
* **Nama Sistem** : Monarch Antiqu'e System

---

## 1. Deskripsi Singkat Program

**Monarch Antiqu'e System** merupakan aplikasi manajemen inventaris barang antik berbasis **Command Line Interface (CLI)** yang dikembangkan menggunakan bahasa pemrograman Java.

Program digunakan untuk mengelola data barang antik dan perhiasan melalui operasi **Create, Read, Update, dan Delete (CRUD)**. Pengguna dapat menambahkan, menampilkan, mencari, memperbarui, dan menghapus data barang.

Pada **Mini Project 4**, program dikembangkan dari Mini Project 3 dengan perubahan utama:

* Penyimpanan data dipindahkan dari `ArrayList` (memori) ke **database MySQL**.
* Akses database dilakukan menggunakan **JDBC dengan `PreparedStatement`**.
* Akses data dipisahkan ke dalam lapisan **DAO (Data Access Object)**.
* Data awal (dummy data) diisi melalui **file `.sql`**, bukan melalui kode program, sehingga saat menu *Tampilkan Semua Barang* dijalankan data langsung tampil.
* Konsep OOP dari Mini Project 3 (Encapsulation, Inheritance, Polymorphism, Abstraction, Interface, dan MVC) tetap dipertahankan.

Nilai tambah: **[CEK: tulis jika ORM diterapkan, misal "Implementasi framework ORM (Hibernate) — lihat Bagian 11". Hapus kalimat ini jika tidak diterapkan.]**

---

## 2. Spesifikasi Program

* **Bahasa Pemrograman** : Java
* **JDK** : JDK 17+
* **IDE** : Apache NetBeans
* **Build Tool** : Maven
* **Database** : MySQL **[CEK: sesuaikan jika memakai MariaDB/PostgreSQL]**
* **Koneksi Database** : JDBC (MySQL Connector/J) dengan `PreparedStatement`
* **Pola Akses Data** : DAO (Data Access Object)
* **Arsitektur** : Model-View-Controller (MVC)
* **Antarmuka** : Command Line Interface (CLI)

---

## 3. Struktur Package

```text
Minpro-4-PBO-ManajemenTokoAntik
├── database/
│   └── toko_antik.sql                  <- skema tabel + dummy data awal
├── src/main/java/com/mycompany/tokoantik
│   ├── config/
│   │   └── DatabaseConnection.java     <- pengelola koneksi JDBC
│   ├── controller/
│   │   └── BarangController.java
│   ├── dao/
│   │   ├── BarangDAO.java              <- interface DAO
│   │   └── BarangDAOImpl.java          <- implementasi JDBC (PreparedStatement)
│   ├── model/
│   │   ├── Authenticable.java
│   │   ├── Barang.java
│   │   ├── BarangAntik.java
│   │   └── BarangPerhiasan.java
│   ├── util/
│   │   └── Validator.java
│   ├── view/
│   │   └── MainView.java
│   └── Main.java
├── pom.xml
└── README.md
```

> **[CEK: sesuaikan nama package/file di atas dengan struktur asli di project, terutama nama file `.sql`, letak `DatabaseConnection`, dan nama class DAO.]**

### Fungsi setiap package

| Package / File | Fungsi                                                                 |
| -------------- | ---------------------------------------------------------------------- |
| `model`        | Menyimpan class entitas dan kontrak interface                          |
| `dao`          | Berisi seluruh query SQL (`PreparedStatement`) untuk CRUD ke database  |
| `config`       | Mengelola pembuatan koneksi ke database                                |
| `controller`   | Menjembatani View dan DAO, serta menangani logika pengelolaan data     |
| `view`         | Menangani tampilan CLI dan interaksi dengan pengguna                   |
| `util`         | Menyediakan fungsi validasi input                                      |
| `database/*.sql` | Skema database dan data awal (dummy data)                            |
| `Main`         | Entry point program                                                    |

---

## 4. Alur Program

Saat program dijalankan, `Main` membuat objek `MainView` lalu menjalankan method `start()`. Seluruh operasi data diteruskan dari View ke Controller, kemudian ke DAO, dan berakhir di database.

```text
Main
  ↓
MainView  (input/output CLI)
  ↓
BarangController  (logika pengelolaan)
  ↓
BarangDAO  →  BarangDAOImpl  (PreparedStatement)
  ↓
DatabaseConnection  (JDBC)
  ↓
Database MySQL  (tabel barang)
```

Menu utama:

```text
1. Tambah Barang
2. Tampilkan Semua Barang
3. Cari Barang
4. Update Barang
5. Hapus Barang
6. Keluar
```

### 4.1 Tambah Barang

Pengguna memilih jenis barang (Barang Antik / Barang Perhiasan), lalu mengisi atribut umum dan atribut khusus. Controller membuat object `BarangAntik` atau `BarangPerhiasan`, kemudian DAO menjalankan `INSERT` melalui `PreparedStatement`.

### 4.2 Tampilkan Semua Barang

DAO menjalankan `SELECT` dan mengubah setiap baris hasil query (`ResultSet`) menjadi object `BarangAntik` atau `BarangPerhiasan` sesuai kolom jenis. Hasil ditampilkan dalam bentuk tabel. Karena data awal sudah ada di database (dari file `.sql`), menu ini langsung menampilkan data tanpa input terlebih dahulu.

### 4.3 Cari Barang

Pencarian dapat dilakukan berdasarkan ID (`WHERE id = ?`) atau nama/keyword (`WHERE nama_barang LIKE ?`). Controller memakai method `cariBarang()` dengan parameter berbeda (overloading).

### 4.4 Update Barang

Pengguna memasukkan ID barang. Data lama diambil dari database, ditampilkan, lalu nilai baru diterapkan melalui setter dan disimpan dengan `UPDATE`. Menekan `ENTER` tanpa input mempertahankan nilai lama.

### 4.5 Hapus Barang

Pengguna memasukkan ID dan memberi konfirmasi (`y/n`) sebelum DAO menjalankan `DELETE`.

### 4.6 Keluar

Program menghentikan perulangan menu dan mengakhiri program.

---

## 5. Penerapan Encapsulation

Encapsulation diterapkan dengan membatasi akses langsung terhadap atribut object dan menyediakan getter/setter.

```java
protected String namaBarang;
protected double harga;
protected int stok;
```

```java
public String getNamaBarang() {
    return namaBarang;
}

public void setNamaBarang(String namaBarang) {
    if (namaBarang == null || namaBarang.trim().isEmpty()) {
        throw new IllegalArgumentException("Nama barang tidak boleh kosong.");
    }
    this.namaBarang = namaBarang;
}
```

Setter menjaga agar data tetap valid (misalnya harga dan stok tidak negatif). Data yang dibaca dari database juga melewati constructor/setter yang sama, sehingga aturan validasi tetap berlaku.

---

## 6. Penerapan Inheritance

`Barang` adalah superclass, sedangkan `BarangAntik` dan `BarangPerhiasan` adalah subclass.

```mermaid
classDiagram
    direction BT

    class Barang {
        <<abstract>>
        #int id
        #String namaBarang
        #double harga
        #int stok
    }

    class BarangAntik {
        -String asalNegara
        -int tahunPembuatan
    }

    class BarangPerhiasan {
        -String material
        -double beratGram
    }

    BarangAntik --|> Barang
    BarangPerhiasan --|> Barang
```

```java
public class BarangAntik extends Barang implements Authenticable
public class BarangPerhiasan extends Barang implements Authenticable
```

Constructor subclass memanggil constructor superclass:

```java
super(id, namaBarang, harga, stok);
```

---

## 7. Penerapan Polymorphism dan Abstraction

### 7.1 Abstraction

`Barang` adalah abstract class yang tidak dibuat langsung sebagai object.

```java
public abstract class Barang {
    public abstract String getJenisBarang();
    public abstract String getDetailAtribut();
}
```

Setiap subclass wajib mengimplementasikan kedua method tersebut. Abstraction juga diterapkan pada layer data melalui **interface `BarangDAO`** yang hanya mendefinisikan kontrak operasi CRUD, sedangkan `BarangDAOImpl` menentukan cara kerjanya dengan JDBC.

### 7.2 Polymorphism — Overriding

```java
@Override
public String getJenisBarang() { return "Perhiasan"; }

@Override
public String getDetailAtribut() {
    return String.format(
        "Mat: %-10s | Berat: %.1fg | Sertifikat: %s",
        material, beratGram, getKodeSertifikat()
    );
}
```

Data dari database disimpan dalam `List<Barang>`, dan pemanggilan `getJenisBarang()` / `getDetailAtribut()` otomatis menyesuaikan tipe object sebenarnya (`BarangAntik` atau `BarangPerhiasan`).

### 7.3 Polymorphism — Overloading

```java
public Barang tambahBarang(String nama, double harga, int stok, String asal, int tahun)
public Barang tambahBarang(String nama, double harga, int stok, String material, double berat)

public Barang cariBarang(int id)
public ArrayList<Barang> cariBarang(String keyword)
```

---

## 8. Penerapan MVC dan DAO

| Lapisan    | File                                           | Tanggung jawab                                   |
| ---------- | ---------------------------------------------- | ------------------------------------------------ |
| Model      | `Barang`, `BarangAntik`, `BarangPerhiasan`, `Authenticable` | Struktur data dan perilaku object   |
| View       | `MainView`                                     | Menu, input, dan tampilan CLI                    |
| Controller | `BarangController`                             | Menghubungkan View dengan DAO                    |
| DAO        | `BarangDAO`, `BarangDAOImpl`                   | Seluruh akses dan query ke database              |

Dengan DAO, tidak ada query SQL di `MainView` maupun `BarangController`. Seluruh SQL berada di `BarangDAOImpl`.

---

## 9. Penerapan JDBC

### 9.1 Database dan File `.sql`

File **`database/toko_antik.sql`** diupload ke repository dan berisi pembuatan database, tabel, serta **dummy data awal**. Data awal diisi lewat file `.sql` ini, **tidak diisi lewat kode Java**.

Cara menjalankan:

```bash
mysql -u root -p < database/toko_antik.sql
```

atau lewat phpMyAdmin / MySQL Workbench (menu *Import*).

Contoh isi file (sesuaikan dengan file asli):

```sql
CREATE DATABASE IF NOT EXISTS db_toko_antik;
USE db_toko_antik;

CREATE TABLE barang (
    id INT AUTO_INCREMENT PRIMARY KEY,
    jenis VARCHAR(20) NOT NULL,
    nama_barang VARCHAR(100) NOT NULL,
    harga DOUBLE NOT NULL,
    stok INT NOT NULL,
    asal_negara VARCHAR(50),
    tahun_pembuatan INT,
    material VARCHAR(50),
    berat_gram DOUBLE
);

INSERT INTO barang (jenis, nama_barang, harga, stok, asal_negara, tahun_pembuatan, material, berat_gram)
VALUES
('ANTIK', '<nama barang antik>', 0, 0, '<negara>', 1900, NULL, NULL),
('PERHIASAN', '<nama perhiasan>', 0, 0, NULL, NULL, '<material>', 0);
```

> **[CEK: ganti blok SQL di atas dengan skema dan dummy data asli dari file `.sql` kamu.]**

### 9.2 Koneksi Database

Class `DatabaseConnection` menyediakan koneksi JDBC:

```java
public class DatabaseConnection {
    private static final String URL  = "jdbc:mysql://localhost:3306/db_toko_antik";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
```

Dependency JDBC driver ditambahkan pada `pom.xml` (MySQL Connector/J).

### 9.3 Prepared Statement

Seluruh query memakai `PreparedStatement` dengan parameter `?` agar aman dari SQL Injection. Contoh pada `BarangDAOImpl`:

```java
// CREATE
String sql = "INSERT INTO barang (jenis, nama_barang, harga, stok, ...) VALUES (?, ?, ?, ?, ...)";
try (Connection conn = DatabaseConnection.getConnection();
     PreparedStatement ps = conn.prepareStatement(sql)) {
    ps.setString(1, barang.getJenisBarang());
    ps.setString(2, barang.getNamaBarang());
    ps.setDouble(3, barang.getHarga());
    ps.setInt(4, barang.getStok());
    ps.executeUpdate();
}

// READ BY ID
String sql = "SELECT * FROM barang WHERE id = ?";

// SEARCH
String sql = "SELECT * FROM barang WHERE nama_barang LIKE ?";
ps.setString(1, "%" + keyword + "%");

// UPDATE
String sql = "UPDATE barang SET nama_barang = ?, harga = ?, stok = ?, ... WHERE id = ?";

// DELETE
String sql = "DELETE FROM barang WHERE id = ?";
```

| Operasi | Query                | Method DAO (contoh)  |
| ------- | -------------------- | -------------------- |
| Create  | `INSERT`             | `tambah(Barang)`     |
| Read    | `SELECT`             | `getAll()`           |
| Search  | `SELECT ... WHERE`   | `cariById()`, `cariByNama()` |
| Update  | `UPDATE`             | `update(Barang)`     |
| Delete  | `DELETE`             | `hapus(int id)`      |

> **[CEK: sesuaikan nama method DAO dengan yang ada di `BarangDAO.java`.]**

`try-with-resources` dipakai agar `Connection` dan `PreparedStatement` otomatis ditutup, dan `SQLException` ditangani dengan `try-catch`.

---

## 10. Penerapan Interface

Interface `Authenticable` tetap digunakan oleh `BarangAntik` dan `BarangPerhiasan`.

```java
public interface Authenticable {
    final String SERTIFIKAT_PREFIX = "CERT-MONARCH-";

    String getKodeSertifikat();
    boolean verifikasiKeaslian();
}
```

Implementasi verifikasi berbeda pada setiap class: `BarangAntik` memeriksa `tahunPembuatan < 1950`, sedangkan `BarangPerhiasan` memeriksa `beratGram > 0.5`.

---

## 11. Penerapan Nilai Tambah — ORM

> **[CEK: Bagian ini hanya boleh ada jika ORM benar-benar diterapkan di kode. Jika tidak, hapus seluruh Bagian 11 dan kalimat nilai tambah di Bagian 1. Jika ya, isi dengan data asli di bawah.]**

* **Framework ORM** : `[Hibernate / JPA / lainnya]`
* **Dependency** : ditambahkan pada `pom.xml`
* **File konfigurasi** : `[hibernate.cfg.xml / persistence.xml]`
* **Class entity** : `[nama class + anotasi, mis. @Entity, @Table, @Id, @Column]`
* **Letak penerapan** : `[nama class, mis. BarangDAOImpl memakai Session / EntityManager]`

Contoh potongan kode:

```java
// tempel potongan kode ORM asli di sini
```

---

## 12. Validasi dan Exception Handling

Class `Validator` pada package `util` menangani validasi input:

```java
try {
    return Integer.parseInt(input);
} catch (NumberFormatException e) {
    System.out.println("-> Input tidak valid!");
}
```

Validasi meliputi input kosong, angka negatif, pilihan menu di luar rentang, dan nilai berat tidak valid. Model melempar `IllegalArgumentException` untuk data tidak valid, dan `SQLException` dari DAO ditangani agar program tidak berhenti saat terjadi kesalahan database.

---

## 13. Dokumentasi Output Program

> **[CEK: screenshot di bawah berasal dari Mini Project 3. Disarankan diganti dengan screenshot terbaru dari versi database, terutama menu *Tampilkan Semua Barang* yang langsung menampilkan data dari file `.sql`.]**

### 13.1 Menu Utama

<img width="968" height="246" alt="image" src="https://github.com/user-attachments/assets/d1c86ec9-ea3d-4e26-996b-b81ab4f39be8" />

* **Input Control:** menerima integer pada prompt `Pilih menu (1-6):`.
* **Validasi:** input di luar `1-6` atau non-numerik memicu pesan peringatan dan *re-prompt*.
* **Control Flow:** pilihan dipetakan dengan `switch-case`.

### 13.2 Tambah Barang

Barang baru disimpan ke database melalui `INSERT` dengan `PreparedStatement`.

#### Tambah Barang Antik

<img width="965" height="225" alt="image" src="https://github.com/user-attachments/assets/deae8bb0-f1ce-48ac-8ed3-8f4bfcc5f615" />

#### Tambah Barang Perhiasan

<img width="971" height="227" alt="image" src="https://github.com/user-attachments/assets/ac5e7da9-7c9d-436e-acd3-a7042ca7c549" />

Jika berhasil, program menampilkan `-> Data barang berhasil ditambahkan!`.

### 13.3 Tampilkan Semua Barang

<img width="1321" height="452" alt="image" src="https://github.com/user-attachments/assets/60ab0adf-2fcf-402b-b0ee-ba5d398ad710" />

Data diambil dari database dan ditampilkan dalam tabel CLI. Dummy data dari file `.sql` langsung tampil pada saat pertama kali menu ini dibuka.

### 13.4 Cari Barang

#### Cari Berdasarkan ID

<img width="1270" height="176" alt="image" src="https://github.com/user-attachments/assets/1764add1-fb22-41e6-9ad8-8f7132d99c9f" />

#### Cari Berdasarkan Nama

<img width="1332" height="167" alt="image" src="https://github.com/user-attachments/assets/bdd11d4e-1dee-4912-b6a6-e4eb72c0b915" />

### 13.5 Update Barang

<img width="1335" height="457" alt="image" src="https://github.com/user-attachments/assets/d25e5999-2fd5-4cc1-a6a2-25b0075640f8" />

Menekan `ENTER` tanpa input mempertahankan nilai lama. Jika berhasil: `-> Barang berhasil diupdate.`

### 13.6 Hapus Barang

<img width="1328" height="376" alt="image" src="https://github.com/user-attachments/assets/942f28c3-0b14-41a0-8d5a-7ca2eb810807" />

Setelah konfirmasi `y`, data dihapus dari database dan muncul `-> Barang berhasil dihapus.`

### 13.7 Keluar Program

<img width="982" height="287" alt="image" src="https://github.com/user-attachments/assets/0e7c835b-823c-417d-ac65-b6886520dbda" />

---

## 14. Cara Menjalankan Program

1. Install JDK 17+, MySQL, dan Apache NetBeans.
2. Import file `database/toko_antik.sql` ke MySQL.
3. Sesuaikan `URL`, `USER`, dan `PASS` pada `DatabaseConnection.java`.
4. Buka project di NetBeans, lakukan *Clean and Build* agar dependency Maven terunduh.
5. Jalankan `Main.java`.

---

## 15. Kesimpulan

Pada Mini Project 4, Monarch Antiqu'e System berkembang dari penyimpanan `ArrayList` menjadi sistem yang terhubung ke database MySQL menggunakan **JDBC (`PreparedStatement`)** dan **DAO**, dengan data awal dari file **`.sql`**. Konsep **Encapsulation, Inheritance, Polymorphism, Abstraction, Interface, dan MVC** tetap diterapkan secara langsung pada program.
