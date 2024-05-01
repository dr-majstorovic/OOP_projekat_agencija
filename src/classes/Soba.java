package classes;

public enum Soba {
    JEDNOKREVETNA,
    DVOKREVETNA,
    TROKREVETNA,
    APARTMAN;

    public static Soba odOznake(String oznaka){
        switch (oznaka){
            case "jednokevetna": return JEDNOKREVETNA;
            case "dvokrevetna": return DVOKREVETNA;
            case "trokrevetna": return TROKREVETNA;
            case "apartman": return APARTMAN;
            default: return null;
        }
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
