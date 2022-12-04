package hospitalroom.main;

import hospitalroom.building.HospitalConsole;

public class Main {

	public static void main(String[] args) {
//		Uygulama çalıştırıldığında main method HospitalConsole sınıfından
//		bir örnek alır ve Console metodunu çalıştırır.
		HospitalConsole hospitalConsole = new HospitalConsole();
		hospitalConsole.Console();
	}
}
