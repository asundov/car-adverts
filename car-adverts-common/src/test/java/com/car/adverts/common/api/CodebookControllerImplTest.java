package test.java.com.car.adverts.common.api;

import com.car.adverts.CarAdvertsApplication;
import com.car.adverts.api.CodebookApiControllerImpl;
import hr.ericsson.eb.car.adverts.api.model.CodebookSimpleResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CarAdvertsApplication.class)
public class CodebookControllerImplTest {

    @Autowired
    private CodebookApiControllerImpl codebookApiControllerImpl;

    private static final Logger log = LogManager.getLogger(AuthApiControllerImplTest.class);

    @Test
//    @Disabled
    public void testGetFuelTypes() {
        ResponseEntity<List<CodebookSimpleResponse>> fuelTypesEntity = codebookApiControllerImpl.getFuelTypes();
        log.info(fuelTypesEntity.getBody());
        assertEquals(HttpStatus.OK, fuelTypesEntity.getStatusCode());
    }
}
