package model;

public enum RoomType {
    DELUXE("WiFi,TV,Mini Bar",4),
    SUITE("WiFi,TV,Mini Bar, Jacuzzi",4),
    SINGLE("WiFi,TV",1),
    DOUBLE("WiFi,TV",2);

    private final String amenities;
    private final int MaxOccupancy;

    RoomType(String amenities, int maxOccupancy) {
        this.amenities = amenities;
        MaxOccupancy = maxOccupancy;
    }

    public String getAmenities() {
        return amenities;
    }

    public int getMaxOccupancy() {
        return MaxOccupancy;
    }
}
