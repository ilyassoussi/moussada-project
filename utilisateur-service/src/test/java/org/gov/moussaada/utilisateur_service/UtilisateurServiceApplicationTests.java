package org.gov.moussaada.utilisateur_service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class UtilisateurServiceApplicationTests {
	@Value("${secret-key}")
	private final String  ENCRYPTION_KEY;

    UtilisateurServiceApplicationTests(@Value("${secret-key}") String encryptionKey) {
        ENCRYPTION_KEY = encryptionKey;
    }

    @Test
	void contextLoads() {
		log.info("hnaya ",ENCRYPTION_KEY);
	}

}
