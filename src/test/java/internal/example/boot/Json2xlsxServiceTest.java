package internal.example.boot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Json2xlsxService.class})
public class Json2xlsxServiceTest {

    @Autowired
    private Json2xlsxService json2xlsxService;

    private Workbook workbook;
    private JsonNode jsonObject;

    @Before
    public void setUp() throws IOException {
        workbook = new XSSFWorkbook();
        ObjectMapper mapper = new ObjectMapper();
        jsonObject = mapper.readTree(Fixure.json());
    }

    @Test
    public void shouldReturnWorkbookWithOneSheet() {
        this.json2xlsxService
                .workbook(workbook)
                .json(jsonObject)
                .build();
        assertThat(workbook.getNumberOfSheets()).isEqualTo(1);
        assertThat(workbook.getSheetAt(0).getSheetName()).isEqualTo("airport");
    }

    @Test
    public void shouldReturnWorkbookWithHeader() {
        this.json2xlsxService
                .workbook(workbook)
                .json(jsonObject)
                .build();
        assertThat(workbook.getNumberOfSheets()).isEqualTo(1);
        assertThat(workbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue()).isEqualTo("airportCode");
        assertThat(workbook.getSheetAt(0).getRow(0).getCell(3).getStringCellValue()).isEqualTo("cityName");
        assertThat(workbook.getSheetAt(0).getRow(0).getCell(9).getStringCellValue()).isEqualTo("identicalMetroCodeBool");
        assertThat(workbook.getSheetAt(0).getRow(0).getCell(16).getStringCellValue()).isEqualTo("statusCode");
    }

    @Test
    public void shouldReturnWorkbookWithData() {
        this.json2xlsxService
                .workbook(workbook)
                .json(jsonObject)
                .build();
        assertThat(workbook.getNumberOfSheets()).isEqualTo(1);
        assertThat(workbook.getSheetAt(0).getRow(1).getCell(0).getStringCellValue()).isEqualTo("DTW");
        assertThat(workbook.getSheetAt(0).getRow(1).getCell(3).getStringCellValue()).isEqualTo("Detroit");
        assertThat(workbook.getSheetAt(0).getRow(1).getCell(9).getStringCellValue()).isEqualTo("0");
        assertThat(workbook.getSheetAt(0).getRow(1).getCell(16).getStringCellValue()).isEqualTo("A");
    }
}
