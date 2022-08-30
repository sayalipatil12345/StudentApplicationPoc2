package com.springboot.jwt.service;

import com.springboot.jwt.dao.ProjectDao;
import com.springboot.jwt.dao.StudentDao;
import com.springboot.jwt.entity.Project;
import com.springboot.jwt.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Project adminRole = new Project();
        adminRole.setRoleName("Admin");
        adminRole.setProjectId("111");
        adminRole.setProjectName("java");
        adminRole.setProjectDescription("java coding");
        
        projectDao.save(adminRole);

        Project userRole = new Project();
        userRole.setRoleName("User");
        userRole.setProjectId("222");
        userRole.setProjectName("c++");
        userRole.setProjectDescription("c++ coding");
        projectDao.save(userRole);

        Student adminUser = new Student();
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        adminUser.setUserFirstName("pooja");
        adminUser.setUserLastName("patil");
        adminUser.setEmailId("pooja@gmail.com");
        adminUser.setMobNo("12345679");
        Set<Project> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setProject(adminRoles);
        studentDao.save(adminUser);

    }

    public Student registerNewStudent(Student user) {
        Project role = projectDao.findById("User").get();
        Set<Project> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setProject(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return studentDao.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

	public List<Student> getAllStudents() {
			List<Student> studentList= this.studentDao.findAll();
			return studentList;
		}

	public Student getStudent(String studentId) {
			Student foundStudent=this.studentDao.findById(studentId).orElse(null);
			return foundStudent;
		}
	}