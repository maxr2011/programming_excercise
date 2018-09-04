package exercise.objects;

import exercise.interfaces.Element;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="country_table")
public class Country implements Element {

	@Id
	@GeneratedValue
	@SuppressWarnings("unused")
	private Long id;

	private String name;
	private double weight;

	public Country() {
	}

	public Country(String n, double w) {
		this.name = n;
		this.weight = w;
	}

	public String getName() {
		return name;
	}

	public Double getWeight() {
		return weight;
	}

	public boolean nameIsNull() {
		return this.name == null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
