package WebSiters.GastroReview;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class GastroReviewApplicationTests {

	@Test
	@Disabled("Test de integración deshabilitado. Requiere PostgreSQL en ejecución. Usar pruebas unitarias para validación.")
	void contextLoads() {
		// Test para verificar que el contexto de Spring se carga correctamente
		// Este test requiere que PostgreSQL esté en ejecución
	}

}
