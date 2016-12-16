package courseSelection.constants;

public enum UNIVERSITY {
    
UNIVERSITY_COLOMBO(1,"University of Colombo"),
UNIVERSITY_PERADENIYA(2,	"University of Peradeniya"), 
UNIVERSITY_SRI_JAYAWARDANAPURA(3,	"University of Sri Jayewardenepura"),
UNIVERSITY_KELANIYA(4,	"University of Kelaniya"),
UNIVERSITY_MORATUWA(5,	"University of Moratuwa"),
UNIVERSITY_JAFFNA(6,	"University of Jaffna"),
UNIVERSITY_RUHUNA(7,	"University of Ruhuna"), 
EASTERN_UNIVERSITY(8,	"Eastern University, Sri Lanka"),
SOUTH_EASTERN_UNIVERSITY(9,	"South Eastern University of Sri Lanka"),
RAJARATA_UNIVERSITY(10,	"Rajarata University of Sri Lanka"),
SABARAGAMUWA_UNIVERSITY(11,	"Sabaragamuwa University of Sri Lanka"),
WAYABA_UNIVERSITY(12,	"Wayamba University of Sri Lanka"),
UVA_WELLASSA_UNIVERSITY(13,	"Uva Wellassa University of Sri Lanka"),
UNIVERSITY_VISUAL_AND_PERFORMING_ART(14,	"University of the Visual & Performing Arts"),
SRIPALEE_CAMPUS(15,	"Sripalee Campus"),
TRINCOMALEE_CAMPUS(16,	"Trincomalee Campus"),
VAVNIYA_CAMPUS(17,	"Vavuniya Campus"),
INSTITUTE_OF_INDIGENOUS_DEDICINE(18,	"Institute of Indigenous Medicine"),
GAMPAHA_WICKRAMARACHCHI_AYURVEDA_INSTITUTE(19,	"Gampaha Wickramarachchi Ayurveda Institute"),
UNIVERSITY_COLOMBO_SCHOOL_OF_COMPUTING(20,	"University of Colombo School of Computing"),
SWAMI_VIPULANANDA_INSTITUTE(21,	"Swami Vipulananda Institute of Aesthetic Studies, Eastern University, Sri Lanka"), 
RAMANATHAN_ACADEMY(22,	"Ramanathan Academy of Fine Arts, University of Jaffn");

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
