package es.chemisoft.objetos;

public class Anyo{
	private int id;
	private String nombre;
	private int total;
	//Constructor
	public Anyo(int id, String nombre, int total) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.total = total;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return nombre;
	}
	public int getId() {
		return id;
	}
}