package totem.mysql;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.EmbeddedId;

//data model for entries in MySQL
@Entity
@Table(name = "graph")
public class GraphData {

	@EmbeddedId
	private DataKey datakey;

	@NotNull
	private double watt;

	public GraphData() {
	}
	
	public GraphData(DataKey datakey, double watt) {
		this.datakey = datakey;
		this.watt = watt;
	}
	
	public GraphData(Date date, String model, double watt) {
		this.datakey = new DataKey(date, model);
		this.watt = watt;
	}
	
	public DataKey getDataKey() {
		return datakey;
	}
	
	public void setDataKey(DataKey datakey) {
		this.datakey = datakey;
	}
	
	public double getWatt() {
		return watt;
	}
	
	public void setWatt(double watt) {
		this.watt = watt;
	}
  
}
