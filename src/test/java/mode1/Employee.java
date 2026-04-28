package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

    public int id;
    public String name;
    public String position;
    public int salary;
    public boolean active;
    public List<String> skills;
}