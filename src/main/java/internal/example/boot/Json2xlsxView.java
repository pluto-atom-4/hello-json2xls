package internal.example.boot;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class Json2xlsxView extends AbstractXlsxView {

    private final Json2xlsxService json2xlsxService;

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        json2xlsxService.workbook(workbook).json((JsonNode)map.get("json")).build();
    }

}
