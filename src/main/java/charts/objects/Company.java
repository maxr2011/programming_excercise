package charts.objects;

public class Company {

	String date;
	String security;
	double weighting;

	public Company(String d, String s, double w) {

		this.date = d;
		this.security = s;
		this.weighting = w;

	}

	public String getDate() {
		return date;
	}

	public String getSecurity() {
		return security;
	}

	public double getWeighting() {
		return weighting;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public void setWeighting(double weighting) {
		this.weighting = weighting;
	}
}
