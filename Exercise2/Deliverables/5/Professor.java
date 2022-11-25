/**
 * Stores professor data 
 */
public class Professor
{
    private String name;
    private String age;
    private String phone;

    public Professor(String name, String age, String phone)
    {
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    void printProfessor()
    {
        System.out.println(String.format("%30s %30s %30s",name, age, phone));
    }
}
