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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@Entity
@XmlRootElement
@Table(name = "s_disciplines")
public class Discipline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "discipline_name", unique = true, nullable = false, length = MAX_DISCIPLINE_NAME_SIZE)
    private String disciplineName;

    //
    @ManyToOne
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;

    @ManyToMany
    @JoinTable(name = "s_disciplines_and_lead_lecturers", joinColumns = @JoinColumn(name = "discipline_id"), inverseJoinColumns = @JoinColumn(name = "lecturer_id"))
    private Set<User> lecturers;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "discipline_id")
    private Set<Assignment> assignments;

    final public static int MIN_DISCIPLINE_NAME_SIZE = 5;
    final public static int MAX_DISCIPLINE_NAME_SIZE = 255;

    public Discipline() {
    }

    public Discipline(String disciplineName, Speciality speciality) {
        this.disciplineName = disciplineName;
        this.speciality = speciality;
    }

    public Long getId() {
        return id;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Set<User> getLecturers() {
        return lecturers;
    }

    public void setLecturers(Set<User> lecturers) {
        this.lecturers = lecturers;
    }
    
    public void addLecturer(User lecturer){
        this.lecturers.add(lecturer);
    }

    public Set<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(Set<Assignment> assignments) {
        this.assignments = assignments;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (this.disciplineName != null ? this.disciplineName.hashCode() : 0);
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
        final Discipline other = (Discipline) obj;
        if ((this.disciplineName == null) ? (other.disciplineName != null) : !this.disciplineName.equals(other.disciplineName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Discipline{" + "id=" + id + ", disciplineName=" + disciplineName + ", speciality=" + speciality + '}';
    }

}
