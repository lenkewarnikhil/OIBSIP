package mypack;

import java.util.*;
import java.sql.*;
public class OnlineReservation {

    public static class PnrRecord {
        private int pnrNumber;
        private String passengerName;
        private String trainNumber;
        private String classType;
        private String journeyDate;
        private String from;
        private String to;

        Scanner sc = new Scanner(System.in);

        public int getpnrNumber() {
            Random random = new Random();
            pnrNumber = random.nextInt(9999) + 1000;
            return pnrNumber;
        }

        public String getPassengerName() {
            System.out.println("Enter the passenger name : ");
            passengerName = sc.nextLine();
            return passengerName;
        }

        public String gettrainNumber() {
            Random random = new Random();
            pnrNumber = random.nextInt(999999) + 100000;
        	
            return trainNumber;
        }

        public String getclassType() {
            System.out.println("Enter the class type : ");
            classType = sc.nextLine();
            return classType;
        }

        public String getjourneyDate() {
            System.out.println("Enter the Journey date as 'YYYY-MM-DD' format");
            journeyDate = sc.nextLine();
            return journeyDate;
        }

        public String start() {
            System.out.println("Enter the starting place : ");
            from = sc.nextLine();
            return from;
        }

        public String destination() {
            System.out.println("Enter the destination place :  ");
            to = sc.nextLine();
            return to;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your name:");
        String name=sc.nextLine();
        System.out.println("Enter userID:");
        String username = sc.nextLine();
        System.out.println("Enter your Password:");
        String password = sc.nextLine();

        String url = "jdbc:mysql://localhost:3306/nikhil";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                System.out.println("Welcome Back! "+name);
                while (true) {
                    String InsertDetails = "insert into reservations values (?, ?, ?, ?, ?, ?, ?)";
                    String DeleteDetails = "DELETE FROM reservations WHERE pnr_number = ?";
                    String ShowDetails = "Select * from reservations";
                    String StatusDetails= "SELECT * FROM reservations WHERE pnr_number = ?";

                    System.out.println("Menu: ");
                    System.out.print("1. Book A Ticket -- ");
                    System.out.print("2. Cancel A Ticket -- ");
                    System.out.print("3. Show All Tickets -- ");
                    System.out.print("4. Check Ticket Status -- ");
                    System.out.print("5. Log out!\n");
                    System.out.println("Please choose your option:");
                    int choice = sc.nextInt();
                    
                    
                    if (choice == 1) {

                        PnrRecord p1 = new PnrRecord();
                        int pnr_number = p1.getpnrNumber();
                        String passengerName = p1.getPassengerName();
                        String trainNumber = p1.gettrainNumber();
                        String classType = p1.getclassType();
                        String dateOfJ = p1.getjourneyDate();
                        String fromCity = p1.start();
                        String toCity = p1.destination();

                        try (PreparedStatement preparedStatement = connection.prepareStatement(InsertDetails)) {
                            preparedStatement.setInt(1, pnr_number);
                            preparedStatement.setString(2, passengerName);
                            preparedStatement.setString(3, trainNumber);
                            preparedStatement.setString(4, classType);
                            preparedStatement.setString(5, dateOfJ);
                            preparedStatement.setString(6, fromCity);
                            preparedStatement.setString(7, toCity);

                            int rowsAffected = preparedStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Your ticket is confirmed by the pnr-number : "+pnr_number);
                                System.out.println("Thank you for 'Booking'!");
                            }

                            else {
                                System.out.println("Sorry! Try again.");
                            }
                        }

                        catch (SQLException e) {
                            System.err.println("SQLException: " + e.getMessage());
                        }

                    }

                    else if (choice == 2) {
                        System.out.println("Enter the PNR number to delete the record : ");
                        int pnrNumber = sc.nextInt();
                        try (PreparedStatement preparedStatement = connection.prepareStatement(DeleteDetails)) {
                            preparedStatement.setInt(1, pnrNumber);
                            int rowsAffected = preparedStatement.executeUpdate();

                            if (rowsAffected > 0) {
                                System.out.println("Your ticket has been canceled.");
                            }

                            else {
                                System.out.println("No records were deleted.");
                            }
                        }

                        catch (SQLException e) {
                            System.err.println("SQLException: " + e.getMessage());
                        }
                    }

                    else if (choice == 3) {
                        try (PreparedStatement preparedStatement = connection.prepareStatement(ShowDetails);
                                ResultSet resultSet = preparedStatement.executeQuery()) {
                            System.out.println("\nAll records printing.\n");
                            while (resultSet.next()) {
                                String pnrNumber = resultSet.getString("pnr_number");
                                String passengerName = resultSet.getString("passenger_name");
                                String trainNumber = resultSet.getString("train_number");
                                String classType = resultSet.getString("class_type");
                                String journeyDate = resultSet.getString("journey_datee");
                                String fromLocation = resultSet.getString("from_location");
                                String toLocation = resultSet.getString("to_location");
                                
                                System.out.print("Journey Date: " + journeyDate);
                                System.out.print("                                           PNR Number: " + pnrNumber+"\n");
                                System.out.println("--------------- Train Number: " + trainNumber+" ---------------");
                                System.out.println("Passenger Name: " + passengerName);
                                System.out.println("Class Type: " + classType);
//                                System.out.println("Journey Date: " + journeyDate);
                                System.out.print("From :" + fromLocation);
                                System.out.print(" --------> To : " + toLocation+"\n");
                                System.out.println("*#-----------*#*-----------#*");
                            }
                        } catch (SQLException e) {
                            System.err.println("SQLException: " + e.getMessage());
                        }
                    }
                    
                    else if(choice == 4) {
                    	System.out.println("Enter the PNR number to check the status : ");
                        int pnrNumber = sc.nextInt();
                        try (PreparedStatement preparedStatement = connection.prepareStatement(StatusDetails)) {
                    		preparedStatement.setInt(1, pnrNumber);
                    		ResultSet resultSet = preparedStatement.executeQuery();
                            System.out.println("**Here is your Ticket Status**");
                            while (resultSet.next()) {
                                String pnr_Number = resultSet.getString("pnr_number");
                                String passengerName = resultSet.getString("passenger_name");
                                String trainNumber = resultSet.getString("train_number");
                                String classType = resultSet.getString("class_type");
                                String journeyDate = resultSet.getString("journey_datee");
                                String fromLocation = resultSet.getString("from_location");
                                String toLocation = resultSet.getString("to_location");

                                System.out.print("Journey Date: " + journeyDate);
                                System.out.print("                                           PNR Number: " + pnr_Number+"\n");
                                System.out.println("--------------- Train Number: " + trainNumber+" ---------------");
                                System.out.println("Passenger Name: " + passengerName);
                                System.out.println("Class Type: " + classType);
//                                System.out.println("Journey Date: " + journeyDate);
                                System.out.print("From :" + fromLocation);
                                System.out.print(" --------> To : " + toLocation+"\n");
                                System.out.println("*#-----------*#*-----------#*");
                            }
                        } catch (SQLException e) {
                            System.err.println("SQLException: " + e.getMessage());
                        }
                    }

                    else if (choice == 5) {
                    	System.out.println("Logged Out.");
                        System.out.println("-----------------Thank You!------------------");
                        break;
                    }

                    else {
                        System.out.println("Choose a valid option.\n");
                    }
                }

            }

            catch (SQLException e) {
            	System.out.println("Sorry Looks like U don't have account!\nPlease create one and come back \n--Thank You--");
                System.err.println("SQLException: " + e.getMessage());
            }
        }

        catch (ClassNotFoundException e) {
            System.err.println("Error loading JDBC driver: " + e.getMessage());
        }

        sc.close();
    }
}
