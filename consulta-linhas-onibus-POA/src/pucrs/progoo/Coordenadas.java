package pucrs.progoo;

import org.jxmapviewer.viewer.GeoPosition;

public class Coordenadas {

	private String idcoordenada;
	private GeoPosition geo;
	private String idlinha;
	
	public Coordenadas(String idcoordenada, double latitude, double longitude, String idlinha) {
		this.idcoordenada = idcoordenada;
		geo = new GeoPosition(latitude, longitude);
		this.idlinha = idlinha;
	}
	
	public String getIdcoordenada() {
		return idcoordenada;
	}
	public GeoPosition getGeo() {
		return geo;
	}
	public String getIdlinha() {
		return idlinha;
	}
	
	
	
	
}
