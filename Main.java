import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

class BookData {
    private ArrayList<HashMap<String, Object>> allBooks;
    private static final String CSV_FILE_PATH = "books.csv";

    public BookData() {
        this.allBooks = new ArrayList<>();
        createCSVFileIfNotExists();
        loadBooksFromCSV();
    }

    public ArrayList<HashMap<String, Object>> getAllBooks() {
        return allBooks;
    }

    private void createCSVFileIfNotExists() {
        File file = new File(CSV_FILE_PATH);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("id,Title,Author,Year,Price,Quantity,DateAdded\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadBooksFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != 7) { // Ensure the correct number of fields
                    System.out.println("Skipping invalid row: " + line);
                    continue;
                }
                try {
                    HashMap<String, Object> book = new HashMap<>();
                    book.put("id", Integer.parseInt(fields[0].trim())); // Parse ID
                    book.put("title", fields[1].trim());                // Title
                    book.put("author", fields[2].trim());               // Author
                    book.put("year", Integer.parseInt(fields[3].trim())); // Year
                    book.put("price", Integer.parseInt(fields[4].trim())); // Price
                    book.put("quantity", Integer.parseInt(fields[5].trim())); // Quantity
                    book.put("dateAdded", fields[6].trim());            // Date Added
                    allBooks.add(book);
                } catch (NumberFormatException e) {
                    System.out.println("Skipping row with invalid numeric data: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading the books from CSV.");
            e.printStackTrace();
        }
    }


    public void addBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine();
        int year = 0;
        while (true) {
            try {
                System.out.print("Enter Publishing Year: ");
                year = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid year.");
            }
        }
        int price = 0;
        while (true) {
            try {
                System.out.print("Enter Book Price: ");
                price = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid price.");
            }
        }
        int quantity = 0;
        while (true) {
            try {
                System.out.print("Enter Quantity: ");
                quantity = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid quantity.");
            }
        }
        int id = new Random().nextInt(1000);
        String dateAdded = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        HashMap<String, Object> book = new HashMap<>();
        book.put("id", id);
        book.put("title", title);
        book.put("author", author);
        book.put("year", year);
        book.put("price", price);
        book.put("quantity", quantity);
        book.put("dateAdded", dateAdded);

