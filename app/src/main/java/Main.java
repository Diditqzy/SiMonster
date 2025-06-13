
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import data.ManajemenData;
import model.latihan.AktivitasHarian;
import model.latihan.FullBody;
import model.latihan.HariIstirahat;
import model.latihan.LowerBody;
import model.latihan.OtotKhusus;
import model.latihan.ProgramLatihan;
import model.latihan.SiklusMingguan;
import model.latihan.UpperBody;
import model.user.Pengguna;

public class Main {
    // Akan diisi dari file JSON oleh ManajemenData
    private static Map<String, Pengguna> basisDataPengguna;
    private static Pengguna penggunaSaatIni = null;

    public static void main(String[] args) {
        // 1. Memuat data dari file JSON saat aplikasi pertama kali dijalankan
        basisDataPengguna = ManajemenData.muatDataPengguna();
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("==============================================");
        System.out.println("   SELAMAT DATANG DI APLIKASI FITNES 270T");
        System.out.println("==============================================");
        
        while (true) {
            if (penggunaSaatIni == null) {
                tampilkanMenuAwal(scanner);
            } else {
                tampilkanMenuUtama(scanner);
            }
        }
    }

    private static void tampilkanMenuAwal(Scanner scanner) {
        System.out.println("\n--- HALAMAN PERTAMA ---");
        System.out.println("1. Login");
        System.out.println("2. Registrasi");
        System.out.println("3. Exit");
        System.out.print("Pilihan Anda: ");
        String pilihan = scanner.nextLine();
        
        switch (pilihan) {
            case "1": 
                prosesLogin(scanner); 
                break;
            case "2": 
                prosesRegistrasi(scanner); 
                break;
            case "3": 
                System.out.println("Menyimpan semua data progres...");
                ManajemenData.simpanDataPengguna(basisDataPengguna);
                System.out.println("Terima kasih. Sampai jumpa!"); 
                System.exit(0); 
                break;
            default: 
                System.out.println("Pilihan tidak valid."); 
                break;
        }
    }
    
    private static void prosesLogin(Scanner scanner) {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        Pengguna pengguna = basisDataPengguna.get(username);
        if (pengguna != null && pengguna.getPassword().equals(password)) {
            penggunaSaatIni = pengguna;
            penggunaSaatIni.login();
        } else {
            System.out.println("Login gagal! Username atau password salah.");
        }
    }

    private static void prosesRegistrasi(Scanner scanner) {
        System.out.println("\n--- REGISTRASI AKUN BARU ---");
        System.out.print("Buat username: ");
        String username = scanner.nextLine();

        if (basisDataPengguna.containsKey(username)) {
            System.out.println("Registrasi gagal! Username '" + username + "' sudah digunakan.");
            return;
        }

        System.out.print("Buat password (minimal 8 karakter, harus ada huruf dan angka): ");
        String password = scanner.nextLine();
        if (!isPasswordValid(password)) {
            System.out.println("Registrasi dibatalkan karena password tidak valid.");
            return;
        }

        Pengguna penggunaBaru = new Pengguna(username, password);
        System.out.print("Masukkan nama lengkap Anda: ");
        penggunaBaru.setNama(scanner.nextLine());

        basisDataPengguna.put(username, penggunaBaru);
        penggunaBaru.registrasi();

        ManajemenData.simpanDataPengguna(basisDataPengguna);
        System.out.println("Registrasi berhasil! Akun baru telah disimpan. Silakan login.");
    }

