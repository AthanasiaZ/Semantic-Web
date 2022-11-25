import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

class RDFHandler
{
    private static Model model; // Stores main rdf model
    private static String file;

    // Namespaces
    private static String krweb = "http://www.krweb.org/";

    /**
     * Loads the rdf file
     * @param fileName Name of the file (String)
     */
    static void loadRDF(String fileName)
    {
        // create an empty model
        model = ModelFactory.createDefaultModel();

        // use the FileManager to find the input file
        file = fileName;
        InputStream file = FileManager.get().open(fileName);
        if (file == null) {
            throw new IllegalArgumentException( "File: " + fileName + " not found");
        }

        // read the RDF/XML file
        model = model.read( file, null);
    }
    
    /**
     * Checks for departments into rdf file.
     * @return departments selector
     */
    private static SimpleSelector selectDepartments()
    {
        // Selects all the resources with a dep_name property
        Model m = ModelFactory.createDefaultModel();
        Property depName = m.createProperty(krweb,"dep_name" );

        return new SimpleSelector(null, depName,(RDFNode) null);
    }
    
    /**
     * @return departments list of departments
     */
    static ArrayList<String> findDepartments()
    {
        SimpleSelector departmentsSelector = selectDepartments();

        // Adding departments to list
        ArrayList<String> departments = new ArrayList<String>();
        StmtIterator iter = model.listStatements(departmentsSelector);
        while (iter.hasNext())
        {
            departments.add(iter.nextStatement().getString());
        }

        return departments;
    }

    /**
     * @return departments hashmap department-URI
     */
    private static HashMap<String,String> findDepartmentURI()
    {
        SimpleSelector departmentsSelector = selectDepartments();

        // Adding Departments and URIs to hashmap
        HashMap<String,String> departments = new HashMap<String,String>();
        StmtIterator iter = model.listStatements(departmentsSelector);
        while (iter.hasNext())
        {
            Statement department = iter.nextStatement();
            departments.put(department.getString(),department.getSubject().toString());
        }

        return departments;
    }
     /**
     *
     * @param department string format department (dep_name)
     * @return persons list of staff
     */
    static ArrayList<Professor> findStaffByDepartment(String department)
    static ArrayList<Student> findStudentsByDepartment(String department)
    static ArrayList<Classroom> findClassroomsByDepartment(String department)
    static ArrayList<Lesson> findLessonsByDepartment(String department)
    {
        // Associating department names with URIs
        HashMap<String,String> departments = findDepartmentURI();


        // Creating Properties, Resources and Nodes
        Model m = ModelFactory.createDefaultModel();
        RDFNode departmentURI = m.createResource(departments.get(department));
        Property memberOf = m.createProperty(krweb,"member_of");
        Property hasName = m.createProperty(krweb,"has_name");
        Property hasPhone = m.createProperty(krweb,"has_phone");
        Property hasAge = m.createProperty(krweb,"has_age");
        Property taughtBy = m.createProperty(krweb, "taught_by");
        Property lesName = m.createProperty(krweb, "les_name");
        Property RoomName = m.createProperty(krweb, "room_name");
        Property RoomCapacity = m.createProperty(krweb, "room_capacity");
        
        
        
        // Selecting Nodes of the given department
        SimpleSelector memberOfSelector = new SimpleSelector(null, memberOf,(RDFNode) departmentURI);
        StmtIterator iter = model.listStatements(memberOfSelector);
        
        // Iterating through each node
        ArrayList<Professor> professors = new ArrayList<Professor>();
        while (iter.hasNext())
        {
            Statement stmt = iter.nextStatement();

            // Assigning and adding values into professor object and adding professor to the list
            String name = stmt.getSubject().getProperty(hasName).getString();
            String phone = stmt.getSubject().getProperty(hasPhone).getString();
            String age = stmt.getSubject().getProperty(hasAge).getString();

            professors.add(new Professor(name,age,phone));
        }

        return professors;
        
        ArrayList<Student> students = new ArrayList<Student>();
        while (iter.hasNext())
        {
            Statement stmt = iter.nextStatement();

            // Assigning and adding values into student object and adding student to the list
            String name = stmt.getSubject().getProperty(hasName).getString();
            String phone = stmt.getSubject().getProperty(hasPhone).getString();
            String age = stmt.getSubject().getProperty(hasAge).getString();

            students.add(new Student(name,age,phone));
        }

        return students;
        
        ArrayList<Lesson> lessons = new ArrayList<Lesson>();
        while (iter.hasNext())
        {
            Statement stmt = iter.nextStatement();

            // Assigning and adding values into lesson object and adding lesson to the list
            String name = stmt.getSubject().getProperty(lesName).getString();
            String phone = stmt.getSubject().getProperty(taughtBy).getString();

            lessons.add(new Student(les_name,taught_by));
        }

        return lessons;
        
        ArrayList<Classroom> classrooms = new ArrayList<Classroom>();
        while (iter.hasNext())
        {
            Statement stmt = iter.nextStatement();

            // Assigning and adding values into classroom object and adding classroom to the list
            String name = stmt.getSubject().getProperty(RoomName).getString();
            String phone = stmt.getSubject().getProperty(RoomCapacity).getString();

            classrooms.add(new Student(room_name, room_capacity));
        }

        return classrooms;
    }
    
