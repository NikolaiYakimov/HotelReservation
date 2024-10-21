package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoomModel {
    private final String roomNumber;
    private final RoomType type;
    private double price;
    private double cancellationFee=50.0;
    private boolean isBooked ;

    RoomModel(RoomBuilder roomBuilder) {
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
        private boolean isBooked = false;

        public RoomBuilder setType(RoomType roomType) {
            this.type = roomType;
            return this;
        }

        public RoomBuilder setNumber(String roomNumber) {
            Pattern pattern = Pattern.compile("^[APD]{1}[1-8]{3}$");
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
}