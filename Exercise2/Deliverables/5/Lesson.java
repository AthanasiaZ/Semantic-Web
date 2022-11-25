/**
 * Stores Lesson data 
 */
public class Lesson
{
    private String les_name;
    private String taught_by;

    public Lesson(String les_name, String taught_by)
    {
        this.les_name = les_name;
        this.taught_by = taught_by;
    }

    void printLesson()
    {
        System.out.println(String.format("%30s %30s","Lesson_Name", "Teacher"));
    }
}
