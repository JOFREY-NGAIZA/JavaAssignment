public class WasteRecord {
    private String wasteType;
    private String disposalMethod;

    public WasteRecord(String wasteType, String disposalMethod) {
        this.wasteType = wasteType;
        this.disposalMethod = disposalMethod;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public String getDisposalMethod() {
        return disposalMethod;
    }

    public void setDisposalMethod(String disposalMethod) {
        this.disposalMethod = disposalMethod;
    }

    @Override
    public String toString() {
        return "Waste Type: " + wasteType + ", Disposal: " + disposalMethod;
    }
}
