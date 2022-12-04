package hospitalroom.building;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


/**
 * @author Kerem
 * <p>
 * Bu sınıf Hospital sınıfından bir örnek kullanarak konsolda kullanıcı tarafından
 * gerçekleştirecek işlemlerin sunulacağı metodları taşıyor.
 * Bunun dışında içinde hastane nesnesinden bir örnek ve konsolun kendini yenilemesi 
 * için kullanılan bir anahtar değişken olan loopStatus değişkenini barındırıyor. 
 *</p>
 */
public class HospitalConsole {
	/*
	 * Bu değişken while döngüsünü başlatabilmek için kullanılır. İstenildiğinde döngüyü
	 * kırabilmek için kullanılır.
	 */
	int loopStatus = 1;
	
	/*
	 * Hospital sınıfı içerisinde hastanenin fonksiyonlarının yazıldığı
	 * esas methodları barındırıyor. O methodlara erişim için bir örneklemi bu sınıfa yazıldı.
	 */
	Hospital hospital = new Hospital();
	
	/*
	 * Kullanıcının konsoldan yapabileceklerini toparlayan ve konsolu kontrol eden method.
	 * Konsola gelecek olan menü bir çıkış case'i switch-case yapısı içerisine yazılmış
	 * sonsuz bir while döngüsü içinde çalıştırılıyor. "choice" değişkeni üzerinden switch
	 * case yapısına hangi case'in çalıştırılacağı bildiriliyor. Her case kendi içindeki
	 * işlemi tamamlamadan evvel choice değişkenini ilk haline geri eşitliyor.
	 * Bunun sebebi bir sonraki while döngüsünde doğru case'i çalıştırılabilmek. 
	 */
	public void Console() {
		while(loopStatus == 1) {
			int choice = 0;
			
			switch (mainScreen(choice)) {
			case 1: {
				admitPatient();
				choice = 0;
				break;
			}
			case 2: {
				findPatientByName();
				choice = 0;
				break;
			}
			case 3:{
				findPatientByBed();
				choice = 0;
				break;
			}
			case 4:{
				listOccupied();
				choice = 0;
				break;
			}
			case 5:{
				listAvailable();
				choice = 0;
				break;
			}
			case 6:{
				releasePatient();
				choice = 0;
				break;
			}
			case 7:{
				loopStatus = 0;
				shutDown();
				break;
			}
			default:
				System.out.printf("Lütfen belirtilen numaralardan seçerek tekrar deneyin.\n");
				choice = 0;
			}
		}
	}
	
	/*
	 * Bu method programın kapatılması için while döngüsünü loopStatus'ü '0'a eşitleyerek kırar.
	 * Programın aniden kapanıp kapanış mesajının okunamamasını önlemek adına sistemi 1 saniye uyutur. 
	 */
	private void shutDown() {
		System.out.println("İşlem tamamlandı. Güle güle.");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}

	/*
	 * Konsoldan hastanın adını ve soyadını alır. Büyük-küçük harf duyarlılığından kurtulmak
	 * adına alınan stringi küçük harfe çevirerek kaydeder. Ad ve soyada göre hasta listesinden
	 * aranan hastayı bulur ve hastaneden taburcu için gerekli fonksiyonu çalıştırır.
	 * 
	 * Hastanın bulunmamasına karşı kontrol bu methodda yapılır.
	 */
	private void releasePatient() {
		Scanner in = new Scanner(System.in);
		
		
		System.out.print("Taburcu olacak hastanın adı: ");
		String name = in.nextLine().toLowerCase();
		System.out.print("Taburcu olacak hastanın soyadı: ");
		String surname = in.nextLine().toLowerCase();
		Patient patient = hospital.getPatient(name, surname);
		if(patient == null) {
			System.out.println("Bu hasta hastanemizde bulunmuyor.");
			return;
		}
		else {
			hospital.releasePatient(patient);
		}
		
	}

