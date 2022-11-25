
/**
 * Stores Student data 
 */
public class Student
{
    private String name;
    private String age;
    private String phone;

    public Student(String name, String age, String phone)
    {
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    void printStudent()
    {
        System.out.println(String.format("%30s %30s %30s",name, age, phone));
    }
}
