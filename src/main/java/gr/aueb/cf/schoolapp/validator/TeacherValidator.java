package gr.aueb.cf.schoolapp.validator;

import gr.aueb.cf.schoolapp.dto.BaseTeacherDTO;

import java.util.HashMap;
import java.util.Map;

public class TeacherValidator<T> {
    private TeacherValidator() {}

    public static <T extends BaseTeacherDTO>Map<String, String> validate(T dto) {
        Map<String, String> errors = new HashMap<>();

        if (dto.getFirstname().length() < 2 || dto.getFirstname().length() > 32) {
            errors.put("firstname", "Το όνομα πρέπει να είναι μεταξύ 2 έως 32 χαρακτήρων");
        }
        if (dto.getLastname().length() < 2 || dto.getLastname().length() > 32) {
            errors.put("lastname", "Το επίθετο πρέπει να είναι μεταξύ 2 έως 32 χαρακτήρων");
        }
        if (dto.getFirstname().matches("^.*\\s+.*$")) {
            errors.put("firstname", "Το όνομα δεν πρέπει να περιέχει κενά");
        }
        if (dto.getLastname().matches("^.*\\s+.*$")) {
            errors.put("lastname", "Το επίθετο δεν πρέπει να περιέχει κενά");
        }

        if (dto.getVat().length() != 9) {
            errors.put("vat", "Το ΑΦΜ πρέπει να είναι 9 χαρακτήρες");
        }

        if (dto.getVat().matches("^.*\\s+.*$")) {
            errors.put("vat", "Το ΑΦΜ δεν πρέπει να περιέχει κενά");
        }

        if (dto.getFatherName().length() < 2 || dto.getFatherName().length() > 32) {
            errors.put("fathername", "Το πατρώνυμο πρέπει να είναι μεταξύ 2 έως 32 χαρακτήρων");
        }

        if (dto.getFatherName().matches("^.*\\s+.*$")) {
            errors.put("fathername", "Το πατρώνυμο δεν πρέπει να περιέχει κενά");
        }

        if (dto.getPhoneNum().length() != 10) {
            errors.put("phoneNum", "Το τηλέφωνο πρέπει να περιέχει 10 χαρακτήρες");
        }

        if (dto.getPhoneNum().matches("^.*\\s+.*$")) {
            errors.put("phoneNum", "Το τηλέφωνο δεν πρέπει να περιέχει κενά");
        }

        if (dto.getEmail().matches("^.*\\s+.*$")) {
            errors.put("email", "Το email δεν πρέπει να περιέχει κενά");
        }

        if (!dto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errors.put("email", " To email δεν είναι έγκυρο");
        }

        if (dto.getStreet().length() < 3 || dto.getStreet().length() > 100) {
            errors.put("street", "Η διεύθυνση πρέπει να είναι μεταξύ 3 έως 100 χαρακτήρων");
        }

        if (dto.getStreet().matches("^.*\\s+.*$")) {
            errors.put("street", "Η διεύθυνση δεν πρέπει να περιέχει κενά");
        }

        if (dto.getStreetNum().length() < 1 || dto.getStreetNum().length() > 3) {
            errors.put("streetNum", "Ο αριθμός πρέπει να είναι έως 3 χαρακτήρες");
        }

        if (dto.getStreetNum().matches("^.*\\s+.*$")) {
            errors.put("streetNum", "Ο αριθμός δεν πρέπει να περιέχει κενά");
        }

        if (dto.getZipCode().length() != 5) {
            errors.put("zipCode", "Ο ΤΚ πρέπει να είναι 5 χαρακτήρες");
        }

        if (dto.getZipCode().matches("^.*\\s+.*$")) {
            errors.put("ZipCode", "Ο ΤΚ δεν πρέπει να περιέχει κενά");
        }

        if (dto.getCityId() == 0) {
            errors.put("city", "Η πόλη είναι υποχρεωτική");
        }


        return errors;
    }
}
