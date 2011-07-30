/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ber.router.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.google.appengine.api.datastore.Key;

/**
 *
 * @author anna
 */
@Entity
@IdClass(ExternalRoutePK.class)
public class Route implements Comparable {

	@Id
    private Hub hub;
	@Id
    private String dest;
    private Integer distance;

    public Route(Hub hub, String dest) {
        this.hub = hub;
        this.dest = dest;
    }

    
    public Route() {
	}

	public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Double getDrayageRate() {
        double rate = 0;
        double a1 = hub.getA1();
        double a2 = hub.getA2();
        double b1 = hub.getB1();
        double b2 = hub.getB2();
        if (distance < ((b2 - b1) / (a1 - a2))) {
            rate = a1 * distance + b1;
        } else {
            rate = a2 * distance + b2;
        }
        return rate;
    }

    public Hub getHub() {
        return hub;
    }

    public void setHub(Hub hub) {
        this.hub = hub;
    }

    public int compareTo(Object r) {
        Route other = null;
        try {
            other = (Route) r;
        } catch (Exception e) {
            System.err.println("Not a Route");
        }
        Double allInRate = hub.getRate() + getDrayageRate();
        Double otherAllInRate = other.hub.getRate() + other.getDrayageRate();
        return allInRate.compareTo(otherAllInRate);

    }
}