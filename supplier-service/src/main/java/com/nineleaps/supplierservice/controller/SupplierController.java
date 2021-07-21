package com.nineleaps.supplierservice.controller;

import com.nineleaps.supplierservice.model.Supplier;
import com.nineleaps.supplierservice.service.SupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.nineleaps.supplierservice.utilities.AppConstant.BASE_URI;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@RestController
@RequestMapping(value = BASE_URI + "/suppliers")
@Api(value = "Supplier resource rest endpoint", description = "Shows the supplier information")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @ApiOperation(value = "Get supplier from the system")
    @GetMapping(value = "/{supplierId}")
    public Supplier getSupplier(@PathVariable final UUID supplierId) {
        return supplierService.getSupplier(supplierId);
    }

    @ApiOperation(value = "Save supplier")
    @PostMapping(value = "/")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Supplier persistSupplier(@Valid @RequestBody final Supplier supplier) {
        return supplierService.saveOrUpdateSupplier(supplier);
    }

    @ApiOperation(value = "Update supplier")
    @PutMapping(value = "/")
    @ResponseStatus(code = HttpStatus.OK)
    public Supplier updateSupplier(@Valid @RequestBody final Supplier supplier) {
        return supplierService.saveOrUpdateSupplier(supplier);
    }

    @ApiOperation(value = "Delete supplier from the system")
    @DeleteMapping(value = "/{supplierId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteSupplier(@PathVariable final UUID supplierId) {
        supplierService.deleteSupplier(supplierId);
    }
}
