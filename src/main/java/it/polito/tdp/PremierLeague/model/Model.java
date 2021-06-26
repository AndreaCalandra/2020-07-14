package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	Graph<Team, DefaultWeightedEdge> grafo;
	PremierLeagueDAO dao;
	Map<Integer, Team> idMap;
	List<Partita> partite;
	List<Adiacenza> lista;
	Map<Integer, Integer> mappaPunti;
	
	public Model () {
		idMap = new HashMap<>();
		dao = new PremierLeagueDAO();
		dao.mapTeams(idMap);
		partite = new ArrayList<>();
		lista = new ArrayList<>();
		mappaPunti = new HashMap<>();
	}
	
	public void creaGrafo() {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		// vertici
		
		Graphs.addAllVertices(grafo, dao.listAllTeams());
		
		// archi
		
		partite = dao.listaPartite(idMap);
		
		for (Team t: dao.listAllTeams()) {
			mappaPunti.put(t.teamID, 0);
		}
		
		for (Team t: dao.listAllTeams()) {
			for (Partita p: partite) {
				if (p.a.equals(t)) {
					if (p.risultato == 1) {
						int i = mappaPunti.get(t.teamID);
						i = i + 3;
						mappaPunti.put(p.a.teamID, i);
					} else if (p.risultato == 0) {
						int i = mappaPunti.get(t.teamID);
						i = i + 1;
						mappaPunti.put(p.a.teamID, i);
					}
				} else if (p.b.equals(t)) {
					if (p.risultato == -1) {
						int i = mappaPunti.get(t.teamID);
						i = i + 3;
						mappaPunti.put(p.b.teamID, i);
					} else if (p.risultato == 0) {
						int i = mappaPunti.get(t.teamID);
						i = i + 1;
						mappaPunti.put(p.b.teamID, i);
					}
				}
			}
		}
		
		for (Map.Entry<Integer, Integer> entry : mappaPunti.entrySet()) {
			Adiacenza a = new Adiacenza(idMap.get(entry.getKey()), entry.getValue());
			lista.add(a);
	    }
		
		for (Adiacenza a1: lista) {
			for (Adiacenza a2: lista) {
				if (!a1.a.equals(a2.a)) {
					DefaultWeightedEdge e = grafo.getEdge(a1.a, a2.a);
					if ( e == null ) {
						if (a1.peso > a2.peso) {
							Graphs.addEdgeWithVertices(grafo, a1.a, a2.a, a1.peso-a2.peso);
						} else if (a1.peso < a2.peso) {
							Graphs.addEdgeWithVertices(grafo, a2.a, a1.a, a2.peso-a1.peso);
						}
					}
				}
			}
		}
		
	}
	
	public int numVertici () {
		return grafo.vertexSet().size();
	}
	
	public int numArchi () {
		return grafo.edgeSet().size();
	}
	
	public List<Team> getSquadre() {
		return dao.listAllTeams();
	}
	
	public List<Team> migliori(Team t) {
		List<Adiacenza> vittoria = new ArrayList<>();
		List<Team> vicini = Graphs.neighborListOf(grafo, t);
		List<Team> ris = new ArrayList<>();
		for (Team team: vicini) {
			DefaultWeightedEdge e = grafo.getEdge(t, team);
			if (e != null) {
				double i = grafo.getEdgeWeight(e);
				if (i > 0) {
					Adiacenza a = new Adiacenza (team, (int) i);
					vittoria.add(a);
				}
			}
		}
		
		Collections.sort(vittoria);
		
		for (Adiacenza ad: vittoria) {
			ris.add(ad.a);
		}
		
		return ris;
	}
		
	public List<Team> peggiori(Team t) {
		List<Adiacenza> sconfitta = new ArrayList<>();
		List<Team> vicini = Graphs.neighborListOf(grafo, t);
		List<Team> ris = new ArrayList<>();
		for (Team team: vicini) {
			DefaultWeightedEdge e = grafo.getEdge(team, t);
			if (e != null) {
				double i = grafo.getEdgeWeight(e);
				if (i > 0) {
					Adiacenza a = new Adiacenza (team, (int) i);
					sconfitta.add(a);
				} 
			}
		}
		
		Collections.sort(sconfitta);
		
		for (Adiacenza ad: sconfitta) {
			ris.add(ad.a);
		}
		
		return ris;
}
	
	

	
}
