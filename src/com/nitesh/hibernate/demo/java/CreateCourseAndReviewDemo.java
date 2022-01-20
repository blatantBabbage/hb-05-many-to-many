package com.nitesh.hibernate.demo.java;

import com.nitesh.hibernate.demo.entity.Course;
import com.nitesh.hibernate.demo.entity.Instructor;
import com.nitesh.hibernate.demo.entity.InstructorDetail;
import com.nitesh.hibernate.demo.entity.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateCourseAndReviewDemo {

    public static void main(String[] args) {

        //create session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Review.class)
                .buildSessionFactory();

        //create session
        Session session = factory.getCurrentSession();

        try {

            //begin the transaction
            System.out.println("starting transaction");
            session.beginTransaction();

            // crate a course
            Course tempCourse = new Course("Mafia 2 - how to kill the Don!");

            // add reviews for the course
            tempCourse.addReview(new Review("Great tips"));
            tempCourse.addReview(new Review("cool tips"));
            tempCourse.addReview(new Review("what a lame course!"));

            // save course and leverage cascade all to save corresponding reviews
            session.save(tempCourse);
            System.out.println("Saving the course and reviews");
            System.out.println(tempCourse);
            System.out.println(tempCourse.getReviews());

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
