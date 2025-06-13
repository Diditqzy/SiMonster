package model.latihan;

import java.util.Arrays;
import java.util.List;

public class UpperBody extends ProgramLatihan {
    public UpperBody() {
        super("Upper Body");
    }

    @Override
    public SiklusMingguan getSiklusMingguan() {
        SiklusMingguan s = new SiklusMingguan();
        String namaProgram = this.getNamaProgram();
        s.setNamaProgram(namaProgram);

        // Data Latihan untuk Upper Body Program
        List<Latihan> d1 = Arrays.asList(
            new Latihan("Push Up", 4, "15", "easy", 5),
            new Latihan("Incline Push Up", 4, "15", "easy", 5),
            new Latihan("Dips", 4, "12", "medium", 8),
            new Latihan("Plank Shoulder Tap", 4, "20 tap", "medium", 8),
            new Latihan("Wall Walk", 2, "3", "hard", 12)
        );
        List<Latihan> d3 = Arrays.asList(
            new Latihan("Archer Push Up", 4, "12", "hard", 10),
            new Latihan("Diamond Push Up", 3, "15", "medium", 8),
            new Latihan("Pike Push Up", 3, "12", "hard", 10),
            new Latihan("Side Plank Reach Under", 3, "12/sisi", "medium", 7)
        );
        List<Latihan> d5 = Arrays.asList(
            new Latihan("One-Arm Push Up (assisted)", 3, "8", "very hard", 15),
            new Latihan("Decline Push Up", 4, "12", "hard", 10),
            new Latihan("Handstand Push Up (wall)", 3, "5", "very hard", 12)
        );
        List<Latihan> d7 = Arrays.asList(
            new Latihan("Clap Push Up", 4, "8", "very hard", 15),
            new Latihan("Weighted Dips (5kg)", 3, "10", "very hard", 12),
            new Latihan("Planche Lean Hold", 3, "20 detik", "hard", 10)
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