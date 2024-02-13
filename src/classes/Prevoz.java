package classes;

public enum Prevoz {
    AVION,
    AUTOBUS,
    SAMOSTALAN;

    public static Prevoz odOznake(String oznaka){
        switch (oznaka){
            case "AVION": return AVION;
            case "AUTOBUS": return AUTOBUS;
            case "SAMOSTALAN": return SAMOSTALAN;
            default: return null;
        }
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
