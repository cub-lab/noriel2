package com.rsystems.noriel.jsoncsvconverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;

public class JsonCsvConverter {
    public static void JsonToCsv(String jsonString, File csvFile) throws IOException {
        JsonNode jsonTree = new ObjectMapper().readTree(jsonString);

        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode firstObject = jsonTree.elements().next();
        firstObject.fieldNames().forEachRemaining(fieldName -> {
            csvSchemaBuilder.addColumn(fieldName);
        });
        CsvSchema csvSchema = csvSchemaBuilder
                .build()
                .withHeader();

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.writerFor(JsonNode.class)
                .with(csvSchema)
                .writeValue(csvFile, jsonTree);

//        CsvMapper csvMapper = new CsvMapper();
//        CsvSchema csvSchema = csvMapper
//                .schemaFor(UserActionTypeForCsv.class)
//                .withHeader();
//        csvMapper.addMixIn(UserActionType.class, UserActionTypeForCsv.class);
//
//        UserActionType[] orderLines = new ObjectMapper()
//                .readValue(jsonString, UserActionType[].class);
//        csvMapper.writerFor(UserActionType[].class)
//                .with(csvSchema)
//                .writeValue(csvFile, orderLines);

    }

}
