package model.user;

import model.latihan.FullBody;
import model.latihan.LowerBody;
import model.latihan.OtotKhusus;
import model.latihan.ProgramLatihan;
import model.latihan.SiklusMingguan;
import model.latihan.UpperBody;

public class Pengguna implements AktivitasPengguna {
    private String username;
    private String password;
    private String nama;
    private int exp;
    private int level;
    private String badge;
    private RiwayatLatihan riwayat;
    private SiklusMingguan siklusSaatIni;

    public Pengguna(String username, String password) {
        this.username = username;
        this.password = password;
        this.nama = username;
        this.exp = 0;
        this.level = 1;
        this.badge = "Pemula";
        this.riwayat = new RiwayatLatihan();
        this.siklusSaatIni = null;
    }
    
    // ... (Semua getter dan setter lainnya tidak berubah) ...

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getNama() { return nama; }
    public int getLevel() { return level; }
    public int getExp() { return exp; }
    public String getBadge() { return badge; }
    public RiwayatLatihan getRiwayat() { return riwayat; }
    public SiklusMingguan getSiklusSaatIni() { return siklusSaatIni; }
    public void setSiklusSaatIni(SiklusMingguan siklus) { this.siklusSaatIni = siklus; }
    public void setNama(String nama) { this.nama = nama; }
    public void setLevel(int level) { this.level = level; }
    public void setExp(int exp) { this.exp = exp; }
    public void setBadge(String badge) { this.badge = badge; }
    
    public void addExp(int jumlah) {
        this.exp += jumlah;
        System.out.println("\nSelamat, Anda mendapatkan " + jumlah + " EXP!");
        checkLevelUp();
    }

    public void checkLevelUp() {
        int expDibutuhkan = level * 100;
        if (this.exp >= expDibutuhkan) {
            this.level++;
            this.exp -= expDibutuhkan;
            System.out.println(">>> LEVEL UP! Anda sekarang Level " + this.level + " <<<");
        }
    }

    public void tampilkanProfil() {
        System.out.println("\n--- PROFIL PENGGUNA ---");
        System.out.println("Nama   : " + getNama());
        System.out.println("Level  : " + this.level);
        System.out.println("EXP    : " + this.exp + " / " + (this.level * 100));
        System.out.println("Badge  : " + this.badge);
        System.out.println("-----------------------");
    }

    /**
     * DITAMBAHKAN: Method ini merekonstruksi siklus latihan dari data yang disimpan.
     * @param namaProgram Nama program yang disimpan
     * @param hariKe Hari ke berapa program tersebut
     */
    public void aturSiklusFromSave(String namaProgram, int hariKe) {
        ProgramLatihan program = null;
        if (namaProgram.startsWith("Otot Khusus")) {
            String targetOtot = namaProgram.substring(namaProgram.indexOf('(') + 1, namaProgram.indexOf(')'));
            program = new OtotKhusus(targetOtot);
        } else if (namaProgram.equals("Upper Body")) {
            program = new UpperBody();
        } else if (namaProgram.equals("Lower Body")) {
            program = new LowerBody();
        } else if (namaProgram.equals("Full Body")) {
            program = new FullBody();
        }

        if (program != null) {
            SiklusMingguan siklus = program.getSiklusMingguan();
            siklus.setHariSaatIniIndex(hariKe); // Butuh setter di SiklusMingguan
            this.setSiklusSaatIni(siklus);
        }
    }

    @Override public void registrasi() { System.out.println("Pengguna '" + username + "' berhasil diregistrasi."); }
    @Override public void login() { System.out.println("Pengguna '" + username + "' berhasil login."); }
    @Override public void keluarAplikasi() { System.out.println("Terima kasih, " + nama + "! Sampai jumpa lagi."); }
}