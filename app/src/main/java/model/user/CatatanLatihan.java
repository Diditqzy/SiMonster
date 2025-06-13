package model.user;


import java.text.SimpleDateFormat;
import java.util.Date;

public class CatatanLatihan {
    // DIUBAH: Kata kunci 'final' dihapus agar bisa di-set saat memuat data
    private Date tanggal;
    private final String namaPengguna;
    private final String daftarLatihan;
    private final int hariKe;
    private final String namaProgram;

    public CatatanLatihan(String namaPengguna, String daftarLatihan, int hariKe, String namaProgram) {
        // Saat catatan baru dibuat, tanggalnya adalah tanggal saat ini
        this.tanggal = new Date(); 
        this.namaPengguna = namaPengguna;
        this.daftarLatihan = daftarLatihan;
        this.hariKe = hariKe;
        this.namaProgram = namaProgram;
    }

    // Getter tidak berubah
    public Date getTanggal() { return tanggal; }
    public String getNamaPengguna() { return namaPengguna; }
    public String getDaftarLatihan() { return daftarLatihan; }
    public int getHariKe() { return hariKe; }
    public String getNamaProgram() { return namaProgram; }

    // DITAMBAHKAN: Metode setter untuk tanggal, digunakan saat memuat dari file
    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public void tampilkan() {
        // 1. Buat objek formatter dengan pola "dd-MM-yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        // 2. Ubah objek Date menjadi String yang sudah diformat
        String tanggalFormatted = sdf.format(this.tanggal);

        // 3. Gunakan String tanggal yang sudah diformat di dalam hasil cetak
        System.out.printf("| %-10s | %-12s | %-60s | Hari ke-%-2d | %s\n",
            namaPengguna, tanggalFormatted, daftarLatihan, hariKe, namaProgram);
    }
}