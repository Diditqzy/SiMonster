# 👹 SiMonster: Gamifikasi Petualangan Kebugaran Anda

*SiMonster* bukan sekadar aplikasi fitness biasa. Ini adalah sebuah platform berbasis Java yang dirancang untuk mengubah rutinitas olahraga yang monoton menjadi sebuah petualangan yang progresif, termotivasi, dan menyenangkan. Dengan memadukan prinsip-prinsip gamifikasi ke dalam latihan, kami bertujuan untuk memecahkan masalah umum dalam kebugaran: *kehilangan motivasi dan konsistensi*.

Nama *"SiMonster"* merepresentasikan kekuatan monster dalam diri setiap pengguna yang akan terus "tumbuh" dan berevolusi menjadi lebih kuat. Setiap repetisi yang Anda selesaikan, setiap level yang Anda naiki, adalah langkah untuk membangun "monster" terkuat versi Anda.

---

## ✨ Fitur Unggulan

Aplikasi ini dibangun di atas beberapa fitur inti yang membedakannya, menciptakan sebuah ekosistem kebugaran yang adiktif dan efektif.

* *Sistem Progresi Gamifikasi:*
    * *EXP & Level:* Setiap aktivitas yang diselesaikan (latihan atau kuis) memberikan Experience Points (EXP). Kumpulkan EXP untuk naik Level, yang secara visual menunjukkan kemajuan dan dedikasi Anda. Sistem ini memberikan feedback instan atas usaha yang Anda lakukan.
    * *Sistem Badge Prestisius:* Selesaikan sebuah program latihan 7 hari untuk membuka *Badge*—sebuah trofi digital yang unik dan membanggakan. Setiap program memberikan badge dengan nama-nama yang kreatif (seperti "Pondasi Tembok China" atau "Sayap Garuda"), memberikan tujuan jangka pendek yang jelas dan rasa pencapaian yang memuaskan.

* *Program Latihan Adaptif & Terstruktur:*
    * Pengguna memiliki kebebasan untuk memilih program yang sesuai dengan target kebugaran mereka, mulai dari *Full Body* untuk latihan menyeluruh, *Upper/Lower Body* untuk fokus pada bagian tubuh tertentu, hingga program *Otot Khusus* yang sangat spesifik (misal: Dada, Punggung, Kaki).
    * Setiap program diatur dalam *Siklus Mingguan* yang logis, memadukan "Hari Bertarung" (latihan intens) dengan "Hari Istirahat" yang produktif, memastikan tubuh mendapatkan waktu pemulihan yang cukup.

* *Hari Istirahat Interaktif:*
    * Kami mengubah hari istirahat yang pasif menjadi kesempatan untuk belajar. Pengguna akan dihadapkan dengan *Kuis Interaktif* seputar dunia kebugaran. Menjawab dengan benar tidak hanya menambah wawasan, tetapi juga memberikan bonus EXP. Ini memastikan pengguna tetap terlibat dengan aplikasi bahkan saat tidak berolahraga.

* *Antarmuka yang Modern dan Responsif:*
    * Dibangun dengan JavaFX, antarmuka pengguna (GUI) dirancang agar modern, bersih, dan intuitif. Desainnya responsif, beradaptasi dengan baik saat ukuran jendela diubah, dan dipercantik dengan animasi yang halus untuk memberikan pengalaman pengguna yang lebih hidup.

* *Penyimpanan Data Otomatis (Persistensi):*
    * Tidak ada progres yang terbuang. Seluruh data pengguna—termasuk level, EXP, badge, program aktif, dan semua riwayat latihan—disimpan secara otomatis dalam file pengguna.json. Saat aplikasi dijalankan kembali, pengguna dapat melanjutkan petualangan mereka tepat di mana mereka berhenti.

---

## 🏗 Arsitektur Perangkat Lunak & Prinsip Desain

Arsitektur proyek SIMONSTER dirancang dengan cermat menggunakan prinsip-prinsip *Object-Oriented Programming (OOP)* untuk memastikan kode yang dihasilkan *modular, mudah dipelihara (maintainable), dan mudah dikembangkan (scalable)*.

### *Mengapa Struktur Ini Dipilih?*
Struktur ini menerapkan pola desain *Model-View-Controller (MVC)*, yang memisahkan antara data, tampilan, dan logika kontrol:
* *Model:* Merepresentasikan data dan logika bisnis inti (semua kelas di package model).
* *View:* Menampilkan data kepada pengguna (semua file FXML dan CSS di resources).
* *Controller:* Menjadi jembatan antara Model dan View (semua kelas Controller di ui.gui).

Pemisahan ini memungkinkan kami untuk mengubah satu bagian (misalnya, mengganti seluruh tampilan/GUI) tanpa harus merusak logika bisnis yang sudah solid.

### *Penerapan Konsep OOP:*

1.  *Encapsulation (Enkapsulasi)*
    * *Contoh:* Kelas Pengguna, Latihan, dan Soal adalah contoh sempurna. Semua atributnya bersifat private. Akses ke data ini hanya bisa dilakukan melalui metode publik (public method) seperti getNama(), addExp(), atau getSoal().
    * *Mengapa ini penting?* Ini melindungi integritas data. Misalnya, EXP tidak bisa diubah sembarangan; ia hanya bisa ditambah melalui metode addExp(), yang juga langsung memicu pengecekan levelUp().

