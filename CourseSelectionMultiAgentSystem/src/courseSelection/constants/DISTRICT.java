package courseSelection.constants;

public enum DISTRICT {
    Jaffna(1),
    Kilinochchi(2),
    Mannar(3),
    Mullaitivu(4),
    Vavuniya(5),
    Puttalam(6),
    Kurunegala(7),
    Gampaha(8),
    Colombo(9),
    Kalutara(10),
    Anuradhapura(11),
    Polonnaruwa(12),
    Matale(13),
    Kandy(14),
    NuwaraEliya(15),
    Kegalle(16),
    Ratnapura(17),
    Trincomalee(18),
    Batticaloa(19),
    Ampara(20),
    Badulla(21),
    Monaragala(22),
    Hambantota(23),
    Matara(24),
    Galle(25);
	
	private int id;

	private DISTRICT(int id) {
		this.setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public static DISTRICT getById(int id) {
		for(DISTRICT d : values()) {
			if(d.id == id) return d;
		}
		return null;
	}
	
}
