package streams;

public class Fund {

	public String name;
	public String country;
	public double nav;
	public double volume;

	public Fund(String name, String country, double nav, double volume) {

		this.name = name;
		this.country = country;
		this.nav = nav;
		this.volume = volume;

	}

	public String getName(){
		return name;
	}
	public String getCountry() { return country; }
	public double getNav() { return nav; }
	public double getVolume() { return volume; }

}
