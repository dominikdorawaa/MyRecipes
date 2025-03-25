package pl.edu.pjatk.MPR_Projekt.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjatk.MPR_Projekt.Model.Unit;
import pl.edu.pjatk.MPR_Projekt.Service.UnitService;

import java.util.List;

@RestController
public class UnitController {


    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }


    @GetMapping("unit/all")
    public ResponseEntity <List<Unit>> getAllUnits() {
        List<Unit> units = unitService.getAllUnits();
        return new ResponseEntity<>(units, HttpStatus.OK);
    }

    @PostMapping("unit/add")
    public ResponseEntity<Unit> addUnit(@RequestBody Unit unit) {
        Unit addedUnit = unitService.addUnit(unit);
        return new ResponseEntity<>(addedUnit, HttpStatus.CREATED);
    }
}
