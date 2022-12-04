package hospitalroom.building;

/**
 * @author Kerem
 *Hastane sınıfında kullanılmak üzere oluşturulmuş yatak sınıfı.
 *Yatak içerisinde numara ve bir hasta tutuyor.
 */
public class Bed {
	private Patient patient;
	private int bedNum;
	
	public Bed(int bedNum){
		this.bedNum = bedNum;
	}
	
	public int getBedNum() {
		return bedNum;
	}
	
	public String getPatient() {
		return patient.toString();
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	@Override
	public String toString() {
		return String.valueOf(bedNum);
		
	}
}
