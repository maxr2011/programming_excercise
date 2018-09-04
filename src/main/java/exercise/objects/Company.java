package exercise.objects;

import exercise.interfaces.Element;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company_table")
public class Company implements Element {

	@Id
	@GeneratedValue
	@SuppressWarnings("unused")
	private Long id;

	private String date, security;
	private double weighting;

	public Company(){

	}

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
