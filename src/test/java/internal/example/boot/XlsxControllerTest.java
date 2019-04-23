package internal.example.boot;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({XlsxController.class, Json2xlsxView.class, Json2xlsxService.class})
public class XlsxControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnHttpStatusOk() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/xlsx/download")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(Fixure.json()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(result.getResponse().getContentAsByteArray());
        assertThat(new XSSFWorkbook(inputStream)).isNotNull();
    }
}
