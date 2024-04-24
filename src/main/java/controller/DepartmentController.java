package controller;

import model.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;


public class DepartmentController {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            //Perform CRUD operations
            addDepartment(session);
            //findDepartment(session, 2);
            //updateDepartment(session, 3);
            //deleteDepartment(session, 4);
        } finally {
            session.close();
            sessionFactory.close();
        }
    }
    public static void addDepartment(Session session) {
        Transaction transaction = session.beginTransaction();
        try {
            Department department1 = new Department(1,"Moh Haseeb",  "IA");
            Department department2 = new Department(2,"James Santana",  "WA");
            Department department3 = new Department(3,"AH Shahparan",  "AZ");
            Department department4 = new Department(4,"Christ",  "NJ");
            Department department5 = new Department(5, "Sid",  "CA");

            session.persist(department1);
            session.persist(department2);
            session.persist(department3);
            session.persist(department4);
            session.persist(department5);

            transaction.commit();
            System.out.println("Departments added successfully");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


    public static void findDepartment(Session session, int departmentId) {
        Transaction transaction = session.beginTransaction();
        try {
            Department department = session.get(Department.class, departmentId);
            if (department != null) {
                System.out.println("Department found: " + department.getName());
                System.out.println(department.getClass().getName());
            } else {
                System.out.println("No department found with ID: " + departmentId);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


    public static void updateDepartment(Session session, int departmentId) {
        Transaction transaction = session.beginTransaction();
        try {
            Department department = session.get(Department.class,  departmentId);
            if (department != null) {
                department.setName("Updated Name");
                department.setState("TX");
                session.update(department);
                transaction.commit();
                System.out.println("Department updated successfully");
            } else {
                System.out.println("No department found to update.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public static void deleteDepartment(Session session, int departmentId) {
        Transaction transaction = session.beginTransaction();
        try {
            Department department = session.get(Department.class, departmentId);
            if (department != null) {
                session.delete(department);
                transaction.commit();
                System.out.println("Department deleted successfully.");
            } else {
                System.out.println("No department found to delete.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }



}
