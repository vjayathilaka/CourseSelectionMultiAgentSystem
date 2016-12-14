package courseSelection.constants;

public enum DISTRICT {
COLOMBO(1), GAMPAHA(2), KALUTARA(3), MATALE(4), KANDY(5), NUWARA_ELIYA(6), GALLE(7), MATARA(8),
HAMBANTOTA(9), JAFFNA(10), KILINOCHCHI(11), MANNAR(12), MULLAITIVU(13), VAVUNIYA(14), TRINCOMALEE(15), BATTICALOA(16),
AMPARA(17), PUTTALAM(18), KURUNEGALA(19), ANURADHAPURA(20), POLONNARUWA(21), BADULLA(22), MONARAGALA(23), KEGALLE(24);
	
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
