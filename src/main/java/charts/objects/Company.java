package charts.objects;

public class Company {

    String date;
    String security;
    double weighting;

    public Company(String d, String s, double w){

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
