/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package j_ee_project.j_ee_students_system.services;

import j_ee_project.j_ee_students_system.services.base.UsersService;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.validation.ParameterNameProvider;
import javax.validation.Validation;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.validation.ValidationConfig;
import org.glassfish.jersey.server.validation.internal.InjectingConstraintValidatorFactory;

/**
 *
 * @author Lightning
 */
@javax.ws.rs.ApplicationPath("services")
public class ApplicationConfig extends Application {
//    @Override
//    public Set<Class<?>> getClasses() {
//        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
//        addRestResourceClasses(resources);
//        return resources;
//    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(j_ee_project.j_ee_students_system.services.BeanValidationExceptionHadler.class);
        resources.add(j_ee_project.j_ee_students_system.services.admin_panel.AssignmentsAdministrationService.class);
        resources.add(j_ee_project.j_ee_students_system.services.admin_panel.DisciplinesAdministrationService.class);
        resources.add(j_ee_project.j_ee_students_system.services.admin_panel.SpecialitiesAdministrationService.class);
        resources.add(j_ee_project.j_ee_students_system.services.admin_panel.UsersAdministrationService.class);
        resources.add(j_ee_project.j_ee_students_system.services.base.AssignmentsService.class);
        resources.add(j_ee_project.j_ee_students_system.services.base.DisciplinesService.class);
        resources.add(j_ee_project.j_ee_students_system.services.base.SpecialitiesService.class);
        resources.add(j_ee_project.j_ee_students_system.services.base.UsersService.class);
        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
    }

}
