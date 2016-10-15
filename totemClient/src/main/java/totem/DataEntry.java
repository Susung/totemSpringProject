package totem;

import java.util.Date;
import java.text.SimpleDateFormat;

public class DataEntry {

	private String date;
	private long time;
	private String model;
	private String type;
	private double amp;
	private double voltage;
	
	public DataEntry(String model, String type, long time, double amp, double voltage) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.model = model;
		this.date = sdf.format(time);
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
}

