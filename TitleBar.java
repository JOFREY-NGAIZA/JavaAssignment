import javax.swing.*;
import java.awt.*;

public class TitleBar {
    private JPanel titleBarPanel;

    public TitleBar(String title, String imagePath) {
        titleBarPanel = new JPanel(new BorderLayout());
        titleBarPanel.setBackground(new Color(60, 179, 113)); // SeaGreen color
        titleBarPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding

        // Title label (left side)
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        // Image label (right side)
        ImageIcon icon = new ImageIcon(imagePath); // Replace with the path to your image
        JLabel imageLabel = new JLabel(icon);

        titleBarPanel.add(titleLabel, BorderLayout.WEST);
        titleBarPanel.add(imageLabel, BorderLayout.EAST);
    }

    public JPanel getTitleBarPanel() {
        return titleBarPanel;
    }
}
