# Dokumentasi Program Mini Project 3 PBO

## Sistem Manajemen Inventaris Toko Barang Antik (Monarch Antiqu'e)

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

Pada Mini Project 3, program dikembangkan dengan menerapkan konsep Pemrograman Berorientasi Objek berupa **Encapsulation, Inheritance, Polymorphism, Abstraction**, serta struktur proyek **Model-View-Controller (MVC)**.

Sebagai nilai tambah, program juga menerapkan **Interface** melalui `Authenticable`.

---

## 2. Spesifikasi Program

* **Bahasa Pemrograman** : Java
* **JDK** : JDK 17+
* **IDE** : Apache NetBeans
* **Struktur Data** : `ArrayList`
* **Arsitektur** : Model-View-Controller (MVC)
* **Antarmuka** : Command Line Interface (CLI)

---

## 3. Struktur Package

Struktur package program adalah sebagai berikut:

```text
com.mycompany.tokoantik
├── controller/
│   └── BarangController.java
│
├── model/
│   ├── Authenticable.java
│   ├── Barang.java
│   ├── BarangAntik.java
│   └── BarangPerhiasan.java
│
├── util/
│   └── Validator.java
│
├── view/
│   └── MainView.java
│
└── Main.java
```

### Fungsi setiap package

| Package      | Fungsi                                               |
| ------------ | ---------------------------------------------------- |
| `model`      | Menyimpan class dan struktur data utama program      |
| `controller` | Menangani logika pengelolaan dan operasi CRUD data   |
| `view`       | Menangani tampilan CLI dan interaksi dengan pengguna |
| `util`       | Menyediakan fungsi validasi input                    |
| `Main`       | Menjadi entry point untuk menjalankan program        |

---

## 4. Alur Program

Saat program dijalankan, `Main` membuat objek `MainView`, kemudian menjalankan method `start()`.

```text
Main
  ↓
MainView
  ↓
BarangController
  ↓
ArrayList<Barang>
  ↓
Menu Utama
  ├── 1. Tambah Barang
  ├── 2. Tampilkan Semua Barang
  ├── 3. Cari Barang
  ├── 4. Update Barang
  ├── 5. Hapus Barang
  └── 6. Keluar
```

### 4.1 Tambah Barang

Pengguna memilih jenis barang:

1. Barang Antik
2. Barang Perhiasan

Program kemudian meminta atribut umum dan atribut khusus sesuai jenis barang. Object yang dibuat dimasukkan ke dalam `ArrayList<Barang>` melalui `BarangController`.

### 4.2 Tampilkan Semua Barang

Program mengambil seluruh object dari `ArrayList<Barang>` dan menampilkannya dalam bentuk tabel.

### 4.3 Cari Barang

Pencarian dapat dilakukan berdasarkan:

* ID barang
* Nama atau keyword

Program menggunakan method `cariBarang()` dengan parameter berbeda untuk kedua jenis pencarian tersebut.

### 4.4 Update Barang

Pengguna memasukkan ID barang yang ingin diperbarui. Data umum dan data khusus barang kemudian dapat diperbarui menggunakan setter.

### 4.5 Hapus Barang

Pengguna memasukkan ID barang kemudian memberikan konfirmasi sebelum object dihapus dari `ArrayList`.

### 4.6 Keluar

Program menghentikan perulangan menu dan mengakhiri program.

---

# 5. Penerapan Encapsulation

Encapsulation diterapkan dengan membatasi akses langsung terhadap atribut object dan menyediakan method untuk mengakses serta mengubah data.

Contohnya pada class `Barang`:

```java
protected String namaBarang;
protected double harga;
protected int stok;
```

Akses terhadap data dilakukan melalui getter dan setter, contohnya:

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

Setter juga digunakan untuk menjaga agar data object tetap valid, seperti mencegah harga dan stok bernilai negatif.

---

# 6. Penerapan Inheritance

