package exercise.objects;

import org.springframework.stereotype.Component;

@Component("Company")
public class Company {

	private final String date, security;
	private final double weighting;

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

}
