package model.latihan;

public abstract class ProgramLatihan {
    protected String namaProgram;
    
    public ProgramLatihan(String namaProgram) {
        this.namaProgram = namaProgram;
    }

    public String getNamaProgram() { 
        return namaProgram; 
    }

    public abstract SiklusMingguan getSiklusMingguan();
}