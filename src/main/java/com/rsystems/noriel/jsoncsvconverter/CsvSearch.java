package com.rsystems.noriel.jsoncsvconverter;

import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CsvSearch extends RowListProcessor {
    //value to be searched for
    private final String stringToMatch;

    //name of column to match (if you don't have headers)
    private final String columnToMatch;

    //position of column to match
    private int indexToMatch = -1;

    public CsvSearch(String columnToMatch, String stringToMatch) {
        this.columnToMatch = columnToMatch;
        this.stringToMatch = stringToMatch.toLowerCase(); //lower case to make the search case-insensitive
    }

    public CsvSearch(int columnToMatch, String stringToMatch) {
        this(stringToMatch, null);
        this.indexToMatch = columnToMatch;
    }

    @Override
    public void rowProcessed(String[] row, ParsingContext context) {
        if (indexToMatch == -1) {
            //initializes the index to match
            indexToMatch = context.indexOf(columnToMatch);
        }

        String value = row[indexToMatch];
        if (value != null && value.toLowerCase().contains(stringToMatch)) {
            super.rowProcessed(row, context); // default behavior of the RowListProcessor: add the row into a List.
        }
        // else skip the row.
    }

    public static void searchCsv() {
        // let's measure the time roughly
        long start = System.currentTimeMillis();

        CsvParserSettings settings = new CsvParserSettings();
        //settings.setHeaders("ItemId", "ItemName", "Recommended_ItemId", "Recommended_ItemName", "AvailableStock");
        settings.setHeaderExtractionEnabled(true); //extract headers from the first row
        settings.setMaxColumns(10);
        // settings.selectFields("emailHash", "Recommended_ItemId");
        // settings.selectIndexes( 1,3);
        CsvSearch search = new CsvSearch("emailHash", "1");

//We instruct the parser to send all rows parsed to your custom RowProcessor.
        settings.setProcessor(search);

//Finally, we create a parser
        CsvParser parser = new CsvParser(settings);

//And parse! All rows are sent to your custom RowProcessor (CsvSearch)
//I'm using a 150MB CSV file with 1.3 million rows.
        parser.parse(new File("d:/aaa.csv"));

//get the collected rows from our processor
        List<String[]> results = search.getRows();

//Nothing else to do. The parser closes the input and does everything for you safely. Let's just get the results:
        System.out.println("Rows matched: " + results.size());
        results.forEach(s -> System.out.println(Arrays.toString((String[]) s)));
        System.out.println("Time taken: " + (System.currentTimeMillis() - start) + " ms");

    }

}
