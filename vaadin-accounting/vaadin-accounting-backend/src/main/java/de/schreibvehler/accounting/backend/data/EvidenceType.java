package de.schreibvehler.accounting.backend.data;


public enum EvidenceType {
    LVChr("Lebensversicherung Christian"),
    RLVChr("Riskolebensversicherung Christian"),
    KfzV("Kfz Versicherung"),
    GehaltChr("Gehalt Christian"),
    GehaltPetra("Gehalt Petra"),
    Kindergeld("Kindergeld"),
    sonstiges("Sonstiges");
    
    private final String description;
    
    private EvidenceType(String desc) {
        description = desc;
    }
    
    public String getDescription() {
        return description;
    }
}
