package dto;

public class UserDTO {
    private int userId;
    private String name;
    private String phone;
    private int stamp;

    public UserDTO(int userId, String name, String phone, int stamp) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.stamp = stamp;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getStamp() { return stamp; }
    public void setStamp(int stamp) { this.stamp = stamp; }
}