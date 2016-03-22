/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pucrs.progoo.geo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import pucrs.progoo.Coordenadas;
import pucrs.progoo.Linha;
import pucrs.progoo.Linhas;
import pucrs.progoo.Parada;
import pucrs.progoo.Paradas;
import pucrs.progoo.Trecho;

/**
 *
 * @author Marcelo Cohen
 */
public class JanelaConsulta extends javax.swing.JFrame {

    private GerenciadorMapa gerenciador;
    private EventosMouse mouse;
    private Random generator = new Random();
    
    private JPanel painelMapa;
    private JPanel painelLateral;
    private JList jlistLinhas;
    public static JLabel labelInstrucao;
//    private JScrollPane jsp;

    private int function = 1; 
    
    List<Parada> p = new ArrayList<>();
    
    private Paradas paradas;
    private Linhas linhas;
    /**
     * Creates new form JanelaConsulta
     */
	public JanelaConsulta() {
		super();
		// initComponents();

		paradas = new Paradas();
		linhas = new Linhas();
		addParadasELinhas(linhas, paradas);

		//System.out.println("linhas: " + linhas.getContador());

		GeoPosition poa = new GeoPosition(-30.05, -51.18);
		gerenciador = new GerenciadorMapa(poa, GerenciadorMapa.FonteImagens.VirtualEarth);
		mouse = new EventosMouse();
		gerenciador.getMapKit().getMainMap().addMouseListener(mouse);
		gerenciador.getMapKit().getMainMap().addMouseMotionListener(mouse);

		painelLateral = new JPanel();
		getContentPane().add(painelLateral, BorderLayout.NORTH);

		painelMapa = new JPanel();
		painelMapa.setLayout(new BorderLayout());
		painelMapa.add(gerenciador.getMapKit(), BorderLayout.CENTER);
		
		JButton btnPopulaLinhas = new JButton("Consulta 1");
		btnPopulaLinhas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gerenciador.clear();
				populaListaCheia();
				labelInstrucao.setText("Selecione uma linha");
				labelInstrucao.setForeground(Color.blue);
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnConjuntoParadas = new JButton("Consulta 4");
		btnConjuntoParadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelInstrucao.setText("Marque as paradas no mapa:");
				labelInstrucao.setFont(new Font("Dialog", Font.BOLD, 11));
				labelInstrucao.setForeground(Color.blue);
				populaParadas();
				function = 1;
				p.clear();
			}
		});
		
		labelInstrucao = new JLabel("Selecione uma busca:");
//		labelInstrucao.setForeground(Color.RED);
		labelInstrucao.setFont(new Font("Dialog", Font.BOLD, 14));
		
		JButton btnSelecPontos = new JButton("Consulta 5");
		btnSelecPontos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populaParadas();
				function = 2;
				labelInstrucao.setText("Selecione 2 paradas");
			}
		});
		
		JButton btnVerLinhasDa = new JButton("Consulta 2");
		btnVerLinhasDa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populaParadas();
				function = 3;
				labelInstrucao.setText("Selecione uma parada");
			}
		});
		
		JButton btnVerLinhasPrximas = new JButton("Consulta 3");
		btnVerLinhasPrximas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				function = 4;
				labelInstrucao.setText("Selecione um ponto");
			}
		});
		
		JButton btnEfetuarConsulta4 = new JButton("Efetuar consulta 4");
		btnEfetuarConsulta4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				conjuntoDeParadas(p);
				p.clear();
			}
		});
		
		tfPesquisa = new JTextField();
		tfPesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String pesq = tfPesquisa.getText();
				filtro(pesq);
			}
		});
		tfPesquisa.setToolTipText("Filtro");
		tfPesquisa.setColumns(10);
		

		GroupLayout gl_painelLateral = new GroupLayout(painelLateral);
		gl_painelLateral.setHorizontalGroup(
			gl_painelLateral.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelLateral.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_painelLateral.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
						.addComponent(btnEfetuarConsulta4, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
						.addComponent(labelInstrucao)
						.addComponent(btnPopulaLinhas, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
						.addComponent(btnVerLinhasDa, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
						.addComponent(btnVerLinhasPrximas, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
						.addComponent(btnConjuntoParadas, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
						.addComponent(btnSelecPontos, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tfPesquisa, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(painelMapa, GroupLayout.PREFERRED_SIZE, 597, GroupLayout.PREFERRED_SIZE)
					.addGap(18))
		);
		gl_painelLateral.setVerticalGroup(
			gl_painelLateral.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_painelLateral.createSequentialGroup()
					.addGap(4)
					.addComponent(labelInstrucao)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPopulaLinhas)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnVerLinhasDa)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnVerLinhasPrximas)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnConjuntoParadas)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSelecPontos)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tfPesquisa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnEfetuarConsulta4)
					.addContainerGap(549, Short.MAX_VALUE))
				.addComponent(painelMapa, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1115, Short.MAX_VALUE)
		);

		jlistLinhas = new JList();
		jlistLinhas.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				Linha l = (Linha) jlistLinhas.getSelectedValue();
				consultaLinha(l);
			}
		});
		
		scrollPane.setViewportView(jlistLinhas);
		//		jsp = new JScrollPane(listLinhas);
