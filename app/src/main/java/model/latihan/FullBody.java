package model.latihan;
import java.util.Arrays;
import java.util.List;

public class FullBody extends ProgramLatihan {
    public FullBody() {
        super("Full Body");
    }

    @Override
    public SiklusMingguan getSiklusMingguan() {
        SiklusMingguan s = new SiklusMingguan();
        String namaProgram = this.getNamaProgram();
        s.setNamaProgram(namaProgram);

        // Data Latihan untuk Full Body Program
        List<Latihan> d1 = Arrays.asList(
            new Latihan("Burpees", 4, "12", "hard", 10),
            new Latihan("Pull Up", 3, "8", "hard", 10),
            new Latihan("Kettlebell Swing", 3, "15", "medium", 8),
            new Latihan("Mountain Climber", 3, "30 detik", "medium", 7),
            new Latihan("Plank", 3, "1 menit", "easy", 5)
        );
        List<Latihan> d3 = Arrays.asList(
            new Latihan("Man Maker", 3, "10", "very hard", 15),
            new Latihan("Jumping Lunges", 4, "12/sisi", "hard", 10),
            new Latihan("Renegade Row", 3, "10/sisi", "medium", 8),
            new Latihan("Hanging Knee Raise", 3, "12", "medium", 7),
            new Latihan("Bear Crawl", 3, "20 meter", "medium", 8)
        );
        List<Latihan> d5 = Arrays.asList(
            new Latihan("Turkish Get-Up", 3, "5/sisi", "very hard", 15),
            new Latihan("Thrusters", 4, "12", "very hard", 12),
            new Latihan("Chin Up", 3, "8", "hard", 10),
            new Latihan("Bicycle Crunch", 3, "20/sisi", "medium", 8),
            new Latihan("Sled Push", 4, "20 meter", "hard", 10)
        );
        List<Latihan> d7 = Arrays.asList(
            new Latihan("Muscle Up (Assisted)", 3, "5", "extreme", 20),
            new Latihan("Clean & Press", 4, "10", "very hard", 15),
            new Latihan("Sandbag Carry", 3, "30 meter", "hard", 10),
            new Latihan("Dragon Flag", 3, "8", "hard", 10),
            new Latihan("Jump Rope", 3, "1 menit", "easy", 5)
        );

        List<AktivitasHarian> jadwal = Arrays.asList(
            new HariBertarung(1, d1, namaProgram), 
            new HariIstirahat(2, namaProgram),
            new HariBertarung(3, d3, namaProgram), 
            new HariIstirahat(4, namaProgram),
            new HariBertarung(5, d5, namaProgram), 
            new HariIstirahat(6, namaProgram),
            new HariBertarung(7, d7, namaProgram)
        );
        s.aturJadwalMingguan(jadwal);
        return s;
    }
}