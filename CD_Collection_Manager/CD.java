import java.util.Objects;

public class CD {
    private String title;
    private String artist;
    private int year;

    // Constructors and other methods

    // Constructor to initialize a CD with title, artist, and year
    public CD(String title, String artist, int year) {
        this.title = title;
        this.artist = artist;
        this.year = year;
    }

    // Override the equals method for proper object comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CD otherCD = (CD) obj;
        return year == otherCD.year && title.equals(otherCD.title) && artist.equals(otherCD.artist);
    }

    // Override the hashCode method to generate a hash based on title, artist, and year
    @Override
    public int hashCode() {
        return Objects.hash(title, artist, year);
    }

    // Override the toString method to provide a string representation of the CD
    @Override
    public String toString() {
        return "CD: " + title + " by " + artist + " (" + year + ")";
    }

    // Getter and setter methods

    // Get the title of the CD
    public String getTitle() {
        return title;
    }

    // Set the title of the CD
    public void setTitle(String title) {
        this.title = title;
    }

    // Get the artist of the CD
    public String getArtist() {
        return artist;
    }

    // Set the artist of the CD
    public void setArtist(String artist) {
        this.artist = artist;
    }

    // Get the year of the CD
    public int getYear() {
        return year;
    }

    // Set the year of the CD
    public void setYear(int year) {
        this.year = year;
    }
}
