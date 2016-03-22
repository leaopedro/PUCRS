package pucrs.progoo;

import java.util.ArrayList;
import java.util.List;

import org.jxmapviewer.viewer.GeoPosition;

public class Trecho {

	private Linha linha;
	private Parada paradaInicial;
	private Parada paradaFinal;
	
	public Trecho(Linha linha, Parada pi, Parada pf){
		this.linha = linha;
		this.paradaInicial = pi;
		this.paradaFinal = pf;
	}
	
	public Linha getLinha() {
		return linha;
	}
	public void setLinha(Linha linha) {
		this.linha = linha;
	}
	public Parada getParadaInicial() {
		return paradaInicial;
	}
	public void setParadaInicial(Parada paradaInicial) {
		this.paradaInicial = paradaInicial;
	}
	public Parada getParadaFinal() {
		return paradaFinal;
	}
	public void setParadaFinal(Parada paradaFinal) {
		this.paradaFinal = paradaFinal;
	}

//	public List<Coordenadas> getCoordTrecho(){
//		
//		List<Coordenadas> coordTrecho = new ArrayList<Coordenadas>();
//		
//		GeoPosition gp1 = paradaInicial.getGeoPosition();
//		
//		
//		for (Coordenadas c : linha.getCoordenadas()){
//			
//			//linha.getCoordenadas().subList(fromIndex, toIndex)
//			
//		}
//		
//		
//	}
	
	@Override
	public String toString() {
		return "Trecho [linha=" + linha + ", paradaInicial=" + paradaInicial + ", paradaFinal=" + paradaFinal + "]";
	}
	
	

}
