
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Fixcoment {
    public static List<String> findFiles(Path path, String[] fileExtensions) throws IOException {

        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be a directory!");
        }

        List<String> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk
                    .filter(p -> !Files.isDirectory(p))
                    // this is a path, not string,
                    // this only test if path end with a certain path
                    //.filter(p -> p.endsWith(fileExtension))
                    // convert path to string first
                    .map(p -> p.toString().toLowerCase())
                    .filter(f -> isEndWith(f, fileExtensions))
                    .collect(Collectors.toList());
            //System.out.println(result);
        }

        return result;

    }

    private static boolean isEndWith(String file, String[] fileExtensions) {
        boolean result = false;
        for (String fileExtension : fileExtensions) {
            if (file.endsWith(fileExtension)) {
                result = true;
                break;
            }
        }
        return result;
    }

    static void modifyFile(String filePath, String  oldString, String   newString) throws IOException {
        File fileToBeModified = new File(filePath);

        String oldContent = "";

        BufferedReader reader = null;

        FileWriter writer = null;

        try
        {
            reader = new BufferedReader(new FileReader(fileToBeModified));

            //Reading all the lines of input text file into oldContent

            String line = reader.readLine();
            if (line.equals("/*"))
                line = line.replace("/*","/**");

            while (line != null )
            {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();

            }

            //Replacing oldString with newString in the oldContent

            String newContent = oldContent.replace(oldString, newString);

            //Rewriting the input text file with newContent

            try {
                writer = new FileWriter(fileToBeModified);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try

            {
                //Closing the resources

                reader.close();

                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String dir = System.getProperty("user.dir");
        System.out.println(dir);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert the path: ");
        String path = scanner.nextLine();


        String[] extensions = {"c", "h"};
        List<String> files = findFiles(Paths.get(path), extensions);

        for (String str: files){
            System.out.println(str);
            File file;
            modifyFile(str, " Name        :", "* @name          ");
            modifyFile(str, " Author      :", "* @author        ");
            modifyFile(str, " Created on  :", "* @date          ");
            modifyFile(str, " Version     :", "* @version       ");
            modifyFile(str, " Copyright   :", "* @copyright     ");
            modifyFile(str, " Description :", "* @details Description : ");
            modifyFile(str, "TODO:", "*/ \n/** @todo");
            modifyFile(str, " ============================================================================", "*");
            modifyFile(str, "/* ", "/**");

           // modifyFile(str,"* @author         Heriberto J. Diaz, Alvaro Velasco Garcia", "* @author         Heriberto J. Diaz\n" +
          //          "* @author         Alvaro Velasco Garcia");
            file = new File(str);
            List<String> data = Files.readAllLines(file.toPath());
            //System.out.println(data);
            //System.out.println("done");
        }


        System.out.println("Done");

    }




}
