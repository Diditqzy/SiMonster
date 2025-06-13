package ui.gui;

import data.ManajemenData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.user.Pengguna;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterController {

    @FXML private TextField fullNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button registerButton;
    @FXML private Hyperlink loginLink;

    private MainApp mainApp;
    private Map<String, Pengguna> basisDataPengguna;

    public void setMainApp(MainApp mainApp, Map<String, Pengguna> basisDataPengguna) {
        this.mainApp = mainApp;
        this.basisDataPengguna = basisDataPengguna;
    }

    @FXML
    private void handleRegister() {
        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Tidak Lengkap", "Semua kolom harus diisi.");
            return;
        }

        if (basisDataPengguna.containsKey(username)) {
            showAlert(Alert.AlertType.ERROR, "Registrasi Gagal", "Username '" + username + "' sudah digunakan oleh pengguna lain.");
            return;
        }

        if (!isPasswordValid(password)) {
            return; // Pesan error sudah ditampilkan di dalam isPasswordValid
        }

        Pengguna penggunaBaru = new Pengguna(username, password);
        penggunaBaru.setNama(fullName);

        basisDataPengguna.put(username, penggunaBaru);
        ManajemenData.simpanDataPengguna(basisDataPengguna); // Langsung simpan

        showAlert(Alert.AlertType.INFORMATION, "Registrasi Berhasil", "Akun baru Anda telah berhasil dibuat. Silakan login.");
        
        handleLoginLink(); // Arahkan kembali ke halaman login
    }

    @FXML
    private void handleLoginLink() {
        try {
            mainApp.showLoginView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Password Tidak Valid", "Password minimal harus 8 karakter.");
            return false;
        }
        boolean hasLetter = Pattern.compile(".*[a-zA-Z].*").matcher(password).matches();
        boolean hasDigit = Pattern.compile(".*[0-9].*").matcher(password).matches();

        if (!hasLetter || !hasDigit) {
            showAlert(Alert.AlertType.ERROR, "Password Tidak Valid", "Password harus mengandung kombinasi huruf dan angka.");
            return false;
        }
        return true;
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}