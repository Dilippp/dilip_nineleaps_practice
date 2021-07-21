package com.nineleaps.supplierservice.controller;

import com.nineleaps.supplierservice.model.Supplier;
import com.nineleaps.supplierservice.service.SupplierService;
import com.nineleaps.supplierservice.utilities.AppConstant;
import com.nineleaps.supplierservice.utilities.CommonUtil;
import com.nineleaps.supplierservice.utilities.JsonUtils;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@ExtendWith(MockitoExtension.class)
class SupplierControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private SupplierController supplierController;

    private String supplierString;

    private Supplier supplier;

    private String supplierStringFail;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(supplierController).build();
        supplierString = CommonUtil.convertInputStreamToString(getClass().getResourceAsStream("/supplier.json"));
        supplier = JsonUtils.deserialize(supplierString, Supplier.class);
        supplierStringFail = CommonUtil.convertInputStreamToString(getClass().getResourceAsStream("/supplier_error.json"));
    }

    @Test
    public void getSupplierTest() throws Exception {
        //Given
        Supplier supplier = new Supplier();
        supplier.setEmail("nineleaps@mail.com");
        supplier.setName("KV");
        when(supplierService.getSupplier(any())).thenReturn(supplier);
        //When
        MvcResult mvcResult = mockMvc.perform(get(AppConstant.BASE_URI + "/suppliers/{supplierId}", UUID.randomUUID())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("KV")))
                .andExpect(jsonPath("$.email", Matchers.is("nineleaps@mail.com"))).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(JsonUtils.deserialize(content, Supplier.class).getSupplierId()).isInstanceOf(UUID.class);
        verify(supplierService, times(1)).getSupplier(any());
        verifyNoMoreInteractions(supplierService);
    }

    @Test
    public void shouldSaveOrUpdateSupplierPassTest() throws Exception {
        //Given
        when(supplierService.saveOrUpdateSupplier(any())).thenReturn(new Supplier());
        //When
        MvcResult mvcResult = mockMvc.perform(post(AppConstant.BASE_URI + "/suppliers/")
                .content(supplierString)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Supplier order = JsonUtils.deserialize(content, Supplier.class);
        //Then
        Assertions.assertThat(order).isNotNull();
        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        verify(supplierService, times(1)).saveOrUpdateSupplier(any());
    }

    @Test
    public void shouldThrowExceptionSaveOrUpdateSupplierTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(AppConstant.BASE_URI + "/suppliers/")
                .content(supplierStringFail)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertThat(status).isEqualTo(400);
    }

    @Test
    public void saveOrUpdateSupplierTest() throws Exception {
        //Given
        when(supplierService.saveOrUpdateSupplier(any())).thenReturn(supplier);
        //When
        MvcResult mvcResult = mockMvc.perform(put(AppConstant.BASE_URI + "/suppliers/")
                .content(supplierString)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Supplier supplier = JsonUtils.deserialize(content, Supplier.class);
        //Then
        Assertions.assertThat(supplier).isNotNull();
        Assertions.assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(supplierService, times(1)).saveOrUpdateSupplier(any());
        verifyNoMoreInteractions(supplierService);
    }

    @Test
    public void saveOrUpdateSupplierExceptionTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put(AppConstant.BASE_URI + "/suppliers/")
                .content(supplierStringFail)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assertions.assertThat(status).isEqualTo(400);
        verifyNoMoreInteractions(supplierService);
    }

    @Test
    public void deleteSupplierTest() throws Exception {
        //Given
        doNothing().when(supplierService).deleteSupplier(any());
        //When
        MvcResult mvcResult = mockMvc.perform(delete(AppConstant.BASE_URI + "/suppliers/{supplierId}", UUID.randomUUID())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(204);
        verify(supplierService, times(1)).deleteSupplier(any());
        verifyNoMoreInteractions(supplierService);
    }
}
