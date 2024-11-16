String eventDate;

        // Loop until a valid date is entered
        while (true) {
            System.out.print("Event Date (YYYY-MM-DD): ");
            eventDate = scan.nextLine();

            // Validate date format
            if (eventDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                System.out.println("Valid date: " + eventDate);
                break;
            } else {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }
}
