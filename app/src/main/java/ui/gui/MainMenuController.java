package ui.gui;

import data.ManajemenData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.latihan.*;
import model.user.CatatanLatihan;
import model.user.Pengguna;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Optional;

public class MainMenuController {

    private MainApp mainApp;
    private Pengguna penggunaSaatIni;
    private Map<String, Pengguna> basisDataPengguna;

    @FXML private Label welcomeLabel;
    @FXML private Label levelLabel;
    @FXML private Label expLabel;
    @FXML private Label badgeLabel;
    
    @FXML private Button programButton;
    @FXML private VBox dashboardPane;
    @FXML private VBox programPane;
    @FXML private VBox historyPane;

    @FXML private TextArea programStatusArea;
    @FXML private Button doExerciseButton;
    @FXML private Button createProgramButton;
    
    @FXML private TableView<CatatanLatihan> historyTable;
    @FXML private TableColumn<CatatanLatihan, String> tanggalColumn;
    @FXML private TableColumn<CatatanLatihan, String> latihanColumn;
    @FXML private TableColumn<CatatanLatihan, Integer> hariColumn;
    @FXML private TableColumn<CatatanLatihan, String> programColumn;


    public void initData(MainApp mainApp, Pengguna pengguna, Map<String, Pengguna> basisDataPengguna) {
        this.mainApp = mainApp;
        this.penggunaSaatIni = pengguna;
        this.basisDataPengguna = basisDataPengguna;
        
        updateProfileUI();
        updateProgramPane();
        setupHistoryTable();
        
        showDashboard(); // Tampilkan dashboard sebagai default
    }

    private void updateProfileUI() {
        welcomeLabel.setText("Halo, " + penggunaSaatIni.getNama() + "!");
        levelLabel.setText("Level: " + penggunaSaatIni.getLevel());
        expLabel.setText(String.format("EXP: %d / %d", penggunaSaatIni.getExp(), penggunaSaatIni.getLevel() * 100));
        badgeLabel.setText("Badge: " + penggunaSaatIni.getBadge());
    }

    @FXML
    private void handleLogout() {
        try {
            ManajemenData.simpanDataPengguna(basisDataPengguna);
            mainApp.showLoginView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // --- Navigasi Pane ---
    @FXML private void showDashboard() {
        dashboardPane.setVisible(true);
        programPane.setVisible(false);
        historyPane.setVisible(false);
    }
    
    @FXML private void showProgram() {
        dashboardPane.setVisible(false);
        programPane.setVisible(true);
        historyPane.setVisible(false);
        updateProgramPane();
    }

    @FXML private void showHistory() {
        dashboardPane.setVisible(false);
        programPane.setVisible(false);
        historyPane.setVisible(true);
        loadHistoryData();
    }

    // --- Logika Program ---
    private void updateProgramPane() {
        SiklusMingguan siklus = penggunaSaatIni.getSiklusSaatIni();
        if (siklus == null) {
            programStatusArea.setText("Anda belum memiliki program latihan aktif. Buat program baru untuk memulai!");
            doExerciseButton.setVisible(false);
            createProgramButton.setVisible(true);
        } else if (siklus.isSelesai()) {
            prosesPemberianBadge(siklus);
            penggunaSaatIni.setSiklusSaatIni(null);
            updateProgramPane(); // Refresh pane
        } else {
            int hariKe = siklus.getHariSaatIniIndex() + 1;
            AktivitasHarian aktivitas = siklus.getHariSaatIni();
            String jenisAktivitas = (aktivitas instanceof HariBertarung) ? "HARI BERTARUNG" : "HARI ISTIRAHAT (KUIS)";
            
            programStatusArea.setText("Program Aktif: " + siklus.getNamaProgram() + "\n\n"
                                    + "Hari ini adalah Hari ke-" + hariKe + ":\n"
                                    + jenisAktivitas);
            
            doExerciseButton.setText("Mulai Aktivitas Hari ke-" + hariKe);
            doExerciseButton.setVisible(true);
            createProgramButton.setVisible(false);
        }
        updateProfileUI();
    }

    @FXML
    private void handleDoExercise() {
        SiklusMingguan siklus = penggunaSaatIni.getSiklusSaatIni();
        if (siklus != null && !siklus.isSelesai()) {
            AktivitasHarian aktivitas = siklus.getHariSaatIni();
            
            // Simulasi aktivitas (di GUI tidak perlu scanner)
            // Untuk kuis dan latihan, kita hanya tampilkan pesan dan berikan EXP
            if (aktivitas instanceof HariBertarung) {
                // Di dunia nyata, ini akan membuka window/scene baru untuk latihan
                int totalExp = ((HariBertarung) aktivitas).getDaftarLatihanHarian().stream().mapToInt(Latihan::getExp).sum();
                penggunaSaatIni.addExp(totalExp);
                showAlert(Alert.AlertType.INFORMATION, "Latihan Selesai!", "Kerja bagus! Anda mendapatkan " + totalExp + " EXP.");
            } else if (aktivitas instanceof HariIstirahat) {
                 // Untuk kuis, kita berikan exp rata-rata saja di GUI ini
                penggunaSaatIni.addExp(30); // Asumsi skor kuis bagus
                showAlert(Alert.AlertType.INFORMATION, "Kuis Selesai!", "Hebat! Otak dan otot perlu seimbang. Anda mendapatkan 30 EXP.");
            }
            
            // Buat catatan latihan
            String namaProgram = siklus.getNamaProgram();
            int hariKe = siklus.getHariSaatIniIndex() + 1;
            String daftarLatihanStr = (aktivitas instanceof HariBertarung) ? 
                ((HariBertarung) aktivitas).getDaftarLatihanHarian().stream().map(Latihan::getNamaLatihan).reduce((a, b) -> a + ", " + b).orElse("")
                : "Kuis Pengetahuan Fitness";
            CatatanLatihan catatan = new CatatanLatihan(penggunaSaatIni.getNama(), daftarLatihanStr, hariKe, namaProgram);
            penggunaSaatIni.getRiwayat().tambahkanCatatan(catatan);

            siklus.majuKeHariBerikutnya();
            updateProgramPane();
        }
    }

    @FXML
    private void handleCreateProgram() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Full Body", "Full Body", "Upper Body", "Lower Body", "Otot Khusus");
        dialog.setTitle("Buat Program Baru");
        dialog.setHeaderText("Pilih jenis program latihan yang Anda inginkan.");
        dialog.setContentText("Pilihan:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(pilihan -> {
            ProgramLatihan programPilihan = null;
            if (pilihan.equals("Otot Khusus")) {
                programPilihan = pilihOtotKhusus();
            } else if (pilihan.equals("Upper Body")) {
                programPilihan = new UpperBody();
            } else if (pilihan.equals("Lower Body")) {
                programPilihan = new LowerBody();
            } else {
                programPilihan = new FullBody();
            }

            if (programPilihan != null) {
                penggunaSaatIni.setSiklusSaatIni(programPilihan.getSiklusMingguan());
                showAlert(Alert.AlertType.INFORMATION, "Program Dibuat", "Program '" + programPilihan.getNamaProgram() + "' berhasil dibuat!");
                updateProgramPane();
            }
        });
    }

