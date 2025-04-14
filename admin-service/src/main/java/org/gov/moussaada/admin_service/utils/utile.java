package org.gov.moussaada.admin_service.utils;

import org.gov.moussaada.utilisateur_service.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class utile {

    private static final Path rootLocation = Paths.get("/var/tmp/PDF");
    private final Path rootLocationimg = Paths.get("src/main/resources/image");

    public static java.util.Date CurentDate() {
        // Obtenir la date et l'heure courante
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Définir le format désiré
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        // Formater la date et l'heure
        String formattedDateTime = currentDateTime.format(formatter);

        // Afficher la date et l'heure formatée
        System.out.println("Date et heure courante formatée : " + formattedDateTime);

        // Convertir LocalDateTime en Date
        return java.util.Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String savePDF(MultipartFile file) throws IOException {
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }

        if (!isPDF(file)) {
            throw new IOException("Ce n'est pas un PDF.");
        }

        if (file.isEmpty()) {
            throw new IOException("Impossible de stocker un fichier vide.");
        }

        String fileName = file.getOriginalFilename();
        Path destinationFile = rootLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();

        // Vérifier si le fichier existe déjà
        if (Files.exists(destinationFile)) {
            // Si le fichier existe, ajouter un suffixe incrémental
            String newFileName = generateUniqueFileName(fileName);
            destinationFile = rootLocation.resolve(Paths.get(newFileName)).normalize().toAbsolutePath();
        }

        if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
            throw new IOException("Impossible de stocker le fichier en dehors du répertoire courant.");
        }

        try {
            Files.copy(file.getInputStream(), destinationFile);
        } catch (IOException e) {
            throw new IOException("Impossible de stocker le fichier.", e);
        }

        return destinationFile.getFileName().toString();
    }

    private static String generateUniqueFileName(String originalFileName) {
        String baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        int count = 1;

        Path destinationFile = rootLocation.resolve(Paths.get(originalFileName)).normalize().toAbsolutePath();

        // Boucle jusqu'à trouver un nom de fichier qui n'existe pas déjà
        while (Files.exists(destinationFile)) {
            String newFileName = baseName + "(" + count + ")" + extension;
            destinationFile = rootLocation.resolve(Paths.get(newFileName)).normalize().toAbsolutePath();
            count++;
        }

        return destinationFile.getFileName().toString();
    }

    public static void imageBloc(){

    }

    public static boolean isPDF(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null && fileName.toLowerCase().endsWith(".pdf");
    }

    public static String CheckPdfAccepded(MultipartFile pdf){
        String pdfFilename = null;
        if (pdf != null && !pdf.isEmpty()) {
            if (!isPDF(pdf)) {
                return String.valueOf(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("File is not a PDF.")));
            }
            try {
                pdfFilename = savePDF(pdf);
            } catch (IOException e) {
                return String.valueOf(ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ErrorResponse("le fichier est deja existe: " + e.getMessage())));
            }
        }
        return pdfFilename;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        if (password == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
