package model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoomModel {
    private final String roomNumber;
    private final RoomType type;
    private double price;
    private double cancellationFee=50.0;
    private boolean isBooked ;

     private RoomModel(RoomBuilder roomBuilder) {
        this.roomNumber = roomBuilder.roomNumber;
        this.type = roomBuilder.type;
        this.price = roomBuilder.price;
        this.cancellationFee = roomBuilder.cancellationFee;
        this.isBooked=roomBuilder.isBooked;
    }

    public static final class RoomBuilder {
        private String roomNumber;
        private RoomType type;
        private double price;
        private double cancellationFee;
        private boolean isBooked ;

        public RoomBuilder setType(RoomType roomType) {
            this.type = roomType;
            return this;
        }

        public RoomBuilder setNumber(String roomNumber) {
            Pattern pattern = Pattern.compile("^[APD]{1}[0-9]{3}$");
            Matcher matcher = pattern.matcher(roomNumber);
            if (matcher.matches()) {
                this.roomNumber = roomNumber;
            } else {
                System.out.println("Invalid room number!");
            }
            return this;
        }

        public RoomBuilder setPrice(double price) {
            if (price < 0 || price > 100000) {
                System.out.println("Invalid price of HotelRoom");
            } else {
                this.price = price;
            }

            return this;
        }

        public RoomBuilder setCancellationFee(double cancellationFee){
            if (cancellationFee < 0 ) {
                System.out.println("Invalid price of HotelRoom");
            } else {
                this.cancellationFee = cancellationFee;
            }
            return this;
        }
        public RoomBuilder setBooking(boolean isBooking){
            this.isBooked=isBooking;
            return this;
        }

        public RoomModel build(){
            return new RoomModel(this);
        }
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public double getCancellationFee() {
        return cancellationFee;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookRoom() {
        this.isBooked=true;
    }

    public void cancelBooking() {
        this.isBooked = false;
    }
    @Override
    public String toString() {
        return roomNumber + " - " + type + " - $" + price + " (Available: " + !isBooked + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RoomModel room = (RoomModel) obj;
        return roomNumber.equalsIgnoreCase(room.roomNumber);  // Compare rooms based on room number
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber.toLowerCase());  // Ensure consistent hashing based on room number
    }

    public String getData(){
         return roomNumber + "," + type + "," + price + "," + isBooked ;
    }
}
