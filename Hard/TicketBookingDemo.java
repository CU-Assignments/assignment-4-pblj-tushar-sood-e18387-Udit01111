import java.util.concurrent.locks.*;

class TicketBookingSystem {
    private int availableSeats;
    private final Lock lock = new ReentrantLock();

    public TicketBookingSystem(int totalSeats) {
        this.availableSeats = totalSeats;
    }

    public void bookSeat(String customerType, int seats) {
        lock.lock();
        try {
            if (seats <= availableSeats) {
                System.out.println(customerType + " successfully booked " + seats + " seat(s).");
                availableSeats -= seats;
            } else {
                System.out.println(customerType + " booking failed. Not enough seats available.");
            }
        } finally {
            lock.unlock();
        }
    }
}

class BookingThread extends Thread {
    private TicketBookingSystem bookingSystem;
    private String customerType;
    private int seats;

    public BookingThread(TicketBookingSystem bookingSystem, String customerType, int seats, int priority) {
        this.bookingSystem = bookingSystem;
        this.customerType = customerType;
        this.seats = seats;
        this.setPriority(priority);
    }

    @Override
    public void run() {
        bookingSystem.bookSeat(customerType, seats);
    }
}

public class TicketBookingDemo {
    public static void main(String[] args) {
        TicketBookingSystem bookingSystem = new TicketBookingSystem(10);

        BookingThread vip1 = new BookingThread(bookingSystem, "VIP Customer 1", 2, Thread.MAX_PRIORITY);
        BookingThread vip2 = new BookingThread(bookingSystem, "VIP Customer 2", 3, Thread.MAX_PRIORITY);
        BookingThread regular1 = new BookingThread(bookingSystem, "Regular Customer 1", 4, Thread.NORM_PRIORITY);
        BookingThread regular2 = new BookingThread(bookingSystem, "Regular Customer 2", 2, Thread.NORM_PRIORITY);
        BookingThread regular3 = new BookingThread(bookingSystem, "Regular Customer 3", 1, Thread.NORM_PRIORITY);

        vip1.start();
        vip2.start();
        regular1.start();
        regular2.start();
        regular3.start();
    }
}
