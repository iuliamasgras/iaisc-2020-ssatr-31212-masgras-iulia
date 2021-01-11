package model;



public class Place {
    private String placeName;
    private int placeMinTime;
    private int placeMaxTime;
    private int placeTokenNumber;

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public int getPlaceMinTime() {
        return placeMinTime;
    }

    public void setPlaceMinTime(int placeMinTime) {
        this.placeMinTime = placeMinTime;
    }

    public int getPlaceMaxTime() {
        return placeMaxTime;
    }

    public void setPlaceMaxTime(int placeMaxTime) {
        this.placeMaxTime = placeMaxTime;
    }

    public int getPlaceTokenNumber() {
        return placeTokenNumber;
    }

    public void setPlaceTokenNumber(int placeTokenNumber) {
        this.placeTokenNumber = placeTokenNumber;
    }

    @Override
    public String toString() {
        return "Place{" +
                "placeName='" + placeName + '\'' +
                ", placeMinTime=" + placeMinTime +
                ", placeMaxTime=" + placeMaxTime +
                ", placeTokenNumber=" + placeTokenNumber +
                '}';
    }
}
