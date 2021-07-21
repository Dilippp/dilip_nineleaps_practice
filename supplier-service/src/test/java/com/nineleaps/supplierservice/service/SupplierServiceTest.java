package com.nineleaps.supplierservice.service;

import com.nineleaps.supplierservice.exception.ResourceNotFoundException;
import com.nineleaps.supplierservice.exception.ServiceException;
import com.nineleaps.supplierservice.model.Supplier;
import com.nineleaps.supplierservice.repository.SupplierRepository;
import com.nineleaps.supplierservice.service.impl.SupplierServiceImpl;
import com.nineleaps.supplierservice.utilities.CommonUtil;
import com.nineleaps.supplierservice.utilities.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@ExtendWith(MockitoExtension.class)
public class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierServiceImpl supplierService;

    private Supplier supplier;

    @BeforeEach
    public void setUp() {
        String string = CommonUtil.convertInputStreamToString(getClass().getResourceAsStream("/supplier.json"));
        supplier = JsonUtils.deserialize(string, Supplier.class);
    }

    @Test
    public void getProductByIdTest() {
        //Given
        when(supplierRepository.findById(any())).thenReturn(Optional.of(supplier));
        //When
        Supplier supplier = supplierService.getSupplier(any());
        //Then
        assertThat(supplier).isNotNull();
        assertThat(supplier.getSupplierId()).isInstanceOf(UUID.class);
        verify(supplierRepository, times(1)).findById(any());
    }

    @Test
    public void getProductByIdThrowExceptionTest() {
        //Given
        when(supplierRepository.findById(any())).thenReturn(Optional.empty());
        //When
        ResourceNotFoundException notFoundException = assertThrows(ResourceNotFoundException.class, () -> supplierService.getSupplier(any()));
        //Then
        assertThat(notFoundException).hasMessage("Supplier not found in the database with id: null");
        verify(supplierRepository, times(1)).findById(any());
    }

    @Test
    public void saveOrUpdateProductTest() {
        //Given
        when(supplierRepository.save(any())).thenReturn(supplier);
        //When
        Supplier supplier = supplierService.saveOrUpdateSupplier(any());
        //Then
        assertThat(supplier).isNotNull();
        assertThat(supplier.getSupplierId()).isInstanceOf(UUID.class);
        verify(supplierRepository, times(1)).save(any());
    }

    @Test
    public void saveOrUpdateProductThrowExceptionTest() {
        //Given
        when(supplierRepository.save(any())).thenThrow(ServiceException.class);
        //When
        ServiceException violationException = assertThrows(ServiceException.class, () -> supplierService.saveOrUpdateSupplier(any()));
        //Then
        assertThat(violationException).hasMessage("Error while persisting or updating data");
        verify(supplierRepository, times(1)).save(any());
    }

    @Test
    public void deleteProductTest() {
        //Given
        when(supplierRepository.findById(any())).thenReturn(Optional.of(supplier));
        doNothing().when(supplierRepository).deleteById(any());
        //When
        supplierService.deleteSupplier(any());
        //Then
        verify(supplierRepository, times(1)).findById(any());
        verify(supplierRepository, times(1)).deleteById(any());
    }

    @Test()
    public void deleteProductThrowExceptionTest() {
        //Given
        when(supplierRepository.findById(any())).thenReturn(Optional.empty());
        //When
        ResourceNotFoundException notFoundException = assertThrows(ResourceNotFoundException.class, () -> supplierService.deleteSupplier(any()));
        //Then
        assertThat(notFoundException).hasMessage("No record found to delete for supplier id: " + "null");
    }
}
