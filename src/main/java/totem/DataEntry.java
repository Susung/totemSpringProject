package totem;

import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;


@Table
public class DataEntry {

	@PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private String date;

	@PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
	private long time;

	@Column
	private String model;

	@Column
	private int amp;
	
	@Column
	private int voltage;
	
	public DataEntry() {
	}
	
	public DataEntry(String model, String date, long time, int amp, int voltage) {
		this.model = model;
		this.date = date;
		this.time = time;
		this.amp = amp;
		this.voltage = voltage;
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
	
	@Override
	public String toString() {
	return "{model="+model+",date="+date+",time="+time+",amp="+amp+",voltage="+voltage+"}";
	}
	
	@Override
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
	}
}

