import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class DataAggregator {

    List<Zone> zones;
    ArrayList<Double> values;

    public DataAggregator(List<Zone> zones) {
        this.zones = zones;

        for (Zone zone : zones) {
            values.add(zone.getRating());
        }
    }

    public void setAggregateRating() {
        double mu = mean();
        double norm = normalisationFactor();
        for (Zone zone : zones) {
            double value = zone.getRating();
            value = (value - mu) / norm + 1; // between 0 and 1
            zone.setNormalisedRating(value);
        }
    }

    public double mean() {
        double mu = 0.0;
        for (double value : values) {
            mu = mu + value;
        }
        mu = mu / values.size();

        return mu;
    }

    public double normalisationFactor() {
        double listMax = Collections.max(values);
        double listMin = Collections.min(values);

        return listMax - listMin;
    }
}
