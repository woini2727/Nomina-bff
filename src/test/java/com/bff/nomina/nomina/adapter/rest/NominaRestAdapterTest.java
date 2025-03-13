package com.bff.nomina.nomina.adapter.rest;

import com.bff.nomina.nomina.config.ErrorCode;
import com.bff.nomina.nomina.config.TestConfig;
import com.bff.nomina.nomina.config.exception.NotFoundException;
import com.bff.nomina.nomina.domain.Nomina;
import com.bff.nomina.nomina.mock.NominaMockFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withNoContent;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@DisplayName("SWCharacterRest Adapter Test")
@Import(TestConfig.class)
@RestClientTest({NominaRestAdapter.class})

class NominaRestAdapterTest {
    private static final String CUIT = "20369724973";
    private static final String JWT_TOKEN = "random token";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private NominaRestAdapter client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HeadersProvider headersProvider;

    @BeforeEach
    void setUp() {
        Mockito.clearInvocations(headersProvider);
        Mockito.when(headersProvider.get(JWT_TOKEN)).thenReturn(getMockHeaders());
    }

    private HttpHeaders getMockHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_HEADER, "Bearer ".concat(JWT_TOKEN));
        return headers;
    }

    @Test
    @DisplayName("when get is called the adapter should return a nomina")
    void testGetByCuitSuccessfully() throws JsonProcessingException {
        final String detailString = objectMapper.writeValueAsString(Nomina.builder().build());
        server.expect(requestTo("http://localhost:4567/" + CUIT)).andRespond(withSuccess(detailString, MediaType.APPLICATION_JSON));
        final Nomina expected = NominaMockFactory.buildNomina();
        final Nomina actual = client.searchByCuit(CUIT,JWT_TOKEN);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("when get is called the adapter should return a NotFound Exceptionm")
    void testSearchByCuitReturnBadRequest() {
        server.expect(requestTo("http://localhost:4567/" + CUIT))
                .andRespond(withNoContent());
        final Throwable thrown = Assertions.catchThrowable(() -> client.searchByCuit(CUIT,JWT_TOKEN));
        Assertions.assertThat(thrown).isExactlyInstanceOf(NotFoundException.class)
                .hasMessage(ErrorCode.NOMINA_NOT_FOUND.getReasonPhrase());
    }

}
