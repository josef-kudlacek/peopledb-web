package eu.kudljo.peopledbweb.business.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PersonTest {

    @Test
    public void canParse() {
        String csvFile = "965851,Mr.,Damian,N,Patillo,M,damian.patillo@outlook.com,Harley Patillo,Lucinda Patillo,Etter,  3/11/1975, 07:48:45 PM,45.51,84,12/16/2004,Q4,H2,2004,12,December,Dec,16,Thursday,Thu,15.72,158746,8%,326-11-9852,209-784-3915,Burrel,Fresno,Burrel,CA,93607,West,dnpatillo,GQj^c:4#B:F8";
        Person person = Person.parse(csvFile);

        assertThat(person.getDob()).isEqualTo(LocalDate.of(1975, 3, 11));
    }
}