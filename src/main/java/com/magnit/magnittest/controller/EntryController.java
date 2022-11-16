package com.magnit.magnittest.controller;

import com.magnit.magnittest.service.EntityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Long.parseLong;
import static java.lang.String.valueOf;

@RestController
@RequestMapping("/magnit")
@AllArgsConstructor
public class EntryController {
    private EntityService entityService;

    @GetMapping("/save/{number}")
    public ResponseEntity<?> saveEntityN(@PathVariable String number) {
        try {
            entityService.saveEntityNumberN(parseLong(number));
            return ResponseEntity.ok("entry was saved");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/table", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> findAllEntities() {
        try {
            return new ResponseEntity<>(entityService.findAllEntitiesAndGetXml(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/convert-table", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> findAllEntitiesAndConvert() {
        try {
            return new ResponseEntity<>(entityService.xsltTransform(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sum-fields")
    public ResponseEntity<String> getSumFields() {
        try {
            String sum = valueOf(entityService.getFieldSum());
            return new ResponseEntity<>(sum, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}