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
//		main.printTeachers();
		main.cascadeTest();
		main.close();
	}

	public Main() {
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void close() {
		session.close();
		HibernateUtil.shutdown();
	}
	
	private void cascadeTest() {
//		School newSchool1 = new School();
//		newSchool1.setName("Nowa szko³a 1");
//		newSchool1.setAddress("ul. Ulica 0, Miasto");
//		School newSchool2 = new School();
//		newSchool2.setName("Nowa szko³a 2");
//		newSchool2.setAddress("ul. Ulica 0, Miasto");
//		
//		SchoolClass newClass1 = new SchoolClass();
//		newClass1.setProfile("profil1");
//		newClass1.setStartYear(2020);
//		newClass1.setCurrentYear(1);
//		SchoolClass newClass2 = new SchoolClass();
//		newClass2.setProfile("profil2");
//		newClass2.setStartYear(2020);
//		newClass2.setCurrentYear(1);
//		
//		Student newStudent1 = new Student();
//		newStudent1.setName("Jan");
//		newStudent1.setSurname("Pierwszy");
//		newStudent1.setPesel("12345678901");
//		Student newStudent2 = new Student();
//		newStudent2.setName("Jan");
//		newStudent2.setSurname("Drugi");
//		newStudent2.setPesel("12345678901");
//		
//		newClass1.addStudent(newStudent1);
//		newClass2.addStudent(newStudent2);
//		newSchool1.addClass(newClass1);
//		newSchool2.addClass(newClass2);
//		
//		Teacher newTeacher1 = new Teacher();
//		newTeacher1.setName("Mateusz");
//		newTeacher1.setSurname("Jeden");
//		newTeacher1.setPesel("00000000000");
//		Teacher newTeacher2 = new Teacher();
//		newTeacher2.setName("Mateusz");
//		newTeacher2.setSurname("Dwa");
//		newTeacher2.setPesel("00000000000");
//		
//		newClass1.addTeacher(newTeacher1);
//		newClass1.addTeacher(newTeacher2);
//		newClass2.addTeacher(newTeacher1);
//		newClass2.addTeacher(newTeacher2);
//		newTeacher1.addClass(newClass1);
//		newTeacher1.addClass(newClass2);
//		newTeacher2.addClass(newClass1);
//		newTeacher2.addClass(newClass2);
//		
//		Transaction transaction = session.beginTransaction();
//		session.save(newSchool1);
//		session.save(newSchool2);
//		session.save(newTeacher1);
//		session.save(newTeacher2);
//		transaction.commit();
		
		String hql = "FROM SchoolClass C WHERE C.id=7";
		Query query = session.createQuery(hql);
		SchoolClass schoolClass = (SchoolClass) query.uniqueResult();
				
		Transaction transaction = session.beginTransaction();
		for(Teacher teacher : schoolClass.getTeachers()) {
			teacher.removeClass(schoolClass);
			session.save(teacher);
		}
		session.delete(schoolClass);
		transaction.commit();
	}
	
	private void printTeachers() {
		Criteria crit = session.createCriteria(Teacher.class);
		List<Teacher> teachers = crit.list();

		System.out.println("### Teachers");
		for (Teacher teacher : teachers) {
			System.out.println(teacher);
			for (SchoolClass schoolClass : teacher.getClasses()) {
				System.out.println("    " + schoolClass);
			}
		}
		
		Criteria crit2 = session.createCriteria(SchoolClass.class);
		List<SchoolClass> schoolClasses = crit2.list();

		System.out.println("### Classes");
		for (SchoolClass schoolClass : schoolClasses) {
			System.out.println(schoolClass);
			for (Teacher teacher : schoolClass.getTeachers()) {
				System.out.println("    " + teacher);
			}
		}
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
