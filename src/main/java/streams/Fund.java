package streams;

class Fund {

	private final String name;
	private final String country;
	private final double nav;
	private final double volume;

	public Fund(String name, String country, double nav, double volume) {

		this.name = name;
		this.country = country;
		this.nav = nav;
		this.volume = volume;

	}

	public String getName() { return name; }
	public String getCountry() { return country; }
	public double getNav() { return nav; }
	public double getVolume() { return volume; }

}
