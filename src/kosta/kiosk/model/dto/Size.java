package kosta.kiosk.model.dto;

public enum Size {

	SMALL("SMALL"), MEDIUM("MEDIUM"), LARGE("LARGE"),;

	private String code;

	Size(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public Size fromValue(String code) {
		for (Size s : values()) {
			if (s.code.equals(code))
				return s;
		}
		throw new IllegalArgumentException("Wrong code:" + code);
	}
}
