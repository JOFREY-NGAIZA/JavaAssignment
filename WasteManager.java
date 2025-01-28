import javax.swing.*;
import java.util.ArrayList;

public class WasteManager {
    private ArrayList<WasteRecord> wasteRecords;

    public WasteManager() {
        wasteRecords = new ArrayList<>();
    }

    // Add waste record to the list
    public void addWasteRecord(String wasteType, DefaultListModel<String> listModel) {
        String disposalMessage = categorizeWaste(wasteType.toLowerCase());
        WasteRecord newRecord = new WasteRecord(wasteType, disposalMessage);
        wasteRecords.add(newRecord);
        listModel.addElement(newRecord.toString());
    }

    // Delete selected record
    public void deleteWasteRecord(int index, DefaultListModel<String> listModel) {
        if (index != -1) {
            wasteRecords.remove(index);
            listModel.remove(index);
        }
    }

    // Edit selected record
    public void editWasteRecord(int index, String newWasteType, DefaultListModel<String> listModel) {
        if (index != -1 && newWasteType != null && !newWasteType.isEmpty()) {
            WasteRecord selectedRecord = wasteRecords.get(index);
            String newDisposalMessage = categorizeWaste(newWasteType.toLowerCase());
            selectedRecord.setWasteType(newWasteType.toLowerCase());
            selectedRecord.setDisposalMethod(newDisposalMessage);
            listModel.set(index, selectedRecord.toString());
        }
    }

    // Search for a waste record
    public void searchWasteRecord(String searchQuery, DefaultListModel<String> listModel) {
        listModel.clear();
        for (WasteRecord record : wasteRecords) {
            if (record.getWasteType().contains(searchQuery.toLowerCase())) {
                listModel.addElement(record.toString());
            }
        }
    }

    // Method to categorize waste and provide disposal advice
    private String categorizeWaste(String wasteType) {
        switch (wasteType) {
            case "organic":
            case "food":
            case "biodegradable":
                return "Disposal Method: Compost or Biodegradable Waste Bin.";
            case "plastic":
            case "glass":
            case "metal":
            case "paper":
                return "Disposal Method: Recycle in appropriate bins.";
            case "hazardous":
            case "toxic":
            case "chemical":
                return "Disposal Method: Handle with care. Use specialized facilities.";
            case "non-recyclable":
            case "general":
                return "Disposal Method: Landfill waste bin.";
            default:
                return "Unknown waste type. Please categorize properly.";
        }
    }
}
