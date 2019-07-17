package playground.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import playground.base.AuditAndOptimisticField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@Entity
@Table(name = "EMPLOYEES")
//@NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
public class Employee extends AuditAndOptimisticField implements Serializable {

    @Id
    @SequenceGenerator(name = "EMPLOYEES_SEQ_GENERATOR", sequenceName = "EMPLOYEES_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEES_SEQ_GENERATOR")
    @Column(name = "EMPLOYEE_ID", unique = true, nullable = false)
    private long employeeId;

    @Column(name = "FIRST_NAME", nullable = false, length = 255)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 255)
    private String lastName;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 20)
    private String phone;

    @Temporal(TemporalType.DATE)
    @Column(name = "HIRE_DATE", nullable = false)
    private Date hireDate;

    @Column(name = "JOB_TITLE", nullable = false, length = 255)
    private String jobTitle;

    //bi-directional many-to-one association to Employee
    @ManyToOne(fetch = FetchType.LAZY)//ManyToOne default JPA fetch is Eager
    @JoinColumn(name = "MANAGER_ID")
    private Employee employee;

    //bi-directional many-to-one association to Employee
    @OneToMany(mappedBy = "employee")
    private List<Employee> employees; //self reference

    //bi-directional many-to-one association to Order
    @OneToMany(mappedBy = "employee")
    private List<Order> orders;

    public Employee addEmployee(Employee employee) {
        getEmployees().add(employee);
        employee.setEmployee(this);

        return employee;
    }

    public Employee removeEmployee(Employee employee) {
        getEmployees().remove(employee);
        employee.setEmployee(null);

        return employee;
    }

    public Order addOrder(Order order) {
        getOrders().add(order);
        order.setEmployee(this);

        return order;
    }

    public Order removeOrder(Order order) {
        getOrders().remove(order);
        order.setEmployee(null);

        return order;
    }
}
