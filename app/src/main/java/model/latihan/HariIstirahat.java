package model.latihan;
import java.util.List;
import java.util.Scanner;

import model.soal.BankSoal;
import model.soal.SesiSoal;
import model.soal.Soal;
import model.user.Pengguna;

public class HariIstirahat implements AktivitasHarian {
    private int hariKe;
    private String kategoriProgram;

    public HariIstirahat(int hariKe, String kategoriProgram) {
        this.hariKe = hariKe;
        this.kategoriProgram = kategoriProgram;
    }

    @Override
    public void lakukanAktivitas(Pengguna pengguna, Scanner scanner) {
        System.out.println("\n--- HARI KE-" + hariKe + ": HARI ISTIRAHAT ---");
        System.out.println("Saatnya istirahat dan uji pemahamanmu!");

        int tingkatKesulitan = (hariKe / 2);
        List<Soal> soalUntukKuis = BankSoal.getSoal(kategoriProgram, tingkatKesulitan, 2);

        if (soalUntukKuis.isEmpty() || soalUntukKuis.size() < 2) {
            System.out.println("Tidak ada kuis yang tersedia untuk hari ini. Selamat beristirahat!");
            pengguna.addExp(10);
            return;
        }

        SesiSoal sesiKuis = new SesiSoal(soalUntukKuis, scanner);
        sesiKuis.mulaiSesi();
        pengguna.addExp(sesiKuis.getSkor() * 15);
    }
}