package courseSelection.constants;

public enum UNIVERSITY {
	
	UniversityOfColombo(1, "University Of Colombo"),
	UniversityOfPeradeniya(2, "University of Peradeniya"),
	UniversityOfMoratuwa(3, "University of Moratuwa"),
	UniversityOfKelaniya(4, "University of Kelaniya"),
	UniversityOfSriJayewardenepura(5, "University of Sri Jayewardenepura"),
	UniversityOfRuhuna(6, "University of Ruhuna"),
	OpenUniversityOfSriLanka(7, "Open University of Sri Lanka"),
	UniversityOfJaffna(8, "University of Jaffna"),
	SouthEasternUniversityOfSriLanka(9, "South Eastern University of Sri Lanka"),
	RajarataUniversity(10, "Rajarata University"),
	SabaragamuwaUniversity(11, "Sabaragamuwa University"),
	EasternUniversity(12, "Eastern University of Sri Lanka"),
	WayambaUniversity(13, "Wayamba University of Sri Lanka"),
	UniversityOfTheVisualAndPerformingArts(14, "University of the Visual and Performing Arts");

	private int id;
	private String name;
	
	private UNIVERSITY(int id, String name) {
		this.id = id;
		this.name= name;
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

	public static UNIVERSITY getById(int id) {
		for(UNIVERSITY u : values()) {
			if(u.id == id) return u;
		}
		return null;
	}
	
	public static UNIVERSITY getByName(String name) {
		for(UNIVERSITY u : values()) {
			if(u.name.equals(name)) return u;
		}
		return null;
	}
        
}
