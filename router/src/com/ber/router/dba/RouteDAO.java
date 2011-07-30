package com.ber.router.dba;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.ber.router.entities.ExternalRoutePK;
import com.ber.router.entities.Hub;
import com.ber.router.entities.Route;


public class RouteDAO {
	private EntityManager em;

	public Route getRoute(Hub hub, String dest) {
		em = EMF.get().createEntityManager();
		Route route = null;
		ExternalRoutePK pk = new ExternalRoutePK();
		pk.setHub(hub);
		pk.setDest(dest);
		try {
			route = em.find(Route.class, pk);
		} finally {
			em.close();
		}
		return route;
	}

	public List<Route> getRoute(Hub hub, List<String> destList) {
		if(destList.isEmpty()) return null;
		em = EMF.get().createEntityManager();
		List<Route> routes = new ArrayList<Route>();
		Route route = null;
		ExternalRoutePK pk = new ExternalRoutePK();
		pk.setHub(hub);
		try {
			for (String dest : destList) {
				pk.setDest(dest);
				route = em.find(Route.class, pk);
				routes.add(route);
			}
		} finally {
			em.close();
		}
		return routes;
	}

	public void saveRoute() {
		em = EMF.get().createEntityManager();
		try {
			// ... do stuff with em ...
		} finally {
			em.close();
		}

	}
}
