package totem.cassandra;

import java.util.Date;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

//data model of the cassandra data entry
@Table
public class DataEntry {

	@PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String date;

	@PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
	private long time;

	@PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.CLUSTERED)
	private String model;
	
	@Column
	private String type;

	@Column
	private double amp;
	
	@Column
	private double voltage;
	
	public DataEntry() {
	}
	
	public DataEntry(String model, String date, String type, long time, double amp, double voltage) {
		Date temp = new Date(time);
		this.model = model;
		this.date = date;
		this.time = time;
		this.amp = amp;
		this.voltage = voltage;
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	

	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}

	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public double getAmp() {
		return amp;
	}
	
	public void setAmp(double amp) {
		this.amp = amp;
	}
	
	public double getVoltage() {
		return voltage;
	}
	
	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}
	
	@Override
	public String toString() {
	return "{model="+model+",date="+date+",time="+time+",amp="+amp+",voltage="+voltage+"}";
	}
	
	/*@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;

		DataEntry d = (DataEntry) obj;

		if (!date.equals(d.date)) return false;
		if (time != d.time) return false;
		if (!model.equals(d.model)) return false;
		if (amp != d.amp) return false;
		if (voltage != d.voltage) return false;

		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = (int) time;
		result = prime * result + date.hashCode();
		result = prime * result + model.hashCode();
		result = prime * result + amp;
		result = prime * result + voltage;
		return result;
	}*/
}

