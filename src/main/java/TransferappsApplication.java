import com.paymybuddy.transferapps.service.InteractiveShell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.paymybuddy.transferapps")
public class TransferappsApplication {
	private static final Logger log;

	public static void main(String[] args) {
		SpringApplication.run(TransferappsApplication.class, args);
		log.info("Initializing TransferApps");
		InteractiveShell.loadWelcomeInterface();
	}
	static {
		log = LogManager.getLogger((Class) TransferappsApplication.class);
	}
}
