package kosta.kiosk.model.dto;

public class ManagerDTO {
	
	private int adminId;
	private String name;
	private String id;
	private String password;
	
	public ManagerDTO() {
		
	}

	public ManagerDTO(int adminId, String name, String id, String password) {
		this.adminId = adminId;
		this.name = name;
		this.id = id;
		this.password = password;
	}
	
	public ManagerDTO(String id, String password) {
		this.id = id;
		this.password = password;
	
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "ManagerDTO [adminId=" + adminId
				+ ", name=" + name
				+ ", id=" + id + "]";
	}
	
	
}
