package kosta.kiosk.model.dto;

public enum IceLevel {
	FEW("FEW"), LOT("LOT"), DEFAULT("DEFAULT");

	private String code;

	IceLevel(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static IceLevel fromCode(String code) {
		for (IceLevel i : values()) {
			if (i.code.equals(code))
				return i;
		}
		throw new IllegalArgumentException("Wrong code:" + code);
	}

}