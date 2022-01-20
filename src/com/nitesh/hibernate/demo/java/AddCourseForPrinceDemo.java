package com.nitesh.hibernate.demo.java;

import com.nitesh.hibernate.demo.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AddCourseForPrinceDemo {

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

            // get student prince from db
            int theId = 3;
            Student tempStudent = session.get(Student.class, theId);
            System.out.println("\nLoaded student : " + tempStudent);
            System.out.println("\nCourses assigned to student : " + tempStudent.getCourses());

            // create more courses
            Course course1 = new Course("How to wake up without sleeping");
            Course course2 = new Course("How to stop without starting");

            // add student prince to those courses
            course1.addStudent(tempStudent);
            course2.addStudent(tempStudent);

            // save the courses
            System.out.println("\nSaving the student ...");
            session.save(course1);
            session.save(course2);

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
