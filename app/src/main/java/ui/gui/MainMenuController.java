package ui.gui;

import data.ManajemenData;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.latihan.*;
import model.soal.BankSoal;
import model.soal.Soal;
import model.user.CatatanLatihan;
import model.user.Pengguna;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainMenuController {

    private MainApp mainApp;
    private Pengguna penggunaSaatIni;
    private Map<String, Pengguna> basisDataPengguna;

    // ... (Variabel FXML lainnya tetap sama)
    @FXML private Label welcomeLabel;
    @FXML private Label levelLabel;
    @FXML private Label expLabel;
    @FXML private Label badgeLabel;
    
    @FXML private VBox dashboardPane;
    @FXML private VBox programPane;
    @FXML private VBox historyPane;
    
    @FXML private VBox programDetailsContainer;
    
    @FXML private ScrollPane historyScrollPane;
    @FXML private VBox historyContainer;
    
    @FXML private Button doExerciseButton;
    @FXML private Button createProgramButton;

    // (Metode inti lainnya tetap sama)
    public void initData(MainApp mainApp, Pengguna pengguna, Map<String, Pengguna> basisDataPengguna) {
        this.mainApp = mainApp;
        this.penggunaSaatIni = pengguna;
        this.basisDataPengguna = basisDataPengguna;
        updateProfileUI();
        updateProgramPane();
        showDashboard();
    }
    
    private void updateProfileUI() {
        welcomeLabel.setText("Halo, " + penggunaSaatIni.getNama() + "!");
        levelLabel.setText("Level: " + penggunaSaatIni.getLevel());
        expLabel.setText(String.format("EXP: %d / %d", penggunaSaatIni.getExp(), penggunaSaatIni.getLevel() * 100));
        badgeLabel.setText("Badge: " + penggunaSaatIni.getBadge());
    }

    @FXML private void handleLogout() {
        try {
            ManajemenData.simpanDataPengguna(basisDataPengguna);
            mainApp.showLoginView();
        } catch (IOException e) { e.printStackTrace(); }
    }
    
    // === METODE NAVIGASI DENGAN ANIMASI ===
    @FXML private void showDashboard() {
        switchPane(dashboardPane);
    }
    
    @FXML private void showProgram() {
        updateProgramPane();
        switchPane(programPane);
    }

    @FXML private void showHistory() {
        loadHistoryData();
        switchPane(historyPane);
    }
    
    // Metode helper baru untuk transisi antar panel
    private void switchPane(Node targetPane) {
        dashboardPane.setVisible(false);
        programPane.setVisible(false);
        historyPane.setVisible(false);
        
        targetPane.setVisible(true);
        fadeIn(targetPane);
    }

    // Metode helper untuk animasi fade-in
    private void fadeIn(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(500), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }
    
    // Metode helper untuk animasi "pop" pada label
    private void popAnimation(Node node) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150), node);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToX(1.2);
        st.setToY(1.2);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }


    private void updateProgramPane() {
        programDetailsContainer.getChildren().clear();
        
        SiklusMingguan siklus = penggunaSaatIni.getSiklusSaatIni();
        if (siklus == null) {
            Label noProgramLabel = new Label("Anda belum memiliki program latihan aktif. Buat program baru untuk memulai!");
            noProgramLabel.getStyleClass().add("program-title-label");
            programDetailsContainer.getChildren().add(noProgramLabel);
            doExerciseButton.setVisible(false);
            createProgramButton.setVisible(true);
        } else if (siklus.isSelesai()) {
            prosesPemberianBadge(siklus);
            penggunaSaatIni.setSiklusSaatIni(null);
            updateProgramPane();
        } else {
            int hariKe = siklus.getHariSaatIniIndex() + 1;
            AktivitasHarian aktivitas = siklus.getHariSaatIni();
            
            Label programTitle = new Label("Program Aktif: " + siklus.getNamaProgram());
            programTitle.getStyleClass().add("program-title-label");

            Label dayStatus = new Label("Hari ke-" + hariKe + ": " + (aktivitas instanceof HariBertarung ? "HARI BERTARUNG" : "HARI ISTIRAHAT"));
            dayStatus.getStyleClass().add("program-day-label");

            programDetailsContainer.getChildren().addAll(programTitle, dayStatus);
            
            if (aktivitas instanceof HariBertarung) {
                Label taskHeader = new Label("Daftar Latihan Hari Ini:");
                taskHeader.getStyleClass().add("task-list-header");
                programDetailsContainer.getChildren().add(taskHeader);

                VBox taskListVBox = new VBox(5);
                ((HariBertarung) aktivitas).getDaftarLatihanHarian().forEach(latihan -> {
                    taskListVBox.getChildren().add(createTaskItem(latihan));
                });
                
                ScrollPane scrollPane = new ScrollPane(taskListVBox);
                scrollPane.setFitToWidth(true);
                scrollPane.getStyleClass().add("task-scroll-pane");
                VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);
                programDetailsContainer.getChildren().add(scrollPane);

            } else {
                Label restDayLabel = new Label("Gunakan hari ini untuk memulihkan tenaga dan menguji pengetahuanmu!");
                restDayLabel.getStyleClass().add("rest-day-label");
                programDetailsContainer.getChildren().add(restDayLabel);
            }
            
            doExerciseButton.setText("Mulai Aktivitas Hari ke-" + hariKe);
            doExerciseButton.setVisible(true);
            createProgramButton.setVisible(false);
        }
        updateProfileUI();
    }
    
    private HBox createTaskItem(Latihan latihan) {
        SVGPath icon = new SVGPath();
        icon.setContent("M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z");
        icon.getStyleClass().add("task-item-icon");
        
        Label taskLabel = new Label(latihan.getNamaLatihan());
        taskLabel.getStyleClass().add("task-item-label");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        
        Label repsLabel = new Label("Set: " + latihan.getSet() + "   Reps: " + latihan.getRep());
        repsLabel.getStyleClass().add("task-item-reps-label");
        
        HBox hbox = new HBox(10, icon, taskLabel, spacer, repsLabel);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(8));
        hbox.getStyleClass().add("task-item-box");
        return hbox;
    }

    @FXML private void handleDoExercise() {
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
        popAnimation(expLabel); // Animasi EXP
        
        String daftarLatihanStr = aktivitas.getDaftarLatihanHarian()
            .stream()
            .map(Latihan::getNamaLatihan)
            .collect(Collectors.joining(", "));
        buatCatatanDanLanjut(siklus, daftarLatihanStr);
    }
    
    private void handleQuizDay(SiklusMingguan siklus) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        styleDialog(confirmDialog.getDialogPane());
        confirmDialog.setTitle("Hari Istirahat");
        confirmDialog.setHeaderText("Ini adalah hari istirahat. Mau mengerjakan kuis?");
        confirmDialog.setContentText("Pilih jawaban Anda:");

        ButtonType buttonTypeYes = new ButtonType("Kerjakan Kuis");
        ButtonType buttonTypeNo = new ButtonType("Lewati Saja", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmDialog.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            int hariKe = siklus.getHariSaatIniIndex() + 1;
            int tingkatKesulitan = (hariKe / 2);
            
            String namaProgram = siklus.getNamaProgram();
            String kategoriKuis = namaProgram; 

            if (namaProgram.equalsIgnoreCase("Full Body")) {
                kategoriKuis = "Upper Body";
            }
            
            List<Soal> soalUntukKuis = BankSoal.getSoal(kategoriKuis, tingkatKesulitan, 2);

            if (soalUntukKuis.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Tidak Ada Kuis", "Maaf, tidak ada kuis yang tersedia untuk kategori ini (" + kategoriKuis + "). Selamat beristirahat!");
                penggunaSaatIni.addExp(10); popAnimation(expLabel);
                buatCatatanDanLanjut(siklus, "Istirahat (tanpa kuis)");
                return;
            }
            
            QuizDialog quizDialog = new QuizDialog(soalUntukKuis);
            Optional<Integer> quizResult = quizDialog.showAndWait();

            quizResult.ifPresent(skor -> {
                int expDidapat = skor * 15;
                penggunaSaatIni.addExp(expDidapat); popAnimation(expLabel);
                showAlert(Alert.AlertType.INFORMATION, "Kuis Selesai!", 
                    "Skor Anda: " + skor + "/" + soalUntukKuis.size() + "\nAnda mendapatkan " + expDidapat + " EXP.");
                buatCatatanDanLanjut(siklus, "Kuis (Skor: " + skor + ")");
            });

        } else {
            showAlert(Alert.AlertType.INFORMATION, "Hari Dilewati", "Anda memilih untuk beristirahat penuh hari ini.");
            buatCatatanDanLanjut(siklus, "Istirahat penuh");
        }
    }
    private void buatCatatanDanLanjut(SiklusMingguan siklus, String deskripsiAktivitas) {
        // Cek apakah ada level up sebelum maju ke hari berikutnya
        int levelSebelum = penggunaSaatIni.getLevel();
        
        String namaProgram = siklus.getNamaProgram();
        int hariKe = siklus.getHariSaatIniIndex() + 1;
        
        CatatanLatihan catatan = new CatatanLatihan(penggunaSaatIni.getNama(), deskripsiAktivitas, hariKe, namaProgram);
        penggunaSaatIni.getRiwayat().tambahkanCatatan(catatan);

        siklus.majuKeHariBerikutnya();
        
        // Perbarui UI dan cek level up
        updateProfileUI(); 
        if(penggunaSaatIni.getLevel() > levelSebelum) {
            popAnimation(levelLabel); // Animasi Level Up
        }
        updateProgramPane();
    }
    @FXML private void handleCreateProgram() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Full Body", "Full Body", "Upper Body", "Lower Body", "Otot Khusus");
        styleDialog(dialog.getDialogPane());
        dialog.setTitle("Buat Program Baru");
        dialog.setHeaderText("Pilih jenis program latihan Anda.");
        dialog.setContentText("Pilihan:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(pilihan -> {
            ProgramLatihan programPilihan = null;
            if (pilihan.equals("Otot Khusus")) { programPilihan = pilihOtotKhusus(); } 
            else if (pilihan.equals("Upper Body")) { programPilihan = new UpperBody(); } 
            else if (pilihan.equals("Lower Body")) { programPilihan = new LowerBody(); } 
            else { programPilihan = new FullBody(); }

            if (programPilihan != null) {
                penggunaSaatIni.setSiklusSaatIni(programPilihan.getSiklusMingguan());
                showAlert(Alert.AlertType.INFORMATION, "Program Dibuat", "Program '" + programPilihan.getNamaProgram() + "' berhasil dibuat!");
                updateProgramPane();
            }
        });
    }
    private ProgramLatihan pilihOtotKhusus() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Dada", "Dada", "Punggung", "Bisep", "Trisep", "Bahu", "Perut", "Kaki", "Lengan");
        styleDialog(dialog.getDialogPane());
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
            if (namaProgram.contains("Dada")) badgeBaru = "Benteng Perkasa"; else if (namaProgram.contains("Punggung")) badgeBaru = "Sayap Garuda"; else if (namaProgram.contains("Bisep")) badgeBaru = "Popeye"; else if (namaProgram.contains("Trisep")) badgeBaru = "Tapal Kuda"; else if (namaProgram.contains("Bahu")) badgeBaru = "Bahu Gatotkaca"; else if (namaProgram.contains("Perut")) badgeBaru = "Roti Sobek"; else if (namaProgram.contains("Kaki")) badgeBaru = "Kaki Gempa"; else if (namaProgram.contains("Lengan")) badgeBaru = "Lengan Meriam";
        } else if (namaProgram.equals("Full Body")) { badgeBaru = "TITAN";
        } else if (namaProgram.equals("Upper Body")) { badgeBaru = "Puncak Herkules";
        } else if (namaProgram.equals("Lower Body")) { badgeBaru = "Pondasi Tembok China"; }

        if (badgeBaru != null) {
            penggunaSaatIni.setBadge(badgeBaru);
            penggunaSaatIni.addExp(250); popAnimation(expLabel);
            showAlert(Alert.AlertType.INFORMATION, "PROGRAM SELESAI!", "Luar biasa! Anda telah menyelesaikan program: " + namaProgram + "\n" + "Anda mendapatkan badge baru: \"" + badgeBaru + "\" dan bonus 250 EXP!");
            popAnimation(badgeLabel); // Animasi badge baru
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Program Selesai", "Selamat, Anda telah menyelesaikan program " + namaProgram + "!");
        }
    }
    
    // === METODE DIPERBARUI DENGAN ANIMASI ===
    private void loadHistoryData() {
        historyContainer.getChildren().clear();
        List<CatatanLatihan> historyList = penggunaSaatIni.getRiwayat().getDaftarCatatan();
        
        Collections.sort(historyList, Comparator.comparing(CatatanLatihan::getTanggal).reversed());

        if (historyList.isEmpty()) {
            Label emptyLabel = new Label("Belum ada riwayat latihan yang tercatat.");
            emptyLabel.getStyleClass().add("rest-day-label");
            historyContainer.getChildren().add(emptyLabel);
        } else {
            SequentialTransition sequentialTransition = new SequentialTransition();
            for (int i = 0; i < historyList.size(); i++) {
                Node card = createHistoryCard(historyList.get(i));
                card.setOpacity(0); // Sembunyikan kartu awalnya
                historyContainer.getChildren().add(card);
                
                // Animasi fade in dan slide in dari bawah
                FadeTransition ft = new FadeTransition(Duration.millis(400), card);
                ft.setFromValue(0);
                ft.setToValue(1);

                TranslateTransition tt = new TranslateTransition(Duration.millis(400), card);
                tt.setFromY(50); // Mulai dari 50px di bawah
                tt.setToY(0);
                
                ParallelTransition pt = new ParallelTransition(ft, tt);
                pt.setDelay(Duration.millis(i * 100)); // Delay agar muncul satu per satu
                sequentialTransition.getChildren().add(pt);
            }
            sequentialTransition.play();
        }
    }
    
    private Node createHistoryCard(CatatanLatihan catatan) {
        VBox card = new VBox(10);
        card.getStyleClass().add("history-card");

        Label dateLabel = new Label(new SimpleDateFormat("dd MMMM yyyy").format(catatan.getTanggal()));
        dateLabel.getStyleClass().add("history-date");
        Label programLabel = new Label(catatan.getNamaProgram() + " - Hari ke-" + catatan.getHariKe());
        programLabel.getStyleClass().add("history-program");
        
        HBox header = new HBox(dateLabel, new Region(), programLabel);
        HBox.setHgrow(header.getChildren().get(1), javafx.scene.layout.Priority.ALWAYS);

        Label activityLabel = new Label(catatan.getDaftarLatihan());
        activityLabel.getStyleClass().add("history-details");
        activityLabel.setWrapText(true);

        card.getChildren().addAll(header, new Separator(), activityLabel);
        return card;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        styleDialog(alert.getDialogPane());
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void styleDialog(DialogPane dialogPane) {
        dialogPane.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        SVGPath graphic = new SVGPath();
        graphic.setContent("M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm1 15h-2v-6h2v6zm0-8h-2V7h2v2z");
        graphic.setStyle("-fx-fill: white; -fx-scale-x: 1.5; -fx-scale-y: 1.5;");
        StackPane pane = new StackPane(graphic);
        pane.setPrefSize(32, 32);
        dialogPane.setGraphic(pane);
    }
}