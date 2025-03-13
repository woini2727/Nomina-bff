package com.bff.nomina.nomina.adapter.usecase;

import com.bff.nomina.nomina.application.port.out.NominaRepository;
import com.bff.nomina.nomina.application.usecase.SearchNominaUseCase;
import com.bff.nomina.nomina.config.exception.NotFoundException;
import com.bff.nomina.nomina.domain.Nomina;
import com.bff.nomina.nomina.mock.ExecutorMockFactory;
import com.bff.nomina.nomina.mock.NominaMockFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.concurrent.Executor;

@DisplayName("")
class SearchNominaUseCaseTest {

    private static final Executor EXECUTOR = ExecutorMockFactory.get();
    private static final String CUIT = "20369724973";
    private static final String JWT_TOKEN = "random token";

    private final NominaRepository nominaRepository = Mockito.mock(NominaRepository.class);
    private SearchNominaUseCase usecase;

    @BeforeEach
    void setUp() {
        usecase = new SearchNominaUseCase(nominaRepository, EXECUTOR);
    }

    @Test
    @DisplayName("When it called it should return an Optional of Nomina")
    void whenGetByCuitIsCalledShouldReturnNominaOptional() {
        final Nomina expectedNomina = NominaMockFactory.buildNomina();

        Mockito.when(nominaRepository.searchByCuit(CUIT, JWT_TOKEN)).thenReturn(expectedNomina);

        final Optional<Nomina> actual = usecase.getByCuit(CUIT, JWT_TOKEN).toCompletableFuture().join();

        Assertions.assertThat(actual).contains(NominaMockFactory.buildNomina());
    }

    @Test
    @DisplayName("when get by cuit is called should return an empty optional")
    void whenGetByCuitIsCalledShouldReturnEmptyOptional() {
        Mockito.when(nominaRepository.searchByCuit(CUIT, JWT_TOKEN)).thenThrow(NotFoundException.class);
        final Optional<Nomina> actual = usecase.getByCuit(CUIT, JWT_TOKEN).toCompletableFuture().join();
        Assertions.assertThat(actual).isEmpty();
    }
}