Inheritance diterapkan dengan menjadikan `Barang` sebagai superclass dan `BarangAntik` serta `BarangPerhiasan` sebagai subclass.

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

Deklarasi inheritance:

```java
public class BarangAntik extends Barang
```

dan:

```java
public class BarangPerhiasan extends Barang
```

Subclass mewarisi atribut dan method dari `Barang`, serta memiliki atribut khusus masing-masing.

Constructor subclass juga menggunakan `super()` untuk memanggil constructor superclass:

```java
super(id, namaBarang, harga, stok);
```

---

# 7. Penerapan Abstraction

Abstraction diterapkan melalui abstract class `Barang`.

```java
public abstract class Barang
```

Class `Barang` tidak dibuat sebagai object secara langsung, tetapi menjadi dasar bagi subclass.

Program juga memiliki abstract method:

```java
public abstract String getJenisBarang();

public abstract String getDetailAtribut();
```

Method tersebut tidak memiliki implementasi pada `Barang`. Setiap subclass wajib memberikan implementasinya sendiri.

Contohnya pada `BarangPerhiasan`:

```java
@Override
public String getJenisBarang() {
    return "Perhiasan";
}
```

Sedangkan `BarangAntik` memberikan implementasi yang berbeda:

```java
@Override
public String getJenisBarang() {
    return "Barang Antik";
}
```

Dengan demikian, `Barang` menentukan kontrak umum, sedangkan subclass menentukan detail implementasinya.

---

# 8. Penerapan Polymorphism

Polymorphism diterapkan melalui **overriding** dan **overloading**.

## 8.1 Method Overriding

Subclass mengimplementasikan kembali method yang berasal dari superclass.

Contohnya:

```java
@Override
public String getDetailAtribut() {
    return String.format(
        "Mat: %-10s | Berat: %.1fg | Sertifikat: %s",
        material, beratGram, getKodeSertifikat()
    );
}
```

`BarangAntik` dan `BarangPerhiasan` memiliki implementasi `getDetailAtribut()` yang berbeda sesuai karakteristik masing-masing.

Selain itu, `getJenisBarang()` juga dioverride oleh kedua subclass.

## 8.2 Method Overloading

Overloading diterapkan pada `BarangController`.

Terdapat dua method `tambahBarang()` dengan parameter berbeda:

```java
public Barang tambahBarang(
    String nama, double harga, int stok,
    String asal, int tahun
)
```

dan:

```java
public Barang tambahBarang(
    String nama, double harga, int stok,
    String material, double berat
)
```

Keduanya memiliki nama method yang sama, tetapi signature parameter berbeda.

Overloading juga diterapkan pada method `cariBarang()`:

```java
public Barang cariBarang(int id)
```

dan:

```java
public ArrayList<Barang> cariBarang(String keyword)
```

Java menentukan method yang digunakan berdasarkan parameter yang diberikan saat pemanggilan.

---

# 9. Penerapan MVC

Program menerapkan pola **Model-View-Controller (MVC)** dengan pembagian tanggung jawab sebagai berikut:

### Model

Berada pada package `model`.

```text
Barang.java
BarangAntik.java
BarangPerhiasan.java
Authenticable.java
```

Berfungsi mendefinisikan struktur data, atribut, perilaku object, dan kontrak interface.

### View

Berada pada:

```text
view/MainView.java
```

Berfungsi menangani interaksi dengan pengguna, seperti menampilkan menu, menerima input, dan menampilkan hasil operasi.

### Controller

Berada pada:

```text
controller/BarangController.java
```

Berfungsi menangani pengelolaan data dan operasi CRUD terhadap `ArrayList<Barang>`.

Pembagian tersebut membuat logika tampilan dan pengelolaan data tidak berada dalam satu class.

---

# 10. Penerapan Interface — Nilai Tambah

Program menerapkan interface `Authenticable` sebagai nilai tambah.

Interface didefinisikan sebagai:

