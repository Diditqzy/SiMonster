package model.soal;

import java.util.List;
import java.util.Scanner;

public class SesiSoal {
    private List<Soal> daftarSoal;
    private int skor;
    // 'transient' berarti "jangan simpan field ini ke dalam JSON"
    private transient Scanner scanner; 

    public SesiSoal(List<Soal> soalList, Scanner scanner) {
        this.daftarSoal = soalList;
        this.skor = 0;
        this.scanner = scanner;
    }

    public int getSkor() { return skor; }

    public void mulaiSesi() {
        System.out.println("\n--- SESI KUIS HARI ISTIRAHAT ---");
        for (int i = 0; i < daftarSoal.size(); i++) {
            Soal soal = daftarSoal.get(i);
            System.out.println("\nSoal " + (i + 1) + ": " + soal.getPertanyaan());
            char opsi = 'a';
            for (String p : soal.getPilihanJawaban()) {
                System.out.println(opsi + ". " + p);
                opsi++;
            }
            System.out.print("Jawaban Anda (a/b/c): ");
            String jawabanPengguna = scanner.nextLine();
            if (soal.cekJawaban(jawabanPengguna)) {
                System.out.println("Benar!");
                skor++;
            } else {
                System.out.println("Salah. Jawaban yang benar adalah: " + soal.getJawabanBenar());
            }
        }
        tampilkanHasil();
    }

    public void tampilkanHasil() {
        System.out.println("\n--- Hasil Kuis ---");
        System.out.println("Skor Anda: " + skor + " dari " + daftarSoal.size());
    }
}