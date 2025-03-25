package pl.edu.pjatk.MPR_Projekt.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.MPR_Projekt.Model.Unit;
import pl.edu.pjatk.MPR_Projekt.Repository.UnitRepository;

import java.util.List;

@Service
public class UnitService {

    private final UnitRepository unitRepository;

    @Autowired
    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }


    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    public Unit addUnit(Unit unit) {
        return unitRepository.save(unit);
    }

}
