package eu.kudljo.peopledbweb.business.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "First name can not be empty")
    private String firstName;

    @NotEmpty(message = "Last name can not be empty")
    private String lastName;

    @Past(message = "Date of birt must be in the past")
    @NotNull(message = "Date of birth must be specified")
    private LocalDate dob;

    @DecimalMin(value = "1000.00", message = "Salary must be at least 1000")
    @NotNull(message = "Salary can not be empty")
    private BigDecimal salary;

    @Email(message = "Email must be valid")
    @NotEmpty(message = "Email must not be empty")
    private String email;

    private String photoFileName;
}
