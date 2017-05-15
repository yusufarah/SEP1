public class Reservation {
    private Guest guest;
    private Arrival arrival;
    private Departure departure;
    private String roomType;
    private boolean bookingInitiator;
    private boolean lateArrivalNotice;
    private boolean priorityGuest;
    private int roomNumber;

    public Reservation(Guest guest, Arrival arrival, Departure departure, String roomType,
                       boolean bookingInitiator, boolean lateArrivalNotice, boolean priorityGuest) {
        this.guest = guest;
        this.arrival = arrival;
        this.departure = departure;
        this.roomType = roomType;
        this.bookingInitiator = bookingInitiator;
        this.lateArrivalNotice = lateArrivalNotice;
        this.priorityGuest = priorityGuest;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Arrival getArrival() {
        return arrival;
    }

    public void setArrival(Arrival arrival) {
        this.arrival = arrival;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public boolean isBookingInitiator() {
        return bookingInitiator;
    }

    public void setBookingInitiator(boolean bookingInitiator) {
        this.bookingInitiator = bookingInitiator;
    }

    public boolean isLateArrivalNotice() {
        return lateArrivalNotice;
    }

    public void setLateArrivalNotice(boolean lateArrivalNotice) {
        this.lateArrivalNotice = lateArrivalNotice;
    }

    public boolean isPriorityGuest() {
        return priorityGuest;
    }

    public void setPriorityGuest(boolean priorityGuest) {
        this.priorityGuest = priorityGuest;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Reservation copy(){
        return new Reservation(guest, arrival, departure, roomType,
        bookingInitiator, lateArrivalNotice, priorityGuest);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Reservation reservation = (Reservation) obj;

        if (bookingInitiator != reservation.bookingInitiator) return false;
        if (lateArrivalNotice != reservation.lateArrivalNotice) return false;
        if (priorityGuest != reservation.priorityGuest) return false;
        if (guest != null ? !guest.equals(reservation.guest) : reservation.guest != null) return false;
        if (arrival != null ? !arrival.equals(reservation.arrival) : reservation.arrival != null) return false;
        if (departure != null ? !departure.equals(reservation.departure) : reservation.departure != null) return false;
        return roomType != null ? roomType.equals(reservation.roomType) : reservation.roomType == null;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "guest=" + guest +
                ", arrival=" + arrival +
                ", departure=" + departure +
                ", roomType='" + roomType + '\'' +
                ", bookingInitiator=" + bookingInitiator +
                ", lateArrivalNotice=" + lateArrivalNotice +
                ", priorityGuest=" + priorityGuest +
                '}';
    }
}