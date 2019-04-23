package internal.example.boot;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class XlsxController {

    private final Json2xlsxView json2xlsView;

    @PostMapping("/xlsx/download")
    public ModelAndView getWorksheet(@RequestBody @Valid JsonNode jsonObject) {
        return new ModelAndView(json2xlsView, "json", jsonObject);
    }
}
