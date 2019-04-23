package internal.example.boot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Json2xlsxService {

    private Workbook workbook;
    private JsonNode json;

    public Json2xlsxService workbook(Workbook workbook) {
        this.workbook = workbook;
        return this;
    }

    public Json2xlsxService json(JsonNode json) {
        this.json = json;
        return this;
    }

    public void build() {
        ArrayNode sheets = (ArrayNode)json.get("sheets");

        for (int i = 0; i < sheets.size(); i++) {
            JsonNode jsonSheet = sheets.get(i);
            Sheet sheet = workbook.createSheet(jsonSheet.get("sheet").asText());
            List<String> fields = getHeader(jsonSheet);
            writeHeader(sheet, fields);
            writeData(sheet, fields, jsonSheet);
        }
    }

    private ArrayList<String> getHeader(JsonNode sheet) {
        JsonNode jsonRow = sheet.get("rows").get(0);
        return Lists.newArrayList(jsonRow.fieldNames());
    }

    private void writeHeader(Sheet sheet, List<String>fields) {
        Row row = sheet.createRow(0);
        int colIndex = 0;
        for (String field: fields) {
            row.createCell(colIndex++).setCellValue(field);
        }
    }

    private int writeData(Sheet sheet, List<String>fields, JsonNode jsonSheet) {
        int rowIndex = 1;
        int colIndex = 0;
        ArrayNode rows = (ArrayNode)jsonSheet.get("rows");
        for (int i = 0; i < rows.size(); i++) {
            Row row = sheet.createRow(rowIndex++);
            for (String field: fields) {
                row.createCell(colIndex++).setCellValue(rows.get(i).get(field).asText());
            }
        }
        return rowIndex;
    }
}
