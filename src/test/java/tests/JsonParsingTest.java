package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Department;
import model.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonParsingTest {

    // JSON лежит в src/test/resources/employees.json
    private static Department department;

    @BeforeAll
    static void loadJson() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = JsonParsingTest.class
                .getResourceAsStream("/employees.json")) {
            department = mapper.readValue(is, Department.class);
        }
    }

    @Test
    void departmentHasCorrectCompanyName() {
        assertThat(department.company).isEqualTo("TechCorp");
    }

    @Test
    void departmentHasCorrectName() {
        assertThat(department.department).isEqualTo("Engineering");
    }

    @Test
    void employeesArrayIsNotEmpty() {
        assertThat(department.employees).isNotEmpty();
    }

    @Test
    void employeesArrayHasCorrectSize() {
        assertThat(department.employees).hasSize(3);
    }

    @Test
    void firstEmployeeHasCorrectData() {
        Employee first = department.employees.get(0);

        assertThat(first.id).isEqualTo(1);
        assertThat(first.name).isEqualTo("John Smith");
        assertThat(first.position).isEqualTo("Senior Developer");
        assertThat(first.salary).isEqualTo(95000);
        assertThat(first.active).isTrue();
    }

    @Test
    void firstEmployeeHasExpectedSkills() {
        Employee first = department.employees.get(0);

        assertThat(first.skills)
                .hasSize(3)
                .contains("Java", "Python", "SQL");
    }

    @Test
    void inactiveEmployeeCanBeFound() {
        assertThat(department.employees)
                .filteredOn(e -> !e.active)
                .hasSize(1)
                .extracting(e -> e.name)
                .containsExactly("Bob Johnson");
    }

    @Test
    void allActiveEmployeesHaveSalaryAbove70000() {
        assertThat(department.employees)
                .filteredOn(e -> e.active)
                .allMatch(e -> e.salary > 70000);
    }

    @Test
    void qaEngineerHasSeleniumSkill() {
        assertThat(department.employees)
                .filteredOn(e -> e.position.equals("QA Engineer"))
                .flatExtracting(e -> e.skills)
                .contains("Selenium");
    }
}