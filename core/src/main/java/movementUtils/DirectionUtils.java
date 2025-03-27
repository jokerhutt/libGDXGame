package movementUtils;

public class DirectionUtils {

    public DirectionUtils () {

    }

    public static float calculateMagnitude (float dx, float dy) {

        float xSquared = dx * dx;
        float ySquared = dy * dy;

        float magnitude = (float) Math.sqrt(xSquared + ySquared);
        return magnitude;

    }

    public static float calculateScaledMagnitude (float dx, float dy, float speed) {
        float magnitude = calculateMagnitude(dx, dy);

        if (magnitude == 0) {
            return 0f;
        }

        float scaledMagnitude = speed / magnitude;
        return scaledMagnitude;
    }

}
