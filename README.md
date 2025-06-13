# 👹SiMonster
SiMonster adalah aplikasi kebugaran (fitness) berbasis java yang dirancang untuk mengubah cara pengguna berolahraga menjadi sebuah petualangan yang progresif dan termotivasi. Aplikasi ini memandu pengguna melalui berbagai program latihan yang terstruktur dalam siklus mingguan, lengkap dengan sistem gamifikasi untuk menjaga semangat dan memantau kemajuan.

Nama "SiMonster" merepresentasikan kekuatan dalam diri pengguna yang akan terus "tumbuh" dan menjadi lebih kuat seiring dengan progres latihan yang diselesaikan. Tujuan utamanya adalah membuat aktivitas kebugaran menjadi lebih menarik, terukur, dan konsisten.
--
## 💾Fitur Utama

- *Sistem Pengguna:* Registrasi dan Login untuk menyimpan progres secara personal.
- *Beragam Program Latihan:* Pilih program sesuai target (Full Body, Upper Body, Lower Body, dan Otot Khusus).
- *Siklus Latihan Terstruktur:* Jadwal latihan 7 hari yang terdiri dari "Hari Bertarung" dan "Hari Istirahat".
- *Sistem Gamifikasi:* Dapatkan *EXP* untuk naik *Level, dan kumpulkan **Badge* unik setelah menyelesaikan program.
- *Kuis Interaktif:* Uji pengetahuan kebugaran Anda pada hari istirahat untuk mendapatkan EXP tambahan.
- *Pelacakan Riwayat:* Semua aktivitas latihan tercatat rapi, lengkap dengan tanggal dan detail latihan.
- *Penyimpanan Data:* Seluruh progres (level, EXP, badge, riwayat) disimpan secara otomatis dalam file pengguna.json.

--
## Teknologi yang Digunakan

- *Bahasa Pemrograman:* Java
- *Penyimpanan Data:* Format file JSON (di-handle secara manual tanpa library eksternal).

--
## Kebutuhan Sistem

- *Java Development Kit (JDK):* Versi 8 atau yang lebih baru.
- *IDE (Opsional):* Seperti Visual Studio Code, IntelliJ IDEA, atau Eclipse untuk mempermudah proses kompilasi dan eksekusi.

---
## 🛣Alur Penggunaan Aplikasi

1.  *Halaman Awal:* Saat aplikasi dijalankan, Anda akan disambut dengan pilihan *Login, **Registrasi, atau **Exit*.
2.  *Registrasi & Login:* Pengguna baru harus *Registrasi* terlebih dahulu. Jika sudah punya akun, pilih *Login*.
3.  *Menu Utama:* Setelah login, Anda akan melihat menu utama yang berisi:
    - *Tampilkan Profile:* Untuk melihat Level, EXP, dan Badge yang Anda miliki.
    - *Buat/Lanjutkan Program:* Untuk memulai program latihan baru atau melanjutkan progres yang sudah ada.
    - *Tampilkan Riwayat:* Melihat catatan semua latihan yang pernah diselesaikan.
    - *Logout & Exit*.
4.  *Sesi Latihan:* Ikuti instruksi pada setiap "Hari Bertarung" untuk melakukan latihan dan "Hari Istirahat" untuk mengerjakan kuis.
5.  *Dapatkan Badge:* Selesaikan seluruh siklus 7 hari untuk mendapatkan badge penghargaan dan bonus EXP, lalu pilih program baru untuk tantangan berikutnya!

---

## 📝Struktur Proyek

Berikut adalah penjelasan singkat mengenai file-file utama dalam proyek ini:

- Main.java: Titik masuk utama aplikasi dan pengontrol alur program.
- Pengguna.java: Blueprint (kelas) yang merepresentasikan data dan profil pengguna.
- ManajemenData.java: Bertanggung jawab untuk menyimpan dan memuat data pengguna dari/ke file pengguna.json.
- ProgramLatihan.java (dan turunannya): Mendefinisikan berbagai jenis program latihan yang tersedia.
- SiklusMingguan.java: Mengelola jadwal dan progres latihan mingguan.
- AktivitasHarian.java (dan turunannya): Mendefinisikan aktivitas untuk setiap hari (latihan atau istirahat).
- BankSoal.java & Soal.java: Mengelola semua pertanyaan dan jawaban untuk sesi kuis.
- pengguna.json: File database untuk menyimpan semua data pengguna.

--
##🦹‍♂😡😎Kelompok 4
*Akhmad Hidayat* H071241003
*Didit Iqbal Alfaruzy* H071241032
*Isnadia Nurfadillah* H071241052