    private ProgramLatihan pilihOtotKhusus() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Dada", "Dada", "Punggung", "Bisep", "Trisep", "Bahu", "Perut", "Kaki", "Lengan");
        dialog.setTitle("Pilih Otot");
        dialog.setHeaderText("Pilih target otot yang ingin Anda latih.");
        dialog.setContentText("Otot:");
        Optional<String> result = dialog.showAndWait();
        return result.map(OtotKhusus::new).orElse(null);
    }
    
    private void prosesPemberianBadge(SiklusMingguan siklusSelesai) {
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
            penggunaSaatIni.setBadge(badgeBaru);
            penggunaSaatIni.addExp(250); // Bonus EXP
            showAlert(Alert.AlertType.CONFIRMATION, "PROGRAM SELESAI!", 
                "Luar biasa! Anda telah menyelesaikan program: " + namaProgram + "\n" +
                "Anda mendapatkan badge baru: \"" + badgeBaru + "\" dan bonus 250 EXP!");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Program Selesai", "Selamat, Anda telah menyelesaikan program " + namaProgram + "!");
        }
    }
    
    // --- Logika Riwayat ---
    private void setupHistoryTable() {
        // Menggunakan PropertyValueFactory untuk menautkan kolom ke properti di kelas CatatanLatihan
        // Perhatian: Kelas CatatanLatihan perlu getter yang sesuai (getTanggal(), getDaftarLatihan(), etc.)
        tanggalColumn.setCellValueFactory(cellData -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return new javafx.beans.property.SimpleStringProperty(sdf.format(cellData.getValue().getTanggal()));
        });
        latihanColumn.setCellValueFactory(new PropertyValueFactory<>("daftarLatihan"));
        hariColumn.setCellValueFactory(new PropertyValueFactory<>("hariKe"));
        programColumn.setCellValueFactory(new PropertyValueFactory<>("namaProgram"));
    }
    
    private void loadHistoryData() {
        ObservableList<CatatanLatihan> historyList = FXCollections.observableArrayList(penggunaSaatIni.getRiwayat().getDaftarCatatan());
        historyTable.setItems(historyList);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}