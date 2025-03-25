//package pl.edu.pjatk.MPR_Projekt;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.core.io.ByteArrayResource;
//import pl.edu.pjatk.MPR_Projekt.Model.Piesek;
//import pl.edu.pjatk.MPR_Projekt.Repository.PiesekRepository;
//import pl.edu.pjatk.MPR_Projekt.Service.PiesekService;
//import pl.edu.pjatk.MPR_Projekt.Service.StringUtilsService;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//
//public class PiesekServiceTest {
//
//    @InjectMocks
//    private PiesekService piesekService;
//
//    @Mock
//    private StringUtilsService stringUtilsService;
//
//    @Mock
//    private PiesekRepository piesekRepository;
//
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        this.piesekService = new PiesekService(piesekRepository, stringUtilsService);
//    }
//
//
//
//    @Test
//    public void testGetPiesekByName(){
//        when(piesekRepository.findByName("Max")).thenReturn(List.of(new Piesek("brown","Max" )));
//
//        List<Piesek> pieski = piesekService.getPiesekByName("Max");
//
//        assertEquals(1, pieski.size());
//        assertEquals("Max", pieski.get(0).getName());
//    }
//
//    @Test
//    public void testGetPiesekList(){
//        when(piesekRepository.findAll()).thenReturn(List.of(new Piesek("brown", "Spike")));
//
//        List<Piesek> piesekList = piesekService.getPiesekList();
//
//        assertEquals(1,piesekList.size());
//        assertEquals("Spike", piesekList.get(0).getName());
//        verify(piesekRepository, times(1)).findAll();
//    }
//
//
//    @Test
//    public void testCreatePiesek(){
//        Piesek piesek = new Piesek("brown","Bobby");
//        when(stringUtilsService.bigFirstLetter(any())).thenReturn("Bobby");
//        piesekService.createPiesek(piesek);
//        verify(stringUtilsService, times(1)).bigFirstLetter("Bobby");
//        verify(piesekRepository, times(7)).save(any(Piesek.class));
//    }
//    @Test
//    public void testDeletePiesekById(){
//        Piesek piesek = new Piesek("brown", "Max");
//        when(piesekRepository.findById(1)).thenReturn(Optional.of(piesek));
//        piesekService.deletePiesekById(1);
//        verify(piesekRepository, times(1)).deleteById(1);
//    }
//
//    @Test
//    public void testDeletePiesekByName(){
//        Piesek piesek = new Piesek("brown", "Max");
//        when(piesekRepository.findByName(any())).thenReturn(List.of(piesek));
//        piesekService.deletePiesekByName("Max");
//        verify(piesekRepository, times(1)).delete(piesek);
//    }
//
//    @Test
//    public void testGetById() {
//        Piesek piesek = new Piesek("brown", "Max");
//        when(piesekRepository.findById(1)).thenReturn(Optional.of(piesek));
//
//
//        Piesek foundPiesek = piesekService.getById(1);
//        assertNotNull(foundPiesek);
//        assertEquals("Max", foundPiesek.getName());
//
//        verify(piesekRepository, times(1)).findById(1);
//    }
//
//    @Test
//    public void testUpdatePiesek() {
//        Piesek existingPiesek = new Piesek("brown", "Spike");
//        Piesek updatedPiesek = new Piesek("white", "Spike");
//        when(piesekRepository.findById(1)).thenReturn(Optional.of(existingPiesek));
//
//        piesekService.updatePiesek(1, updatedPiesek);
//
//        verify(piesekRepository, times(1)).save(existingPiesek);
//        assertEquals("white", existingPiesek.getColor());
//    }
//
//}
