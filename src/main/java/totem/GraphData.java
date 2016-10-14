package totem;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.EmbeddedId;

@Entity
@Table
public class GraphData {

	@EmbeddedId
	private DataKey datakey;

	@NotNull
	private int amp;
	
	@NotNull
	private int voltage;

	public GraphData() {
	}
	
	public GraphData(Date date, String model, int amp, int voltage) {
		this.datakey = new DataKey(date, model);
		this.amp = amp;
		this.voltage = voltage;
	}
	
	public DataKey getDataKey() {
		return datakey;
	}
	
	public void setDataKey(DataKey datakey) {
		this.datakey = datakey;
	}
	
	public int getAmp() {
		return amp;
	}
	
	public void setAmp(int amp) {
		this.amp = amp;
	}
	
	public int getVoltage() {
		return voltage;
	}
	
	public void setVoltage(int voltage) {
		this.voltage = voltage;
	}
  
}
