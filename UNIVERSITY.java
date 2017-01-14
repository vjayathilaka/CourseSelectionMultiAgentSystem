package distancecalculator;

public enum UNIVERSITY {
    
UNIVERSITY_COLOMBO(1,"University of Colombo", "University of Colombo"),
UNIVERSITY_PERADENIYA(2,	"University of Peradeniya", "University of Peradeniya"), 
UNIVERSITY_SRI_JAYAWARDANAPURA(3,	"University of Sri Jayewardenepura", "University of Sri Jayewardenepura"),
UNIVERSITY_KELANIYA(4,	"University of Kelaniya", "University of Kelaniya"),
UNIVERSITY_MORATUWA(5,	"University of Moratuwa", "University of Moratuwa"),
UNIVERSITY_JAFFNA(6,	"University of Jaffna", "University of Jaffna"),
UNIVERSITY_RUHUNA(7,	"University of Ruhuna", "University of Ruhuna"), 
EASTERN_UNIVERSITY(8,	"Eastern University, Sri Lanka", "Eastern University, Sri Lanka"),
SOUTH_EASTERN_UNIVERSITY(9,	"South Eastern University of Sri Lanka", "South Eastern University of Sri Lanka"),
RAJARATA_UNIVERSITY(10,	"Rajarata University of Sri Lanka", "8.366367,80.502356"),
SABARAGAMUWA_UNIVERSITY(11,	"Sabaragamuwa University of Sri Lanka", "Sabaragamuwa University of Sri Lanka"),
WAYABA_UNIVERSITY(12,	"Wayamba University of Sri Lanka", "Wayamba University of Sri Lanka"),
UVA_WELLASSA_UNIVERSITY(13,	"Uva Wellassa University of Sri Lanka", "6.982999,81.062692"),
UNIVERSITY_VISUAL_AND_PERFORMING_ART(14,	"University of the Visual & Performing Arts", "6.911058,79.869048"),
SRIPALEE_CAMPUS(15,	"Sripalee Campus", "6.707777,80.070070"),
TRINCOMALEE_CAMPUS(16, "Trincomalee Campus", "Trincomalee Campus"),
VAVNIYA_CAMPUS(17,	"Vavuniya Campus", "Vavuniya Campus"),
INSTITUTE_OF_INDIGENOUS_DEDICINE(18,	"Institute of Indigenous Medicine", "6.908755,79.888965"),
GAMPAHA_WICKRAMARACHCHI_AYURVEDA_INSTITUTE(19,	"Gampaha Wickramarachchi Ayurveda Institute", "Gampaha Wickramarachchi Ayurveda Institute"),
UNIVERSITY_COLOMBO_SCHOOL_OF_COMPUTING(20,	"University of Colombo School of Computing", "6.902570,79.861315"),
SWAMI_VIPULANANDA_INSTITUTE(21,	"Swami Vipulananda Institute of Aesthetic Studies, Eastern University, Sri Lanka", "7.700163,81.718090"),
RAMANATHAN_ACADEMY(22,	"Ramanathan Academy of Fine Arts, University of Jaffn", "9.730455,80.026431");

	private int id;
	private String name;
        private String cordinates;
	
	private UNIVERSITY(int id, String name, String cordinates) {
		this.id = id;
		this.name= name;
                this.cordinates = cordinates;
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

        public String getCordinates() {
            return cordinates;
        }

        public void setCordinates(String cordinates) {
            this.cordinates = cordinates;
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
