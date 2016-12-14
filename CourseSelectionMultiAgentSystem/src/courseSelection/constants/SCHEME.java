package courseSelection.constants;

public enum SCHEME {
	
	ART(1, "Art Stream"),
	COMMERCE(2, "Commerce Stream"),
	BIOLOGICAL(3, "Biological Stream"),
        PHYSICAL_SCIENCT(4, "Physical Science Stream");
	
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
