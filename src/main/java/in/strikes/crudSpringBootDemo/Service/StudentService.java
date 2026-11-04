package in.strikes.crudSpringBootDemo.Service;


import in.strikes.crudSpringBootDemo.Entity.Student;
import in.strikes.crudSpringBootDemo.Repository.StudentRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Create
    public Student createStudent(Student studentreq) {
        studentreq.setDeleted(false);

        Student s1 = studentRepository.save(studentreq);

        return s1;

    }

    // Get one from id
    public Student getStudent(Long id) {

        Optional<Student> studentData = studentRepository.findByIdAndDeletedIsFalse(id);

        if (studentData.isPresent()) {
            return studentData.get();
        }

        return null;
    }

    // Get All student in List Fromat
    public List<Student> getAllStudent() {

        List<Student> studentList = studentRepository.findByDeletedIsFalse();

        return studentList;
    }

    // Update one imformatipn from id
    public Student updateStudent(Long id, Student studentreq) {

        Optional<Student> studentDataExist = studentRepository.findByIdAndDeletedIsFalse(id);

        if(studentDataExist.isEmpty()) {
            return null;
        }

        Student studentToUpdate = studentDataExist.get();

        studentToUpdate.setName(studentreq.getName());
        studentToUpdate.setAge(studentreq.getAge());
        studentToUpdate.setEmail(studentreq.getEmail());
        studentToUpdate.setSubject(studentreq.getSubject());
        studentToUpdate.setRollno(studentreq.getRollno());


        return  studentRepository.save(studentToUpdate);
    }

    // Delete one student from database
    public boolean deleteStudent(Long id) {

        boolean studentData = studentRepository.existsById(id);

        if (!studentData) {
            return false;
        }

        studentRepository.deleteById(id);

        return true;

    }

    public boolean softDeleteStudent(Long id) {
        Optional<Student> existsStudent = studentRepository.findByIdAndDeletedIsFalse(id);

        if (existsStudent.isEmpty()) {
            return false;
        }

        Student studentToSave = existsStudent.get();
        studentToSave.setDeleted(true);

        studentRepository.save(studentToSave);
        return true;
    }
}
