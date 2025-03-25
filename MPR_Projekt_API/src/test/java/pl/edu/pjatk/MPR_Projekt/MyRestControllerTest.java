//package pl.edu.pjatk.MPR_Projekt;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//import pl.edu.pjatk.MPR_Projekt.Controller.MyRestController;
//import pl.edu.pjatk.MPR_Projekt.Model.Piesek;
//import pl.edu.pjatk.MPR_Projekt.Service.PiesekService;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//
//public class MyRestControllerTest {
//
//    @InjectMocks
//    private MyRestController myRestController;
//
//    @Mock
//    private PiesekService piesekService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testGetAll() {
//        when(piesekService.getPiesekList()).thenReturn(List.of(new Piesek("brown", "Max")));
//
//        ResponseEntity<List<Piesek>> response = myRestController.getAll();
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//        assertEquals(1, response.getBody().size());
//        assertEquals("Max", response.getBody().get(0).getName());
//        verify(piesekService, times(1)).getPiesekList();
//    }
//
//    @Test
//    public void testGetById() {
//        Piesek piesek = new Piesek("brown", "Max");
//        when(piesekService.getById(1)).thenReturn(piesek);
//
//        ResponseEntity<Piesek> response = myRestController.getById(1);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//        assertEquals("Max", response.getBody().getName());
//        verify(piesekService, times(1)).getById(1);
//    }
//
//    @Test
//    public void testGetByName() {
//        when(piesekService.getPiesekByName("Max")).thenReturn(List.of(new Piesek("brown", "Max")));
//
//        ResponseEntity<List<Piesek>> response = myRestController.getByName("Max");
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertNotNull(response.getBody());
//        assertEquals(1, response.getBody().size());
//        assertEquals("Max", response.getBody().get(0).getName());
//        verify(piesekService, times(1)).getPiesekByName("Max");
//    }
//
//    @Test
//    public void testCreate() {
//        Piesek piesek = new Piesek("brown", "Max");
//
//        ResponseEntity<Piesek> response = myRestController.create(piesek);
//
//        assertEquals(201, response.getStatusCodeValue());
//        verify(piesekService, times(1)).createPiesek(piesek);
//    }
//
//    @Test
//    public void testDeleteById() {
//        doNothing().when(piesekService).deletePiesekById(1);
//
//        ResponseEntity<Piesek> response = myRestController.delete(1);
//
//        assertEquals(200, response.getStatusCodeValue());
//        verify(piesekService, times(1)).deletePiesekById(1);
//    }
//
//    @Test
//    public void testDeleteByName() {
//        doNothing().when(piesekService).deletePiesekByName("Max");
//
//        ResponseEntity<Piesek> response = myRestController.delete("Max");
//
//        assertEquals(200, response.getStatusCodeValue());
//        verify(piesekService, times(1)).deletePiesekByName("Max");
//    }
//
//    @Test
//    public void testUpdate() {
//        Piesek updatedPiesek = new Piesek("white", "Spike");
//        doNothing().when(piesekService).updatePiesek(1, updatedPiesek);
//
//        ResponseEntity<Piesek> response = myRestController.update(1, updatedPiesek);
//
//        assertEquals(200, response.getStatusCodeValue());
//        verify(piesekService, times(1)).updatePiesek(1, updatedPiesek);
//    }
//}