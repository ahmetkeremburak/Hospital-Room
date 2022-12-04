package hospitalroom.building;

/**
 * @author Kerem
 *Hastane sınıfında kullanılmak üzere oluşturulmuş hasta sınıfı.
 *İçerisinde hasta adı, hasta soyadı ve hastanın yatağı bilgisi bulunuyor.
 */
public class Patient {
	private String name;
	private String surname;
	private Bed bed;
	
	public Patient( String name, String surname) {
		this.setName(name);
		this.setSurname(surname);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public Bed getBed() {
		return bed;
	}

	public void setBed(Bed bed) {
		this.bed = bed;
	}

	@Override
	public String toString() {
		// returns patients name
		return name + " " + surname;
	}

}
