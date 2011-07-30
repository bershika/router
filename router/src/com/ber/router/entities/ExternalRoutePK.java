package com.ber.router.entities;

import java.io.Serializable;

public class ExternalRoutePK implements Serializable{
	
	private Hub hub;
    private String dest;
    
	public Hub getHub() {
		return hub;
	}
	public void setHub(Hub hub) {
		this.hub = hub;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dest == null) ? 0 : dest.hashCode());
		result = prime * result + ((hub == null) ? 0 : hub.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExternalRoutePK other = (ExternalRoutePK) obj;
		if (dest == null) {
			if (other.dest != null)
				return false;
		} else if (!dest.equals(other.dest))
			return false;
		if (hub == null) {
			if (other.hub != null)
				return false;
		} else if (!hub.getName().equals(other.hub.getName()))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ExternalRoutePK [hub=" + hub + ", dest=" + dest + "]";
	}
	
    
}
