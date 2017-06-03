package generator;


public class GivenQuantGenerator implements QuantGenerator {
    double number=0;
    public GivenQuantGenerator(double number)
    {
        this.number = number;
    }

    public double generate()
    {
        return number;
    }
}