	/*
	 * Boş yatakları yazdırılması için uygun metodu Hospital sınıfından çağırır.
	 */
	private void listAvailable() {
		hospital.listAvailable();
	}

	/*
	 * Dolu yatakların yazdırılması için uygun metodu Hospital sınıfından çağırır.
	 */
	private void listOccupied() {
		hospital.listOccupied();
	}

	/*
	 * Console metodunda Switch-case yapısına hangi case'in seçildiğine dair bilgiyi iletir.
	 * Kullanıcılar için konsola seçeneklerini yazdırır ve konsoldan girdiyi alır.
	 * Beklenmeyen bir girdi gelmesine karşı kullanıcı seçimini try-catch yapısı içinde alır.
	 * Bir evvelki mesajın rahat okunabilmesi adına harekete geçerken sistemi bir saniye uyutur,
	 * ardından konsola kullanıcı seçeneklerini yazdırır.
	 */
	private int mainScreen(int choice) {
		Scanner in = new Scanner(System.in);
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("----------------------------------------");
		System.out.println("1) Hasta kayıt");
		System.out.println("2) Hasta isminden yatak numarası bulma");
		System.out.println("3) Yatak numarasından hasta ismi bulma");
		System.out.println("4) Dolu yatakların listesi");
		System.out.println("5) Boş yatakların listesi" );
		System.out.println("6) Hasta taburcu");
		System.out.println("7) Çıkış");
		System.out.println("----------------------------------------");
		System.out.print("Lütfen işleminizin numarasını girin:");
		try {
			choice = in.nextInt();
		} catch (InputMismatchException e) {
			System.out.printf("Numara girmelisiniz.\n");
			choice = 0;
			return choice;
		}
		System.out.println("----------------------------------------");
		
		return choice;	
	}
	
	/*
	 * Hasta girişi için konsoldan gerekli birlgileri alır ve hasta kayıt fonksiyonunu çağırır.
	 * Boş bir 'Patient' sınıfı değişkenine konsoldan alınan ad ve soyadı yazar.
	 * Alınan ad ve soyadı büyük-küçük harf duyarlılığını aşmak adına küçük harfe çevirerek kaydeder.
	 * 'Hospital' sınıfının 'admitPatient' sınıfını çağırarak oluşturduğu 'patient' ile kayıt yapar.
	 * Giriş yapan hastanın adını ve aldığı yatak numarasını konsola yazdırır.  
	 */
	private void admitPatient() {
		Scanner in = new Scanner(System.in);
		Patient patient = new Patient(null,null);
		
		System.out.println("Kayıt olacak hastanın adı: ");
		String name = in.nextLine().toLowerCase();
		System.out.println("Kayıt olacak hastanın soyadı: ");
		String surname = in.next().toLowerCase();
		patient.setName(name);
		patient.setSurname(surname);
		hospital.admitPatient(patient);
		System.out.printf("-----------------------------------------\n%s hastaneye giriş yaptı.", patient);
		System.out.println("\nOda numarası " + patient.getBed() + "\n-----------------------------------------");
	}
	
	/*
	 * Konsoldan ad ve soyad alarak Hospital.findPatient metodunu çalıştırır.
	 */
	private void findPatientByName() {
		Scanner in = new Scanner(System.in);
		System.out.print("Aranan hastanın adı: ");
		String name = in.nextLine().toLowerCase();
		System.out.print("Aranan hastanın soyadı: ");
		String surname = in.nextLine().toLowerCase();
		
		hospital.findPatient(name, surname);
	}
	
	/*
	 * Konsoldan bir numara alarak Hospital.findPatient metodunu çalıştırır.
	 */
	private void findPatientByBed() {
		Scanner in = new Scanner(System.in);
		System.out.print("Aranan hastanın yatak numarası: ");
		try {
			int bed = in.nextInt();
			hospital.findPatient(bed);
		} catch (Exception e) {
			System.out.println("Lütfen numaralar dışında değer girmeyin.");

		}
	}
}	