```java
public interface Authenticable {
    final String SERTIFIKAT_PREFIX = "CERT-MONARCH-";

    String getKodeSertifikat();
    boolean verifikasiKeaslian();
}
```

Interface tersebut menjadi kontrak bahwa class yang mengimplementasikannya harus menyediakan method:

* `getKodeSertifikat()`
* `verifikasiKeaslian()`

`BarangAntik` dan `BarangPerhiasan` mengimplementasikan interface tersebut:

```java
public class BarangAntik extends Barang implements Authenticable
```

```java
public class BarangPerhiasan extends Barang implements Authenticable
```

Implementasi verifikasi dapat berbeda pada setiap class.

Pada `BarangAntik`:

```java
@Override
public boolean verifikasiKeaslian() {
    return tahunPembuatan < 1950;
}
```

Pada `BarangPerhiasan`:

```java
@Override
public boolean verifikasiKeaslian() {
    return beratGram > 0.5;
}
```

Dengan demikian, interface menentukan kemampuan yang harus dimiliki, sedangkan masing-masing subclass menentukan cara menjalankan kemampuan tersebut.

---

# 11. Validasi dan Exception Handling

Program menyediakan class `Validator` pada package `util` untuk menangani validasi input pengguna.

Contohnya:

```java
try {
    return Integer.parseInt(input);
} catch (NumberFormatException e) {
    System.out.println("-> Input tidak valid!");
}
```

`try-catch` digunakan untuk menangani input yang tidak dapat dikonversi menjadi tipe data yang sesuai.

Validasi juga dilakukan terhadap nilai seperti:

* input kosong
* angka negatif
* pilihan menu di luar rentang yang ditentukan
* nilai berat yang tidak valid

Pada model, data yang tidak memenuhi aturan juga akan menghasilkan `IllegalArgumentException`.

---

## 12. Dokumentasi Output Program

Berikut merupakan dokumentasi output program berdasarkan setiap menu yang tersedia pada **Monarch Antiqu'e System**.

### 12.1 Menu Utama

Menampilkan menu utama program yang terdiri dari fitur tambah, tampilkan, cari, update, hapus, dan keluar.

<img width="968" height="246" alt="image" src="https://github.com/user-attachments/assets/d1c86ec9-ea3d-4e26-996b-b81ab4f39be8" />

**Spesifikasi Teknis & Mekanisme Pengolahan:**
* **Input Control:** Sistem menerima masukan integer pada prompt `Pilih menu (1-6):`.
* **Input Validation & Exception Handling:** 
  * Jika input berada di luar jangkauan angka `1-6` atau berupa karakter non-numerik, sistem akan memicu error handling/pesan peringatan dan melakukan *re-prompt* tanpa menghentikan program.
* **Control Flow:** Pilihan angka dipetakan menggunakan struktur percabangan (`switch-case`) untuk memanggil masing-masing sub-fungsi/prosedur yang sesuai.
* **Screen Refresh:** Setiap kali kembali ke Menu Utama, layar terminal dapat dibersihkan (`cls` / `clear`) untuk menjaga kebersihan antarmuka CLI.
  
---

### 12.2 Tambah Barang

Menu ini digunakan untuk menambahkan barang baru. Program menyediakan dua jenis barang, yaitu **Barang Antik** dan **Perhiasan**.

#### Tambah Barang Antik

<img width="965" height="225" alt="image" src="https://github.com/user-attachments/assets/deae8bb0-f1ce-48ac-8ed3-8f4bfcc5f615" />

