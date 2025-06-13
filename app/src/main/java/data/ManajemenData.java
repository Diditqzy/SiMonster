package data;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import model.latihan.SiklusMingguan;
import model.user.CatatanLatihan;
import model.user.Pengguna;
import model.user.RiwayatLatihan;


/**
 * Kelas ini bertanggung jawab untuk menangani semua operasi file,
 * yaitu menyimpan data pengguna ke format JSON dan memuatnya kembali.
 */
public class ManajemenData {
    private static final String NAMA_FILE = "pengguna.json";

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\r");
    }

    private static String unescape(String s) {
        if (s == null) return "";
        return s.replace("\\n", "\n").replace("\\r", "\r").replace("\\\"", "\"").replace("\\\\", "\\");
    }

    // =========================================================================
    // === METODE simpanDataPengguna DIUBAH UNTUK MENYIMPAN TANGGAL SEBAGAI STRING ===
    // =========================================================================
    public static void simpanDataPengguna(Map<String, Pengguna> data) {
        StringBuilder jsonBuilder = new StringBuilder();
        // Format tanggal yang akan digunakan untuk menyimpan dan memuat
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        jsonBuilder.append("{\n");
        if (data != null && !data.isEmpty()) {
            int userCount = 0;
            for (Map.Entry<String, Pengguna> entry : data.entrySet()) {
                userCount++;
                Pengguna p = entry.getValue();
                
                jsonBuilder.append("  \"").append(escape(p.getUsername())).append("\": {\n");
                jsonBuilder.append("    \"password\": \"").append(escape(p.getPassword())).append("\",\n");
                jsonBuilder.append("    \"nama\": \"").append(escape(p.getNama())).append("\",\n");
                jsonBuilder.append("    \"level\": ").append(p.getLevel()).append(",\n");
                jsonBuilder.append("    \"exp\": ").append(p.getExp()).append(",\n");
                jsonBuilder.append("    \"badge\": \"").append(escape(p.getBadge())).append("\",\n");

                SiklusMingguan siklus = p.getSiklusSaatIni();
                if (siklus != null && !siklus.isSelesai()) {
                    jsonBuilder.append("    \"siklusProgram\": \"").append(escape(siklus.getNamaProgram())).append("\",\n");
                    jsonBuilder.append("    \"siklusHariKe\": ").append(siklus.getHariSaatIniIndex()).append(",\n");
                }

                RiwayatLatihan riwayat = p.getRiwayat();
                jsonBuilder.append("    \"riwayat\": [\n");
                if (riwayat != null && !riwayat.getDaftarCatatan().isEmpty()) {
                    int catatanCount = 0;
                    for (CatatanLatihan c : riwayat.getDaftarCatatan()) {
                        catatanCount++;
                        jsonBuilder.append("      {\n");
                        // Mengubah tanggal menjadi string format "dd-MM-yyyy"
                        String tanggalFormatted = sdf.format(c.getTanggal());
                        jsonBuilder.append("        \"tanggal\": \"").append(escape(tanggalFormatted)).append("\",\n");
                        jsonBuilder.append("        \"daftarLatihan\": \"").append(escape(c.getDaftarLatihan())).append("\",\n");
                        jsonBuilder.append("        \"hariKe\": ").append(c.getHariKe()).append(",\n");
                        jsonBuilder.append("        \"namaProgram\": \"").append(escape(c.getNamaProgram())).append("\"\n");
                        jsonBuilder.append("      }").append(catatanCount < riwayat.getDaftarCatatan().size() ? "," : "").append("\n");
                    }
                }
                jsonBuilder.append("    ]\n");
                jsonBuilder.append("  }").append(userCount < data.size() ? "," : "").append("\n");
            }
        }
        jsonBuilder.append("}");

        try (Writer writer = new FileWriter(NAMA_FILE)) {
            writer.write(jsonBuilder.toString());
        } catch (IOException e) {
            System.err.println("Gagal menyimpan data: " + e.getMessage());
        }
    }

    // =========================================================================
    // === METODE muatDataPengguna DIUBAH UNTUK MEMBACA TANGGAL DARI STRING ===
    // =========================================================================
    public static Map<String, Pengguna> muatDataPengguna() {
        File file = new File(NAMA_FILE);
        if (!file.exists()) {
            System.out.println("File data tidak ditemukan. Membuat file kosong baru: " + NAMA_FILE);
            return new HashMap<>();
        }

        Map<String, Pengguna> data = new HashMap<>();
        // Format tanggal yang akan digunakan untuk menyimpan dan memuat
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            String content = jsonContent.toString().trim();
            if (content.length() <= 2) return new HashMap<>();

            content = content.substring(1, content.length() - 1).trim();

            while (!content.isEmpty()) {
                int userKeyStart = content.indexOf('"');
                if (userKeyStart == -1) break;

                int userKeyEnd = content.indexOf('"', userKeyStart + 1);
                if (userKeyEnd == -1) break;

                String username = unescape(content.substring(userKeyStart + 1, userKeyEnd));

                int blockStart = content.indexOf('{', userKeyEnd);
                if (blockStart == -1) break;

                int braceCount = 1;
                int blockEnd = -1;
                for (int i = blockStart + 1; i < content.length(); i++) {
                    if (content.charAt(i) == '{') braceCount++;
                    if (content.charAt(i) == '}') braceCount--;
                    if (braceCount == 0) {
                        blockEnd = i;
                        break;
                    }
                }
                if (blockEnd == -1) break; 

                String userBlock = content.substring(blockStart, blockEnd + 1);

                String password = ekstrakNilaiString(userBlock, "password");
                Pengguna p = new Pengguna(username, password);
                p.setNama(ekstrakNilaiString(userBlock, "nama"));
                p.setLevel(ekstrakNilaiInt(userBlock, "level"));
                p.setExp(ekstrakNilaiInt(userBlock, "exp"));
                p.setBadge(ekstrakNilaiString(userBlock, "badge"));

                String riwayatBlock = ekstrakBlok(userBlock, "riwayat");
                if (riwayatBlock != null && riwayatBlock.length() > 2) {
                    String catatanContent = riwayatBlock.substring(1, riwayatBlock.length() - 1).trim();
                    while(!catatanContent.isEmpty()) {
                        int catatanStart = catatanContent.indexOf('{');
                        if (catatanStart == -1) break;
                        int catatanEnd = catatanContent.indexOf('}', catatanStart);
                        if(catatanEnd == -1) break;
                        
                        String catatanEntry = catatanContent.substring(catatanStart, catatanEnd + 1);
                        
                        CatatanLatihan c = new CatatanLatihan(p.getNama(),
                                ekstrakNilaiString(catatanEntry, "daftarLatihan"),
                                ekstrakNilaiInt(catatanEntry, "hariKe"),
                                ekstrakNilaiString(catatanEntry, "namaProgram"));
                        
                        // Membaca tanggal dari string format "dd-MM-yyyy"
                        String tanggalStr = ekstrakNilaiString(catatanEntry, "tanggal");
                        try {
                            // Coba parsing dulu, kalau gagal (karena format lama), coba parsing sebagai long
                            c.setTanggal(sdf.parse(tanggalStr));
                        } catch (ParseException e) {
                            try {
                                // Fallback untuk data lama yang masih menggunakan format angka (timestamp)
                                long tanggalLong = Long.parseLong(tanggalStr);
                                c.setTanggal(new Date(tanggalLong));
                            } catch (NumberFormatException e2) {
                                System.err.println("Gagal mem-parsing tanggal: " + tanggalStr);
                            }
                        }
                        
                        p.getRiwayat().tambahkanCatatan(c);

                        catatanContent = catatanContent.substring(catatanEnd + 1).trim();
                        if(catatanContent.startsWith(",")) {
                            catatanContent = catatanContent.substring(1).trim();
                        }
                    }
                }

                String siklusProgram = ekstrakNilaiString(userBlock, "siklusProgram");
                if (siklusProgram != null && !siklusProgram.isEmpty()) {
                    int siklusHariKe = ekstrakNilaiInt(userBlock, "siklusHariKe");
                    p.aturSiklusFromSave(siklusProgram, siklusHariKe);
                }
                
                data.put(username, p);
                
                content = content.substring(blockEnd + 1).trim();
                if (!content.isEmpty() && content.startsWith(",")) {
                    content = content.substring(1).trim();
                }
            }
            System.out.println("Data pengguna berhasil dimuat dari file.");
        } catch (Exception e) {
            System.err.println("KRITIS: Gagal memuat atau parsing data. File mungkin korup. " + e.getMessage());
            e.printStackTrace();
            return new HashMap<>();
        }
        return data;
    }

    private static String ekstrakNilai(String block, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = block.indexOf(searchKey);
        if (startIndex == -1) return null;
        startIndex += searchKey.length();
        String sub = block.substring(startIndex).trim();
        if (sub.startsWith("\"")) {
            int endIndex = sub.indexOf("\"", 1);
            if (endIndex == -1) return null;
            return unescape(sub.substring(1, endIndex));
        } else {
            int endIndex = sub.indexOf(",");
            if (endIndex == -1) endIndex = sub.indexOf("}");
            if (endIndex == -1) endIndex = sub.indexOf("]");
            if (endIndex == -1) endIndex = sub.length();
            return sub.substring(0, endIndex).trim();
        }
    }
    
    private static String ekstrakBlok(String block, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = block.indexOf(searchKey);
        if (startIndex == -1) return null;
        startIndex += searchKey.length();
        String sub = block.substring(startIndex).trim();
        if (!sub.startsWith("[")) return null;

        int braceCount = 0;
        int startBracket = -1;
        for(int i=0; i<sub.length(); i++) {
            if(sub.charAt(i) == '[') {
                if(startBracket == -1) startBracket = i;
                braceCount++;
            }
            if (sub.charAt(i) == ']') {
                braceCount--;
            }
            if (startBracket != -1 && braceCount == 0) {
                return sub.substring(startBracket, i + 1);
            }
        }
        return null;
    }
    
    private static String ekstrakNilaiString(String block, String key) { String v = ekstrakNilai(block, key); return v == null ? "" : v; }
    private static int ekstrakNilaiInt(String block, String key) { String v = ekstrakNilai(block, key); return v == null || v.isEmpty() ? 0 : Integer.parseInt(v); }
    private static long ekstrakNilaiLong(String block, String key) { String v = ekstrakNilai(block, key); return v == null || v.isEmpty() ? 0 : Long.parseLong(v); }
}