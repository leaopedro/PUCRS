package pucrs.progoo;

import java.util.ArrayList;
import java.util.List;

import org.jxmapviewer.viewer.GeoPosition;

import pucrs.progoo.geo.AlgoritmosGeograficos;

public class Linha {
	private String idlinha;
	private String nome;
	private String codigo;
	private String tipo;
	private ArrayList<Parada> paradasL;
	private ArrayList<Coordenadas> coordenadas;
	
	

	public Linha(String idlinha, String nome, String codigo, String tipo) {
		this.idlinha = idlinha;
		this.nome = nome;
		this.codigo = codigo;
		this.tipo = tipo;
		paradasL = new ArrayList<Parada>();
		coordenadas = new ArrayList<Coordenadas>();
	}
	
//	public List<Coordenadas> getCoordTrecho(Trecho t){
//		GeoPosition gp1 = t.getParadaInicial().getGeoPosition();
//		GeoPosition gp2 = t.getParadaInicial().getGeoPosition();
//		List<Coordenadas> coordTrecho = new ArrayList<Coordenadas>();
//		
//		
//		for (Coordenadas c : coordenadas){
////			if (c.getGeo()> gp1 && c.getGeo()<gp2){
////				
////			}
//		}
//		
//		
//	}
	
	public void addCoordenada(Coordenadas coor){
		coordenadas.add(coor);
	}
	
	public boolean isEmptyCoordenadas(){
		if(coordenadas.isEmpty()) return true;
		return false;
	}
	
	public boolean isEmptyParadas(){
		if(paradasL.isEmpty()) return true;
		return false;
	}
	
	public void addParada(Parada parada){
		paradasL.add(parada);
	}
	
	public ArrayList<Parada> getParadasL() {
		return paradasL;
	}

	public ArrayList<Coordenadas> getCoordenadas() {
		return coordenadas;
	}

	public boolean passaProximo(GeoPosition gp){
		for(Coordenadas c : coordenadas){
			if(AlgoritmosGeograficos.calcDistancia(c.getGeo(), gp) <= 0.15){
				return true;
			}
		}
		return false;
	}
	
	public boolean passaNaParada(Parada p){
		for(Parada par : paradasL){
			if(par == p){
				return true;
			}
		}
		return false;
	}
	
	public boolean passaNaLoc(GeoPosition geo){
		for(Coordenadas cor : coordenadas){
			if(geo == cor.getGeo()){
				return true;
			}
		}
		return false;
	}

	public String getIdlinha() {
		return idlinha;
	}


	public String getNome() {
		return nome;
	}


	public String getCodigo() {
		return codigo;
	}


	public String getTipo() {
		return tipo;
	}
	
	public String toString() {
		return "Cod: "+codigo +" Nome: "+ nome ;
	}
}