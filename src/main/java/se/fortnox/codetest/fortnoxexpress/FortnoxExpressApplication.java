package se.fortnox.codetest.fortnoxexpress;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.fortnox.codetest.fortnoxexpress.service.OrderService;

@SpringBootApplication
public class FortnoxExpressApplication {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public static void main(String[] args) {
        logger.info("server starting....");

        IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
        options.WorkerIdBitLength = 10;
        YitIdHelper.setIdGenerator(options);

        SpringApplication.run(FortnoxExpressApplication.class, args);

    }

}
