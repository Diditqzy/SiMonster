package model.latihan;

public class Latihan {
    private String namaLatihan;
    private int set;
    private String rep;
    private String tingkatKesulitan;
    private int exp;

    public Latihan(String namaLatihan, int set, String rep, String tingkatKesulitan, int exp) {
        this.namaLatihan = namaLatihan;
        this.set = set;
        this.rep = rep;
        this.tingkatKesulitan = tingkatKesulitan;
        this.exp = exp;
    }

    public String getNamaLatihan() { return namaLatihan; }
    public int getExp() { return exp; }
    
    public int getSet() { return set; }
    public String getRep() { return rep; }

    public void doLatihan() {
        System.out.printf("--> %-35s | %d set x %-12s || %d exp\n", namaLatihan, set, rep, exp);
    }
}