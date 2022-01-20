package com.nitesh.hibernate.demo.java;

import com.nitesh.hibernate.demo.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateCourseAndStudentDemo {

    public static void main(String[] args) {

        //create session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Review.class)
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        //create session
        Session session = factory.getCurrentSession();

        try {

            //begin the transaction
            System.out.println("starting transaction");
            session.beginTransaction();

            // crate a course
            Course tempCourse = new Course("Mafia 2 - how to kill the Don!");

            // save the course
            session.save(tempCourse);
            System.out.println("Saving the course : " + tempCourse);


            // crate student
            Student tempStudent1 = new Student("nitesh", "singh", "code@spring.com");
            Student tempStudent2 = new Student("prince", "kumar", "code@mahindra.com");

            // add students for the course
            tempCourse.addStudent(tempStudent1);
            tempCourse.addStudent(tempStudent2);

            // save students and leverage cascade all to save corresponding courses
            session.save(tempStudent1);
            session.save(tempStudent2);
            System.out.println("Saving the students and courses : " +tempCourse.getStudents());

            //commit transaction
            System.out.println("committing to the database");
            session.getTransaction().commit();

            System.out.println("done");

        }
        // handle exception
         catch (Exception exc) {
            exc.printStackTrace();
         }
        finally {

            // clean up code
            session.close();
            factory.close();
        }
    }
}
