/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ber.router.domain;

import java.util.List;

import com.ber.router.entities.Route;

/**
 *
 * @author anna
 */
public class DestinationRoutes {
    private String destination;
    private List<Route> routes;
    
    
    public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	public DestinationRoutes(String name, List<Route> routes){
        destination = name;
        this.routes = routes;
    }

    public String getDestination() {
        return destination;
    }

    public List<Route> getRoutes() {
        return routes;
    }
    
    
}
