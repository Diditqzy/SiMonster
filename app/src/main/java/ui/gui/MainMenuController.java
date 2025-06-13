package ui.gui;

import data.ManajemenData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.latihan.*;
import model.soal.BankSoal;
import model.soal.Soal;
import model.user.CatatanLatihan;
import model.user.Pengguna;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class MainMenuController {

    private MainApp mainApp;
    private Pengguna penggunaSaatIni;
    private Map<String, Pengguna> basisDataPengguna;

    // ... (Variabel @FXML lainnya tetap sama) ...
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
        
        showDashboard();
    }

    // ... (Metode updateProfileUI, handleLogout, navigasi pane tetap sama) ...
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

    // --- Logika Program (Diperbarui) ---
    private void updateProgramPane() {
        SiklusMingguan siklus = penggunaSaatIni.getSiklusSaatIni();
        if (siklus == null) {
            programStatusArea.setText("Anda belum memiliki program latihan aktif. Buat program baru untuk memulai!");
            doExerciseButton.setVisible(false);
            createProgramButton.setVisible(true);
        } else if (siklus.isSelesai()) {
            prosesPemberianBadge(siklus);
            penggunaSaatIni.setSiklusSaatIni(null);
            updateProgramPane();
        } else {
            int hariKe = siklus.getHariSaatIniIndex() + 1;
            AktivitasHarian aktivitas = siklus.getHariSaatIni();
            
            StringBuilder statusText = new StringBuilder();
            statusText.append("Program Aktif: ").append(siklus.getNamaProgram()).append("\n\n");
            statusText.append("Hari ini adalah Hari ke-").append(hariKe).append(":\n");
            
            if (aktivitas instanceof HariBertarung) {
                statusText.append("HARI BERTARUNG\n\n");
                statusText.append("Daftar Latihan:\n");
                ((HariBertarung) aktivitas).getDaftarLatihanHarian().forEach(latihan -> {
                    statusText.append("- ").append(latihan.getNamaLatihan()).append("\n");
                });
            } else {
                statusText.append("HARI ISTIRAHAT (KUIS)\n\n");
                statusText.append("Gunakan hari ini untuk memulihkan tenaga dan menguji pengetahuanmu!");
            }
            
            programStatusArea.setText(statusText.toString());
            doExerciseButton.setText("Mulai Aktivitas Hari ke-" + hariKe);
            doExerciseButton.setVisible(true);
            createProgramButton.setVisible(false);
        }
        updateProfileUI();
    }

    @FXML
    private void handleDoExercise() {
        SiklusMingguan siklus = penggunaSaatIni.getSiklusSaatIni();
        if (siklus == null || siklus.isSelesai()) return;

        AktivitasHarian aktivitas = siklus.getHariSaatIni();
        
        if (aktivitas instanceof HariIstirahat) {
            handleQuizDay(siklus);
        } else if (aktivitas instanceof HariBertarung) {
            handleTrainingDay(siklus, (HariBertarung) aktivitas);
        }
    }
    
    private void handleTrainingDay(SiklusMingguan siklus, HariBertarung aktivitas) {
        int totalExp = aktivitas.getDaftarLatihanHarian().stream().mapToInt(Latihan::getExp).sum();
        penggunaSaatIni.addExp(totalExp);
        showAlert(Alert.AlertType.INFORMATION, "Latihan Selesai!", "Kerja bagus! Anda mendapatkan " + totalExp + " EXP.");
        
        String daftarLatihanStr = aktivitas.getDaftarLatihanHarian()
            .stream()
            .map(Latihan::getNamaLatihan)
            .collect(Collectors.joining(", "));
        buatCatatanDanLanjut(siklus, daftarLatihanStr);
    }
    
    private void handleQuizDay(SiklusMingguan siklus) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Hari Istirahat");
        confirmDialog.setHeaderText("Ini adalah hari istirahat. Mau mengerjakan kuis?");
        confirmDialog.setContentText("Pilih jawaban Anda:");

        ButtonType buttonTypeYes = new ButtonType("Kerjakan Kuis");
        ButtonType buttonTypeNo = new ButtonType("Lewati Saja", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmDialog.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            // Pengguna memilih mengerjakan kuis
            int hariKe = siklus.getHariSaatIniIndex() + 1;
            int tingkatKesulitan = (hariKe / 2);
            List<Soal> soalUntukKuis = BankSoal.getSoal(siklus.getNamaProgram(), tingkatKesulitan, 2);

            if (soalUntukKuis.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Tidak Ada Kuis", "Maaf, tidak ada kuis yang tersedia untuk program ini. Selamat beristirahat!");
                penggunaSaatIni.addExp(10);
                buatCatatanDanLanjut(siklus, "Istirahat (tanpa kuis)");
                return;
            }
            
            // Tampilkan dialog kuis kustom
            QuizDialog quizDialog = new QuizDialog(soalUntukKuis);
            Optional<Integer> quizResult = quizDialog.showAndWait();

            quizResult.ifPresent(skor -> {
                int expDidapat = skor * 15;
                penggunaSaatIni.addExp(expDidapat);
                showAlert(Alert.AlertType.INFORMATION, "Kuis Selesai!", 
                    "Skor Anda: " + skor + "/" + soalUntukKuis.size() + "\nAnda mendapatkan " + expDidapat + " EXP.");
                buatCatatanDanLanjut(siklus, "Kuis (Skor: " + skor + ")");
            });

        } else {
            // Pengguna memilih melewati kuis
            showAlert(Alert.AlertType.INFORMATION, "Hari Dilewati", "Anda memilih untuk beristirahat penuh hari ini.");
            buatCatatanDanLanjut(siklus, "Istirahat penuh");
        }
    }
    
    private void buatCatatanDanLanjut(SiklusMingguan siklus, String deskripsiAktivitas) {
        String namaProgram = siklus.getNamaProgram();
        int hariKe = siklus.getHariSaatIniIndex() + 1;
        
        CatatanLatihan catatan = new CatatanLatihan(penggunaSaatIni.getNama(), deskripsiAktivitas, hariKe, namaProgram);
        penggunaSaatIni.getRiwayat().tambahkanCatatan(catatan);

        siklus.majuKeHariBerikutnya();
        updateProgramPane();
    }
    
    // ... (sisa metode seperti handleCreateProgram, prosesPemberianBadge, setupHistoryTable, dll tetap sama) ...
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
    
    private void setupHistoryTable() {
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