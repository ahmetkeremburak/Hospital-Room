package hospitalroom.building;

import java.util.LinkedList;

import hospitalroom.datastructure.SinglyLinkedList;


/**
 * @author Kerem
 *Ödevde istenen ana fonksiyonları yerine getiren metodların bulunduğu sınıftır.
 *Hastane içerisinde bulunan boş ve dolu yataklar, hastanedeki hastalar gibi veriler bu sınıfta
 *ayağa kaldırılan SinglyLinkedList veri yapılarında tutulur.
 */
public class Hospital{
	/*
	 * Bu üç bağlı liste sırasıyla boş yatakları, dolu yatakları ve hasta bilgilerini
	 * ödev için yazılan normal tek yönlü bağlı liste yapısı şeklinde tutar.
	 */
	private SinglyLinkedList<Bed> availableBeds = new SinglyLinkedList<Bed>();
	private SinglyLinkedList<Bed> occupiedBeds = new SinglyLinkedList<Bed>();
	private SinglyLinkedList<Patient> patients = new SinglyLinkedList<Patient>();
	
	/*
	 * Constructor metodu.
	 * Sınıf örneklendiğinde bir for döngüsü yardımıyla 100 adet boş yatak nesnesini
	 * boş yataklar listesine ekler.
	 */
	public Hospital() {
		for (int i = 1; i <= 100; i++) {
			Bed b = new Bed(i);
			availableBeds.add(b);
		}
	}
	
	/*
	 * @param p
	 * 	Patient sınıfından oluşturulan hasta nesnesi
	 * 
	 * Metod hastaneye yeni hasta girişi yapar.
	 *
	 * if yapısı ile hastanede yer olup olmadığını kontrol edip
	 * yer var ise hastayı hastalar listesine ekler.
	 * 
	 * Boş yataklar listesini her ihtimale karşı düzenler ve en baştaki yatağı yeni hastaya verir.
	 * Hastanın yatağı dolu yataklar listesine eklenir.
	 * Dolu yataklar listesi tekrar sıralanır.
	 * 
	 * Hastanın yatağına da hasta bilgisi kayıt edilir.
	 */
	public void admitPatient(Patient p) {
		if(patients.size() <= 100) {
			patients.add(p);
			availableBeds = sortList(availableBeds);
			p.setBed(availableBeds.removeFirst());
			occupiedBeds.add(p.getBed());
			occupiedBeds = sortList(occupiedBeds);
			(p.getBed()).setPatient(p);
		}
	}
	
	/*@param p
	 * 	Patient sınıfından oluşturulan hasta nesnesi
	 * 
	 * Metod hasta taburcu eder.
	 * Dolu yataklar listesinden hastanın yatağı kaldırılır.
	 * Boş yataklar listesine hastanın yatağı eklenir.
	 * Hasta hastalar listesinden kaldırılır.
	 * Dolu yataklar listesi ve boş yataklar listesi sıralanır.
	 * Hastanın yatağından hasta bilgileri silinir.
	 * Hasta nesnesinden yatak bilgisi silinir.
	 * Konsola taburcu mesajı yazdırılır.
	 * 
	 */
	public void releasePatient(Patient p) {
		occupiedBeds.remove(p.getBed());
		availableBeds.add(p.getBed());
		patients.remove(p);
		availableBeds = sortList(availableBeds);
		occupiedBeds = sortList(occupiedBeds);
		p.getBed().setPatient(null);
		p.setBed(null);
		System.out.println( "Geçmiş olsun. "+ p + " taburcu edildi.");
		
	}
	
	/*
	 * Boş yatakların bilgileri yazdırılır.
	 * Toplam boş yatak sayısı yazdırılır.
	 */
	public void listAvailable() {
		System.out.println("Boş yataklar: ");
		for (Bed bed : availableBeds) {
			System.out.println("Yatak No." + bed);
		}
		System.out.println("Toplam boş yatak sayısı: " + availableBeds.size());
	}
	
	/*
	 * Dolu yatakların bilgileri yazdırılır.
	 * Toplam dolu yatak sayısı yazdırılır.
	 */
	public void listOccupied() {
		System.out.println("Dolu yataklar: ");
		for (Bed bed : occupiedBeds) {
			System.out.print("Yatak No. " + bed);
			System.out.println(" - Hasta adı: " + bed.getPatient());
		}
		System.out.println("Toplam dolu yatak sayısı: " + occupiedBeds.size());
	}
	