//				listLinhas.setPreferredSize(new Dimension(800, 500));
		painelLateral.setLayout(gl_painelLateral);
		
		populaListaCheia();
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
//	int cont = 0;
//	List<Trecho> listTrechos = new ArrayList<>();
	
	public <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
	
	List<Trecho> caminho = new ArrayList<>();
	
	private List<Trecho> encontraCaminho(Parada p1, Parada p2){

		List<Linha> listLinhasP1 = p1.getLinhasP();
		
		List<Linha> listLinhasP2 = p2.getLinhasP();
		
		List<Linha> intersecao = intersection(listLinhasP1, listLinhasP2);
		
		if (!intersecao.isEmpty()){
			Trecho trecho = new Trecho (intersecao.get(0), p1, p2);
			caminho.add(trecho);
			return caminho;
		}else{
			Linha l = listLinhasP1.get(0);
			List<Parada> paradasl = l.getParadasL();
			int pos = paradasl.indexOf(p1);
			Parada p3 = paradasl.get(pos+1);
			return encontraCaminho(p3, p2);
		}
		
//		if (p1 == p2)return null;
//		
//		List<Linha> listLinhas = returnLinhaPorParada(p1);
//
//		List<Parada> paradasl = new ArrayList<>();
//		
//		Linha l = listLinhas.get(0);
//		paradasl = l.getParadasL();
//		cont = 0;
//		for(Parada p : paradasl){
//			if(p == p2){
//				Trecho t = new Trecho(l, p1, p);
//				listTrechos.add(t);
////					paradasDescer.add(p);
//				return listTrechos;
//			}
//			cont+=1;
//		}
//			
//		return encontraCaminho(paradasl.get(cont), p2);
		
//		mostraConjuntoLinhas(listLinhas);
//		mostraParadas(paradasDescer, "DESCER AQUI", Color.cyan);
	}
	
	private void filtro(String pesq){
		
		Map<String, Linha> lstLinhas = linhas.getLinhas();
		
		DefaultListModel<Linha> newModel = new DefaultListModel<Linha>();

		for(Entry<String, Linha> e : lstLinhas.entrySet()){
			Linha l =  e.getValue();
		     if (l.getNome().toLowerCase().contains(pesq.toLowerCase()) ||
		    		 l.getCodigo().toLowerCase().contains(pesq.toLowerCase())){
		    	 newModel.addElement(l);
		     }
		}
		
		jlistLinhas.setModel(newModel);
		
	}
	
	
	private void mostraTrecho(Trecho t){  //NAO FINALIZADO
			
		MyWaypoint p1 = new MyWaypoint(Color.blue, "Subir na linha "+ t.getLinha().getNome() , t.getParadaInicial().getGeoPosition());
		MyWaypoint p2 = new MyWaypoint(Color.blue, "Descer da linha "+ t.getLinha().getNome() ,  t.getParadaFinal().getGeoPosition());
		
		List<MyWaypoint> listPoints = new ArrayList<>();
		listPoints.add(p1);
		listPoints.add(p2);
		gerenciador.setPontos(listPoints);
		
		Tracado tr = new Tracado();
    		GeoPosition loc1 = p1.getPosition();
    		GeoPosition loc2 = p1.getPosition();
    		tr.addPonto(loc1);
    		tr.addPonto(loc2);
    		
		gerenciador.addTracado(tr);
		
	
	}
	
	private void conjuntoDeParadas(List<Parada> p){
		if (p.isEmpty())return;
		
		Map<String, Linha> lstLinhas = linhas.getLinhas();
		
		List<Linha> listLinhas = new ArrayList<>();
		
		int size = p.size();
		int cont = 0;

		for(Entry<String, Linha> e : lstLinhas.entrySet()){
			Linha l =  e.getValue();
			cont = 0;
    			for (Parada par : p){
    				if (l.passaNaParada(par)){
    					cont +=1;
    				}
    				if (cont >= size){
    					listLinhas.add(l);
    					break;
    				}
    			}
    		}

		//System.out.println(listLinhas);
		populaJList(listLinhas);
	}
	
	private void populaJList(List<Linha> listLinhas){
		DefaultListModel model = new DefaultListModel();
		
		for (Linha l : listLinhas) {
			model.addElement(l);
		}
		
		jlistLinhas.setModel(model);
	}

	private void populaListaCheia() {

		DefaultListModel model = new DefaultListModel();
		
		Map<String, Linha> lstLinhas = linhas.getLinhas();

		for (Entry<String, Linha> e : lstLinhas.entrySet()) {
			Linha l = e.getValue();
			model.addElement(l);
		}
		 
		jlistLinhas.setModel(model); 
    	
//        listLinhas.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//        listLinhas.setLayoutOrientation(JList.HORIZONTAL_WRAP);
//        listLinhas.setVisibleRowCount(-1);
//        JScrollPane listScroller = new JScrollPane(listLinhas);
//        listScroller.setPreferredSize(new Dimension(250, 80));
    }

	private void mostraParadas(List<Parada> listParadas, String txt, Color color){
		if (listParadas.isEmpty())return;
		List<MyWaypoint> listPoints = new ArrayList<>();
		
		
		for (Parada p : listParadas){
			GeoPosition pos = p.getGeoPosition();
			listPoints.add(new MyWaypoint(color, txt, pos));
		}
		
		gerenciador.setPontos(listPoints);
		
	}
	private void getLinhasProximas(GeoPosition gp){
		
		Map<String, Linha> listLinhas = linhas.getLinhas();
		
		List<Linha> linhasProx = new ArrayList<>(); 
		
		for(Entry<String, Linha> e : listLinhas.entrySet()){
			Linha l = e.getValue();
			if (l.passaProximo(gp)){
				linhasProx.add(l);
			}
		}
		
		labelInstrucao.setText(linhasProx.size() + " linhas proximas!");
		
		if(linhasProx.size() > 0){
    		labelInstrucao.setForeground(Color.blue);
    	}else{
    		labelInstrucao.setForeground(Color.red);
    		gerenciador.clear();
    	}
		
		populaJList(linhasProx);
		
	}
    private void populaParadas(){
    	gerenciador.clear();
    	//Paradas paradas = new Paradas();
    	
    	Map<String, Parada> lstParadas = paradas.getParadas();
    	
    	List<MyWaypoint> lstPoints = new ArrayList<>();
    	
    	for(Entry<String, Parada> e : lstParadas.entrySet()){
    		Parada par =  e.getValue();
    		GeoPosition pos = par.getGeoPosition();
    		lstPoints.add(new MyWaypoint(Color.BLACK, "", pos));
    	}

        // Informa o resultado para o gerenciador
    	gerenciador.setPontos(lstPoints);
    	
    	this.repaint();
    }
    
    private void addParadasELinhas(Linhas linhas, Paradas paradas) {
		Path path3 = Paths.get("paradalinha.csv");
		try (Scanner sc = new Scanner(Files.newBufferedReader(path3,
				Charset.forName("utf8")))) {
			sc.useDelimiter("[;\n]"); // separadores: ; e nova linha
			String idlinha, idparada;
			for (int i = 0; i < 2; i++) {
				sc.next();
			}
			while (sc.hasNext()) {
				idlinha = sc.next();
				idparada = sc.next();
				
				Linha linha = linhas.find(idlinha);
				Parada parada = paradas.find(idparada);

				if (linha != null && parada != null) {
					linha.addParada(parada);
					parada.addLinha(linha);
				}
			}
		} catch (Exception e) {
			labelInstrucao.setText("Erro! Reinicie!");
			labelInstrucao.setForeground(Color.red);
			e.printStackTrace();
			System.out.println("Erro ao ler arquivo paradalinha");
		}
	}
    
    private void mostraConjuntoLinhas(List<Linha> lstLinhas){

    	if(lstLinhas.isEmpty()){
    		return;
    	}
    	
    	List<MyWaypoint> lstPoints = new ArrayList<>();
    	ArrayList<Coordenadas> lstCoordenadas;
    	
    	for (Linha l : lstLinhas){
    		Tracado tr = new Tracado();
    		lstCoordenadas = l.getCoordenadas();
    		for(Coordenadas e : lstCoordenadas){
        		GeoPosition loc = e.getGeo();
        		lstPoints.add(new MyWaypoint(Color.green, "", loc));
        		tr.addPonto(loc);
        	}
    		gerenciador.addTracado(tr);
    	}
    	
    	
    }

    private void consulta(java.awt.event.ActionEvent evt) {

        // Para obter um ponto clicado no mapa, usar como segue:
    	GeoPosition pos = gerenciador.getPosicao();     

        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        /* Exemplo de uso:
        
        GeoPosition loc = new GeoPosition(-30.05, -51.18);  // ex: localiza��o de uma parada
        GeoPosition loc2 = new GeoPosition(-30.08, -51.22); // ex: localiza��o de uma parada
        lstPoints.add(new MyWaypoint(Color.BLUE, "Teste", loc));
        lstPoints.add(new MyWaypoint(Color.BLACK, "Teste 2", loc2));
        */
        //Linha linhateste = linhas.find("128108");
          Linha linhateste = linhas.find("128188");
        System.out.println(linhateste.getNome());
        
        ArrayList<Coordenadas> lstCoordenadas = linhateste.getCoordenadas();
        
        Tracado tr = new Tracado();
        
        for(Coordenadas e : lstCoordenadas){
    		GeoPosition loc = e.getGeo();
    		lstPoints.add(new MyWaypoint(Color.BLACK, "", loc));
    		tr.addPonto(loc);
    	}
        
        ArrayList<Parada> lstpar = linhateste.getParadasL();
        
        for(Parada e : lstpar){
    		GeoPosition loc = e.getGeoPosition();
    		lstPoints.add(new MyWaypoint(Color.MAGENTA, "", loc));
    	}
        

        // Informa o resultado para o gerenciador
        gerenciador.setPontos(lstPoints);
        
        // Exemplo: criando um tra�ado
        //Tracado tr = new Tracado();
        // Adicionando as mesmas localiza��es de antes
        //tr.addPonto(loc);
        //tr.addPonto(loc2);
        tr.setCor(Color.CYAN);
        // E adicionando o tra�ado...
        gerenciador.addTracado(tr);
        
        // Outro:
        //GeoPosition loc3 = new GeoPosition(-30.02, -51.16);  
        //GeoPosition loc4 = new GeoPosition(-30.01, -51.24);
        //Tracado tr2 = new Tracado();
        //tr2.addPonto(loc3);
        //tr2.addPonto(loc4);
        //tr2.setCor(Color.MAGENTA);
        // E adicionando o tra�ado...
        //gerenciador.addTracado(tr2);
        
        this.repaint();

    }
    
    private void consultaLinha(Linha l){
    	gerenciador.clear();
    	if (l == null){
    		return;
    	}
    	List<MyWaypoint> lstPoints = new ArrayList<>();
    	ArrayList<Coordenadas> lstCoordenadas = l.getCoordenadas();
    	Tracado tr = new Tracado();
        
        for(Coordenadas e : lstCoordenadas){
    		GeoPosition loc = e.getGeo();
    		//lstPoints.add(new MyWaypoint(Color.BLACK, "", loc));
    		tr.addPonto(loc);
    	}
        
        ArrayList<Parada> lstpar = l.getParadasL();
        
        for(Parada e : lstpar){
    		GeoPosition loc = e.getGeoPosition();
    		lstPoints.add(new MyWaypoint(Color.MAGENTA, "", loc));
    	}
        

        // Informa o resultado para o gerenciador
        gerenciador.setPontos(lstPoints);
        tr.setCor(Color.CYAN);
        gerenciador.addTracado(tr);
    	
        this.repaint();
    }
    
    private void mostraLinhasPorParada(Parada p){
    	gerenciador.clear();
    	MyWaypoint parada = new MyWaypoint(Color.BLACK, "", p.getGeoPosition());
    	ArrayList pontos = new ArrayList<MyWaypoint>();
    	pontos.add(parada);
    	gerenciador.setPontos(pontos);
    	
    	
    	List<Linha> linhasP = p.getLinhasP();
    	
    	DefaultListModel model = new DefaultListModel();
    	
    	labelInstrucao.setText(linhasP.size()+" Linhas encontradas!");
    	
    	if(linhasP.size() > 0){
    		labelInstrucao.setForeground(Color.blue);
    	}else{
    		labelInstrucao.setForeground(Color.red);
    	}

		for (Linha l : linhasP) {
			model.addElement(l);
		}
		 
		jlistLinhas.setModel(model); 
    	
    }
    private void getParadasProximas(Parada p){
    	
    	Map<String, Parada> lstParadas = paradas.getParadas();
    	
    	List<MyWaypoint> lstPoints = new ArrayList<>();
    	
    	
    	
    	for(Entry<String, Parada> e : lstParadas.entrySet()){
    		Parada par =  e.getValue();
    		
    		if (paradas.isProxima(par, p)){
    			lstPoints.add(new MyWaypoint(Color.BLACK, "", par.getGeoPosition()));
    			labelInstrucao.setText(lstPoints.size() + "parada(s) encontradas");
    			labelInstrucao.setForeground(Color.blue);
    			if(lstPoints.size() > 0){
    	    		labelInstrucao.setForeground(Color.blue);
    	    	}else{
    	    		labelInstrucao.setForeground(Color.red);
    	    	}
    		}

    	}

        // Informa o resultado para o gerenciador
    	gerenciador.setPontos(lstPoints);
    	
    	
    	
    }

    /*
    private void consulta(java.awt.event.ActionEvent evt) {

        // Para obter um ponto clicado no mapa, usar como segue:
    	GeoPosition pos = gerenciador.getPosicao();     

        // Lista para armazenar o resultado da consulta
        List<MyWaypoint> lstPoints = new ArrayList<>();
        
        // Exemplo de uso:
        
        GeoPosition loc = new GeoPosition(-30.05, -51.18);  // ex: localiza��o de uma parada
        GeoPosition loc2 = new GeoPosition(-30.08, -51.22); // ex: localiza��o de uma parada
        lstPoints.add(new MyWaypoint(Color.BLUE, "Teste", loc));
        lstPoints.add(new MyWaypoint(Color.BLACK, "Teste 2", loc2));

        // Informa o resultado para o gerenciador
        gerenciador.setPontos(lstPoints);
        
        // Exemplo: criando um tra�ado
        Tracado tr = new Tracado();
        // Adicionando as mesmas localiza��es de antes
        tr.addPonto(loc);
        tr.addPonto(loc2);
        tr.setCor(Color.CYAN);
        // E adicionando o tra�ado...
        gerenciador.addTracado(tr);
        
        // Outro:
        GeoPosition loc3 = new GeoPosition(-30.02, -51.16);  
        GeoPosition loc4 = new GeoPosition(-30.01, -51.24);
        Tracado tr2 = new Tracado();
        tr2.addPonto(loc3);
        tr2.addPonto(loc4);
        tr2.setCor(Color.MAGENTA);
        // E adicionando o tra�ado...
        gerenciador.addTracado(tr2);
        
        this.repaint();

    }*/
    private int clicks = 0;
    private int maxClicks=3;
    private JTextField tfPesquisa;
    
    
    
    private void botaoEsquerdo(GeoPosition loc){
    	if (clicks>=maxClicks){
    		p.clear();
    		clicks=0;
    	}
    	if(function == 1){
    		
    		Parada par = paradas.encontraPorLoc(loc);
    		if (par != null){
				p.add(par);
				labelInstrucao.setText("Paradas selecionadas: " +p.size());
			}
    		
    	}else if(function == 2){
        		maxClicks = 2;
        		
                switch (clicks++) {
                  case 0:
                	  
                    Parada par1 = paradas.encontraPorLoc(loc);
        			if (par1 != null){
        				p.add(par1);
        				labelInstrucao.setText("Paradas selecionadas: " +p.size());
        			}
                    break;
                  case 1:
                    //System.out.println("Clicou 2");
                    Parada par2 = paradas.encontraPorLoc(loc);
        			if (par2 != null){
        				p.add(par2);
        				labelInstrucao.setText("Paradas selecionadas: " +p.size());
        			}
        			if (p.size() == 2){
        				caminho = new ArrayList<>();
//        				List<Linha> linhasPegar = new ArrayList<>();
        				List<Trecho> t = encontraCaminho(p.get(0), p.get(1));
        				if (t.isEmpty()){
        					labelInstrucao.setText("Nenhum caminho encontrado");
        					labelInstrucao.setForeground(Color.red);
        				}else{
        					for (Trecho tr : t){
        						mostraTrecho(tr);
        					}
        				}
        			}else{
        				labelInstrucao.setText("Tente novamente");
        				labelInstrucao.setForeground(Color.red);
        			}
                    break;
                }
    	}else if(function == 3){
    		Parada par1 = paradas.encontraPorLoc(loc);
    		if (par1 != null){
    			mostraLinhasPorParada(par1);
    		}
    	}else if(function == 4){
    		List<MyWaypoint> lstPoints = new ArrayList<>();
    		gerenciador.setPontos(lstPoints);
    		getLinhasProximas(loc);
    	}
    	
    }
    private class EventosMouse extends MouseAdapter
    {
    	private int lastButton = -1;    	
    	
    	@Override
    	public void mousePressed(MouseEvent e) {
    		JXMapViewer mapa = gerenciador.getMapKit().getMainMap();
    		GeoPosition loc = mapa.convertPointToGeoPosition(e.getPoint());
//    		System.out.println(loc.getLatitude()+", "+loc.getLongitude());
    		gerenciador.setPosicao(loc);
    		gerenciador.getMapKit().repaint(); 
    		lastButton = e.getButton();
    		
    		if(lastButton==MouseEvent.BUTTON1) {
    			botaoEsquerdo(loc);
    	    }
    		if(lastButton==MouseEvent.BUTTON2) {
    			clicks=0;
    		}
    		// Bot�o 3: seleciona localiza��o
    		if(lastButton==MouseEvent.BUTTON3) {  
    			clicks=0;
    			Parada p = paradas.encontraPorLoc(loc);
    			if (p != null){
    				getParadasProximas(p);
    			}
    			//gerenciador.getMapKit().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    			

    		}
    	}    
    } 	
}