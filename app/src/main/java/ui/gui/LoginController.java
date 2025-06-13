package ui.gui;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.user.Pengguna;

import java.io.IOException;
import java.util.Map;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Hyperlink registerLink;
    @FXML private Label statusLabel;

    private MainApp mainApp;
    private Map<String, Pengguna> basisDataPengguna;

    public void setMainApp(MainApp mainApp, Map<String, Pengguna> basisDataPengguna) {
        this.mainApp = mainApp;
        this.basisDataPengguna = basisDataPengguna;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Tidak Lengkap", "Username dan password tidak boleh kosong.");
            return;
        }
        
        Pengguna pengguna = basisDataPengguna.get(username);
        
        if (pengguna != null && pengguna.getPassword().equals(password)) {
            try {
                mainApp.showMainMenuView(pengguna);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Kesalahan Aplikasi", "Gagal memuat halaman utama.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Gagal", "Username atau password yang Anda masukkan salah.");
        }
    }

    @FXML
    private void handleRegisterLink() {
        try {
            mainApp.showRegisterView();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Kesalahan Aplikasi", "Gagal memuat halaman registrasi.");
        }
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