	/*
	 * @param i
	 * 	Aranacak yatağın numarası için gereken Integer değer.
	 * 
	 * Metod verilen yatak numarasında yatan hasta bilgilerini yazdırır.
	 * 
	 * Eğer verilen 'i' değeri 100'den büyükse hastane yatak sayısı hatırlatılır.
	 * 
	 * 'i' değeri 100'den büyük değilse 
	 * Yeni oluşturulan 'b' yatak nesnesine '0' numarası verilir.
	 * Dolu yataklar for-each yapısı kontrol edilir.
	 * Verilen yatak numarası bulununca yatak bilgisi ve hasta adı yazdırılır.
	 * Eğer hasta bulunmuşsa 'b' nesnesine yatak numarası kaydedilir.
	 * Eğer 'b' nesnesi '0' numarasını göstermeye devam ederse hastanın bulunamadığı mesajı yazdırılır.
	 */
	public void findPatient(int i){
		Bed b = new Bed(0);
		if(i > 100) {
			System.out.println("Hastanede bu numaralı bir yatak yok.");
		}
		else {
			for (Bed bed : occupiedBeds) {
				if(bed.getBedNum() == i) {
					System.out.println(i + " numaralı yatakta " + bed.getPatient() + " isimli hasta yatmaktadır.");
					b = bed;
					break;
				}
			}
		
			if(b.getBedNum() == 0 && i <= 100) {
				System.out.println("Aranan yatakta hasta bulunmuyor.");
			}
		}
	}
	
	/*
	 * @param name
	 * 	Hastanın adı
	 * @param surname
	 * 	Hastanın soyadı
	 * 
	 * Metod verilen ad ve soyadı ile aranan hastanın yatak numarasını bulur ve yazdırır.
	 * 
	 * person isimli boş bir patient nesnesi oluşturur.
	 * for-each yapısı ile hastalar listesini döndürür.
	 * eğer hastanın adı verilen ada eşitse
	 * eğer hastanın soyadı verilen soyada eşitse
	 * hastayı person değişkenine kaydeder.
	 * Hastanın yatak numarasını yazdırır.
	 * 
	 * Eğer for-each yapısından sonra person nesnesi hala boş ise
	 * Aranan hastanın bulunamadığını konsola yazdırır.
	 */
	public void findPatient(String name, String surname){	
		Patient person = new Patient(null, null);
		for (Patient p : patients) {
			if(p.getName().equals(name)){
				if(p.getSurname().equals(surname)) {
					person = p;
					System.out.print(p + " isimli hastanın yatak numarası: ");
					System.out.println(p.getBed());
					break;
				}
			}
		}
		
		if(person.getName() == null) {
			System.out.println("Bu isimde bir hasta bulunmuyor.");
		}
	}
	
	/*
	 * @param name
	 * 	Hasta adı
	 * 
	 * @param surname
	 * 	Hasta soyadı
	 * 
	 * Metod verilen ad ve soyad üzerinden hasta bulur ve hastayı döndürür.
	 * 
	 * Eğer hasta bulunamazsa null döndürür.
	 * 
	 * Metodun yazılma amacı verilen ad ve soyadı üzerinden remove methoduna person döndürmek.
	 */
	public Patient getPatient(String name, String surname) {
		for (Patient p : patients) {
			if(p.getName().equals(name)){
				if(p.getSurname().equals(surname)) {
					return p;
				}
			}
		}
		return null;
		
		
	}
	
	/*
	 * @param list
	 * 	Bu sınıf ile oluşturulmuş yatak nesnesi tutan normal tek yönlü bağlı liste
	 * 
	 * Metod verilen normal tek yönlü bağlı listeyi yatak numaralarına göre sıralayıp geri döndürür.
	 * 
	 *  Verilen liste for-each yapısı ile java'nın içindeki bağlı liste yapısına yazdırılır.
	 *  Bağlı liste yatak numaralarını karşılaştırarak küçükten büyüğe sıralar.
	 *  Java'nın bağlı liste yapısı bir çit yönlü bağlı listedir.
	 *  Sort edebilmek için elemanları bir diziye koyar ve merge sort algoritmasıyla belirtilen kaideyle düzenler.
	 *  Merge sort algoritmasının Big O Notation'ı O(Nlogn)'dir.
	 *  
	 *  Düzenlenmiş bağlı liste boş bir normal tek yönlü bağlı listeye yazdırılır ve yeni liste döndürülür.
	 */
	public SinglyLinkedList<Bed> sortList(SinglyLinkedList<Bed> list){
		
		SinglyLinkedList<Bed> newList = new SinglyLinkedList<Bed>();
		LinkedList<Bed> link = new LinkedList<Bed>();
		for (Bed e : list) {
			link.add(e);
		}
		link.sort((o1, o2) -> o1.getBedNum() - o2.getBedNum());
		for (Bed bed : link) {
			newList.add(bed);
		}
		return newList;
	}
	
	
	
	
}
