package metervolumedemo.profile;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ProfileControllerIntegrationTest {

    private static final String INCOMPLETE_PROFILE = "{ \"profile\": \"A\" }";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateWhenProfileDataIncompleteThenReturnBadRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/profile").contentType(APPLICATION_JSON_UTF8).content(INCOMPLETE_PROFILE))
               .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateWhenProfileDataCorrectThenReturnOk() throws Exception {
        String correctProfile = new String(Files.readAllBytes(Paths.get(getClass().getResource("/profile.json").toURI())), StandardCharsets.UTF_8);

        mockMvc.perform(MockMvcRequestBuilders.post("/profile").contentType(APPLICATION_JSON_UTF8).content(correctProfile)).andExpect(status().isOk());
    }
}