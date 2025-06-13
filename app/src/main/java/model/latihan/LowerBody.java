package model.latihan;
import java.util.Arrays;
import java.util.List;

public class LowerBody extends ProgramLatihan {
    public LowerBody() {
        super("Lower Body");
    }

    @Override
    public SiklusMingguan getSiklusMingguan() {
        SiklusMingguan s = new SiklusMingguan();
        String namaProgram = this.getNamaProgram();
        s.setNamaProgram(namaProgram);

        // Data Latihan untuk Lower Body Program
        List<Latihan> d1 = Arrays.asList(
            new Latihan("Squat", 4, "20", "easy", 5),
            new Latihan("Lunges", 3, "15/sisi", "medium", 7),
            new Latihan("Calf Raise", 4, "20", "easy", 4),
            new Latihan("Glute Bridge", 3, "15", "medium", 6)
        );
        List<Latihan> d3 = Arrays.asList(
            new Latihan("Bulgarian Split Squat", 3, "12/sisi", "hard", 10),
            new Latihan("Jump Squat", 4, "15", "medium", 8),
            new Latihan("Single-Leg Deadlift", 3, "10/sisi", "hard", 9)
        );
        List<Latihan> d5 = Arrays.asList(
            new Latihan("Pistol Squat (assisted)", 3, "6/sisi", "very hard", 15),
            new Latihan("Nordic Hamstring Curl", 3, "8", "very hard", 12),
            new Latihan("Box Jump", 4, "10", "hard", 10)
        );
        List<Latihan> d7 = Arrays.asList(
            new Latihan("Weighted Squat (10kg)", 4, "12", "very hard", 15),
            new Latihan("Skater Jump", 3, "15/sisi", "hard", 10),
            new Latihan("Depth Jump", 3, "8", "very hard", 12)
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