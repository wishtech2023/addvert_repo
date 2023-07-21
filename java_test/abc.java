package java_test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class abc {
    public static void main(String[] args) {
        String csvFilePath = "C:\\Users\\jvkbr\\IdeaProjects\\spark_scala_aws\\src\\main\\resources\\input\\state_data.csv"; // Replace with the path to your CSV file

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            br.readLine(); // Skip the header row if present

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String state = data[0];
                String district = data[1];
                String mandal = data[2];
                String villages = data[3];

                createSubfolders(state, district, mandal);
                writeToCSVFile(state, district, mandal, villages);
            }

            String imagePath = "C:\\Users\\jvkbr\\IdeaProjects\\spark_scala_aws\\src\\main\\resources\\input\\sample_3.4kb.jpg"; // Replace with the path to your image file
            copyImageToSubfolders(imagePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createSubfolders(String state, String district, String mandal) {
        Path stateDir = Paths.get("output", state);
        Path districtDir = stateDir.resolve(district);
        Path mandalDir = districtDir.resolve(mandal);

        try {
            Files.createDirectories(mandalDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToCSVFile(String state, String district, String mandal, String villages) {
        String csvFilePath = Paths.get("output", state, district, mandal, villages + ".csv").toString();
    }

    private static void copyImageToSubfolders(String imagePath) {
        try {
            Files.walk(Paths.get("output"))
                    .filter(Files::isDirectory)
                    .forEach(folder -> {
                        Path imageFile = folder.resolve("image.png"); // Name the image file as needed
                        try {
                            Files.copy(Paths.get(imagePath), imageFile, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
