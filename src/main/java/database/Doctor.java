package database;

public class Doctor {

    private int id;   // 🔥 ADD THIS
    private String name;
    private int experience;
    private String hospital;
    private String phone;
    private String image;
    private String availability;

    // GETTERS
    public int getId() {   // 🔥 ADD THIS
        return id;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public String getHospital() {
        return hospital;
    }

    public String getPhone() {
        return phone;
    }

    public String getImage() {
        return image;
    }

    // SETTERS
    public void setId(int id) {   // 🔥 ADD THIS
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
