/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sort;

import java.util.Comparator;

/**
 *
 * @author HP
 */
public class Employee implements Comparable {
    String ID;
    String name;
    int salary;

    public Employee(String ID, String name, int salary) {
        this.ID = ID;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return ID + ", " + name + ", " + salary;
    }
    
    
    @Override
    public int compareTo(Object t) { // standard comparing
        return ID.compareTo(((Employee)t).ID);
    }
    
    public static Comparator compareObj = new Comparator() {
        @Override
        public int compare(Object t, Object t1) {
            Employee emp1 = (Employee) t;
            Employee emp2 = (Employee) t1;
            int d = emp1.salary - emp2.salary;
            if (d>0) return -1; // lower salary -> move upper
            // d==0 is equal so compare ID
            if (d==0) return emp1.ID.compareTo(emp2.ID);
            return 1;
        }
    };
}
