package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RunningLocationServiceApplication.class)
@WebAppConfiguration
public class LocationRepositoryTest {

    @Autowired
    LocationRepositoryTest repository;

    @Test
    public void whenSaveLocation_expectOk() {
        this.repository.save();
        assertThat();
    }
}
