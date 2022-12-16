package com.axel.backend.crud.controllers;

import com.axel.backend.crud.models.entity.PositionFiscal;
import com.axel.backend.crud.models.services.IPositionFiscalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class PositionFiscalRestController {

    @Autowired
    private IPositionFiscalService fiscalService;

    @GetMapping("/position")
    public List<PositionFiscal> index(){
        return fiscalService.findAll();
    }

    @GetMapping("/position/page/{page}")
    public Page<PositionFiscal> index(@PathVariable Integer page){
        return fiscalService.findAll(PageRequest.of(page,15,
                Sort.by("year").descending().and(Sort.by("category").descending()).and(Sort.by("amount").descending())));
    }

    @PostMapping("/position")
    public ResponseEntity<?> create(@Valid @RequestBody PositionFiscal positionFiscal, BindingResult result){
        PositionFiscal fiscalNew = null;
        Map<String,Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(fieldError -> "El campo '" + fieldError.getField() + "' " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }

        try {
            fiscalNew = fiscalService.save(positionFiscal);
        }catch (DataAccessException e){
            response.put("mensaje","Error al registrar en la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El registro se ha realizado con exito!");
        response.put("position",fiscalNew);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @PutMapping("/position/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody PositionFiscal positionFiscal, BindingResult result,@PathVariable Long id){
        PositionFiscal fiscalActual=fiscalService.findById(id);
        PositionFiscal fiscalUpdate=null;

        Map<String,Object> response = new HashMap<>();

        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(fieldError -> "El campo '" + fieldError.getField() + "' " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        if(fiscalActual == null){
            response.put("mensaje","Error: no se pudo editar, el registro ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            fiscalActual.setYear(positionFiscal.getYear());
            fiscalActual.setState(positionFiscal.getState());
            fiscalActual.setCategory(positionFiscal.getCategory());
            fiscalActual.setAmount(positionFiscal.getAmount());
            fiscalActual.setItem(positionFiscal.getItem());
            fiscalActual.setPercent(positionFiscal.getPercent());

            fiscalUpdate = fiscalService.save(fiscalActual);

        }catch (DataAccessException e){
            response.put("mensaje","Error al actualizar en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El registro ha sido actualizado con exito!");
        response.put("position",fiscalUpdate);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/position/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();

        try{
            fiscalService.delete(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error al eliminar el registro de la base de datos");
            response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El registro ha sido eliminado con exito!");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }
}
