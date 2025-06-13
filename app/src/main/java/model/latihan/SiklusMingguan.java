package model.latihan;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.user.Pengguna;

public class SiklusMingguan {
    private List<AktivitasHarian> jadwalHarian;
    private int hariSaatIniIndex;
    private String namaProgram; // DITAMBAHKAN

    public SiklusMingguan() {
        this.jadwalHarian = new ArrayList<>();
        this.hariSaatIniIndex = 0;
    }

    // UBAH konstruktor pada SEMUA kelas ProgramLatihan (UpperBody, LowerBody, dll)
    // untuk memanggil metode ini setelah membuat objek SiklusMingguan.
    // Contoh di UpperBody.java:
    // SiklusMingguan s = new SiklusMingguan();
    // s.setNamaProgram(this.getNamaProgram());
    public void setNamaProgram(String namaProgram) {
        this.namaProgram = namaProgram;
    }

    public String getNamaProgram() {
        // Ambil dari salah satu jadwal jika belum di-set
        if (this.namaProgram == null && !jadwalHarian.isEmpty()) {
            AktivitasHarian aktivitas = jadwalHarian.get(0);
            if (aktivitas instanceof HariBertarung) {
                // Ini asumsi, perlu cara yang lebih baik untuk mendapatkan nama program
            }
        }
        return this.namaProgram;
    }

    public void aturJadwalMingguan(List<AktivitasHarian> jadwal) {
        this.jadwalHarian = jadwal;
    }
    
    public void jalankanAktivitasHariIni(Pengguna pengguna, Scanner scanner) {
        if (!isSelesai()) {
            getHariSaatIni().lakukanAktivitas(pengguna, scanner);
        } else {
            System.out.println("Siklus mingguan telah selesai!");
        }
    }

    public void majuKeHariBerikutnya() {
        if (!isSelesai()) {
            this.hariSaatIniIndex++;
        }
    }

    public AktivitasHarian getHariSaatIni() {
        return jadwalHarian.get(hariSaatIniIndex);
    }

    public int getHariSaatIniIndex() {
        return hariSaatIniIndex;
    }
    
    // DITAMBAHKAN: Setter untuk mengatur hari saat memuat data
    public void setHariSaatIniIndex(int hariKe) {
        if (hariKe >= 0 && hariKe < jadwalHarian.size()) {
            this.hariSaatIniIndex = hariKe;
        }
    }
    
    public boolean isSelesai() {
        return hariSaatIniIndex >= jadwalHarian.size();
    }
}