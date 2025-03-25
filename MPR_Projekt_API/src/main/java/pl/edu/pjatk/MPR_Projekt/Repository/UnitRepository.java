package pl.edu.pjatk.MPR_Projekt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjatk.MPR_Projekt.Model.Unit;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    List<Unit> findAll();
}