        allBooks.add(book);
        saveBooksToCSV();
        System.out.println("Book added successfully.");
    }

    public void removeBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Book ID to Remove: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        boolean found = false;

        for (HashMap<String, Object> book : allBooks) {
            if ((int) book.get("id") == id) {
                allBooks.remove(book);
                System.out.println("Book removed successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Book not found.");
        } else {
            saveBooksToCSV();
        }
    }

    public void updateBookInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Book ID to Update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        boolean found = false;

        for (HashMap<String, Object> book : allBooks) {
            if ((int) book.get("id") == id) {
                System.out.print("Enter New Book Title: ");
                String title = scanner.nextLine();
                System.out.print("Enter New Author Name: ");
                String author = scanner.nextLine();
                int year = 0;
                while (true) {
                    try {
                        System.out.print("Enter New Publishing Year: ");
                        year = Integer.parseInt(scanner.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid year.");
                    }
                }
                int price = 0;
                while (true) {
                    try {
                        System.out.print("Enter New Book Price: ");
                        price = Integer.parseInt(scanner.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid price.");
                    }
                }
                int quantity = 0;
                while (true) {
                    try {
                        System.out.print("Enter New Quantity: ");
                        quantity = Integer.parseInt(scanner.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid quantity.");
                    }
                }

                book.put("title", title);
                book.put("author", author);
                book.put("year", year);
                book.put("price", price);
                book.put("quantity", quantity);
                System.out.println("Book information updated successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Book not found.");
        } else {
            saveBooksToCSV();
        }
    }

    public void searchBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Book Title to Search: ");
        String title = scanner.nextLine();
        boolean found = false;

        for (HashMap<String, Object> book : allBooks) {
            if (book.get("title").toString().equalsIgnoreCase(title)) {
                System.out.println("ID: " + book.get("id"));
                System.out.println("Title: " + book.get("title"));
                System.out.println("Author: " + book.get("author"));
                System.out.println("Year: " + book.get("year"));
                System.out.println("Price: " + book.get("price"));
                System.out.println("Quantity: " + book.get("quantity"));
                System.out.println("Date Added: " + book.get("dateAdded"));
                System.out.println();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Book not found.");
        }
    }

    public void viewBooks() {
        System.out.println("Books Available:");
        for (HashMap<String, Object> book : allBooks) {
            System.out.println("ID: " + book.get("id"));
            System.out.println("Title: " + book.get("title"));
            System.out.println("Author: " + book.get("author"));
            System.out.println("Year: " + book.get("year"));
            System.out.println("Price: " + book.get("price"));
            System.out.println("Quantity: " + book.get("quantity"));
            System.out.println("Date Added: " + book.get("dateAdded"));
            System.out.println();
        }
    }

    public void saveBooksToCSV() {
        try (FileWriter writer = new FileWriter(CSV_FILE_PATH)) {
            for (HashMap<String, Object> book : allBooks) {
                writer.append(book.get("id").toString())
                        .append(',')
                        .append(book.get("title").toString())
                        .append(',')
                        .append(book.get("author").toString())
                        .append(',')
                        .append(book.get("year").toString())
                        .append(',')
                        .append(book.get("price").toString())
                        .append(',')
                        .append(book.get("quantity").toString())
                        .append(',')
                        .append(book.get("dateAdded").toString())
                        .append('\n');
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the books to CSV.");
            e.printStackTrace();
        }
    }
}

class AdminPortal {
    private BookData bookData;

    public AdminPortal(BookData bookData) {
        this.bookData = bookData;
    }

    public void showAdminMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Admin Portal:");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Remove Book");
            System.out.println("4. Update Book Info");
            System.out.println("5. Search Book");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    bookData.addBook();
                    break;
                case 2:
                    bookData.viewBooks();
                    break;
                case 3:
                    bookData.removeBook();
                    break;
                case 4:
                    bookData.updateBookInfo();
                    break;
                case 5:
                    bookData.searchBook();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

class StudentPortal {
    private BookData bookData;
    private ArrayList<HashMap<String, Object>> lentBooks;
    public StudentPortal(BookData bookData) {
        this.bookData = bookData;
        this.lentBooks = new ArrayList<>();
    }
    public void showStudentMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Student Portal:");
            System.out.println("1. View All Books");
            System.out.println("2. Search Book");
            System.out.println("3. Lend Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    bookData.viewBooks();
                    break;
                case 2:
                    bookData.searchBook();
                    break;
                case 3:
                    lendBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void lendBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Book ID to Lend: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        boolean found = false;

        for (HashMap<String, Object> book : bookData.getAllBooks()) {
            if ((int) book.get("id") == id) {
                if ((int) book.get("quantity") > 0) {
                    book.put("quantity", (int) book.get("quantity") - 1);
                    lentBooks.add(book);
                    System.out.println("Book lent successfully.");
                } else {
                    System.out.println("Book is out of stock.");
                }
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Book not found.");
        } else {
            bookData.saveBooksToCSV();
        }
    }

    private void returnBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Book ID to Return: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        boolean found = false;

        for (HashMap<String, Object> book : lentBooks) {
            if ((int) book.get("id") == id) {
                book.put("quantity", (int) book.get("quantity") + 1);
                lentBooks.remove(book);
                System.out.println("Book returned successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Book not found in lent books.");
        } else {
            bookData.saveBooksToCSV();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BookData bookData = new BookData();
        AdminPortal adminPortal = new AdminPortal(bookData);
        StudentPortal studentPortal = new StudentPortal(bookData);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Admin Portal");
            System.out.println("2. Student Portal");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    adminPortal.showAdminMenu();
                    break;
                case 2:
                    studentPortal.showStudentMenu();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}