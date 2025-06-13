package model.latihan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OtotKhusus extends ProgramLatihan {
    private String targetOtot;

    public OtotKhusus(String targetOtot) {
        // Nama program akan diformat secara dinamis, misal: "Otot Khusus (Dada)"
        super("Otot Khusus (" + targetOtot + ")");
        this.targetOtot = targetOtot;
    }

    @Override
    public SiklusMingguan getSiklusMingguan() {
        SiklusMingguan s = new SiklusMingguan();
        s.setNamaProgram(this.getNamaProgram());
        List<Latihan> d1 = new ArrayList<>();
        List<Latihan> d3 = new ArrayList<>();
        List<Latihan> d5 = new ArrayList<>();
        List<Latihan> d7 = new ArrayList<>();
        
        // Switch statement akan memilih data latihan yang benar berdasarkan targetOtot
        switch (this.targetOtot) {
            case "Dada":
                d1 = Arrays.asList(new Latihan("Push Up", 4, "15", "easy", 5), new Latihan("Incline Push Up", 4, "12", "easy", 6), new Latihan("Wide Push Up", 3, "15", "medium", 7), new Latihan("Plank to Push Up", 3, "10", "medium", 6));
                d3 = Arrays.asList(new Latihan("Diamond Push Up", 3, "15", "medium", 8), new Latihan("Archer Push Up", 3, "10", "hard", 10), new Latihan("Decline Push Up", 3, "12", "hard", 10), new Latihan("Explosive Push Up", 3, "8", "hard", 12));
                d5 = Arrays.asList(new Latihan("One Arm Push Up (Assisted)", 3, "6", "very hard", 15), new Latihan("Hand Release Push Up", 4, "12", "hard", 10), new Latihan("Pseudo Planche Push Up", 3, "10", "very hard", 12));
                d7 = Arrays.asList(new Latihan("Ring Push Up", 3, "10", "hard", 12), new Latihan("Weighted Push Up (5kg)", 3, "8", "very hard", 15), new Latihan("Chest Squeeze Push Up", 3, "12", "medium", 10));
                break;
            case "Punggung":
                d1 = Arrays.asList(new Latihan("Superman Hold", 3, "30 detik", "easy", 5), new Latihan("Wall Pull", 4, "12", "easy", 6), new Latihan("Reverse Snow Angel", 3, "15", "medium", 6), new Latihan("Wall Slide", 3, "12", "medium", 5));
                d3 = Arrays.asList(new Latihan("Prone Cobra Raise", 3, "20 detik", "medium", 7), new Latihan("Back Extension (lantai)", 4, "15", "medium", 8), new Latihan("Y-T-W Raise", 3, "1 set", "hard", 9), new Latihan("Door Frame Row", 3, "12", "hard", 10));
                d5 = Arrays.asList(new Latihan("Inverted Row (bawah meja)", 3, "10", "hard", 10), new Latihan("Towel Row", 3, "12", "hard", 10), new Latihan("Superman Pull", 3, "15", "hard", 9));
                d7 = Arrays.asList(new Latihan("Isometric Superman Hold", 3, "40 detik", "hard", 12), new Latihan("Weighted Door Row (tas)", 3, "10", "very hard", 15), new Latihan("Cobra Pull", 3, "12", "hard", 10));
                break;
            case "Bisep":
                d1 = Arrays.asList(new Latihan("Isometric Bicep Hold", 3, "30 detik", "easy", 5), new Latihan("Towel Curl", 3, "12", "easy", 6), new Latihan("Negative Curl", 3, "10", "medium", 7));
                d3 = Arrays.asList(new Latihan("Isometric Wall Curl", 3, "20 detik", "medium", 8), new Latihan("Lying Bicep Curl (tas)", 3, "12", "medium", 9), new Latihan("Hammer Curl (handuk)", 3, "10", "medium", 9));
                d5 = Arrays.asList(new Latihan("Concentration Curl (tas)", 3, "10", "hard", 10), new Latihan("Eccentric Towel Curl", 3, "8", "hard", 10), new Latihan("Tempo Curl", 3, "8", "hard", 10));
                d7 = Arrays.asList(new Latihan("Zottman Curl (handuk)", 3, "10", "hard", 12), new Latihan("Weighted Hammer Curl", 3, "8", "very hard", 15), new Latihan("Towel Twist Hold", 3, "30 detik", "hard", 10));
                break;
            case "Trisep":
                 d1 = Arrays.asList(new Latihan("Wall Triceps Extension", 3, "15", "easy", 5), new Latihan("Bench Dip (kursi)", 3, "12", "easy", 6), new Latihan("Overhead Extension (botol)", 3, "10", "medium", 7));
                 d3 = Arrays.asList(new Latihan("Diamond Wall Push Up", 3, "15", "medium", 8), new Latihan("Triceps Kickback (botol)", 3, "12", "medium", 9), new Latihan("Close-Grip Push Up (lutut)", 3, "10", "hard", 10));
                 d5 = Arrays.asList(new Latihan("Chair Dip (kaki lurus)", 3, "8", "hard", 12), new Latihan("Overhead Triceps Press", 3, "10", "hard", 12), new Latihan("Wall Triceps Hold", 3, "30 detik", "medium", 10));
                 d7 = Arrays.asList(new Latihan("One Arm Triceps Extension", 3, "10", "very hard", 15), new Latihan("Close Grip Incline Push Up", 3, "10", "hard", 12), new Latihan("Triceps Isometric Push", 3, "30 detik", "medium", 10));
                 break;
            case "Bahu":
                 d1 = Arrays.asList(new Latihan("Wall Walk Arm Lift", 3, "10", "easy", 5), new Latihan("Arm Circles", 3, "1 menit", "easy", 6), new Latihan("Front Raise (botol air)", 3, "12", "medium", 7));
                 d3 = Arrays.asList(new Latihan("Lateral Raise", 3, "12", "medium", 9), new Latihan("Reverse Fly", 3, "10", "medium", 10), new Latihan("Isometric Arm Raise", 3, "30 detik", "medium", 9));
                 d5 = Arrays.asList(new Latihan("Handstand Wall Hold", 3, "30 detik", "hard", 12), new Latihan("Wall Pike Push Up", 3, "10", "hard", 12), new Latihan("Shoulder Press (botol)", 3, "8", "hard", 10));
                 d7 = Arrays.asList(new Latihan("Elevated Pike Push Up", 3, "10", "very hard", 15), new Latihan("Isometric Lateral Hold", 3, "40 detik", "hard", 12), new Latihan("Slow Arm Circle (beban)", 3, "1 menit", "hard", 10));
                 break;
            case "Perut":
                 d1 = Arrays.asList(new Latihan("Crunch", 4, "20", "easy", 5), new Latihan("Leg Raise", 3, "15", "easy", 6), new Latihan("Plank", 3, "30 detik", "medium", 7));
                 d3 = Arrays.asList(new Latihan("Bicycle Crunch", 3, "20", "medium", 8), new Latihan("Plank Reach", 3, "15/sisi", "medium", 8), new Latihan("Flutter Kick", 3, "30 detik", "medium", 9));
                 d5 = Arrays.asList(new Latihan("Toe Touch", 3, "20", "hard", 10), new Latihan("V-Up", 3, "12", "hard", 12), new Latihan("Side Plank with Lift", 3, "10/sisi", "hard", 10));
                 d7 = Arrays.asList(new Latihan("Hollow Body Hold", 3, "30 detik", "very hard", 15), new Latihan("Plank Walkout", 3, "10", "hard", 12), new Latihan("Side V-Up", 3, "15/sisi", "hard", 12));
                 break;
            case "Kaki":
                 d1 = Arrays.asList(new Latihan("Bodyweight Squat", 4, "20", "easy", 5), new Latihan("Standing Calf Raise", 4, "25", "easy", 4), new Latihan("Wall Sit", 3, "30 detik", "medium", 6));
                 d3 = Arrays.asList(new Latihan("Lunges", 3, "15/sisi", "medium", 8), new Latihan("Glute Bridge March", 3, "20", "medium", 8), new Latihan("Heel Elevated Squat", 3, "15", "medium", 9));
                 d5 = Arrays.asList(new Latihan("Jump Squat", 3, "15", "hard", 10), new Latihan("Bulgarian Split Squat", 3, "12/sisi", "hard", 12), new Latihan("Isometric Lunge Hold", 3, "30 detik/sisi", "hard", 10));
                 d7 = Arrays.asList(new Latihan("Pistol Squat (assisted)", 3, "8/sisi", "very hard", 15), new Latihan("Skater Lunge", 3, "15/sisi", "hard", 12), new Latihan("Wall Sit with Pulse", 3, "20", "hard", 12));
                 break;
            case "Lengan":
                 d1 = Arrays.asList(new Latihan("Bicep Curl (botol air)", 3, "15", "easy", 6), new Latihan("Overhead Triceps Ext.", 3, "12", "easy", 6), new Latihan("Isometric Arm Curl Hold", 3, "30 detik", "medium", 7));
                 d3 = Arrays.asList(new Latihan("Hammer Curl (botol air)", 3, "12", "medium", 8), new Latihan("Triceps Kickback", 3, "15", "medium", 9), new Latihan("Wall Push Curl", 3, "30 detik", "medium", 9));
                 d5 = Arrays.asList(new Latihan("Resistance Band Curl", 3, "15", "hard", 12), new Latihan("Chair Dip (kaki lurus)", 3, "10", "hard", 12), new Latihan("Isometric Curl Pulse", 3, "20", "hard", 10));
                 d7 = Arrays.asList(new Latihan("Slow Bicep Curl (beban)", 3, "10", "very hard", 15), new Latihan("One Arm Kickback", 3, "12", "hard", 12), new Latihan("Arm Hold Out Pulse", 3, "1 menit", "hard", 12));
                 break;
            default:
                // Jika pilihan tidak valid, kembalikan jadwal kosong untuk menghindari error
                System.out.println("Program untuk otot '" + this.targetOtot + "' tidak ditemukan.");
                s.aturJadwalMingguan(new ArrayList<>()); // Mengatur jadwal kosong
                return s; 
        }

        // Membuat jadwal mingguan berdasarkan data yang dipilih
        List<AktivitasHarian> jadwal = Arrays.asList(
            new HariBertarung(1, d1, this.getNamaProgram()), new HariIstirahat(2, this.getNamaProgram()),
            new HariBertarung(3, d3, this.getNamaProgram()), new HariIstirahat(4, this.getNamaProgram()),
            new HariBertarung(5, d5, this.getNamaProgram()), new HariIstirahat(6, this.getNamaProgram()),
            new HariBertarung(7, d7, this.getNamaProgram())
        );
        s.aturJadwalMingguan(jadwal);
        return s;
    }
}