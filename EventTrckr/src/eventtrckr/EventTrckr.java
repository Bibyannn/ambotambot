package eventtrckr;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EventTrckr {
    static Config conf = new Config();
    
   static Scanner scan = new Scanner(System.in);
static Customer customer = new Customer();
static Venues venues = new Venues();
static Events events = new Events();

public static void main(String[] args) { 
    Config.connectDB();
    int choice;

    do {
        try {
            System.out.println("\n╔═════════════════════════╗");
            System.out.println("║          Event Tracker System          ║");
            System.out.println("╚═════════════════════════╝\n");
            System.out.println("1. Customers");
            System.out.println("2. Venues");
            System.out.println("3. Events");
            System.out.println("4. Generate Reports");
            System.out.println("5. Exit");
            
            System.out.print("\nEnter Option: ");
            choice = scan.nextInt();
            scan.nextLine();
            System.out.println("");

            switch (choice) {
                case 1:  
                    customer.customerConfig();
                    break;
                case 2:
                    venues.VenuesConfig();
                    break;
                case 3:
                    events.eventsConfig();
                    break;
                case 4:
                    generateReports();
                    break;
                case 5:                      
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid Option.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scan.nextLine();
            choice = -1;
        }
    } while (choice != 5);
}

static void generateReports() {
    System.out.println("\n╔══════════════════════════════╗");
    System.out.println("║            CUSTOMER BOOKING REPORT              ║");
    System.out.println("╚══════════════════════════════╝");
    customer.viewCustomers();

    int custId;
    do {
        System.out.print("\nEnter Customer ID for the report: ");
        custId = scan.nextInt();
        if (!conf.doesIDExist("customer", custId)) {
            System.out.println("Customer ID not found. Please try again.");
        }
    } while (!conf.doesIDExist("customer", custId));

    System.out.println("\n╔════════════════════╗");
    System.out.println("║         CUSTOMER DETAILS         ║");
    System.out.println("╚═════════════════════╝");
    System.out.printf("Customer ID  : %-15d%n", custId);
    System.out.printf("Name         : %-15s%n", conf.getDataFromID("customer", custId, "name"));
    System.out.printf("Email        : %-15s%n", conf.getDataFromID("customer", custId, "email"));
    System.out.println("────────────────────────────────────");

    if (conf.isTableEmpty("booking WHERE customer_id = " + custId)) {
        System.out.println("No booking records found for this customer.");
    } else {
        System.out.println("Booking Details:");
        System.out.printf(" %-10s │ %-20s │ %-15s │ %-10s%n", "Booking ID", "Destination", "Booking Date", "Status");
        System.out.println("───────────────────────────────────────────────────────────────────────");

        String sql = "SELECT booking.id, trip.destination, booking.booking_date, booking.status " +
                     "FROM booking " +
                     "JOIN trip ON booking.trip_id = trip.id " +
                     "WHERE booking.customer_id = " + custId;

        String[] headers = {"Booking ID", "Destination", "Booking Date", "Status"};
        String[] columns = {"id", "destination", "booking_date", "status"};

        conf.viewRecords(sql, headers, columns);
        System.out.println("───────────────────────────────────────────────────────────────────────");
    }
    System.out.println("╔═════════════════════╗");
    System.out.println("║          END OF DETAILS          ║");
    System.out.println("╚═════════════════════╝");
}
}