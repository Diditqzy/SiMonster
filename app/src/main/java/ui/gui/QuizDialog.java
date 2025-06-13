package ui.gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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

        setTitle("Sesi Kuis Hari Istirahat");
        setHeaderText("Jawab pertanyaan berikut untuk mendapatkan EXP tambahan!");

        setupUI();
        displaySoal();

        // Mengatur hasil dialog. Hasilnya adalah skor (Integer).
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.FINISH) {
                return skor;
            }
            return null;
        });
    }

    private void setupUI() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        soalLabel = new Label();
        soalLabel.setWrapText(true);
        soalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        pilihanGroup = new ToggleGroup();
        pilihanBox = new VBox(10); // Spasi 10px antar pilihan

        nextButton = new Button("Selanjutnya");
        nextButton.setOnAction(e -> handleNextButton());
        
        VBox mainLayout = new VBox(20, soalLabel, pilihanBox, nextButton);
        getDialogPane().setContent(mainLayout);
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL); // Tombol untuk batal
    }

    private void displaySoal() {
        Soal soal = soalList.get(currentSoalIndex);
        soalLabel.setText("Soal " + (currentSoalIndex + 1) + ": " + soal.getPertanyaan());

        pilihanBox.getChildren().clear();
        soal.getPilihanJawaban().forEach(pilihan -> {
            RadioButton rb = new RadioButton(pilihan);
            rb.setToggleGroup(pilihanGroup);
            pilihanBox.getChildren().add(rb);
        });

        if (currentSoalIndex == soalList.size() - 1) {
            nextButton.setText("Selesai");
        }
    }

    private void handleNextButton() {
        RadioButton selectedRadioButton = (RadioButton) pilihanGroup.getSelectedToggle();
        if (selectedRadioButton == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Pilih salah satu jawaban!");
            alert.showAndWait();
            return;
        }

        Soal soal = soalList.get(currentSoalIndex);
        String jawabanPengguna = getJawabanFromText(selectedRadioButton.getText());
        
        if (soal.cekJawaban(jawabanPengguna)) {
            skor++;
        }

        currentSoalIndex++;
        if (currentSoalIndex < soalList.size()) {
            displaySoal();
        } else {
            // Jika kuis selesai
            setResult(skor);
            close();
        }
    }

    // Helper untuk mengubah jawaban dari "a. Teks Jawaban" menjadi "a"
    private String getJawabanFromText(String text) {
        // Logika ini disesuaikan dengan format soal dan jawaban Anda
        // Diasumsikan jawaban adalah huruf pertama (a, b, c)
        if (text != null && text.length() > 1 && text.charAt(1) == '.') {
            return String.valueOf(text.charAt(0));
        }
        return text; // Fallback
    }
}