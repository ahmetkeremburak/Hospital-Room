package hospitalroom.datastructure;

import java.util.Iterator;

/**
 * @author Kerem
 *
 * @param <E> 
 * 	herhangi bir yapı ile çalışabilmek adına sınıfın aldığı generic type değişken.
 * 
 * <p>
 * Tarafımdan yazılan bu veri yapısı modeli normal tek yönlü bağlı liste mantığıyla çalışır.
 * for-each yapısı ile kullanılabilmesi için 'Iterable' arayüzünü 'implement' etmiştir.
 * </p>
 */
public class SinglyLinkedList<E> implements Iterable<E> {
	
	//Bağlı listenin bir "node"unu temsil edecek iç sınıf
	private class Node{
		@SuppressWarnings(value = {"unused"})
		E data;
		@SuppressWarnings(value = {"unused"})
		Node next;
		
		@SuppressWarnings("unused")
		public Node(E data, Node next){
		this.data= data ;
		this.next= next;
		}
	}
		
	//Listenin head ve tail pointerlarını temsil edecek node'lar
	private Node head;
	private Node tail;
	private int size = 0;
	
	/*
	 * Liste yoksa oluşturur ve elemanı ekler.
	 * Liste varsa verilen data ile bir node oluşturup listenin sonuna ekler.
	 * Liste uzunluğunu 1 arttırır.
	 */
	public void add(E data) {
		Node newNode = new Node(data, null);
		
		if(this.head == null) {
			head = newNode;
			tail = newNode;
		}
		else {
			Node temp = head;
			
			while(temp.next != null) {
				temp = temp.next;
			}
			
			temp.next = newNode;
			tail = newNode;
		}
		
		size++;
	}
		
	/*
	 * Liste yoksa oluşturur ve elemanı ekler.
	 * Liste varsa verilen data ile yeni bir node oluşturur.
	 * Yeni eleman next pointerıyla eski liste başını gösterir.
	 * Head pointerı yeni liste başını gösterir.
	 * Listenin uzunluğunu 1 arttırır.
	 */
	public void addFirst(E data){
		Node newNode = new Node(data, null);
			
		if(head==null){

		head=newNode;
		tail=newNode;
		
		}
		
		else{

		newNode.next = head;
		head = newNode;
		}
		
		size++;
	}
	
	/*
	 * Liste boş ise null bir eleman döndürür
	 * Liste tek elemandan oluşuyorsa head pointerını null işaretler ve tek elemanın değerinin döndürür.
	 * Listede birden fazla eleman varsa ilk elemanı boşa alıp değerini döndürür.
	 * Head pointerı yeni liste başını gösterir.
	 * Liste uzunluğu 1 azaltır.
	 */
	public E removeFirst() {
		Node temp = new Node(null,null);
		Node temp2 = new Node(null, null);
		if(head == null) {
			return null;
		}
		if(head != null && head.next == null) {
			temp = head;
			head = null;
			size--;
			return (E) temp.data;
		}
		else {
			temp = head.next;
			temp2 = head;
			head = null;
			head = temp;
			size--;
			return (E) temp2.data;
		}
	}
	
	/*
	 * Liste boş ise null bir eleman döndürür.
	 * Liste tek elemandan oluşuyorsa head pointerını null işaretler ve tek elemanın değerinin döndürür.
	 * Listede birden fazla eleman varsa temp yardımıyla listenin sonuna gider.
	 * Sondan bir önceki elemanın pointerını null'a çevirir. Boşta kalan son elemanın değerini döndürür.
	 * Listenin boyutunu 1 azaltır.
	 */
	public E removeLast() {
		Node temp = new Node(null,null);
		Node lamb = new Node(null,null);
		
		if(head == null) {
			return null;
		}
		if(head != null && head.next == null) {
			temp = head;
			head = null;
			size--;
			return temp.data; 
		}
		else {
			temp = head;
			while(temp.next.next != null) {
				temp = temp.next;
			}
			tail = temp;
			lamb = temp.next;
			temp.next = null;
			size--;
			return lamb.data;
			
		}
	}
	
	/*
	 * Verilen datayı taşıyan node'u bulur ve listeden siler.
	 * 
	 * Liste boş ise null bir eleman döndürür.
	 * 
	 * Liste tek elemandan oluşuyorsa head pointerını null işaretler.
	 * Tek elemanın datası aranan dataya eşitse onu döndürür.
	 * 
	 * Listede birden fazla eleman varsa silinecek node'un konumuna göre uygun işlemi yapar.
	 * Arada bulunan düğümler için önceki ve sonraki elemanı birbirine bağlar.
	 * Aranan elemanın değerini döndürür.
	 * 
	 * Listenin uzunluğunu 1 azaltır.
	 * 
	 */
	public E remove(E data) {
		Node temp = new Node(null,null);
		
		if(head == null) {
			return null;
		}
		
		if(head.next == null && head.data == data) {
			temp = head;
			head = null;
			size--;
			return temp.data;
		}
		
		else {
			if(head.data == data) {
				temp.data = removeFirst();
				return temp.data;
			}
			else {
				temp = head;
				while(temp.next.data != data && temp != null) {
					temp = temp.next;
				}
				if(temp == null) {
					return null;
				}
				if(temp.next.next == null && temp.next.data == data) {
					temp.data = removeLast();
					return temp.data;
				}
				else {
					Node remove = temp.next;
					Node connect = temp.next.next;
					temp.next = connect;
					remove.next = null;
					size--;
					return remove.data;
				}
			}
		}
	}
	
	/*
	 * Listenin istenilen andaki uzunluğunu döndürür.
	 */
	public int size() {
		return size;
	}

	/*
	 * Listenin for-each metodunun çalışabilmesi için gereken iterator sınıfına
	 * listeyi verir ve sonucunu döndürür.
	 */
	@Override
	public Iterator<E> iterator() {
		return new SinglyLinkedListIterator(this);
	}
	
	/*
	 * Java'nın Iterator arayüzünü implement eden iç sınıf.
	 * Listenin ihtiyaçları doğrultusunda iterator arayüzünden gelen metodların barındırır.
	 */
	class SinglyLinkedListIterator implements Iterator<E> {

		Node current;
		
		public SinglyLinkedListIterator(SinglyLinkedList<E> list){
			current = list.head;
		}
		
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return current != null;
		}

		@Override
		public E next() {
			// TODO Auto-generated method stub
			E element = current.data;
			current = current.next;
			return element;
		}
		
		
	}
}
