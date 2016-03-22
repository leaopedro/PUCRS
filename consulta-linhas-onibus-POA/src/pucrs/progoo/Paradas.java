package pucrs.progoo;

import java.awt.Color;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.jxmapviewer.viewer.GeoPosition;

import pucrs.progoo.geo.AlgoritmosGeograficos;
import pucrs.progoo.geo.JanelaConsulta;

public class Paradas {

	private Map<String, Parada> paradas;
	
	public Paradas(){
		paradas = new HashMap<>();
		
		carregaParadas();
	}
	
	
	
	public Parada find(String idparada){
		if(paradas.containsKey(idparada)) return paradas.get(idparada);
		return null;
	}
	
	public void carregaParadas(){
		Path path1 = Paths.get("paradas.csv");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path1, Charset.forName("utf8")))) {
			  sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			  String idparada, codigo, terminal;
			  double latitude;
			  double longitude;
			  
			  for(int i=0; i<5; i++){
				  sc.next();
			  }
			  
			  while(sc.hasNext()){
				  idparada = sc.next();
				  codigo = sc.next();
				  longitude = sc.nextDouble();
				  latitude = sc.nextDouble();
				  terminal = sc.next();
				  Parada nova = new Parada(idparada, codigo, latitude, longitude, terminal);
				  paradas.put(idparada, nova);
			  }
		}catch(Exception e){
			JanelaConsulta.labelInstrucao.setText("Erro! Reinicie!");
			JanelaConsulta.labelInstrucao.setForeground(Color.red);
			System.out.println("Erro ao ler arquivo paradas");
		}
	}
	
	public Map<String, Parada> getParadas(){
		return paradas;
	}
	
	public boolean isProxima(Parada p1, Parada p2){
		if (AlgoritmosGeograficos.calcDistancia(p1.getGeoPosition(), p2.getGeoPosition())<0.3){
			return true;
		}else{
			return false;
		}
	}
	
	
	public Parada encontraPorLoc(GeoPosition gp){
		
		for(Entry<String, Parada> e : paradas.entrySet()){
    		Parada par =  e.getValue();
    		if( AlgoritmosGeograficos.calcDistancia(par.getGeoPosition(), gp)< 0.03){
    			return par;
    		}
    	}
		return null;
		
	}
	/*public void listar(){
		for(Parada par: paradas){
			System.out.println(par);
		}
	}*/
}