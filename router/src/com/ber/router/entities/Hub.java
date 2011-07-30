/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ber.router.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author anna
 */
@Entity
public class Hub {
	
	@Id
    private String name;
	 
    private int rate;
    private Double a1;
    private Double b1;
    private Double a2;
    private Double b2;

    
    public Hub() {}

	public Hub(String name, int rate, Double a1, Double b1, Double a2, Double b2) {
        this.name = name;
        this.rate = rate;
        this.a1 = a1;
        this.b1 = b1;
        this.a2 = a2;
        this.b2 = b2;
    }
    
    

    public Double getA1() {
        return a1;
    }

    public void setA1(Double a1) {
        this.a1 = a1;
    }

    public Double getA2() {
        return a2;
    }

    public void setA2(Double a2) {
        this.a2 = a2;
    }

    public Double getB1() {
        return b1;
    }

    public void setB1(Double b1) {
        this.b1 = b1;
    }

    public Double getB2() {
        return b2;
    }

    public void setB2(Double b2) {
        this.b2 = b2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
    
    
}
