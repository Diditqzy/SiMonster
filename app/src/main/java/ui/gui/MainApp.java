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

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Muat data pengguna dari file saat aplikasi dimulai
        basisDataPengguna = ManajemenData.muatDataPengguna();

        primaryStage.setTitle("SIMONSTER - Aplikasi Fitness");
        showLoginView(); // Tampilkan layar login pertama kali
        primaryStage.show();
    }

    /**
     * Menampilkan layar login.
     */
    public void showLoginView() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/LoginView.fxml")));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.setMainApp(this, basisDataPengguna);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }

    /**
     * Menampilkan layar registrasi.
     */
    public void showRegisterView() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/RegisterView.fxml")));
        Parent root = loader.load();

        RegisterController controller = loader.getController();
        controller.setMainApp(this, basisDataPengguna);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }

    /**
     * Menampilkan dashboard utama setelah login berhasil.
     * @param pengguna Pengguna yang sedang login.
     */
    public void showMainMenuView(Pengguna pengguna) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/MainMenuView.fxml")));
        Parent root = loader.load();

        MainMenuController controller = loader.getController();
        controller.initData(this, pengguna, basisDataPengguna);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }


    /**
     * Menyimpan data pengguna saat aplikasi ditutup.
     */
    @Override
    public void stop() throws Exception {
        System.out.println("Menyimpan semua data sebelum keluar...");
        ManajemenData.simpanDataPengguna(basisDataPengguna);
        System.out.println("Data berhasil disimpan. Sampai jumpa!");
    }

    public static void main(String[] args) {
        launch(args);
    }
}