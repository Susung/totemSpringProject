package totem.mysql;

import java.util.Date;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class DataKey implements Serializable {
	private Date date;
	private String model;
	
	public DataKey() {
	}
	
	public DataKey(Date date, String model) {
		this.date = date;
		this.model = model;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
}
