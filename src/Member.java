import java.io.Serializable;

class Member implements Serializable {

    public Object firstName;
    public Object lastName;
    public Object sex;
    public String city;
    public Object university;
    public Object bdate;

    Member(Object name, Object date, Object surname, Object sex, String city, Object univer){

        firstName=name;
        lastName=surname;
        bdate = date;
        this.sex=sex;
        this.city=city;
        university=univer;
    }
}