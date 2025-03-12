package com.bff.nomina.nomina.adapter.controller;

import com.bff.nomina.nomina.application.port.in.SearchNominaQuery;
import com.bff.nomina.nomina.config.TestConfig;
import com.bff.nomina.nomina.domain.Nomina;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Nomina Controller Adapter Test")
@WebMvcTest(NominaController.class)
@Import({TestConfig.class})
class NominaControllerAdapterTest {
    private static final String CUIT = "20-36972497-3";
    private static final String JWT_TOKEN = "random token";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchNominaQuery searchNominaQuery;

    @Test
    @DisplayName("when search is called should respond with nomina model for view")
    void getAuthenticatedNominaByCuitSuccessfully() throws Exception {

        Mockito.when(searchNominaQuery.getByCuit(CUIT, JWT_TOKEN))
                .thenReturn(CompletableFuture.completedFuture(Optional.of(Nomina.builder().build())));
        final MvcResult result = mockMvc.perform(get("/search").param("cuit", CUIT).param("jwt", JWT_TOKEN)).andReturn();
        mockMvc.perform(asyncDispatch(result))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("nomina"))
                .andExpect(view().name("index"));

    }

    @Test
    @DisplayName("when search is called without jwt should return a Bad Request")
    void getAuthenticatedNominaWithoutJwtReturnBadRequest() throws Exception {
        final String expected = "{ \"name\": \"Bad Request\", " +
                "\"description\": \"Parameter jwt of type String is required\", " +
                "\"code\": \"400:101\" }";
        Mockito.when(searchNominaQuery.getByCuit(CUIT, JWT_TOKEN))
                .thenReturn(CompletableFuture.completedFuture(Optional.of(Nomina.builder().build())));

        mockMvc.perform(get("/search").param("cuit", CUIT))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expected));
    }

    @Test
    @DisplayName("when search is called without cuit should return a Bad Request")
    void getAuthenticatedNominaWithoutCuitReturnBadRequest() throws Exception {
        final String expected = "{ \"name\": \"Bad Request\", " +
                "\"description\": \"Parameter cuit of type String is required\", " +
                "\"code\": \"400:101\" }";
        Mockito.when(searchNominaQuery.getByCuit(CUIT, JWT_TOKEN))
                .thenReturn(CompletableFuture.completedFuture(Optional.of(Nomina.builder().build())));

        mockMvc.perform(get("/search").param("jwt", JWT_TOKEN))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expected));
    }
}
