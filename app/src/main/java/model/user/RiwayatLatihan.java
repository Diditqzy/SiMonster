package model.user;

import java.util.ArrayList;
import java.util.List;

public class RiwayatLatihan {
    private List<CatatanLatihan> daftarCatatan;

    public RiwayatLatihan() {
        this.daftarCatatan = new ArrayList<>();
    }

    public List<CatatanLatihan> getDaftarCatatan() {
        return this.daftarCatatan;
    }

    public void tambahkanCatatan(CatatanLatihan catatan) {
        if (this.daftarCatatan == null) {
            this.daftarCatatan = new ArrayList<>();
        }
        this.daftarCatatan.add(catatan);
    }

    public void tampilkanSemuaRiwayat() {
        System.out.println("\n--- RIWAYAT LATIHAN ---");
        if (daftarCatatan == null || daftarCatatan.isEmpty()) {
            System.out.println("Belum ada riwayat latihan yang tercatat.");
        } else {
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
            // Header baru dengan tambahan kolom "Tanggal"
            System.out.printf("| %-10s | %-12s | %-60s | %-9s | %s\n", "Nama", "Tanggal", "Latihan yang Diselesaikan", "Hari", "Program");  
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
            for (CatatanLatihan catatan : daftarCatatan) {
                catatan.tampilkan();
            }
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------");
        }
    }
}