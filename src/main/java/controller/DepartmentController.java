package controller;

import model.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import java.util.List;


public class DepartmentController {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            //Perform CRUD operations
            //addDepartment(session);
            //findDepartment(session, 2);
            //updateDepartment(session, 3);
            //deleteDepartment(session, 4);
            //findDepartmentHql(sessionFactory, session);
            //findDepartmentHql2(sessionFactory, session);
            //getRecordById(sessionFactory,session);
            //getRecords(session);
            //getMaxId(session);
            //getCountIdGroupBy(session);
            namedQueryExample(session);
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
    public static void findDepartmentHql(SessionFactory factory,Session session) {
        String hqlFrom = "FROM Department"; // Example of HQL to get all records of user class
        String hqlSelect = "SELECT d FROM Department d";
        TypedQuery<Department> query = session.createQuery(hqlFrom, Department.class);
        List<Department> results = query.getResultList();

        System.out.printf("%s%5s%15s%n","|Department Id","|Name","|State|");
        for (Department d:results) {
            System.out.printf(" %-13d %-13s %-30s %n", d.getId(), d.getName(), d.getState());
        }
    }

    public static void findDepartmentHql2(SessionFactory factory,Session session) {
        String hqlFrom = "FROM Department"; // Example of HQL to get all records of user class
        String hqlSelect = "SELECT d FROM Department d";
        TypedQuery<Department> query = session.createQuery(hqlSelect, Department.class);
        List<Department> results = query.getResultList();

        System.out.printf("%s%5s%15s%n","|Department Id","|Name","|State|");
        for (Department d:results) {
            System.out.printf(" %-13d %-13s %-30s %n", d.getId(), d.getName(), d.getState());
        }
    }

    public static void getRecordById(SessionFactory factory, Session session) {
        String hql = "FROM Department d WHERE d.id > 2 ORDER BY d.state DESC";
        TypedQuery<Department> query = session.createQuery(hql, Department.class);
        List<Department> results = query.getResultList();
        System.out.printf("%s%5s%15s%n","|Department Id","|Name","|State|");
        for (Department d : results) {
            System.out.printf(" %-13d %-13s %-30s %n", d.getId(), d.getName(), d.getState());
        }
    }
    public static void getRecords (Session session) {
        TypedQuery<Object[]> query = session.createQuery(
                "SELECT D.state, D.name FROM Department AS D", Object[].class);
        List<Object[]> results = query.getResultList();
        System.out.printf("%s%13s%n","State","Name");
        for (Object[] a : results) {
            System.out.printf("%-16s%s%n",a[0],a[1]);
        }
    }
    public static void getMaxId(Session session) {
        String hql = "SELECT max(D.id) FROM Department D";
        TypedQuery<Object> query = session.createQuery(hql,Object.class);
        Object result = query.getSingleResult();
        System.out.printf("%s%s","Maximum Id:",result);
    }
    public static void getCountIdGroupBy(Session session)
    {
        String hql = "SELECT count (D.id), D.state FROM Department D GROUP BY D.state";
        TypedQuery query = session.createQuery(hql);
        List<Object[]> result =query.getResultList();
        for (Object[] o : result) {
            System.out.println("Id count " +o[0] +" | state: "+ o[1] );
        }
    }
    public static void namedQueryExample(Session session) {
        String hql = "FROM Department d WHERE d.id = :id";
        TypedQuery<Department> query = session.createQuery(hql, Department.class);
        query.setParameter("id", 2);
        List<Department> result = query.getResultList();

        System.out.printf("%s%5s%15s%n","|Department Id","|Name","|State|");
        for (Department d : result) {
            System.out.printf(" %-13d %-13s %-30s %n", d.getId(), d.getName(), d.getState());
        }
    }

}
