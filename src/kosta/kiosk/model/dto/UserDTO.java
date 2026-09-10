package kosta.kiosk.model.dto;

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

    // 오버로딩
    // 회원가입 전용 생성자: DB 저장 전이라 userId가 아직 없으므로 제외. 스탬프도 기본값이 0이므로 제외.
    public UserDTO(String name, String phone) {
        this.name = name;
        this.phone = phone;
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