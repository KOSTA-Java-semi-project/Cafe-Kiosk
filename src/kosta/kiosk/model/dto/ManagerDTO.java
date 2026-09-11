package kosta.kiosk.model.dto;

public class ManagerDTO {
	
	private int managerId;
	private String name;
	private String id;
	private String password;
	
	public ManagerDTO() {
		
	}

	public ManagerDTO(int managerId, String name, String id, String password) {
		this.managerId = managerId;
		this.name = name;
		this.id = id;
		this.password = password;
	}
	
	public ManagerDTO(String id, String password) {
		this.id = id;
		this.password = password;
	
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int adminId) {
		this.managerId = adminId;
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
		return "ManagerDTO [adminId=" + managerId
				+ ", name=" + name
				+ ", id=" + id + "]";
	}
	
	
}
