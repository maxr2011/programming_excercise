package charts.objects;

public class Country {

    private String name;
    private double weight;

    public Country() {
        //leerer Konstruktor
    }

    public Country(String n, double w){
        this.name = n;
        this.weight = w;
    }

    public String getName(){
        return name;
    }

    public Double getWeight(){
        return weight;
    }

    public boolean nameIsNull(){
        return this.name == null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
