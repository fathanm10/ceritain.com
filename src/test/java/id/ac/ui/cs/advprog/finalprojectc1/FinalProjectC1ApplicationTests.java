package id.ac.ui.cs.advprog.finalprojectc1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FinalProjectC1ApplicationTests {

    @Test
    void contextLoads() {
        FinalProjectC1Application finalProjectC1Application = new FinalProjectC1Application();
        assertNotNull(finalProjectC1Application);

        FinalProjectC1Application.main(new String[0]);
    }

}
