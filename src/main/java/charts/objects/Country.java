package charts.objects;

public class Country {

    String name;
    double weight;

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

}
