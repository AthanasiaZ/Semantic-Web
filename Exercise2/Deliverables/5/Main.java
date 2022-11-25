import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    private static Scanner scanner = new Scanner(System.in);
    private static String fileName = "3.rdf";
    public static void main(String args[])
    {

        System.out.print("File Name: ");
        fileName = scanner.next();
        RDFHandler.loadRDF(fileName);

        while(true)
        {
            printMenu();

            int choice = scanner.nextInt();

            switch (choice)
            {
                case 1:
                    showStaffByDepartment();
                    break;
                case 2:
                    showStudentsbyDepartment();
                    break;
                case 3:
                    showClassroomsbyDepartment();
                    break;
                case 4:
                    showLessonsbyDepartment();
                case 5:
                    addData();
                    break;
                case 6:
                    showDataByURI();
                    break;
                case 7:
                    System.exit(0);
            }
        }
    }

    /**
     * Prints Menu
     */
    private static void printMenu()
    {
        System.out.println("*****************************************************************");
        System.out.println("* Please select one of the following:");
        System.out.println("* 1. Show Staff by Department");
        System.out.println("* 2. Show Students by Department");
        System.out.println("* 3. Show Classrooms by Department");
        System.out.println("* 4. Show Lessons by Department");
        System.out.println("* 4. Add Data");
        System.out.println("* 6. Show Statements by URI");
        System.out.println("* 7. Exit");
        System.out.println("*****************************************************************");
        System.out.print("Choice: ");
    }
/**
     * Show list of available Departments
     * Asks user to select a Department
     * Prints the staff of the selected Department
     */
    private static void showStaffByDepartment()
    {
        // Creating List of Departments
        ArrayList<String> departments = RDFHandler.findDepartments();

        // Printing Available Departments
        System.out.println("Departments: ");
        for (String department : departments)
        {
            System.out.println((departments.indexOf(department)+1) + ". " + department);
        }

        // Ask user to select Department
        System.out.print("Select Department: ");
        int departmentIndex = scanner.nextInt();
        departmentIndex--;
        String department = departments.get(departmentIndex);
        System.out.println("You Selected: " + department);

        // Creating lists of staff, students, classrooms, lessons of selected Department
        ArrayList<Professor> professors = RDFHandler.findStaffByDepartment(department);
        ArrayList<Student> students = RDFHandler.findStudentsByDepartment(department);
        ArrayList<Classroom> classrooms =  RDFHandler.findClassroomsbyDepartment(department);
        ArrayList<Lesson> lessons = RDFHandler.findLessonsByDepartment(department);

        // Printing Staff of selected Department
        System.out.println(String.format("%30s %30s %30s","Name", "Age", "Phone"));
        for (Professor professor:professors)
        {
            professor.printProfessor();
        }

        // Printing Students of selected Department
        System.out.println(String.format("%30s %30s %30s","Name", "Age", "Phone"));
        for (Student student:student)
        {
            student.printStudent();
        }

        // Printing Classrooms of selected Department
        System.out.println(String.format("%30s %30s","Room_Name", "Capacity"));
        for (Classroom classroom:classroom)
        {
            classroom.printClassroom();
        }

        // Printing Lessons of selected Department
        System.out.println(String.format("%30s %30s","Lesson_Name", "Teacher"));
        for (Lesson lesson:lesson)
        {
            lesson.printLesson();
        }
    }

    /**
     * Shows add data menu and ask the user his choice
     */
    private static void addData()
    {
        printAddDataMenu();

        int choice = scanner.nextInt();
        switch (choice)
        {
            case 1:
                addProfessor();
                break;
            case 2:
                addStudent();
                break;
            case 3:
                addDepartment();
                break;
            case 4:
                addLesson();
                break;
            case 5:
                addClassroom();
            case 6:
                break;
        }

        RDFHandler.loadRDF(fileName);
    }

     /**
     * Prints add data menu
     */
    private static void printAddDataMenu()
    {
        System.out.println("*****************************************************************");
        System.out.println("* Please select one of the following:");
        System.out.println("* 1. Add Professor");
        System.out.println("* 2. Add Student");
        System.out.println("* 3. Add Department");
        System.out.println("* 4. Add Lesson");
        System.out.println("* 5. Add Classroom");
        System.out.println("* 6. Cancel");
        System.out.println("*****************************************************************");
        System.out.print("Choice: ");
    }

    /**
     * Asks user professor's data
     * Adds the data to rdf file
     */
    private static void addProfessor()
    {
        System.out.print("Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.next();
        System.out.print("Age: ");
        String age = scanner.next();
        System.out.print("Department: ");
        String department = scanner.next();
        System.out.print("Lesson: ");
        String lesson = scanner.next();

        RDFHandler.addProfesor(name, phone, age, department, lesson);
    }

    /**
     * Asks user students's data
     * Adds the data to rdf file
     */
    private static void addStudent()
    {
        System.out.print("Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.next();
        System.out.print("Age: ");
        String age = scanner.next();
        System.out.print("Department: ");
        String department = scanner.next();

        RDFHandler.addStudent(name, phone, age, department);
    }

    /**
     * Asks user departments's data
     * Adds the data to rdf file
     */
    private static void addDepartment()
    {
        System.out.print("Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.next();

        RDFHandler.addDepartment(name, city);
    }

    /**
     * Asks user lesson's data
     * Adds the data to rdf file
     */
    private static void addLesson()
    {
        System.out.print("Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Teacher: ");
        scanner.nextLine();
        String teacher = scanner.nextLine();

        RDFHandler.addLesson(name, teacher);
    }

    /**
     * Asks user classroom's data
     * Adds the data to rdf file
     */
    private static void addClassroom()
    {
        System.out.print("Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Capacity: ");
        scanner.nextLine();
        String teacher = scanner.nextLine();

        RDFHandler.addClassroom(name, capacity);
    }


    /**
     * Asks user for URI
     * Prints all statements of the given URI
     */
    private static void showDataByURI()
    {
        System.out.print("URI: ");
        String uri = scanner.next();

        String uriInfo = RDFHandler.selectDataByUri(uri);

        System.out.println(uriInfo);
    }
}