package com.example.birthay.reader;

import com.example.birthay.model.Birthdays;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class BirthdayReader {

    private static CsvMapper mapper;

    private static ObjectReader objectReader;

    private static CsvSchema schema;

    static {
        mapper = new CsvMapper();
        schema = mapper.typedSchemaFor(Birthdays.class)
                .withHeader().withStrictHeaders(true)
                .withColumnReordering(true);
        objectReader = mapper.readerFor(Birthdays.class)
                .with(schema);

    }

    public static void readAll(Path sourceFile, Path templateFile) {
        try (InputStream inputStream = Files.newInputStream(sourceFile)) {
            MappingIterator<Birthdays> iterator = mapper.readerFor(Birthdays.class)
                    .with(schema).readValues(inputStream);
            String templateFileContent = Files.readString(templateFile);
            iterator.readAll().forEach(birthdays -> {

                String resolvedString = templateFileContent
                        .replace("<first_name>", birthdays.getFirstName())
                        .replace("<last_name>", birthdays.getLastName())
                        .replace("<email>", birthdays.getEmail())
                        .replace("<date_of_birth>", birthdays.getDateOfBirth());
                log.info(resolvedString);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
