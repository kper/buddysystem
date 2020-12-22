package wtf.juridicum.buddy;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

@Configuration
@Profile({"datagen", "prod"})
public class DataGeneratorBean {

    private static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private DataSource source;

    public DataGeneratorBean(DataSource source) {
        this.source = source;
    }

    /**
     * Executed once when the component is instantiated. Inserts some dummy data.
     */
    @PostConstruct
    void insertDummyData() {
        try {
            ScriptUtils.executeSqlScript(source.getConnection(), new ClassPathResource("sql/insertData.sql"));
        } catch (Exception e) {
            LOGGER.error("Error inserting test data", e);
        }
    }
}