    private static void tampilkanMenuUtama(Scanner scanner) {
        SiklusMingguan siklusSaatIni = penggunaSaatIni.getSiklusSaatIni();
        System.out.println("\n--- MENU UTAMA ---");
        System.out.println("Halo, " + penggunaSaatIni.getNama() + " (Badge: " + penggunaSaatIni.getBadge() +")!");
        System.out.println("1. Tampilkan Profile");
        
        if (siklusSaatIni == null) {
            System.out.println("2. Buat Program Latihan");
        } else {
            String status = siklusSaatIni.isSelesai() ? "Selesai" : "Hari ke-" + (siklusSaatIni.getHariSaatIniIndex() + 1);
            System.out.println("2. Lanjutkan Program (" + status + ")");
        }
        
        System.out.println("3. Tampilkan Riwayat Latihan");
        System.out.println("4. Logout");
        System.out.println("5. Exit");
        System.out.print("Pilihan Anda: ");
        String pilihan = scanner.nextLine();
        
        switch (pilihan) {
            case "1": 
                penggunaSaatIni.tampilkanProfil(); 
                break;
            case "2":
                prosesMenuLatihan(scanner, siklusSaatIni);
                break;
            case "3": 
                penggunaSaatIni.getRiwayat().tampilkanSemuaRiwayat(); 
                break;
            case "4":
                System.out.println("Menyimpan progres Anda...");
                ManajemenData.simpanDataPengguna(basisDataPengguna);
                penggunaSaatIni = null; 
                System.out.println("Anda telah logout. Data disimpan."); 
                break;
            case "5":
                System.out.println("Menyimpan progres Anda sebelum keluar...");
                ManajemenData.simpanDataPengguna(basisDataPengguna);
                penggunaSaatIni.keluarAplikasi(); 
                System.exit(0); 
                break;
            default: 
                System.out.println("Pilihan tidak valid."); 
                break;
        }
    }
    
    // =========================================================================
    // === METODE prosesMenuLatihan DIUBAH UNTUK MEMANGGIL LOGIKA BADGE ===
    // =========================================================================
    private static void prosesMenuLatihan(Scanner scanner, SiklusMingguan siklusSaatIni) {
        if (siklusSaatIni == null) {
            ProgramLatihan programPilihan = buatProgramBaru(scanner);
            if (programPilihan != null) {
                siklusSaatIni = programPilihan.getSiklusMingguan();
                penggunaSaatIni.setSiklusSaatIni(siklusSaatIni);
                System.out.println("\nProgram latihan '" + programPilihan.getNamaProgram() + "' telah berhasil dibuat!");
                tampilkanHalamanJadwal(scanner, true, siklusSaatIni);
                siklusSaatIni.jalankanAktivitasHariIni(penggunaSaatIni, scanner);
                siklusSaatIni.majuKeHariBerikutnya();
            }
        } else {
            if (siklusSaatIni.isSelesai()) {
                // Panggil metode untuk memberikan badge baru
                prosesPemberianBadge(penggunaSaatIni, siklusSaatIni);
                
                System.out.println("Program latihan telah direset. Buat program baru untuk tantangan berikutnya!");
                penggunaSaatIni.setSiklusSaatIni(null); // Reset program
                return;
            }

            AktivitasHarian aktivitasHariIni = siklusSaatIni.getHariSaatIni();
            if (aktivitasHariIni instanceof HariIstirahat) {
                System.out.print("\nIni adalah hari istirahat (kuis). Mau mengerjakan? (y/n): ");
                if (scanner.nextLine().equalsIgnoreCase("n")) {
                    System.out.println("Hari istirahat dilewati. Kembali ke menu utama.");
                    siklusSaatIni.majuKeHariBerikutnya();
                    return;
                }
            }
            
            tampilkanHalamanJadwal(scanner, false, siklusSaatIni);
            siklusSaatIni.jalankanAktivitasHariIni(penggunaSaatIni, scanner);
            siklusSaatIni.majuKeHariBerikutnya();
        }
    }

    // =========================================================================
    // === METODE BARU UNTUK MEMBERIKAN BADGE SETELAH PROGRAM SELESAI ===
    // =========================================================================
    private static void prosesPemberianBadge(Pengguna pengguna, SiklusMingguan siklusSelesai) {
        String namaProgram = siklusSelesai.getNamaProgram();
        String badgeBaru = null;

        if (namaProgram.startsWith("Otot Khusus")) {
            if (namaProgram.contains("Dada")) badgeBaru = "Benteng Perkasa";
            else if (namaProgram.contains("Punggung")) badgeBaru = "Sayap Garuda";
            else if (namaProgram.contains("Bisep")) badgeBaru = "Popeye";
            else if (namaProgram.contains("Trisep")) badgeBaru = "Tapal Kuda";
            else if (namaProgram.contains("Bahu")) badgeBaru = "Bahu Gatotkaca";
            else if (namaProgram.contains("Perut")) badgeBaru = "Roti Sobek";
            else if (namaProgram.contains("Kaki")) badgeBaru = "Kaki Gempa";
            else if (namaProgram.contains("Lengan")) badgeBaru = "Lengan Meriam";
        } else if (namaProgram.equals("Full Body")) {
            badgeBaru = "TITAN";
        } else if (namaProgram.equals("Upper Body")) {
            badgeBaru = "Puncak Herkules";
        } else if (namaProgram.equals("Lower Body")) {
            badgeBaru = "Pondasi Tembok China";
        }

        if (badgeBaru != null) {
            pengguna.setBadge(badgeBaru);
            System.out.println("\n=======================================================");
            System.out.println("           S E L A M A T! PROGRAM SELESAI!");
            System.out.println("Anda telah menyelesaikan program: " + namaProgram);
            System.out.println("Anda mendapatkan badge baru: \"" + badgeBaru + "\"!");
            System.out.println("=======================================================");
            
            // Berikan bonus EXP karena telah menyelesaikan program
            pengguna.addExp(250); 
        } else {
             System.out.println("\nSelamat, Anda telah menyelesaikan program " + namaProgram + "!");
        }
    }
    
