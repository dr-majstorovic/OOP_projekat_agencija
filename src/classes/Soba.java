package classes;

public enum Soba {
    JEDNOKREVETNA,
    DVOKREVETNA,
    TROKREVETNA,
    APARTMAN;

    public static Soba odOznake(String oznaka){
        switch (oznaka){
            case "JEDNOKREVETNA": return JEDNOKREVETNA;
            case "DVOKREVETNA": return DVOKREVETNA;
            case "TROKREVETNA": return TROKREVETNA;
            case "APARTMAN": return APARTMAN;
            default: return null;
        }
    }
}