    /**
     * Executes SPARQL query and prints the results
     * @param queryString string SPARQL query
     */
    private static void executeSPARQL(String queryString)
    {
        // Creating Query
        Query query = QueryFactory.create(queryString);

        // Execute the query and obtain results
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        ResultSet results = qe.execSelect();

        // Output query results
        ResultSetFormatter.out(System.out, results, query);

        // Free up resources used running the query
        qe.close();
    }
    
      /**
     * Adds a professor ontology into rdf file
     * @param name (has_name property)
     * @param phone (has_phone property)
     * @param age (has_age property)
     * @param department (member_of property) as string NOT URI
     * @param lesson (teaches property) as string NOT URI
     */
    static void addProfesor(String name, String phone, String age, String department, String lesson)
    {
        String newNodeString = "\t<!-- Added with rdf tool (Professor)-->\n" +
                "\t<rdf:Description rdf:about=\"http://www.krweb.org/" + name.toLowerCase().replace(" ", "") + "\">\n" +
                "\t\t<rdf:type rdf:resource=\"http://www.krweb.org/#Professor\"/>\n" +
                "\t\t<krweb:has_name>" + name + "</krweb:has_name>\n" +
                "\t\t<krweb:has_phone>" + phone + "</krweb:has_phone>\n" +
                "\t\t<krweb:has_age>" + age + "</krweb:has_age>\n" +
                "\t\t<krweb:member_of rdf:resource=\"http://www.krweb.org/"+department.toLowerCase().replace(" ","")+ "\"/>\n" +
                "\t\t<krweb:teaches rdf:resource=\"http://www.krweb.org/" + lesson + "\" />\n" +
                "\t</rdf:Description>";

        addNodeToFile(newNodeString);
    }

    /**
     * Adds a student ontology into rdf file
     * @param name (has_name property)
     * @param phone (has_phone property)
     * @param age (has_age property)
     * @param department (member_of property) as string NOT URI
     */
    static void addStudent(String name, String phone, String age, String department)
    {
        String newNodeString = "\t<!-- Added with rdf tool (Student)-->\n" +
                "\t<rdf:Description rdf:about=\"http://www.krweb.org/" + name.toLowerCase().replace(" ", "") + "\">\n" +
                "\t\t<rdf:type rdf:resource=\"http://www.krweb.org/#Student\"/>\n" +
                "\t\t<krweb:has_name>" + name + "</krweb:has_name>\n" +
                "\t\t<krweb:has_phone>" + phone + "</krweb:has_phone>\n" +
                "\t\t<krweb:has_age>" + age + "</krweb:has_age>\n" +
                "\t\t<krweb:member_of rdf:resource=\"http://www.krweb.org/"+department.toLowerCase().replace(" ","")+ "\"/>\n" +
                "\t</rdf:Description>";

        addNodeToFile(newNodeString);
    }

