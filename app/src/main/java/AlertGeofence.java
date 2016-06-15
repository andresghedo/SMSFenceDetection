import java.util.ArrayList;

/**
 * Created by andrea on 14/06/16.
 */
public class AlertGeofence {

    private Double lat;

    private Double lng;

    private ArrayList<String> numbers;

    private String message;

    private void setLat(Double lat) {
        this.lat = lat;
    }

    private void setLng(Double lng) {
        this.lng = lng;
    }

    private void setNumbers(ArrayList<String> numbers) {
        this.numbers = numbers;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    private Double getLat() {
        return this.lat;
    }

    private Double getLng() {
        return this.lng;
    }

    private ArrayList<String> getNumbers() {
        return this.numbers;
    }

    private String getMessage() {
        return this.message;
    }

}
