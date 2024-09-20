import java.time.LocalDate;
import java.util.*;

class LibraryItem {
    private int id;
    private String title;
    private String publisher;
    private LocalDate publishDate;

    public LibraryItem(int id, String title, String publisher, LocalDate publishDate) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.publishDate = publishDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Title: " + title + ", Publisher: " + publisher + ", Published: " + publishDate;
    }
}

class Book extends LibraryItem {
    private String author;

    public Book(int id, String title, String publisher, LocalDate publishDate, String author) {
        super(id, title, publisher, publishDate);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return super.toString() + ", Author: " + author;
    }
}

class Journal extends LibraryItem {
    private String volume;

    public Journal(int id, String title, String publisher, LocalDate publishDate, String volume) {
        super(id, title, publisher, publishDate);
        this.volume = volume;
    }

    @Override
    public String toString() {
        return super.toString() + ", Volume: " + volume;
    }
}

class Magazine extends LibraryItem {
    private int issueNumber;

    public Magazine(int id, String title, String publisher, LocalDate publishDate, int issueNumber) {
        super(id, title, publisher, publishDate);
        this.issueNumber = issueNumber;
    }

    @Override
    public String toString() {
        return super.toString() + ", Issue: " + issueNumber;
    }
}

class BorrowTransaction {
    private LibraryItem item;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean returned;

    public BorrowTransaction(LibraryItem item, LocalDate borrowDate, LocalDate dueDate) {
        this.item = item;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returned = false;
    }

    public void checkIfOverdue() throws OverdueItemException {
        if (LocalDate.now().isAfter(dueDate) && !returned) {
            throw new OverdueItemException("The item is overdue!");
        }
    }

    public void returnItem() {
        returned = true;
        System.out.println("Item returned: " + item.getTitle());
    }
}

class OverdueItemException extends Exception {
    public OverdueItemException(String message) {
        super(message);
    }
}

public class Main {
    private static List<LibraryItem> catalog = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Catalog System:");
            System.out.println("1. Add a Book");
            System.out.println("2. Add a Journal");
            System.out.println("3. Add a Magazine");
            System.out.println("4. Search Item by ID");
            System.out.println("5. Remove an Item");
            System.out.println("6. Borrow an Item");
            System.out.println("7. Exit");
            System.out.println("8. List All Books");  // New feature

            System.out.print("Choose an option: ");
            int choice = getIntInput(scanner);

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    addJournal(scanner);
                    break;
                case 3:
                    addMagazine(scanner);
                    break;
                case 4:
                    searchItem(scanner);
                    break;
                case 5:
                    removeItem(scanner);
                    break;
                case 6:
                    borrowItem(scanner);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                case 8:  // New feature for listing all books
                    listAllBooks();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addBook(Scanner scanner) {
        System.out.print("Enter Book ID: ");
        int id = getIntInput(scanner);
        System.out.print("Enter Book Title: ");
        String title = scanner.next();
        System.out.print("Enter Publisher: ");
        String publisher = scanner.next();
        System.out.print("Enter Author: ");
        String author = scanner.next();
        System.out.print("Enter Publish Year, Month, Day: ");
        LocalDate publishDate = getDateInput(scanner);

        Book book = new Book(id, title, publisher, publishDate, author);
        catalog.add(book);
        System.out.println("Book added: " + book);
    }

    private static void addJournal(Scanner scanner) {
        System.out.print("Enter Journal ID: ");
        int id = getIntInput(scanner);
        System.out.print("Enter Journal Title: ");
        String title = scanner.next();
        System.out.print("Enter Publisher: ");
        String publisher = scanner.next();
        System.out.print("Enter Volume: ");
        String volume = scanner.next();
        System.out.print("Enter Publish Year, Month, Day: ");
        LocalDate publishDate = getDateInput(scanner);

        Journal journal = new Journal(id, title, publisher, publishDate, volume);
        catalog.add(journal);
        System.out.println("Journal added: " + journal);
    }

    private static void addMagazine(Scanner scanner) {
        System.out.print("Enter Magazine ID: ");
        int id = getIntInput(scanner);
        System.out.print("Enter Magazine Title: ");
        String title = scanner.next();
        System.out.print("Enter Publisher: ");
        String publisher = scanner.next();
        System.out.print("Enter Issue Number: ");
        int issueNumber = getIntInput(scanner);
        System.out.print("Enter Publish Year, Month, Day: ");
        LocalDate publishDate = getDateInput(scanner);

        Magazine magazine = new Magazine(id, title, publisher, publishDate, issueNumber);
        catalog.add(magazine);
        System.out.println("Magazine added: " + magazine);
    }

    private static void searchItem(Scanner scanner) {
        System.out.print("Enter Item ID to search: ");
        int id = getIntInput(scanner);
        LibraryItem foundItem = catalog.stream().filter(item -> item.getId() == id).findFirst().orElse(null);

        if (foundItem != null) {
            System.out.println("Item found: " + foundItem);
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void removeItem(Scanner scanner) {
        System.out.print("Enter Item ID to remove: ");
        int id = getIntInput(scanner);
        LibraryItem itemToRemove = catalog.stream().filter(item -> item.getId() == id).findFirst().orElse(null);

        if (itemToRemove != null) {
            catalog.remove(itemToRemove);
            System.out.println("Item removed: " + itemToRemove);
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void borrowItem(Scanner scanner) {
        System.out.print("Enter Item ID to borrow: ");
        int id = getIntInput(scanner);
        LibraryItem itemToBorrow = catalog.stream().filter(item -> item.getId() == id).findFirst().orElse(null);

        if (itemToBorrow != null) {
            BorrowTransaction transaction = new BorrowTransaction(itemToBorrow, LocalDate.now(), LocalDate.now().plusDays(7));
            System.out.println("Borrowing: " + itemToBorrow);

            try {
                transaction.checkIfOverdue();
            } catch (OverdueItemException e) {
                System.out.println(e.getMessage());
            }
            transaction.returnItem();
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void listAllBooks() {
        System.out.println("Listing all books in the catalog:");
        catalog.stream()
               .filter(item -> item instanceof Book)
               .forEach(System.out::println);
    }

    private static int getIntInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a valid number: ");
                scanner.next();  // Consume invalid input
            }
        }
    }

    private static LocalDate getDateInput(Scanner scanner) {
        while (true) {
            try {
                return LocalDate.of(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter Year, Month, Day: ");
                scanner.next();  // Consume invalid input
            }
        }
    }
}