    /**
     * Adds a department ontology into rdf file
     * @param name (dep_name property)
     * @param city (dep_city property)
     */
    static void addDepartment(String name, String city)
    {
        String newNodeString = "\t<!-- Added with rdf tool (Department) -->\n" +
                "<rdf:Description rdf:about=\"http://www.krweb.org/" + name.toLowerCase().replace(" ", "") + "\">\n" +
                "        <rdf:type rdf:resource=\"http://www.krweb.org/#Department\"/>\n" +
                "        <krweb:dep_name>" + name + "</krweb:dep_name>\n" +
                "        <krweb:dep_city>" + city + "</krweb:dep_city>\n" +
                "    </rdf:Description>";

        addNodeToFile(newNodeString);
    }

    /**
     * Adds a lesson ontology into rdf file
     * @param name (les_name property) as string NOT URI
     * @param teacher (taught_by property) as string NOT URI
     */
    static void addLesson(String name, String taught_by)
    {
        String newNodeString = "\t<!-- Added with rdf tool (Department) -->\n" +
                "<rdf:Description rdf:about=\"http://www.krweb.org/" + name.toLowerCase().replace(" ", "") + "\">\n" +
                "        <rdf:type rdf:resource=\"http://www.krweb.org/#Lesson\"/>\n" +
                "        <krweb:les_name>" + name + "</krweb:les_name>\n" +
                "        <krweb:taught_by rdf:resource=\"http://www.krweb.org/" + taught_by.toLowerCase().replace(" ", "") + "\"/>\n" +
                "    </rdf:Description>";

        addNodeToFile(newNodeString);
    }
    
    /**
     * Adds a classroom ontology into rdf file
     * @param name (room_name property) as string NOT URI
     * @param capacity (room_capacity property) as string NOT URI
     */
    static void addClassroom(String name, String room_capacity)
    {
        String newNodeString = "\t<!-- Added with rdf tool (Department) -->\n" +
                "<rdf:Description rdf:about=\"http://www.krweb.org/" + name.toLowerCase().replace(" ", "") + "\">\n" +
                "        <rdf:type rdf:resource=\"http://www.krweb.org/#Classroom\"/>\n" +
                "        <krweb:room_name>" + name + "</krweb:room_name>\n" +
                "        <krweb:room_capacity rdf:resource=\"http://www.krweb.org/" + room_capacity.toLowerCase().replace(" ", "") + "\"/>\n" +
                "    </rdf:Description>";

        addNodeToFile(newNodeString);
    }

     /**
     * Add a node into root element
     * @param node xml node as string
     */
    private static void addNodeToFile(String node)
    {
        File inputFile = new File(file);
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        Document doc = null;
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            // creating input stream
            doc = builder.parse(inputFile );
            Node newNode = doc.createTextNode(node);
            doc.getDocumentElement().appendChild(newNode);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            DOMSource source = new DOMSource(doc);
            StreamResult streamResult =  new StreamResult(new File(file));
            transformer.transform(source, streamResult);

            fixXmlFile(file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Fixing escape characters of the given xml file
     * @param file as string
     * @throws IOException (check)
     */
    private static void fixXmlFile(String file) throws IOException
    {

        String xmlFile = readFile(file);
        xmlFile = xmlFile.replace("&lt;","<").replace("&gt;",">").replace("</rdf:RDF>", "\r</rdf:RDF>");

        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.print(xmlFile);
        writer.close();
    }

    /**
     *
     * @param fileName name of file as string
     * @return A string of the file content
     * @throws IOException if file not exist
     */
    private static String readFile(String fileName) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    /**
     * Retern all statements of the given uri
     * @param uri (string uri)
     * @return string of statements
     */
    static String selectDataByUri(String uri)
    {
        String uriInfo = "";
        // Selects all the resources with a dep_name property
        Model m = ModelFactory.createDefaultModel();
        //Property depName = m.createProperty(krweb,"dep_name" );
        Resource resource = m.createResource(uri);

        SimpleSelector selector = new SimpleSelector(resource, null,(RDFNode) null);

        StmtIterator iter = model.listStatements(selector);

        // Iterating through each node
        while (iter.hasNext())
        {
            Statement stmt = iter.nextStatement();
            uriInfo += stmt.toString()
                    .replace("[","")
                    .replace("]","")
                    .replace("\"","")
                    .replace(",","")
                    + "\n";
        }

        return uriInfo;
    }
}
