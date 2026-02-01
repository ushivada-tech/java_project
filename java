import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.Ɵme.LocalDate;
import java.uƟl.ArrayList;
// ===== Interface =====
interface RentalOperaƟons {
 void addVehicle(Vehicle v);
 void showAvailableVehicles(DefaultListModel<String> model);
 void rentVehicle(Customer c, int vehicleId, int days, JTextArea output);
 void returnVehicle(int vehicleId, JTextArea output);
}
// ===== Vehicle Class =====
class Vehicle {
 int vehicleId;
 String type;
 String brand;
 double ratePerDay;
 boolean available;
 Vehicle(int id, String type, String brand, double ratePerDay) {
 this.vehicleId = id;
 this.type = type;
 this.brand = brand;
 this.ratePerDay = ratePerDay;
 this.available = true;
 }
 public String toString() {
 return vehicleId + " | " + type + " | " + brand + " | Rate: " + ratePerDay + " | Available: "
+ available;
 }
}
// ===== Customer Class =====
class Customer {
 int customerId;
 String name;
 Customer(int id, String name) {
 this.customerId = id;
 this.name = name;
 }
 public String toString() {
 return customerId + " | " + name;
 }
}
// ===== Rental Class =====
class Rental {
 Customer customer;
 Vehicle vehicle;
 LocalDate startDate;
 LocalDate endDate;
 double totalCost;
 Rental(Customer c, Vehicle v, LocalDate start, LocalDate end, double totalCost) {
 this.customer = c;
 this.vehicle = v;
 this.startDate = start;
 this.endDate = end;
 this.totalCost = totalCost;
 }
 public String toString() {
 return "Rental -> Customer: " + customer.name + ", Vehicle: " + vehicle.brand +
 ", From: " + startDate + ", To: " + endDate + ", Total: $" + totalCost;
 }
}
// ===== Rental Manager Class =====
class RentalManager implements RentalOperaƟons {
 ArrayList<Vehicle> vehicles = new ArrayList<>();
 ArrayList<Rental> rentals = new ArrayList<>();
 public void addVehicle(Vehicle v) {
 vehicles.add(v);
 }
 public void showAvailableVehicles(DefaultListModel<String> model) {
 model.clear();
 for (Vehicle v : vehicles) {
 if (v.available) model.addElement(v.toString());
 }
 }
 public void rentVehicle(Customer c, int vehicleId, int days, JTextArea output) {
 for (Vehicle v : vehicles) {
 if (v.vehicleId == vehicleId && v.available) {
 v.available = false;
 double totalCost = v.ratePerDay * days;
 Rental r = new Rental(c, v, LocalDate.now(), LocalDate.now().plusDays(days),
totalCost);
 rentals.add(r);
 output.setText("膆 Vehicle rented successfully!\n\n" + r);
 return;
 }
 }
 output.setText(" Vehicle not available!");
 }
 public void returnVehicle(int vehicleId, JTextArea output) {
 for (Vehicle v : vehicles) {
 if (v.vehicleId == vehicleId && !v.available) {
 v.available = true;
 output.setText("膆 Vehicle returned successfully!");
 return;
 }
 }
 output.setText(" Invalid Vehicle ID or already available!");
 }
}
// ===== Main GUI Program =====
public class VehicleRentalSystemSwing extends JFrame {
 RentalManager manager = new RentalManager();
 Customer customer;
 JTextField nameField, vehicleIdField, daysField, returnIdField;
 DefaultListModel<String> listModel = new DefaultListModel<>();
 JTextArea outputArea;
 public VehicleRentalSystemSwing() {
 // ==== Window Setup ====
 setTitle("렘렙렚렛렜렞렟렝 Vehicle Rental System");
 setDefaultCloseOperaƟon(JFrame.EXIT_ON_CLOSE);
 setSize(750, 500);
 setLocaƟonRelaƟveTo(null);
 setLayout(new BorderLayout(10, 10));
 // ==== Top Panel (User RegistraƟon) ====
 JPanel topPanel = new JPanel();
 topPanel.add(new JLabel("Enter Your Name:"));
 nameField = new JTextField(15);
 topPanel.add(nameField);
 JBuƩon registerBtn = new JBuƩon("Register");
 topPanel.add(registerBtn);
 add(topPanel, BorderLayout.NORTH);
 // ==== Center Panel (Vehicle List + Output) ====
 JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
 JList<String> vehicleList = new JList<>(listModel);
 vehicleList.setBorder(BorderFactory.createTitledBorder("Available Vehicles"));
 JScrollPane listScroll = new JScrollPane(vehicleList);
 centerPanel.add(listScroll);
 outputArea = new JTextArea();
 outputArea.setEditable(false);
 outputArea.setBorder(BorderFactory.createTitledBorder("System Messages"));
 centerPanel.add(new JScrollPane(outputArea));
 add(centerPanel, BorderLayout.CENTER);
 // ==== BoƩom Panel (Rent + Return SecƟons) ====
 JPanel boƩomPanel = new JPanel(new GridLayout(2, 1));
 // Rent SecƟon
 JPanel rentPanel = new JPanel();
 rentPanel.add(new JLabel("Vehicle ID:"));
 vehicleIdField = new JTextField(5);
 rentPanel.add(vehicleIdField);
 rentPanel.add(new JLabel("Days:"));
 daysField = new JTextField(5);
 rentPanel.add(daysField);
 JBuƩon rentBtn = new JBuƩon("Rent Vehicle");
 rentPanel.add(rentBtn);
 // Return SecƟon
 JPanel returnPanel = new JPanel();
 returnPanel.add(new JLabel("Return Vehicle ID:"));
 returnIdField = new JTextField(5);
 returnPanel.add(returnIdField);
 JBuƩon returnBtn = new JBuƩon("Return Vehicle");
 returnPanel.add(returnBtn);
 boƩomPanel.add(rentPanel);
 boƩomPanel.add(returnPanel);
 add(boƩomPanel, BorderLayout.SOUTH);
 // ==== Add Vehicles ====
 manager.addVehicle(new Vehicle(1, "Car", "Toyota", 50));
 manager.addVehicle(new Vehicle(2, "Bike", "Honda", 20));
 manager.addVehicle(new Vehicle(3, "Van", "Nissan", 80));
 manager.addVehicle(new Vehicle(4, "Car", "Hyundai", 45));
 // ==== BuƩon AcƟons ====
 registerBtn.addAcƟonListener(e -> {
 String name = nameField.getText().trim();
 if (name.isEmpty()) {
 JOpƟonPane.showMessageDialog(this, "Please enter your name.");
 return;
 }
 customer = new Customer(1, name);
 manager.showAvailableVehicles(listModel);
 outputArea.setText("蹉蹊蹋蹌蹍蹎蹏蹐蹑 Welcome, " + name + "!\nYou can now rent or return
vehicles below.");
 });
 rentBtn.addAcƟonListener(e -> {
 if (customer == null) {
 JOpƟonPane.showMessageDialog(this, "Register your name first!");
 return;
 }
 try {
 int vid = Integer.parseInt(vehicleIdField.getText());
 int days = Integer.parseInt(daysField.getText());
 manager.rentVehicle(customer, vid, days, outputArea);
 manager.showAvailableVehicles(listModel);
 } catch (ExcepƟon ex) {
 JOpƟonPane.showMessageDialog(this, "Enter valid Vehicle ID and number of
days.");
 }
 });
 returnBtn.addAcƟonListener(e -> {
 try {
 int rid = Integer.parseInt(returnIdField.getText());
 manager.returnVehicle(rid, outputArea);
 manager.showAvailableVehicles(listModel);
 } catch (ExcepƟon ex) {
 JOpƟonPane.showMessageDialog(this, "Enter a valid Vehicle ID.");
 }
 });
 setVisible(true);
 }
 public staƟc void main(String[] args) {
 SwingUƟliƟes.invokeLater(VehicleRentalSystemSwing::new);
 }
} 
