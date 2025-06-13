package model.soal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BankSoal {
    private static final List<Soal> semuaSoal = new ArrayList<>();

    static {
        // === UPPER BODY (Nama Kategori sudah benar) ===
        semuaSoal.add(new Soal("Mana bukan manfaat push up?", Arrays.asList("Menguatkan dada", "Meningkatkan fleksibilitas paha", "Melatih core"), "b", "Upper Body", 1));
        semuaSoal.add(new Soal("Untuk pemula, modifikasi dips yang aman adalah:", Arrays.asList("Menggunakan band resistansi", "Melompat setinggi mungkin", "Menahan napas"), "a", "Upper Body", 1));
        semuaSoal.add(new Soal("Pike push up terutama melatih otot:", Arrays.asList("Paha", "Bahu (deltoid anterior)", "Betis"), "b", "Upper Body", 2));
        semuaSoal.add(new Soal("Risiko archer push up jika teknik salah:", Arrays.asList("Cedera bahu atau siku", "Sakit kepala", "Kram jari kaki"), "a", "Upper Body", 2));
        semuaSoal.add(new Soal("Handstand push up membutuhkan:", Arrays.asList("Mobilitas bahu dan kekuatan core", "Sepatu khusus", "Permukaan miring"), "a", "Upper Body", 3));
        semuaSoal.add(new Soal("Mengapa decline push up lebih sulit?", Arrays.asList("Beban lebih besar pada dada atas", "Harus sambil bernapas cepat"), "a", "Upper Body", 3));

        // === LOWER BODY (Nama Kategori sudah benar) ===
        semuaSoal.add(new Soal("Squat yang benar harus:", Arrays.asList("Lutut sejajar jari kaki", "Punggung membungkuk", "Tumit terangkat"), "a", "Lower Body", 1));
        semuaSoal.add(new Soal("Glute bridge melatih:", Arrays.asList("Hamstring dan glutes", "Otot lengan", "Leher"), "a", "Lower Body", 1));
        semuaSoal.add(new Soal("Bulgarian split squat memfokuskan beban pada:", Arrays.asList("Kaki depan", "Tulang belakang", "Siku"), "a", "Lower Body", 2));
        semuaSoal.add(new Soal("Kesalahan umum saat single-leg deadlift:", Arrays.asList("Punggung melengkung", "Kepala menunduk", "Napas terlalu cepat"), "a", "Lower Body", 2));
        semuaSoal.add(new Soal("Pistol squat membutuhkan:", Arrays.asList("Keseimbangan dan mobilitas pergelangan kaki", "Tali skipping", "Suhu ruangan dingin"), "a", "Lower Body", 3));
        semuaSoal.add(new Soal("Nordic hamstring curl berisiko cedera jika:", Arrays.asList("Pinggul terangkat", "Lutut ditekuk 90°"), "a", "Lower Body", 3));

        // === OTOT KHUSUS (NAMA KATEGORI DIPERBAIKI) ===
        semuaSoal.add(new Soal("Otot utama saat push up adalah:", Arrays.asList("Trisep", "Punggung", "Dada"), "c", "Otot Khusus (Dada)", 1));
        semuaSoal.add(new Soal("Incline push up cocok untuk:", Arrays.asList("Pemula", "Atlet profesional", "Latihan kaki"), "a", "Otot Khusus (Dada)", 1));
        semuaSoal.add(new Soal("Diamond push up lebih menargetkan:", Arrays.asList("Dada tengah", "Dada bawah", "Dada dalam dan trisep"), "c", "Otot Khusus (Dada)", 2));
        semuaSoal.add(new Soal("Risiko decline push up bila teknik salah:", Arrays.asList("Cedera bahu", "Kram kaki", "Nyeri leher"), "a", "Otot Khusus (Dada)", 2));
        semuaSoal.add(new Soal("One-arm push up membutuhkan:", Arrays.asList("Kekuatan unilateral tinggi", "Bantuan alat berat", "Fokus pada kaki"), "a", "Otot Khusus (Dada)", 3));
        semuaSoal.add(new Soal("Risiko pseudo planche push up adalah:", Arrays.asList("Siku terkilir jika teknik buruk", "Keseleo pergelangan kaki", "Pegal di bokong"), "a", "Otot Khusus (Dada)", 3));

        semuaSoal.add(new Soal("Latihan 'superman hold' menargetkan:", Arrays.asList("Otot dada", "Punggung bawah", "Paha"), "b", "Otot Khusus (Punggung)", 1));
        semuaSoal.add(new Soal("Reverse snow angel melatih:", Arrays.asList("Otot punggung atas", "Otot betis", "Otot trisep"), "a", "Otot Khusus (Punggung)", 1));
        semuaSoal.add(new Soal("Fungsi utama back extension adalah:", Arrays.asList("Melatih punggung bawah", "Memperkuat leher", "Menambah tinggi badan"), "a", "Otot Khusus (Punggung)", 2));
        semuaSoal.add(new Soal("Gerakan Y-T-W efektif untuk:", Arrays.asList("Mobilitas bahu dan punggung atas", "Otot perut", "Otot kaki"), "a", "Otot Khusus (Punggung)", 2));
        semuaSoal.add(new Soal("Inverted row melatih:", Arrays.asList("Trisep", "Punggung dan bisep", "Bahu bagian depan"), "b", "Otot Khusus (Punggung)", 3));
        semuaSoal.add(new Soal("Risiko towel row jika teknik salah:", Arrays.asList("Tarikan tidak stabil bisa cedera bahu", "Sakit kepala", "Cedera tumit"), "a", "Otot Khusus (Punggung)", 3));

        semuaSoal.add(new Soal("Towel curl menargetkan otot:", Arrays.asList("Dada", "Bisep", "Paha"), "b", "Otot Khusus (Bisep)", 1));
        semuaSoal.add(new Soal("Gerakan negatif membantu:", Arrays.asList("Penguatan fase eksentrik", "Peregangan paha", "Keseimbangan kaki"), "a", "Otot Khusus (Bisep)", 1));
        semuaSoal.add(new Soal("Posisi tangan pada hammer curl adalah:", Arrays.asList("Telapak ke atas", "Netral/tegak", "Terbalik ke bawah"), "b", "Otot Khusus (Bisep)", 2));
        semuaSoal.add(new Soal("Latihan isometrik berguna untuk:", Arrays.asList("Stamina", "Menahan kontraksi otot lebih lama", "Memperpanjang otot"), "b", "Otot Khusus (Bisep)", 2));
        semuaSoal.add(new Soal("Gerakan tempo efektif untuk:", Arrays.asList("Menambah kecepatan", "Menambah tekanan otot", "Menurunkan fleksibilitas"), "b", "Otot Khusus (Bisep)", 3));
        semuaSoal.add(new Soal("Kesalahan pada concentration curl:", Arrays.asList("Siku bergerak bebas", "Duduk terlalu nyaman", "Tidak memakai alat"), "a", "Otot Khusus (Bisep)", 3));
        
        semuaSoal.add(new Soal("Fungsi utama bench dip:", Arrays.asList("Melatih dada", "Melatih trisep", "Melatih punggung"), "b", "Otot Khusus (Trisep)", 1));
        semuaSoal.add(new Soal("Overhead extension melibatkan:", Arrays.asList("Fleksor pinggul", "Trisep panjang", "Leher"), "b", "Otot Khusus (Trisep)", 1));
        semuaSoal.add(new Soal("Triceps kickback menargetkan:", Arrays.asList("Trisep lateral", "Otot bahu", "Betis"), "a", "Otot Khusus (Trisep)", 2));
        semuaSoal.add(new Soal("Close-grip push up efektif untuk:", Arrays.asList("Punggung", "Inti tubuh", "Trisep"), "c", "Otot Khusus (Trisep)", 2));
        semuaSoal.add(new Soal("Risiko chair dip jika teknik salah:", Arrays.asList("Cedera bahu", "Keseleo lutut", "Pegal betis"), "a", "Otot Khusus (Trisep)", 3));
        semuaSoal.add(new Soal("Triceps press membantu:", Arrays.asList("Memperkuat trisep secara menyeluruh", "Melatih leher", "Melatih dada"), "a", "Otot Khusus (Trisep)", 3));
        
        semuaSoal.add(new Soal("Arm circles melatih:", Arrays.asList("Deltoid", "Glutes", "Dada"), "a", "Otot Khusus (Bahu)", 1));
        semuaSoal.add(new Soal("Front raise menargetkan bagian:", Arrays.asList("Dada", "Bahu depan", "Punggung"), "b", "Otot Khusus (Bahu)", 1));
        semuaSoal.add(new Soal("Lateral raise melatih:", Arrays.asList("Deltoid tengah", "Paha", "Trisep"), "a", "Otot Khusus (Bahu)", 2));
        semuaSoal.add(new Soal("Reverse fly melatih:", Arrays.asList("Dada", "Bahu belakang", "Leher"), "b", "Otot Khusus (Bahu)", 2));
        semuaSoal.add(new Soal("Handstand hold melatih:", Arrays.asList("Inti", "Bahu dan core", "Leher"), "b", "Otot Khusus (Bahu)", 3));
        semuaSoal.add(new Soal("Kesalahan saat pike push up:", Arrays.asList("Posisi pinggul terlalu rendah", "Terlalu banyak istirahat", "Nafas pendek"), "a", "Otot Khusus (Bahu)", 3));

        semuaSoal.add(new Soal("Crunch menargetkan otot:", Arrays.asList("Punggung", "Perut", "Leher"), "b", "Otot Khusus (Perut)", 1));
        semuaSoal.add(new Soal("Leg raise memperkuat bagian:", Arrays.asList("Otot perut bawah", "Paha atas", "Lengan"), "a", "Otot Khusus (Perut)", 1));
        semuaSoal.add(new Soal("Risiko flutter kick jika postur salah:", Arrays.asList("Tegang di leher", "Nyeri punggung bawah", "Sakit bahu"), "b", "Otot Khusus (Perut)", 2));
        semuaSoal.add(new Soal("Bicycle crunch melatih:", Arrays.asList("Otot oblique", "Betis", "Bahu"), "a", "Otot Khusus (Perut)", 2));
        semuaSoal.add(new Soal("Side plank efektif untuk:", Arrays.asList("Oblique dan core", "Lengan", "Leher"), "a", "Otot Khusus (Perut)", 3));
        semuaSoal.add(new Soal("Kesalahan umum saat V-Up:", Arrays.asList("Tidak mengangkat tangan", "Gerakan terburu-buru tanpa kontrol", "Tidak menggunakan beban"), "b", "Otot Khusus (Perut)", 3));

        semuaSoal.add(new Soal("Squat dasar melatih:", Arrays.asList("Paha depan, glutes", "Lengan", "Leher"), "a", "Otot Khusus (Kaki)", 1));
        semuaSoal.add(new Soal("Wall sit memperkuat:", Arrays.asList("Punggung", "Otot kaki dan stabilitas", "Perut"), "b", "Otot Khusus (Kaki)", 1));
        semuaSoal.add(new Soal("Lunges membantu:", Arrays.asList("Mobilitas sendi dan kekuatan kaki", "Menguatkan leher", "Melatih trisep"), "a", "Otot Khusus (Kaki)", 2));
        semuaSoal.add(new Soal("Kesalahan pada heel elevated squat:", Arrays.asList("Mengangkat tumit terlalu tinggi", "Terlalu banyak jeda", "Tidak pakai sepatu"), "a", "Otot Khusus (Kaki)", 2));
        semuaSoal.add(new Soal("Jump squat melatih:", Arrays.asList("Daya ledak otot kaki", "Bahu", "Punggung"), "a", "Otot Khusus (Kaki)", 3));
        semuaSoal.add(new Soal("Risiko bulgarian squat jika tidak seimbang:", Arrays.asList("Cedera pergelangan kaki", "Pusing", "Lemas tangan"), "a", "Otot Khusus (Kaki)", 3));
        
        semuaSoal.add(new Soal("Latihan bicep curl menargetkan:", Arrays.asList("Bisep", "Glutes", "Leher"), "a", "Otot Khusus (Lengan)", 1));
        semuaSoal.add(new Soal("Isometric hold membantu:", Arrays.asList("Meningkatkan kekuatan statis", "Melatih kardio", "Melatih core"), "a", "Otot Khusus (Lengan)", 1));
        semuaSoal.add(new Soal("Hammer curl bekerja pada:", Arrays.asList("Bisep dan brachialis", "Trisep", "Paha"), "a", "Otot Khusus (Lengan)", 2));
        semuaSoal.add(new Soal("Risiko kickback yang salah teknik:", Arrays.asList("Bahu terlalu aktif", "Nyeri punggung", "Lengan terlalu kuat"), "a", "Otot Khusus (Lengan)", 2));
        semuaSoal.add(new Soal("Menggunakan resistance band saat curl:", Arrays.asList("Meningkatkan tahanan progresif", "Membuat otot lengan cepat lelah", "Tidak efektif"), "a", "Otot Khusus (Lengan)", 3));
        semuaSoal.add(new Soal("Dip membantu:", Arrays.asList("Trisep", "Leher", "Betis"), "a", "Otot Khusus (Lengan)", 3));
    }

    public static List<Soal> getSoal(String kategori, int tingkatKesulitan, int jumlah) {
        // 1. Prioritas utama: Cari yang cocok persis (kategori & tingkat kesulitan)
        List<Soal> hasil = semuaSoal.stream()
                .filter(soal -> soal.getKategoriProgram().equalsIgnoreCase(kategori) && soal.getTingkatKesulitan() == tingkatKesulitan)
                .collect(Collectors.toList());

        // 2. Fallback: Jika tidak ada, cari kategori yang sama dengan tingkat kesulitan BEBAS.
        if (hasil.size() < jumlah) {
            List<Soal> fallbackHasil = semuaSoal.stream()
                .filter(soal -> soal.getKategoriProgram().equalsIgnoreCase(kategori))
                .collect(Collectors.toList());
            // Tambahkan soal yang belum ada di daftar hasil
            for(Soal s : fallbackHasil) {
                if(!hasil.contains(s)) {
                    hasil.add(s);
                }
            }
        }
        
        Collections.shuffle(hasil);
        return hasil.stream().limit(jumlah).collect(Collectors.toList());
    }
}