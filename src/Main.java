import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {

	Session session;

	public static void main(String[] args) {
		Main main = new Main();
//		main.addNewData();
//		main.printSchools();
//		main.executeQueries();
//		main.updateObject();
		main.close();
	}

	public Main() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void close() {
		session.close();
		HibernateUtil.shutdown();
	}

	private void executeQueries() {
//		 query0();
//		 query1();
//		 query2();
//		 query3();
//		 query4();
//		 query5();
//		 query6();
	}

	private void query0() {
		String hql = "FROM School";
		Query query = session.createQuery(hql);
		List<School> results = query.list();
		System.out.println(results);
	}

	private void query1() {
		String hql = "FROM School S WHERE S.name='UE'";
		Query query = session.createQuery(hql);
		List<School> results = query.list();
		System.out.println(results);
	}

	private void query2() {
		String hql = "FROM School S WHERE S.name='UE'";
		Query query = session.createQuery(hql);
		List<School> results = query.list();
		Transaction transaction = session.beginTransaction();
		for (School s : results) {
			session.delete(s);
		}
		transaction.commit();
	}

	private void query3() {
		String hql = "SELECT COUNT(S) FROM School S";
		Query query = session.createQuery(hql);
		Long schoolsCount = (Long) query.uniqueResult();
		System.out.println("Schools count: " + schoolsCount);
	}

	private void query4() {
		String hql = "SELECT COUNT(S) FROM Student S";
		Query query = session.createQuery(hql);
		Long schoolsCount = (Long) query.uniqueResult();
		System.out.println("Students count: " + schoolsCount);
	}

	private void query5() {
		String hql = "SELECT COUNT(S) FROM School S WHERE size(S.classes)>=2";
		Query query = session.createQuery(hql);
		Long schoolsCount = (Long) query.uniqueResult();
		System.out.println("Schools count: " + schoolsCount);
	}

	private void query6() {
		String hql = "SELECT s FROM School s INNER JOIN s.classes classes WHERE classes.profile = 'mat-fiz' AND classes.currentYear>=2";
		Query query = session.createQuery(hql);
		List<School> results = query.list();
		System.out.println(results);
	}

	private void updateObject() {
		Query query = session.createQuery("from School where id= :id");
		query.setLong("id", 3);
		School school = (School) query.uniqueResult();
		school.setAddress("Nowy adres2");
		Transaction transaction = session.beginTransaction();
		session.save(school);
		transaction.commit();
	}
	
	private void addNewData() {
		School newSchool = new School();
		newSchool.setName("Nowa szko³a");
		newSchool.setAddress("ul. Ulica 0, Miasto");
		
		SchoolClass newClass = new SchoolClass();
		newClass.setProfile("profil");
		newClass.setStartYear(2020);
		newClass.setCurrentYear(1);
		
		Student newStudent = new Student();
		newStudent.setName("Jan");
		newStudent.setSurname("Kowalski");
		newStudent.setPesel("12345678901");
		
		newClass.addStudent(newStudent);
		newSchool.addClass(newClass);
		
		Transaction transaction = session.beginTransaction();
		session.save(newSchool);
		transaction.commit();
	}

	private void printSchools() {
		Criteria crit = session.createCriteria(School.class);
		List<School> schools = crit.list();

		System.out.println("### Schools and classes");
		for (School school : schools) {
			System.out.println(school);
			for (SchoolClass schoolClass : school.getClasses()) {
				System.out.println("    " + schoolClass);
				for (Student student : schoolClass.getStudents()) {
					System.out.print("        " + student);
				}
			}
		}
	}
}
