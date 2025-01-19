package com.deploy.aws.services;

import com.deploy.aws.dto.StudentDTO;
import com.deploy.aws.entities.StudentEntity;
import com.deploy.aws.exceptions.ResponseNotFoundException;
import com.deploy.aws.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;
    // ModelMapper class is used to convert StudentEntity into StudentDTO

    public StudentDTO getStudentById(Long id){
        log.info("Getting student by id : {}", id);
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Student not found with id : {}",id);
                    return new ResponseNotFoundException("Student not found with id : " + id);
                });
        //return new StudentDTO(studentEntity.getName(),studentEntity.getAddress(),studentEntity.getId(),studentEntity.getIsPassed(),studentEntity.getResultDate());

        log.info("Successfully fetched student by id : {}", id);
        return  modelMapper.map(studentEntity ,StudentDTO.class);
    }

    public StudentDTO insertStudent(StudentDTO studentDTO){
        // Here we perform different operations such as
        // log in...
        log.info("Inserting new student : {}", studentDTO);

        List<StudentEntity> studentsList = studentRepository.findByEmail(studentDTO.getEmail());
        if(!studentsList.isEmpty()){
            log.error("Student already exists with email : {}", studentDTO.getEmail());
            throw new ResponseNotFoundException("Student already exists with email :" + studentDTO.getEmail());
        }

        StudentEntity toSaveEntity = modelMapper.map(studentDTO,StudentEntity.class);
        studentRepository.save(toSaveEntity);
        log.info("Successfully inserted student with id : {}", toSaveEntity.getId());
        return modelMapper.map(toSaveEntity,StudentDTO.class);
    }

    public List<StudentDTO> getAllStudents(){
        log.info("Getting all students...");
        List<StudentEntity> allStudents = studentRepository.findAll();

        log.info("Successfully fetched all students");
        return allStudents
                .stream()
                .map(studentEntity -> modelMapper.map(studentEntity,StudentDTO.class))
                .collect(Collectors.toList());
    }

    public void isStudentExistsById(Long id){
        boolean isExists = studentRepository.existsById(id);
        if(!isExists){
            log.error("Student not found with id : {}", id);
            throw new ResponseNotFoundException("Student not found with id : " + id);
        }
    }

    public StudentDTO updateStudentById(StudentDTO studentDTO, Long id){
        isStudentExistsById(id);
        log.info("Updating existing student with id : {}", id);
        StudentEntity studentEntity = modelMapper.map(studentDTO, StudentEntity.class);
        studentEntity.setId(id);

        log.info("Successfully updated existing student with id : {}", id);
        return modelMapper.map(studentRepository.save(studentEntity), StudentDTO.class);
    }

    public StudentDTO partiallyUpdateStudentById(Map<String, Object> updates, Long id){
        isStudentExistsById(id);
        log.info("Partially Updating existing student with id : {}", id);
        StudentEntity studentEntity = studentRepository.findById(id).get(); // Not null
        updates.forEach((field,value) -> {
            Field fieldToBeUpdate =  ReflectionUtils.findField( StudentEntity.class, field);
            if(fieldToBeUpdate != null){
                fieldToBeUpdate.setAccessible(true);
                ReflectionUtils.setField(fieldToBeUpdate,studentEntity,value);
            }
        });

        log.info("Successfully partially updated student with id : {}", id);
        return modelMapper.map(studentRepository.save(studentEntity), StudentDTO.class);
    }

    public Boolean deleteStudentById(Long id){
        isStudentExistsById(id);
        studentRepository.deleteById(id);
        log.info("Successfully deleted student with id : {}", id);
        return true;
    }

    public List<StudentDTO> findByEmail(String email){
        log.info("Fetching all students with email : {}", email);
        return studentRepository.findByEmail(email).stream()
                .map(studentEntity -> modelMapper.map(studentEntity, StudentDTO.class))
                .toList();
    }
}
