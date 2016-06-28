/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j_ee_project.j_ee_students_system.security;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Lightning
 */
@SessionScoped
public class UserSessionData implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String sessionId;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }    
    
}