    private static ProgramLatihan buatProgramBaru(Scanner scanner) {
        System.out.println("\n--- BUAT PROGRAM BARU ---");
        System.out.println("1. Otot Khusus");
        System.out.println("2. Upper Body Program");
        System.out.println("3. Lower Body Program");
        System.out.println("4. Full Body Program");
        System.out.println("5. Kembali");
        System.out.print("Pilihan Anda: ");
        String pilihan = scanner.nextLine();
        
        switch (pilihan) {
            case "1": return pilihOtotKhusus(scanner);
            case "2": return new UpperBody();
            case "3": return new LowerBody();
            case "4": return new FullBody();
            case "5": return null;
            default: 
                System.out.println("Pilihan tidak valid."); 
                return null;
        }
    }
    
    private static ProgramLatihan pilihOtotKhusus(Scanner scanner) {
        System.out.println("\n--- PILIH OTOT ---");
        System.out.println("1. Dada\n2. Punggung\n3. Bisep\n4. Trisep\n5. Bahu\n6. Perut\n7. Kaki\n8. Lengan\n9. Kembali");
        System.out.print("Pilihan Anda: ");
        String pilihanOtot = scanner.nextLine();

        switch (pilihanOtot) {
            case "1": return new OtotKhusus("Dada");
            case "2": return new OtotKhusus("Punggung");
            case "3": return new OtotKhusus("Bisep");
            case "4": return new OtotKhusus("Trisep");
            case "5": return new OtotKhusus("Bahu");
            case "6": return new OtotKhusus("Perut");
            case "7": return new OtotKhusus("Kaki");
            case "8": return new OtotKhusus("Lengan");
            case "9": return null;
            default: 
                System.out.println("Pilihan tidak valid."); 
                return null;
        }
    }

    private static void tampilkanHalamanJadwal(Scanner scanner, boolean isProgramBaru, SiklusMingguan siklus) {
        System.out.println("\n--- JADWAL HARIAN ANDA ---");
        System.out.println("HARI 1: Bertarung | HARI 2: Istirahat | HARI 3: Bertarung | HARI 4: Istirahat");
        System.out.println("HARI 5: Bertarung | HARI 6: Istirahat | HARI 7: Bertarung");
        System.out.println("-----------------------------------------------------------------------------");

        String prompt = isProgramBaru
            ? "Tekan Enter untuk memulai HARI 1..."
            : "Tekan Enter untuk melanjutkan ke HARI " + (siklus.getHariSaatIniIndex() + 1) + "...";
        System.out.print(prompt);
        scanner.nextLine();
    }
    
    private static boolean isPasswordValid(String password) {
        if (password == null || password.length() < 8) {
            System.out.println("Password gagal! Minimal harus 8 karakter.");
            return false;
        }
        boolean hasLetter = Pattern.compile(".*[a-zA-Z].*").matcher(password).matches();
        boolean hasDigit = Pattern.compile(".*[0-9].*").matcher(password).matches();
        
        if (!hasLetter) {
            System.out.println("Password gagal! Harus mengandung setidaknya satu huruf.");
            return false;
        }
        if (!hasDigit) {
            System.out.println("Password gagal! Harus mengandung setidaknya satu angka.");
            return false;
        }
        return true;
    }
}