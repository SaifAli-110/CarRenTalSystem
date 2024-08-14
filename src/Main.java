//Project -> Car Rental System.


import java.util.*;

class Car {
    private String CarId;
    private String Car_Brand;
    private String Car_Model;
    private double BasePricePerDay;
    private boolean isAvailable;

    public Car(String CarId,String Car_Brand,String Car_Model,double BasePricePerDay){
        this.CarId = CarId;
        this.Car_Brand = Car_Brand;
        this.Car_Model = Car_Model;
        this.BasePricePerDay = BasePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return CarId;
    }

    public String getCar_Brand() {
        return Car_Brand;
    }

    public String getCar_Model() {
        return Car_Model;
    }

    public double CalculatePrice(int RentDays){
        return BasePricePerDay * RentDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    public void rent(){
        isAvailable = false;
    }
    public void returnCar(){
        isAvailable = true;
    }
}

class Customer{
    private String customerName;
    private String customerId;

    public Customer(String customerName, String customerId){
        this.customerName = customerName;
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerId() {
        return customerId;
    }
}
class Rental{

    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer,int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}
class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }
    public void AddCar(Car car){
        cars.add(car);
    }
    public void AddCustomer(Customer customer){
        customers.add(customer);
    }
    public void RentCar(Car car,Customer customer,int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car, customer, days));
        }else {
            System.out.println("Car is Not Available for Rent.....!");
        }
    }
    public void ReturnCar(Car car){
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals){
            if (rental.getCar() == car){
                rentalToRemove = rental;
                break;
            }
        }
        if(rentalToRemove != null){
            rentals.remove(rentalToRemove);
            System.out.println("Car Return Successfully....!");
        }else {
            System.out.println("Car Not Return Successfully....!");
        }
    }

//    Menu()
    public void Menu(){
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("-------------------------------");
            System.out.println("*****| Car Rental System |*****");
            System.out.println("-------------------------------\n");

            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit\n");

            System.out.println("Enter an Option : ");
            int option = sc.nextInt();

            sc.nextLine();

            if (option == 1){
                System.out.println("\n==| Rent a Car |==");
                System.out.println("Enter your Name : ");
                String customerName = sc.nextLine();

                System.out.println("\nAvailable Car");
                for (Car car : cars){
                    if (car.isAvailable()){
                        System.out.println(car.getCarId()+" - "+car.getCar_Brand()+" - "+car.getCar_Model());
                    }
                }
                System.out.println("Enter Car id you want to rent : ");
                String carId = sc.nextLine();

                System.out.println("Enter the Number of days fr rent : ");
                int rentDay = sc.nextInt();
                sc.nextLine();

                Customer customer = new Customer("Cus : "+(customers.size() + 1) , customerName );
                customers.add(customer);

                Car SelectedCar = null;
                for (Car car : cars){
                    if(car.getCarId().equals(carId) && car.isAvailable()){
                        SelectedCar = car;
                        break;
                    }
                }

                if (SelectedCar != null){
                    double total_Price = SelectedCar.CalculatePrice(rentDay);
                    System.out.println("\n\t*****| Rental Information |*****\n");
                    System.out.println("Customer ID   : "+customer.getCustomerId());
                    System.out.println("Customer Name : "+customer.getCustomerName());
                    System.out.println("Car           : "+SelectedCar.getCar_Brand()+" - "+SelectedCar.getCar_Model());
                    System.out.println("Rental Days   : "+rentDay);
                    System.out.println("Total Price   : $%.2f% : "+total_Price);

                    System.out.println("\n*****| Confirm Rental [YES/NO] |******");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("Y")){
                        RentCar(SelectedCar,customer,rentDay);
                        System.out.println("\nCar Rented Successfully.....!");
                    }else {
                        System.out.println("Rented Canceled.....!");
                    }
                }else {
                    System.out.println("Invalid Car Selection or Car Not Available for Rent.....!");
                }
            } else if (option == 2) {
                System.out.println("\n*****| Returned a Car|*****");
                System.out.println("Enter the Car ID You want to Return : ");
                String carID = sc.nextLine();

                Car carToReturn = null;
                for (Car car : cars){
                    if (car.getCarId().equals(carID) && !car.isAvailable()){
                        carToReturn = car;
                        break;
                    }
                }
                if (carToReturn != null){
                    Customer customer = null;
                    for (Rental rental : rentals){
                        if (rental.getCar() == carToReturn){
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null){
                        ReturnCar(carToReturn);
                        System.out.println("Car Return Successfully.....!");
                    }else {
                        System.out.println("Car was not Return Rental Information Canceled....!");
                    }
                }else {
                    System.out.println("Invalid Car ID Please Try Again....!");
                }
            }
            else if (option == 3){
                break;
            }else {
                System.out.println("Invalid Option Please Enter a Valid Option....!");
            }
        }
        System.out.println("Thank you for using Car Rental System");
    }
}
public class Main {
    public static void main(String[] args) {

        CarRentalSystem CRS = new CarRentalSystem();
        Car car1  = new Car("C001","TOYOTA","CAMRY",60.0);
        Car car2  = new Car("C002","Land Rover","Range Rover",70.0);
        Car car3  = new Car("C003","HONDA","",110.0);

        CRS.AddCar(car1);
        CRS.AddCar(car2);
        CRS.AddCar(car3);

        CRS.Menu();

    }
}