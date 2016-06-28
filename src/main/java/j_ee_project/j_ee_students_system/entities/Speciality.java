/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j_ee_project.j_ee_students_system.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@Entity
@XmlRootElement
@Table(name = "s_specialities")
public class Speciality implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    
    @Column(name = "speciality_name", unique = true, length = MAX_SPECIALITY_NAME_SIZE )
    private String specialityName;
    
   
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "s_specialities_and_students_enrolled", joinColumns = @JoinColumn(name = "speciality_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="speciality_id")
    private Set<Discipline> disciplines;
 
    @Transient
    final public static int MAX_SPECIALITY_NAME_SIZE = 255;
    final public static int MIN_SPECIALITY_NAME_SIZE = 5;
    
    public Speciality() {
    }

    public Speciality(String specialityName) {
        this.specialityName = specialityName;
    }

    public Long getId() {
        return id;
    }
  
    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
    
    public void addStudent(Student student){
        this.students.add(student);
    }

    public Set<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
        this.disciplines = disciplines;
    }
    
    public void addDiscipline(Discipline discipline){
        this.disciplines.add(discipline);
    }
    


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.specialityName != null ? this.specialityName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Speciality other = (Speciality) obj;
        if ((this.specialityName == null) ? (other.specialityName != null) : !this.specialityName.equals(other.specialityName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StudentSpeciality{" + "id=" + id + ", specialityName=" + specialityName + '}';
    }
    
    
}
