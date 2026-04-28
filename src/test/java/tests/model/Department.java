package tests.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Department {

    public String company;
    public String department;
    public List<tests.model.Employee> employees;
}