package com.example.backend;

import com.example.backend.model.Consultation;
import com.example.backend.model.Patient;
import com.example.backend.model.User;
import com.example.backend.model.dtos.ConsultationDto;
import com.example.backend.model.dtos.PatientDto;
import com.example.backend.model.dtos.UserDto;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestCreationFactory {

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls) {
        return listOf(cls, (Object[]) null);
    }

    @SuppressWarnings("all")
    public static <T> List<T> listOf(Class cls, Object... parameters) {
        int nrElements = new Random().nextInt(10) + 5;
        Supplier<?> supplier;

        if (cls.equals(Patient.class)) {
            supplier = TestCreationFactory::newPatient;
        } else if (cls.equals(PatientDto.class)) {
            supplier = TestCreationFactory::newPatientDto;
        } else if (cls.equals(User.class)) {
            supplier = TestCreationFactory::newUser;
        } else if (cls.equals(UserDto.class)) {
            supplier = TestCreationFactory::newUserDto;
        } else if (cls.equals(Consultation.class)) {
            supplier = TestCreationFactory::newConsultation;
        } else if (cls.equals(ConsultationDto.class)) {
            supplier = TestCreationFactory::newConsultationDto;
        } else {
            supplier = () -> new String("You failed.");
        }

        Supplier<?> finalSupplier = supplier;
        return IntStream.range(0, nrElements).mapToObj(i ->
                (T) finalSupplier.get()
        ).collect(Collectors.toSet()) // remove duplicates in case of Long for example
                .stream().collect(toList());
    }

    private static Patient newPatient() {
        return Patient.builder()
                .id(randomInt())
                .name(randomString())
                .identityCardNr(randomInt())
                .PNC(randomString())
                .birthDate(timestamp())
                .address(randomString())
                .build();
    }

    public static Date timestamp() {
        return new Date(ThreadLocalRandom.current().nextInt() * 1000L);
    }

    private static PatientDto newPatientDto() {
        return PatientDto.builder()
                .id(randomInt())
                .name(randomString())
                .identityCardNr(randomInt())
                .PNC(randomString())
                .birthDate(timestamp())
                .address(randomString())
                .build();
    }

    private static User newUser() {
        return User.builder()
                .id(randomInt())
                .username(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();
    }

    private static UserDto newUserDto() {
        return UserDto.builder()
                .id(randomInt())
                .name(randomString())
                .email(randomEmail())
                .password(randomString())
                .build();
    }

    private static Consultation newConsultation() {
        return Consultation.builder()
                .id(randomInt())
                .scheduled(timestamp())
                .details(randomString())
                .patient(newPatient())
                .doctor(newUser())
                .build();
    }

    private static ConsultationDto newConsultationDto() {
        return ConsultationDto.builder()
                .id(randomInt())
                .scheduled(timestamp())
                .details(randomString())
                .patientId(randomInt())
                .doctorId(randomInt())
                .build();
    }

    public static String randomEmail() {
        return randomString() + "@" + randomString() + ".com";
    }

    public static byte[] randomBytes() {
        byte[] bytes = new byte[Math.toIntExact(randomInt())];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static int randomInt() {
        return new Random().nextInt(1999);
    }

    public static Boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static int randomBoundedInt(int upperBound) {
        return new Random().nextInt(upperBound);
    }

    public static String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
