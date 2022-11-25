/**
 * Stores Classroom data 
 */
public class Classroom
{
    private String room_name;
    private String room_capacity;

    public Classroom(String room_name, String room_apacity)
    {
        this.room_name = room_name;
        this.room_capacity = room_capacity;
    }

    void printClassroom()
    {
        System.out.println(String.format("%30s %30s","Room_Name", "Capacity"));
    }
}