Sub-menu **Tambah Barang Antik** menangani alur masukan data secara berurutan untuk mencatat item baru ke dalam memori sistem. Proses diawali dengan percabangan opsi jenis barang (`1` untuk Barang Antik Umum dan `2` untuk Barang Perhiasan) yang menentukan skema instansiasi objek. Setelah tipe barang dipilih, program mengeksekusi instruksi input linier untuk membaca masukan string pada atribut nama barang dan asal negara, serta melakukan *parsing* numerik pada harga, stok, dan tahun pembuatan. Setiap variabel yang diinputkan akan divalidasi tipe datanya sebelum dimasukkan ke dalam *array* atau struktur data utama. Jika seluruh nilai atribut valid dan operasi penambahan data ke dalam sistem berhasil, program akan mencetak pesan konfirmasi `-> Data barang berhasil ditambahkan!` sebagai penanda akhir eksekusi fungsi.

#### Tambah Barang Perhiasan

<img width="971" height="227" alt="image" src="https://github.com/user-attachments/assets/ac5e7da9-7c9d-436e-acd3-a7042ca7c549" />

Sub-menu **Tambah Barang Perhiasan** bertugas menangani pencatatan item perhiasan ke dalam sistem melalui pemetaan atribut khusus. Ketika pengguna memilih opsi jenis barang `2` pada menu awal, program mengarahkan alur eksekusi untuk menginstansiasi objek khusus perhiasan. Selanjutnya, sistem secara sekuensial meminta masukan input teks untuk nama barang dan material, serta melakukan *parsing* data numerik untuk harga, stok barang, dan berat dalam satuan gram. Seluruh nilai variabel ini divalidasi dan disimpan ke dalam struktur data utama sistem. Setelah semua atribut berhasil dialokasikan, program memberikan respon umpan balik berupa pesan `-> Data barang berhasil ditambahkan!` sebagai penanda bahwa proses penyimpanan data perhiasan telah selesai dilakukan.

---

### 12.3 Tampilkan Semua Barang

Menampilkan seluruh barang yang tersimpan dalam sistem beserta informasi seperti ID, nama, jenis, harga, stok, dan atribut khusus barang.

<img width="1321" height="452" alt="image" src="https://github.com/user-attachments/assets/60ab0adf-2fcf-402b-b0ee-ba5d398ad710" />

Menu **Tampilkan Semua Barang** menangani proses pemanggilan dan penyajian seluruh koleksi data yang tersimpan di dalam memori sistem ke dalam bentuk tabel tabular CLI. Ketika pengguna memilih opsi `2`, program akan melakukan *traversal* atau iterasi terhadap seluruh objek inventaris dan memformat datanya agar sejajar secara rapi. Setiap baris data menampilkan atribut umum seperti ID, Nama Barang, Jenis, Harga, dan Stok, serta atribut spesifik berdasarkan kelas barang pada kolom Detail & Sertifikat Keaslian. Untuk kategori *Barang Antik*, sistem menampilkan rincian Asal Negara, Tahun Pembuatan, dan Nomor Sertifikat. Sedangkan untuk kategori *Perhiasan*, sistem secara dinamis menyajikan informasi Material (Mat), Berat dalam gram, dan Nomor Sertifikat.

---

### 12.4 Cari Barang

Menu **Cari Barang** bertugas menangani pencarian data inventaris berdasarkan metode pencarian yang dipilih oleh pengguna. Saat menu ini dipanggil, sistem menyediakan dua skema pencarian, yaitu pencarian berdasarkan ID (`1`) dan pencarian berdasarkan kata kunci nama (`2`). 

#### Cari Berdasarkan ID

<img width="1270" height="176" alt="image" src="https://github.com/user-attachments/assets/1764add1-fb22-41e6-9ad8-8f7132d99c9f" />

Pada metode pencarian berdasarkan ID, program melakukan *exact matching* atau pencarian presisi pada kunci utama (ID) objek.

#### Cari Berdasarkan Nama

<img width="1332" height="167" alt="image" src="https://github.com/user-attachments/assets/bdd11d4e-1dee-4912-b6a6-e4eb72c0b915" />

