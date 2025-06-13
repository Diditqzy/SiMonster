package model.user;


public interface AktivitasPengguna {
    /**
     * Mendefinisikan aksi untuk mendaftarkan pengguna baru.
     */
    void registrasi();

    /**
     * Mendefinisikan aksi untuk login pengguna.
     */
    void login();

    /**
     * Mendefinisikan aksi untuk keluar dari aplikasi.
     */
    void keluarAplikasi();
}