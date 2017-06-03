package generator;


import java.util.Random;

public class RandomQuantGenerator implements QuantGenerator {

    public double generate()
    {
        Random random = new Random();
        return 1.8 *random.nextDouble();
    }
}
