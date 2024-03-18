import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class StudentDatabase {
    private static Connection connection = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        StudentDatabase studentDatabase = new StudentDatabase();
        try {
            // Setting url, user and password
            String url = "jdbc:postgresql://localhost:port/database name";
            String user = "";
            String password = "";
            Class.forName("org.postgresql.Driver");

            // Connecting to database
            connection = DriverManager.getConnection(url, user, password);
            int choice = -1; // Initialize choice variable

            while (choice != 0) {
                studentDatabase.printMenu();
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scanner.nextLine());
                System.out.println();

                // Call control function based on user choice
                studentDatabase.control(choice);
            }

            // Close the scanner when the program exits
            scanner.close();

            // Catch any exceptions
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void printMenu() {
        // Print the menu options
        System.out.println("(0) Exit");
        System.out.println("(1) Get all students");
        System.out.println("(2) Add a student");
        System.out.println("(3) Update student email");
        System.out.println("(4) Delete a student");
    }

    private void control(int choice) {
        switch (choice) {
            case 0:
                System.out.println("Exiting...");
                break;
            case 1:
                try {
                    // Retrieve and display all students
                    getAllStudents();
                } catch (SQLException e) {
                    System.out.println(e);
                }
                System.out.println();
                break;
            case 2:
                // Code to add a new student
                System.out.print("Enter first name: ");
                String first_name = scanner.nextLine();
                System.out.print("Enter last name: ");
                String last_name = scanner.nextLine();
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter enrollment date (YYYY-MM-DD): ");
                String enrollment_date = scanner.nextLine();

                try {
                    addStudent(first_name, last_name, email, enrollment_date);
                } catch (SQLException e) {
                    System.out.println(e);
                }
                break;
            case 3:
                // Code to update student email
                System.out.print("Enter student id: ");
                int student_id = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new email: ");
                String new_email = scanner.nextLine();

                try {
                    updateStudentEmail(student_id, new_email);
                } catch (SQLException e) {
                    System.out.println(e);
                }
                break;
            case 4:
                // Code to delete a student
                System.out.print("Enter student id: ");
                int delete_student_id = scanner.nextInt();
                scanner.nextLine();

                try {
                    deleteStudent(delete_student_id);
                } catch (SQLException e) {
                    System.out.println(e);
                }
                break;
            default:
                System.out.println("Invalid choice. Try again.");
                break;
        }
    }

    // Method to get and return all students
    private void getAllStudents() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

        // Loops through table getting the data and outputting it
        while (resultSet.next()) {
            String first_name = resultSet.getString("first_name");
            int student_id = resultSet.getInt("student_id");
            String last_name = resultSet.getString("last_name");
            String email = resultSet.getString("email");
            String enrollment_date = resultSet.getString("enrollment_date");

            System.out.printf("%-5s | %-10s | %-10s | %-25s | %-15s%n", student_id, first_name, last_name, email, enrollment_date);
        }
    }

    // Method to add a student
    private void addStudent(String first_name, String last_name, String email, String enrollment_date) throws SQLException {
        try {
            // SQL query to insert a new student
            String sql = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, email);
            preparedStatement.setDate(4, java.sql.Date.valueOf(enrollment_date));

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student added successfully.");
            } else {
                System.out.println("Failed to add student.");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Method to update student email
    private void updateStudentEmail(int student_id, String new_email) throws SQLException {
        try {
            // SQL query to update student email
            String sql = "UPDATE students SET email = ? WHERE student_id = ?";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, new_email);
            preparedStatement.setInt(2, student_id);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Email updated successfully.");
            } else {
                System.out.println("Failed to update email.");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Method to delete a student
    private void deleteStudent(int delete_student_id) throws SQLException {
        try {
            // SQL query to delete a student
            String sql = "DELETE FROM students WHERE student_id = ?";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, delete_student_id);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Failed to delete student.");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
