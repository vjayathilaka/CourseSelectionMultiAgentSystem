package courseSelection.constants;

public enum SCHEME {
	
	SCIENCE(1, "Science"),
	ART(2, "Art"),
	COMMERSE(3, "Commerse");
	
	private int id;
	private String name;
	
	SCHEME(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static SCHEME getById(int id) {
		for(SCHEME s : values()) {
			if(s.id == id) return s;
		}
		return null;
	}
	
	public static SCHEME getByName(String name) {
		for(SCHEME s : values()) {
			if(s.name.equals(name)) return s;
		}
		return null;
	}
}
