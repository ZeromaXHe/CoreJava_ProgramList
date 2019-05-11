//II 2-2: 75/818
//程序清单2-2中所示的程序将三条记录写到了一个数据文件中，然后以逆序将它们从文件中读回。
// 为了高效地执行，这里需要使用随机访问，因为我们需要首先读入第三条记录。
package randomAccess;

import java.io.*;
import java.time.LocalDate;

public class RandomAccessTest {
    public static void main(String[] args) throws IOException {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
        staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);

        try(DataOutputStream out = new DataOutputStream(new FileOutputStream("employee.dat"))){
            //save all employee records to the file employee.dat
            for(Employee e:staff){
                writeData(out,e);
            }
        }

        try(RandomAccessFile in = new RandomAccessFile("employee.dat","r")){
            //retrieve all records into a new array

            //compute the array size
            int n = (int)(in.length()/Employee.RECORD_SIZE);
            Employee[] newStaff = new Employee[n];

            //read employees in reverse order
            for(int i=n-1;i>=0;i--){
                newStaff[i] = new Employee();
                in.seek(i*Employee.RECORD_SIZE);
                newStaff[i] = readData(in);
            }

            //print the newly read employee records
            for(Employee e:newStaff)
                System.out.println(e);
        }
    }

    /**
     * Writes employee data to a data output
     * @param out the data output
     * @param e the employee
     */
    public static void writeData(DataOutput out,Employee e)throws IOException{
        DataIO.writeFixedString(e.getName(),Employee.NAME_SIZE,out);
        out.writeDouble(e.getSalary());

        LocalDate hireDay = e.getHireDay();
        out.writeInt(hireDay.getYear());
        out.writeInt(hireDay.getMonthValue());
        out.writeInt(hireDay.getDayOfMonth());
    }

    /**
     * Reads employee data from a data input
     * @param in the data input
     * @return the employee
     */
    public static Employee readData(DataInput in)throws IOException{
        String name = DataIO.readFixedString(Employee.NAME_SIZE,in);
        double salary = in.readDouble();
        int y = in.readInt();
        int m = in.readInt();
        int d = in.readInt();
        return new Employee(name, salary, y, m-1, d);
    }

    //DataIO是作者在正文中写的助手类,我们将writeFixedString和readFixedString方法放到了DataIO助手类的内部。
    private static class DataIO{
        //writeFixedString写出从字符串开头开始的指定数量的码元（如果码元过少，该方法将用0值来补齐字符串）
        public static void writeFixedString(String s, int size, DataOutput out) throws IOException{
            for(int i=0;i<size;i++){
                char ch = 0;
                if(i<s.length()) ch = s.charAt(i);
                out.writeChar(ch);
            }
        }

        //readFixedString方法从输入流中读入字符，直至读入size个码元，或者直至遇到具有0值的字符值，然后跳过输入字段中剩余的0值。
        // 为了提供效率，这个方法使用了StringBuilder类来读入字符串。
        public static String readFixedString(int size, DataInput in) throws IOException{
            StringBuilder b = new StringBuilder(size);
            int i = 0;
            boolean more = true;
            while(more && i<size){
                char ch = in.readChar();
                i++;
                if(ch==0) more = false;
                else b.append(ch);
            }
            in.skipBytes(2*(size-i));
            return b.toString();
        }
    }

    //自己打的（原文没有）：
    private static class Employee{
        private String name;
        private double salary;
        private LocalDate hireDay;
        static final int NAME_SIZE = 40;//这个地方有毒，这里用的是字符数量
        static final int RECORD_SIZE = 100;//这里特么又用字节数量，感觉被搞了呀

        Employee(){

        }
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
