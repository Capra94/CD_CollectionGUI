import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CDCollection {

    private static final String JDBC_URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

    private ArrayList<CD> cdList;

    public CDCollection() {
        cdList = new ArrayList<>();
    }



    public boolean contains(CD cd) {
        return cdList.contains(cd);
    }

    public void addCD(CD newCD) {
        if (!cdList.contains(newCD)) {
            cdList.add(newCD);
            System.out.println("CD added successfully");
        }
    }

    public void updateCD(CD oldCD, CD updatedCD) {
        if (cdList.contains(oldCD)) {
            int index = cdList.indexOf(oldCD);
            cdList.set(index, updatedCD);
            System.out.println("CD updated successfully");
        } else {
            System.out.println("The given CD is not in the collection");
        }
    }

    public boolean removeCD(CD cdToRemove) {
        if (cdList.contains(cdToRemove)) {
            cdList.remove(cdToRemove);
            return true;
        } else {
            return false;
        }

    }

   
    public String displayCollection() {
        StringBuilder displayString = new StringBuilder();
    
        // Adds the header "CD-Collection" only once
        displayString.append("CD-Collection\n");
    
        // Iterates through the CD list and appends each CD to the displayString
        for (int i = 0; i < cdList.size(); i++) {
            displayString.append(" ").append(i + 1 + ".").append(" ").append(cdList.get(i)).append("\n");
        }
    
        return displayString.toString();
    }

     // New method to connect to the database
     private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

        // New method to load CDs from the database
        public void loadCDsFromDatabase() {
            try (Connection connection = getConnection();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM CDs")) {
    
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String artist = resultSet.getString("artist");
                    int year = resultSet.getInt("year");
    
                    CD cd = new CD(title, artist, year);
                    cdList.add(cd);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        // New method to add a CD to the database
        public void addCDToDatabase(CD newCD) {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO CDs (title, artist, year) VALUES (?, ?, ?)")) {
    
                preparedStatement.setString(1, newCD.getTitle());
                preparedStatement.setString(2, newCD.getArtist());
                preparedStatement.setInt(3, newCD.getYear());
    
                preparedStatement.executeUpdate();
    
                System.out.println("CD added to the database successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        // New method to update a CD in the database
        public void updateCDInDatabase(CD oldCD, CD updatedCD) {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "UPDATE CDs SET title = ?, artist = ?, year = ? WHERE title = ? AND artist = ? AND year = ?")) {
    
                preparedStatement.setString(1, updatedCD.getTitle());
                preparedStatement.setString(2, updatedCD.getArtist());
                preparedStatement.setInt(3, updatedCD.getYear());
                preparedStatement.setString(4, oldCD.getTitle());
                preparedStatement.setString(5, oldCD.getArtist());
                preparedStatement.setInt(6, oldCD.getYear());
    
                int rowsUpdated = preparedStatement.executeUpdate();
    
                if (rowsUpdated > 0) {
                    System.out.println("CD updated in the database successfully");
                } else {
                    System.out.println("The given CD is not in the collection");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        // New method to remove a CD from the database
        public void removeCDFromDatabase(CD cdToRemove) {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "DELETE FROM CDs WHERE title = ? AND artist = ? AND year = ?")) {
    
                preparedStatement.setString(1, cdToRemove.getTitle());
                preparedStatement.setString(2, cdToRemove.getArtist());
                preparedStatement.setInt(3, cdToRemove.getYear());
    
                int rowsDeleted = preparedStatement.executeUpdate();
    
                if (rowsDeleted > 0) {
                    System.out.println("CD removed from the database successfully");
                } else {
                    System.out.println("The given CD is not in the collection");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

}
