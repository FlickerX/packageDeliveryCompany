package delivery.kursinis;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;

public class BaseUnitTest {
    private static SessionFactory sessionFactory;

    @BeforeEach
    public void setUp() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml"); // Load the test-specific Hibernate configuration
        sessionFactory = configuration.buildSessionFactory();
    }

}
