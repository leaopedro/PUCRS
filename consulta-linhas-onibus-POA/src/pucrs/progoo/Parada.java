package pucrs.progoo;

import java.util.ArrayList;

import org.jxmapviewer.viewer.GeoPosition;

public class Parada {
	private String idParada;
	private String codigo;
	private String terminal;
	private ArrayList<Linha> linhasP;
	private GeoPosition geo;
	
	
	public Parada(String idParada, String codigo, double latitude,
			double longitude, String terminal) {
		this.idParada = idParada;
		this.codigo = codigo;
		geo = new GeoPosition(latitude, longitude);
		this.terminal = terminal;
		
		linhasP = new ArrayList<Linha>();
	}
	
	public void addLinha(Linha linha){
		linhasP.add(linha);
	}
	
	public ArrayList<Linha> getLinhasP(){
		return linhasP;
	}
	
	public String getIdParada() {
		return idParada;
	}
	public String getCodigo() {
		return codigo;
	}
	
	public GeoPosition getGeoPosition(){
		return geo;
	}
	public String getTerminal() {
		return terminal;
	}
	
	public String toString() {
		return 	"Parada: "+ idParada +" cod: "+ codigo + " Terminal: " + terminal;
	}
	
}