2.  *Inheritance (Pewarisan)*
    * *Contoh:* ProgramLatihan adalah sebuah abstract class yang menjadi "induk" bagi kelas-kelas program spesifik seperti FullBody, UpperBody, LowerBody, dan OtotKhusus. Semua kelas anak ini "mewarisi" properti dan perilaku dasar dari ProgramLatihan.
    * *Mengapa ini penting?* Ini menghindari duplikasi kode dan menciptakan hierarki yang logis. Menambah program baru semudah membuat kelas baru yang mewarisi ProgramLatihan.

3.  *Polymorphism (Polimorfisme)*
    * *Contoh Terbaik:* Interface AktivitasHarian adalah jantung dari polimorfisme di aplikasi ini. Ia diimplementasikan oleh dua kelas yang sangat berbeda: HariBertarung dan HariIstirahat.
    * *Mengapa ini penting?* Kelas SiklusMingguan dapat menyimpan daftar AktivitasHarian tanpa perlu tahu jenis aktivitas spesifiknya. Saat metode lakukanAktivitas() dipanggil, Java secara otomatis akan menjalankan versi metode yang benar sesuai dengan objeknya. Ini membuat kode sangat bersih dan fleksibel.

4.  *Abstraction (Abstraksi)*
    * *Contoh:* MainMenuController tidak perlu tahu bagaimana ManajemenData.simpanDataPengguna() mengubah data menjadi format JSON. Ia hanya perlu memanggil metode tersebut, dan semua detail implementasi yang rumit disembunyikan.
    * *Mengapa ini penting?* Abstraksi menyederhanakan interaksi antar komponen, memungkinkan pengembang untuk fokus pada satu tugas pada satu waktu.


### Struktur Kelas dan Relasinya
'''
Berikut adalah visualisasi struktur package dan kelas utama dalam proyek ini:

SiMonster/
└── src/main/
├── java/
│   ├── data/
│   │   └── ManajemenData.java        # Mengelola simpan/muat data ke file JSON.
│   ├── model/
│   │   ├── latihan/
│   │   │   ├── ProgramLatihan.java   # Abstract class untuk semua program.
│   │   │   ├── FullBody.java       # Turunan ProgramLatihan.
│   │   │   ├── UpperBody.java      # Turunan ProgramLatihan.
│   │   │   ├── LowerBody.java      # Turunan ProgramLatihan.
│   │   │   ├── OtotKhusus.java     # Turunan ProgramLatihan.
│   │   │   ├── SiklusMingguan.java   # Mengelola jadwal 7 hari.
│   │   │   ├── AktivitasHarian.java  # Interface untuk aktivitas harian.
│   │   │   ├── HariBertarung.java    # Implementasi AktivitasHarian.
│   │   │   ├── HariIstirahat.java    # Implementasi AktivitasHarian.
│   │   │   └── Latihan.java          # Entitas untuk satu jenis latihan.
│   │   ├── soal/
│   │   │   ├── BankSoal.java         # Penyedia soal untuk kuis.
│   │   │   ├── SesiSoal.java         # Mengelola sesi kuis (versi konsol).
│   │   │   └── Soal.java             # Entitas untuk satu soal.
│   │   └── user/
│   │       ├── Pengguna.java         # Entitas utama pengguna.
│   │       ├── CatatanLatihan.java   # Entitas untuk satu catatan riwayat.
│   │       └── RiwayatLatihan.java   # Mengelola kumpulan catatan riwayat.
│   └── ui/
│       └── gui/
│           ├── MainApp.java          # Titik masuk aplikasi JavaFX.
│           ├── LoginController.java    # Logika untuk layar login.
│           ├── MainMenuController.java # Logika untuk dashboard utama.
│           ├── RegisterController.java # Logika untuk layar registrasi.
│           └── QuizDialog.java       # Logika untuk pop-up kuis.
└── resources/
├── fxml/                         # File desain antarmuka.
├── styles/                       # File CSS untuk styling.
└── pengguna.json                 # File database.
'''

## 🚀 Teknologi & Kebutuhan

* *Bahasa Pemrograman:* Java
* *Framework GUI:* JavaFX
* *Build Tool:* Gradle
* *Penyimpanan Data:* File JSON (di-handle secara manual)
* *Java Development Kit (JDK):* Versi 17 atau lebih tinggi direkomendasikan.

---

## 🛣 Alur Penggunaan Aplikasi

1.  *Halaman Awal:* Saat aplikasi dijalankan, Anda akan disambut dengan pilihan *Login, **Registrasi, atau **Exit*.
2.  *Registrasi & Login:* Pengguna baru harus *Registrasi* terlebih dahulu. Jika sudah punya akun, pilih *Login*.
3.  *Menu Utama:* Setelah login, Anda akan melihat menu utama yang berisi:
    * *Dashboard:* Halaman sambutan utama yang dinamis.
    * *Program Latihan:* Untuk memulai program latihan baru atau melanjutkan progres yang sudah ada.
    * *Riwayat Latihan:* Melihat catatan semua latihan yang pernah diselesaikan dalam format kartu yang menarik.
    * *Logout*.
4.  *Sesi Latihan:* Ikuti instruksi pada setiap "Hari Bertarung" untuk melakukan latihan dan "Hari Istirahat" untuk mengerjakan kuis interaktif.
5.  *Dapatkan Badge:* Selesaikan seluruh siklus 7 hari untuk mendapatkan badge penghargaan dan bonus EXP, lalu pilih program baru untuk tantangan berikutnya!

---

## 🦹‍♂😡😎 Kelompok 4

* *Akhmad Hidayat* H071241003
* *Didit Iqbal Alfaruzy* H071241032
* *Isnadia Nurfadillah* H071241052
