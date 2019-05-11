//II 2-3: 83/818
//程序清单2-3是保存和重新加载Employee和Manager对象网络的代码（有些对象共享相同的表示秘书的雇员）。
// 注意，秘书对象在重新加载之后是唯一的，当newStaff[1]被恢复时，它会反映到经理们的secretary域中。
package objectStream;

import java.io.*;
import java.time.LocalDate;

public class ObjectStreamTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Employee harry = new Employee("Harry Hacker",50000,1989,10,1);
        Manager carl = new Manager("Carl Cracker",80000,1987,12,15);
        carl.setSecretary(harry);
        Manager tony = new Manager("Tony Tester",40000,1990,3,15);
        tony.setSecretary(harry);

        Employee[] staff =new Employee[3];
        staff[0] = carl;
        staff[1] = harry;
        staff[2] = tony;

        //save all employee records to the file employee.dat
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employee.dat"))){
            out.writeObject(staff);
        }

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("employee.dat"))){
            //retrieve all records into a new array

            Employee[] newStaff = (Employee[])in.readObject();

            //raise secretary's salary
            newStaff[1].raiseSalary(10);

            //print the newly read employee records
            for(Employee e: newStaff){
                System.out.println(e);
            }
        }
    }

    //自己打的（原文没有）：
    private static class Employee implements Serializable{
        private String name;
        private double salary;
        private LocalDate hireDay;


        Employee(String name, double salary, int year, int month, int day){
            this.hireDay = LocalDate.of(year,month,day);
            this.name = name;
            this.salary = salary;
        }

        public void raiseSalary(double byPercent){
            double raise = salary * byPercent/100;
            salary += raise;
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

    //自己写的Manager继承Employee（原文没有）：
    private static class Manager extends Employee implements Serializable{
        private String name;
        private double salary;
        private LocalDate hireDay;
        private Employee secretary;
        Manager(String name, double salary, int year, int month, int day){
            super(name,salary,year,month,day);
        }

        public void setSecretary(Employee secretary) {
            this.secretary = secretary;
        }
    }
}