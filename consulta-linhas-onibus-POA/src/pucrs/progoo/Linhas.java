package pucrs.progoo;


import java.awt.Color;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import pucrs.progoo.geo.JanelaConsulta;


public class Linhas {
	
	private Map<String, Linha> linhas;
	private int contador;
	
	public Linhas(){
		linhas = new HashMap<>();
		carregaLinhas();
		addCoordenadas();
	}
	
	
	
	public Linha find(String idlinha){
		if(linhas.containsKey(idlinha)) return linhas.get(idlinha);
		return null;
	}
	
	public void carregaLinhas(){
		Path path = Paths.get("linhas.csv");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path, Charset.forName("utf8")))) {
			  sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			  String idlinha, nome, codigo, tipo;
			  for(int i=0; i<4; i++){
				  sc.next();
			  }
			  while(sc.hasNext()){
				  idlinha = sc.next();
				  nome = sc.next();
				  codigo = sc.next();
				  tipo = sc.next();
				  Linha nova = new Linha(idlinha, nome, codigo, tipo);
				  linhas.put(idlinha, nova);
				  contador++;
			  }
		}catch(Exception e){
			JanelaConsulta.labelInstrucao.setText("Erro! Reinicie!");
			JanelaConsulta.labelInstrucao.setForeground(Color.red);
			System.out.println("Erro ao ler arquivo linhas");
		}
			 
	}
	
	
	public Map<String, Linha> getLinhas(){
		return linhas;
	}
	
	public void addCoordenadas(){
		Path path2 = Paths.get("coordenadas.csv");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path2, Charset.forName("utf8")))) {
			  sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			  
			  String idcoordenada, idlinha;
			  double latitude;
			  double longitude;
			  
			  for(int i=0; i<4; i++){
				  sc.next();
			  }
			  while(sc.hasNext()){
				  idcoordenada = sc.next();
				  latitude = sc.nextDouble();
				  longitude = sc.nextDouble();
				  idlinha = sc.next();
				  
				  Coordenadas nova = new Coordenadas(idcoordenada, latitude, longitude, idlinha);
				  Linha aux = linhas.get(idlinha);
				  if(aux != null)
				  aux.addCoordenada(nova);
			  }
		}catch(Exception e){
			System.out.println("Erro ao ler arquivo coordenadas:");
			e.printStackTrace();
		}
			 
	}



	public int getContador() {
		return contador;
	}



	public void setContador(int contador) {
		this.contador = contador;
	}
	
	/*
	public void removeNull(){
		int i = 1008;
		for(String idlinha : linhas.keySet()){
    		Linha l =  linhas.get(idlinha);
    		if(l.isEmptyCoordenadas() == true || l.isEmptyParadas() == true){
    			linhas.remove(l.getIdlinha());
    			i--;
    		}
    	}
		setContador(i);
	}
	
	public void listar(){
		for(Linha lin: linhas){
			System.out.println(lin);
		}
	}*/
}