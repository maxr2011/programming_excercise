package charts.objects;

public class Country {

    String name;
    double weight;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
