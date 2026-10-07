import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Logger {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o seu nome: ");
        String name = scanner.nextLine();

        System.out.println("Bem-vindo, " + name + "!");

        Path logFile = Paths.get("/logs/log.txt");
        // Path logFile = Paths.get("logs/log.txt");

        try {
            Files.createDirectories(logFile.getParent());

            if (Files.exists(logFile)) {
                String previousName = Files.readString(logFile);

                System.out.println("Nome anterior: " + previousName);
            }

            Files.writeString(logFile, name);

        } catch (IOException e) {
            System.err.println("Erro ao trabalhar com o ficheiro: "
                    + e.getMessage());
        }

        scanner.close();
    }
}