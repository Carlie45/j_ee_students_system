/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j_ee_project.j_ee_students_system.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lightning
 */
@Entity
@XmlRootElement
@Table(name = "s_lecturers_degrees")
public class Degree implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "degree_name", nullable = false, unique = true, length = MAX_DEGREE_NAME_SIZE)
    private String degreeName;
    
    @ManyToMany(mappedBy = "degrees")
    private Set<Lecturer> lecturers;
    
    @Transient
    final public static int MIN_DEGREE_NAME_SIZE = 2;
    final public static int MAX_DEGREE_NAME_SIZE = 255;

    public Degree() {
    }

    public Degree(String degreeName) {
        this.degreeName = degreeName;
    }

    public Long getId() {
        return id;
    }   

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public Set<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(Set<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public void addLecturer(Lecturer lecturer){
        this.lecturers.add(lecturer);
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.degreeName != null ? this.degreeName.hashCode() : 0);
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
        final Degree other = (Degree) obj;
        if ((this.degreeName == null) ? (other.degreeName != null) : !this.degreeName.equals(other.degreeName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Degree{" + "id=" + id + ", degreeName=" + degreeName + '}';
    } 
    
    
}
