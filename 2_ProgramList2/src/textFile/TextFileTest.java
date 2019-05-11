//II:2-1 67/818
//程序清单2-1
package textFile;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class TextFileTest {
    public static void main(String[] args) throws IOException {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
        staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);

        //save all employee records to file employee.dat
        try(PrintWriter out = new PrintWriter("employee.dat","UTF-8")){
            writeData(staff,out);
        }

        //retrieve all records into a new array
        try(Scanner in = new Scanner(new FileInputStream("employee.dat"),"UTF-8")){
            Employee[] newStaff = readData(in);

            //print the newly read employee records
            for(Employee e: newStaff)
                System.out.println(e);
        }
    }
    /**
     * Writes all employees in an array to a print writer
     * @param employees an array of employees
     * @param out a print writer
     */
    private static void writeData(Employee[] employees, PrintWriter out) throws IOException{
        //write number of employees
        out.println(employees.length);

        for (Employee e: employees)
            writeEmployee(out, e);
    }

    /**
     * Reads an array of employees from a scanner
     * @param in the scanner
     * @return the array of employees
     */
    private static Employee[] readData(Scanner in){
        //retrieve the array size
        int n = in.nextInt();
        in.nextLine();//consume newline

        Employee[] employees = new Employee[n];
        for(int i = 0; i<n;i++){
            employees[i] = readEmployee(in);
        }
        return employees;
    }

    /**
     * Writes employee data to a print writer
     * @param out the print writer
     */
    private static void writeEmployee(PrintWriter out, Employee e){
        out.println(e.getName() + "|" + e.getSalary() + "|" + e.getHireDay());
    }

    /**
     * Reads employee data from a buffered reader
     * @param in the scanner
     */
    private static Employee readEmployee(Scanner in){
        String line = in.nextLine();
        String[] tokens = line.split("\\|");
        String name = tokens[0];
        double salary = Double.parseDouble(tokens[1]);
        LocalDate hireDate = LocalDate.parse(tokens[2]);
        int year = hireDate.getYear();
        int month= hireDate.getMonthValue();
        int day = hireDate.getDayOfMonth();
        return new Employee(name, salary, year, month, day);
    }

    //自己打的（原文没有）：
    private static class Employee{
        private String name;
        private double salary;
        private LocalDate hireDay;

       Employee(String name, double salary, int year, int month, int day){
           this.hireDay = LocalDate.of(year,month,day);
           this.name = name;
           this.salary = salary;
       }

        public String getName() {
            return name;
        }

        public double getSalary() {
            return salary;
        }

        public LocalDate getHireDay() {
            return hireDay;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", salary=" + salary +
                    ", hireDay=" + hireDay +
                    '}';
        }
    }
}
