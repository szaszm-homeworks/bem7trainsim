package bem7trainsim;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marci on 2017.03.18.
 * Represents the stations where the passengers can get down from the trains
 */
public class DownStation extends SimpleRail {
    private Color color;

    /**
     * Creates a station with given color where people can get off the train
     * @param color the color of the station
     */
    public DownStation(Color color, SimpleRail.Orientation orientation) {
        super(orientation);
        this.color = color;
    }

    /**
     * @param train The train which is arrived
     * @throws CollisionException Thrown if the rail already has a train
     */
    public void arrive(Train train) throws CollisionException {
        super.arrive(train);
        train.empty(color);
    }

    /**
     * Gets the draw data of the field
     * @return the string representing the field
     */
    @Override
    public FieldDrawData getDrawData() {
        List<String> layers = new ArrayList<>();

        if (color.equals(Color.RED)) {
            layers.add("I");
        }
        else if (color.equals(Color.YELLOW)) {
            layers.add("Á");
        }
        else if (color.equals(Color.GREEN)) {
            layers.add("Ö");
        }
        else layers.add("É");

        if (coach != null)
            layers.add(coach.getDrawData());

        return new FieldDrawData(layers);
    }
}
