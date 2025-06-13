package ui.gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.soal.Soal;

import java.util.List;

public class QuizDialog extends Dialog<Integer> {
    private List<Soal> soalList;
    private int currentSoalIndex = 0;
    private int skor = 0;

    private Label soalLabel;
    private ToggleGroup pilihanGroup;
    private VBox pilihanBox;
    private Button nextButton;

    public QuizDialog(List<Soal> soalList) {
        this.soalList = soalList;

        // Menerapkan style ke dialog
        DialogPane dialogPane = getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        dialogPane.getStyleClass().add("quiz-dialog"); // Class khusus untuk kuis
        
        setTitle("Sesi Kuis Hari Istirahat");
        setHeaderText("Jawab pertanyaan berikut!");

        setupUI();
        displaySoal();

        setResultConverter(dialogButton -> {
            if (dialogButton.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                return skor;
            }
            return null; // Jika dibatalkan
        });
    }

    private void setupUI() {
        soalLabel = new Label();
        soalLabel.setWrapText(true);
        soalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #333;");

        pilihanGroup = new ToggleGroup();
        pilihanBox = new VBox(10); // Spasi 10px antar pilihan

        VBox mainLayout = new VBox(20, soalLabel, pilihanBox);
        mainLayout.setPadding(new Insets(10));
        getDialogPane().setContent(mainLayout);

        // Mengatur tombol-tombol
        ButtonType nextButtonType = new ButtonType("Selanjutnya", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(nextButtonType, ButtonType.CANCEL);

        final Button nextBtn = (Button) getDialogPane().lookupButton(nextButtonType);
        nextBtn.addEventFilter(javafx.event.ActionEvent.ACTION, event -> {
            // Logika ini dijalankan SEBELUM dialog ditutup
            RadioButton selectedRadioButton = (RadioButton) pilihanGroup.getSelectedToggle();
            if (selectedRadioButton == null) {
                showAlert(Alert.AlertType.WARNING, "Pilihan Kosong", "Pilih salah satu jawaban!");
                event.consume(); // Mencegah dialog tertutup
                return;
            }
            handleAnswer(selectedRadioButton.getText());
            
            if (currentSoalIndex < soalList.size()) {
                displaySoal();
                event.consume(); // Mencegah dialog tertutup karena masih ada soal
            } else {
                // Kuis selesai, biarkan dialog tertutup
                nextBtn.setText("Selesai");
            }
        });
    }

    private void displaySoal() {
        Soal soal = soalList.get(currentSoalIndex);
        soalLabel.setText("Soal " + (currentSoalIndex + 1) + ": " + soal.getPertanyaan());

        pilihanBox.getChildren().clear();
        pilihanGroup.selectToggle(null); // Reset pilihan
        
        char optionChar = 'a';
        for (String pilihan : soal.getPilihanJawaban()) {
            RadioButton rb = new RadioButton(optionChar + ". " + pilihan);
            rb.setUserData(String.valueOf(optionChar)); // Simpan 'a', 'b', 'c' sebagai data
            rb.setToggleGroup(pilihanGroup);
            pilihanBox.getChildren().add(rb);
            optionChar++;
        }
    }
    
    private void handleAnswer(String selectedText) {
        RadioButton selectedRadioButton = (RadioButton) pilihanGroup.getSelectedToggle();
        String jawabanPengguna = (String) selectedRadioButton.getUserData();
        
        Soal soal = soalList.get(currentSoalIndex);
        if (soal.cekJawaban(jawabanPengguna)) {
            skor++;
        }
        currentSoalIndex++;
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
    }
}