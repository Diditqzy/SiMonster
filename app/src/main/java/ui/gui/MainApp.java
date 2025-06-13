package ui.gui;

import data.ManajemenData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.user.Pengguna;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class MainApp extends Application {

    private Stage primaryStage;
    private static Map<String, Pengguna> basisDataPengguna;

    // Variabel untuk menyimpan ukuran jendela terakhir
    private double lastWidth = 800;  // Ukuran default awal untuk menu utama
    private double lastHeight = 600; // Ukuran default awal untuk menu utama

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        basisDataPengguna = ManajemenData.muatDataPengguna();

        primaryStage.setTitle("SIMONSTER - Aplikasi Fitness");
        showLoginView();
        primaryStage.show();

        // Tambahkan listener untuk mendeteksi perubahan ukuran jendela oleh pengguna
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (!primaryStage.isMaximized()) {
                lastWidth = newVal.doubleValue();
            }
        });
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (!primaryStage.isMaximized()) {
                lastHeight = newVal.doubleValue();
            }
        });
    }

    /**
     * Menampilkan layar login. Jendela akan secara otomatis menyesuaikan
     * ukurannya agar pas dengan konten login yang kecil.
     */
    public void showLoginView() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/LoginView.fxml")));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.setMainApp(this, basisDataPengguna);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        // Biarkan jendela menyesuaikan ukurannya untuk scene login
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
    }

    /**
     * Menampilkan layar registrasi.
     */
    public void showRegisterView() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/RegisterView.fxml")));
        Parent root = loader.load();

        RegisterController controller = loader.getController();
        controller.setMainApp(this, basisDataPengguna);

        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
    }

    /**
     * Menampilkan dashboard utama. Ukuran jendela akan diatur ke ukuran
     * terakhir yang disimpan.
     */
    public void showMainMenuView(Pengguna pengguna) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/MainMenuView.fxml")));
        Parent root = loader.load();

        MainMenuController controller = loader.getController();
        controller.initData(this, pengguna, basisDataPengguna);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        // Terapkan ukuran yang tersimpan saat pindah ke menu utama
        primaryStage.setWidth(lastWidth);
        primaryStage.setHeight(lastHeight);
        primaryStage.centerOnScreen();
    }


    @Override
    public void stop() throws Exception {
        System.out.println("Menyimpan semua data sebelum keluar...");
        ManajemenData.simpanDataPengguna(basisDataPengguna);
    }

    public static void main(String[] args) {
        launch(args);
    }
}