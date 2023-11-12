import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CDCollectionGUI extends JFrame {
    private CDCollection cdCollection;

    private JTextField titleField;
    private JTextField artistField;
    private JTextArea displayArea;
    private JTextField yearField;

    public CDCollectionGUI() {
        // Initialize CD collection and load CDs from the database
        cdCollection = new CDCollection();
        cdCollection.loadCDsFromDatabase();

        // Initialize GUI components
        titleField = new JTextField(20);
        artistField = new JTextField(20);
        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        yearField = new JTextField(4);

        // Create buttons for various actions
        JButton addButton = new JButton("Add CD");
        JButton updateButton = new JButton("Update CD");
        JButton removeButton = new JButton("Remove CD");
        JButton displayButton = new JButton("Display Collection");

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCD();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCD();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCD();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCollection();
            }
        });

        // Create and configure the GUI layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Artist:"));
        panel.add(artistField);
        panel.add(new JLabel("Year:"));
        panel.add(yearField);
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(removeButton);
        panel.add(displayButton);

        panel.setForeground(Color.WHITE);

        // Set up the main frame
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        setTitle("CD-Collection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to add a new CD to the collection
    private void addCD() {
        // Get user input
        String title = titleField.getText();
        String artist = artistField.getText();
        String yearText = yearField.getText();

        // Check for empty fields
        if (!title.isEmpty() && !artist.isEmpty() && !yearText.isEmpty()) {
            // Parse year as an integer
            int year = Integer.parseInt(yearText);
            CD newCD = new CD(title, artist, year);

            // Check for duplicate entry
            if (cdCollection.contains(newCD)) {
                JOptionPane.showMessageDialog(this, "This CD already exists in the Collection!");
            } else {
                // Add CD to the collection and update the display
                cdCollection.addCD(newCD);
                cdCollection.addCDToDatabase(newCD);
                displayArea.append("CD added: " + newCD + "\n");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Title, artist, and year must not be empty.");
        }
    }

    // Method to update an existing CD in the collection
    private void updateCD() {
        // Get user input
        String title = titleField.getText();
        String artist = artistField.getText();
        String yearText = yearField.getText();

        // Check for empty fields
        if (!title.isEmpty() && !artist.isEmpty() && !yearText.isEmpty()) {
            // Parse year as an integer
            int year = Integer.parseInt(yearText);
            CD oldCD = new CD(title, artist, year);
            CD updatedCD = new CD("(Updated): " + title, artist, year);

            // Update the CD in the collection and update the display
            cdCollection.updateCD(oldCD, updatedCD);
            cdCollection.updateCDInDatabase(oldCD, updatedCD);
            displayArea.append("CD updated: " + updatedCD + "\n");
        } else {
            JOptionPane.showMessageDialog(this, "Title, artist, and year must not be empty.");
        }
    }

    // Method to remove a CD from the collection
    private void removeCD() {
        // Get user input
        String title = titleField.getText();
        String artist = artistField.getText();
        String yearText = yearField.getText();

        // Check for empty fields
        if (!title.isEmpty() && !artist.isEmpty() && !yearText.isEmpty()) {
            // Parse year as an integer
            int year = Integer.parseInt(yearText);
            CD cdToRemove = new CD(title, artist, year);

            // Check whether CD exists before attempting to remove
            boolean removedSuccessfully = cdCollection.removeCD(cdToRemove);
            if (removedSuccessfully) {
                cdCollection.removeCDFromDatabase(cdToRemove);
                displayArea.append("CD removed: " + cdToRemove + "\n");
            } else {
                JOptionPane.showMessageDialog(this, "CD does not exist in the collection.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Title, artist, and year must not be empty.");
        }
    }

    // Method to display the entire CD collection
    private void showCollection() {
        displayArea.setText("");
        displayArea.append(cdCollection.displayCollection());
    }

    public static void main(String[] args) {
        // Launch the GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CDCollectionGUI();
            }
        });
    }
}