Sedangkan pada metode pencarian berdasarkan nama, program menjalankan pencarian kata kunci (*keyword matching*) yang memeriksa kecocokan string nama barang. Jika data yang dicari ditemukan di dalam sistem, program langsung menampilkan rincian barang tersebut lengkap dengan atribut khusus sesuai jenis kategorinya.

---

### 12.5 Update Barang

Menu **Update Barang** bertugas menangani pembaruan data inventaris yang telah tersimpan berdasarkan ID barang yang dipilih. Saat menu dijalankan, sistem terlebih dahulu menampilkan tabel daftar barang sebagai acuan navigasi pengguna. Setelah pengguna memasukkan ID target, program akan memuat nilai lama (*existing value*) untuk setiap atribut dan memberikan petunjuk pembaruan. Sistem menerapkan mekanisme penanganan input kondisional, di mana jika pengguna menekan tombol `ENTER` tanpa memasukkan teks, nilai lama barang akan tetap dipertahankan. Apabila pengguna mengetikkan input baru, sistem akan melakukan *parsing* serta pembaruan nilai atribut terkait dalam memori. Proses diakhiri dengan respon pesan konfirmasi `-> Barang berhasil diupdate.` sebagai penanda suksesnya pembaruan data.

<img width="1335" height="457" alt="image" src="https://github.com/user-attachments/assets/d25e5999-2fd5-4cc1-a6a2-25b0075640f8" />

---

### 12.6 Hapus Barang

Menu **Hapus Barang** bertugas menangani penghapusan entitas data dari struktur data memori berdasarkan ID barang yang ditentukan pengguna. Saat menu diakses, program terlebih dahulu menyajikan tabel daftar barang sebagai referensi visual ID. Sistem dilengkapi dengan mekanisme penanganan kesalahan input (*input error handling*); jika masukan ID bukan berupa angka bulat atau tidak valid, program akan memicu pesan peringatan `-> Input tidak valid! Harap masukkan angka bulat.` dan melakukan *re-prompt* hingga input yang sesuai diberikan. Setelah ID valid diterima, program menjalankan perintah konfirmasi dua arah (`y/n`) untuk memastikan keamanan data. Jika pengguna mengonfirmasi dengan memilih `y`, elemen barang terkait akan dihapus dari daftar dan sistem mengembalikan pesan respon `-> Barang berhasil dihapus.`.

<img width="1328" height="376" alt="image" src="https://github.com/user-attachments/assets/942f28c3-0b14-41a0-8d5a-7ca2eb810807" />

---

### 12.7 Keluar Program

Menu **Keluar Program** bertugas menghentikan seluruh eksekusi aplikasi dan memutus perulangan utama (*main loop*) sistem. Saat pengguna memilih opsi `6` pada prompt menu utama, program mengeksekusi perintah terminasi yang mencetak pesan penutup `Terima kasih telah menggunakan Monarch Antiqu'e System!` sebelum mengembalikan status kode keluar secara aman.

<img width="982" height="287" alt="image" src="https://github.com/user-attachments/assets/0e7c835b-823c-417d-ac65-b6886520dbda" />

---

# 13. Kesimpulan

Monarch Antiqu'e System merupakan aplikasi manajemen inventaris berbasis Java yang menerapkan konsep utama Pemrograman Berorientasi Objek.

Konsep yang diterapkan meliputi:

* **Encapsulation** melalui getter, setter, access modifier, dan validasi data.
* **Inheritance** melalui `Barang` sebagai superclass dan `BarangAntik` serta `BarangPerhiasan` sebagai subclass.
* **Polymorphism** melalui method overriding dan overloading.
* **Abstraction** melalui abstract class `Barang` dan abstract method.
* **MVC** melalui pemisahan package `model`, `view`, dan `controller`.
* **Interface** melalui `Authenticable` sebagai nilai tambah.

Seluruh fitur tersebut digunakan secara langsung dalam program untuk membangun sistem inventaris yang terstruktur dan menerapkan prinsip Pemrograman Berorientasi Objek.
