package in.strikes.crudSpringBootDemo.Controller;

import in.strikes.crudSpringBootDemo.Entity.Student;
import in.strikes.crudSpringBootDemo.Service.StudentService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {

        Student s1 = studentService.createStudent(student);

        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(s1);
    }

    //Get one student
    @GetMapping("/get")
    public ResponseEntity<Student> getStudent(@RequestParam Long id) {

        Student StudentData = studentService.getStudent(id);

        if (StudentData == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(StudentData);
    }

    //Get All student
    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getStudent() {

        List<Student> AllStudentData = studentService.getAllStudent();

        if (AllStudentData.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(AllStudentData);
    }

    //Update any student from id
    @PutMapping("/update")
    public ResponseEntity<Student> updateStudent(@RequestParam Long id, @RequestBody Student studentreq) {

        Student StudentData = studentService.updateStudent(id,  studentreq);

        if (StudentData == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(StudentData);
    }

    //Delete one student
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestParam Long id) {

        boolean exists = studentService.deleteStudent(id);

        if(!exists){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Deleted sucessfully");
    }

    @PatchMapping("/soft-delete")
    public ResponseEntity<String> softDeleteStudent(@RequestParam Long id) {
        boolean delete = studentService.softDeleteStudent(id);

        if (!delete){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Deleted sucessfully");

    }

}
