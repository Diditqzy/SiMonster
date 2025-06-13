package model.soal;
import java.util.List;

public class Soal {
    private String pertanyaan;
    private List<String> pilihanJawaban;
    private String jawabanBenar;
    private String kategoriProgram;
    private int tingkatKesulitan;

    public Soal(String pertanyaan, List<String> pilihanJawaban, String jawabanBenar, String kategoriProgram, int tingkatKesulitan) {
        this.pertanyaan = pertanyaan;
        this.pilihanJawaban = pilihanJawaban;
        this.jawabanBenar = jawabanBenar;
        this.kategoriProgram = kategoriProgram;
        this.tingkatKesulitan = tingkatKesulitan;
    }

    public String getPertanyaan() { return pertanyaan; }
    public List<String> getPilihanJawaban() { return pilihanJawaban; }
    public String getJawabanBenar() { return jawabanBenar; }
    public String getKategoriProgram() { return kategoriProgram; }
    public int getTingkatKesulitan() { return tingkatKesulitan; }

    public boolean cekJawaban(String jawabanPengguna) {
        return jawabanPengguna.trim().equalsIgnoreCase(this.jawabanBenar);
    }
}