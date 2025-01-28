import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WasteManagementApp {

    // Data structure to hold waste records
    private ArrayList<WasteRecord> wasteRecords = new ArrayList<>();

    // Main method to launch the app
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WasteManagementApp().createUI());
    }

    // Create UI for Waste Management App
    public void createUI() {
        // Create main frame (window)
        JFrame frame = new JFrame("Waste Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        // Create a title bar panel with a title and an image
        JPanel titleBarPanel = new JPanel(new BorderLayout());
        titleBarPanel.setBackground(new Color(165, 65, 50)); // SeaGreen color
        titleBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding

        // Title label (left side)
        JLabel titleLabel = new JLabel("Waste Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        // Image label (right side)
        JLabel imageLabel = new JLabel(new ImageIcon(
            new ImageIcon("images/waste.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)),
            SwingConstants.RIGHT);

        
        // Add title and image to the title bar panel
        titleBarPanel.add(titleLabel, BorderLayout.WEST);
        titleBarPanel.add(imageLabel, BorderLayout.EAST);

        // Create a center panel for input and buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding for the center panel

        JLabel instructionLabel = new JLabel("Enter the waste type below:", JLabel.CENTER);
        JTextField wasteInput = new JTextField();
        wasteInput.setPreferredSize(new Dimension(200, 30));

        // Create buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton submitButton = new JButton("Add Record");
        submitButton.setBackground(new Color(102, 205, 170)); // SeaGreen color
        submitButton.setForeground(Color.WHITE);

        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setBackground(new Color(255, 99, 71)); // Tomato color
        deleteButton.setForeground(Color.WHITE);

        JButton editButton = new JButton("Edit Selected");
        editButton.setBackground(new Color(135, 206, 235)); // SkyBlue color
        editButton.setForeground(Color.WHITE);

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(255, 215, 0)); // Gold color
        searchButton.setForeground(Color.BLACK);

        // Add buttons to the panel
        buttonPanel.add(submitButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(searchButton);

        centerPanel.add(instructionLabel);
        centerPanel.add(wasteInput);
        centerPanel.add(buttonPanel);

        // Create bottom panel for the list of waste records
        JPanel listPanel = new JPanel(new BorderLayout());
        JLabel listLabel = new JLabel("Waste Records:", JLabel.CENTER);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> wasteList = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(wasteList);
        listScrollPane.setPreferredSize(new Dimension(400, 150));

        listPanel.add(listLabel, BorderLayout.NORTH);
        listPanel.add(listScrollPane, BorderLayout.CENTER);

        // Add components to the frame
        frame.add(titleBarPanel, BorderLayout.NORTH); // Add title bar to the top
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(listPanel, BorderLayout.SOUTH);

        // Add action listener to the submit button (Add Record)
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String wasteType = wasteInput.getText().toLowerCase();

                if (!wasteType.isEmpty()) {
                    String disposalMessage = categorizeWaste(wasteType);
                    JOptionPane.showMessageDialog(frame, disposalMessage, "Disposal Advice", JOptionPane.INFORMATION_MESSAGE);

                    // Create a new record and add it to the list
                    WasteRecord newRecord = new WasteRecord(wasteType, disposalMessage);
                    wasteRecords.add(newRecord);
                    listModel.addElement(newRecord.toString());

                    wasteInput.setText(""); // Clear input field
                } else {
                    showErrorDialog(frame, "Please enter a valid waste type.");
                }
            }
        });

        // Delete selected record
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = wasteList.getSelectedIndex();
                if (selectedIndex != -1) {
                    wasteRecords.remove(selectedIndex); // Remove from the list
                    listModel.remove(selectedIndex); // Remove from the displayed list
                } else {
                    showErrorDialog(frame, "No item selected to delete.");
                }
            }
        });

        // Edit selected record
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = wasteList.getSelectedIndex();
                if (selectedIndex != -1) {
                    WasteRecord selectedRecord = wasteRecords.get(selectedIndex);
                    String newWasteType = JOptionPane.showInputDialog(frame, "Edit Waste Type:", selectedRecord.getWasteType());

                    if (newWasteType != null && !newWasteType.isEmpty()) {
                        String newDisposalMessage = categorizeWaste(newWasteType.toLowerCase());
                        selectedRecord.setWasteType(newWasteType.toLowerCase());
                        selectedRecord.setDisposalMethod(newDisposalMessage);

                        listModel.set(selectedIndex, selectedRecord.toString()); // Update the displayed list
                        JOptionPane.showMessageDialog(frame, "Record updated successfully.");
                    }
                } else {
                    showErrorDialog(frame, "No item selected to edit.");
                }
            }
        });

        // Search for a waste record
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = JOptionPane.showInputDialog(frame, "Enter waste type to search:");
                if (searchQuery != null && !searchQuery.isEmpty()) {
                    listModel.clear();
                    boolean found = false;
                    for (WasteRecord record : wasteRecords) {
                        if (record.getWasteType().contains(searchQuery.toLowerCase())) {
                            listModel.addElement(record.toString());
                            found = true;
                        }
                    }
                    if (!found) {
                        JOptionPane.showMessageDialog(frame, "No records found for: " + searchQuery, "Search Results", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }

    // Method to categorize waste and provide disposal advice
    public String categorizeWaste(String wasteType) {
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

    // Show error dialog
    private void showErrorDialog(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // WasteRecord class to store waste information
    class WasteRecord {
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
}
