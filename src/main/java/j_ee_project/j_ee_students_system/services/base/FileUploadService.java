package j_ee_project.j_ee_students_system.services.base;

import j_ee_project.j_ee_students_system.data_management.AssignmentDataManager;
import j_ee_project.j_ee_students_system.data_management.AssignmentSolutionDataManager;
import j_ee_project.j_ee_students_system.data_management.UserDataManager;
import j_ee_project.j_ee_students_system.entities.Assignment;
import j_ee_project.j_ee_students_system.entities.AssignmentSolution;
import j_ee_project.j_ee_students_system.entities.User;
import j_ee_project.j_ee_students_system.security.SystemSecurityManager;
import j_ee_project.j_ee_students_system.security.UserSessionData;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.lang3.StringEscapeUtils;

@WebServlet("/services/file_upload/*")
@MultipartConfig
public class FileUploadService extends HttpServlet {

    @EJB
    private SystemSecurityManager systemSecurityManager;

    @EJB
    private UserDataManager userDataManager;

    @EJB
    private AssignmentDataManager assignmentDataManager;

    @EJB
    private AssignmentSolutionDataManager assignmentSolutionDataManager;

    @Inject
    private UserSessionData userSessionData;

    public final static String FILE_UPLOAD_DIRECTORY = "D:\\Programming\\Java\\JavaEE\\j_ee_students_system_files_uploaded\\";
    public final static int MAX_FILE_SIZE = 2048 * 1024;
    public final static String ASSIGNMENTS_ATTACHEMENTS_DIRECTORY = "AssignmentsAttachements\\";
    public final static String ASSIGNMENTS_SOLUTIONS_DIRECTORY = "AssignmentSolutions\\";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uriPath = request.getPathInfo();
        if (uriPath == null || uriPath.isEmpty()) {
            response.sendError(400);
            return;
        }
        String uriParts[] = uriPath.split("/");
        if (uriParts.length != 2) {
            response.sendError(400);
            return;
        }
        String uploadType = uriParts[1];

        User user = null;
        Assignment assignment = null;
        String subDirectory = ASSIGNMENTS_ATTACHEMENTS_DIRECTORY;
        if (!uploadType.equals("assignmentAttachement")) {
            subDirectory = ASSIGNMENTS_SOLUTIONS_DIRECTORY;           
            try {
                Long assignmentId = Long.parseLong(uploadType);
                user = userDataManager.find(userSessionData.getUserId());
                assignment = assignmentDataManager.find(assignmentId);
                AssignmentSolution assignmentSolution = assignmentSolutionDataManager.getAssignmentSolution(user, assignment);
                Date endDate = assignment.getEndTime();
                Date now = Calendar.getInstance().getTime();
                if (assignmentSolution != null || now.after(endDate)) {
                    response.sendError(400);
                    return;
                }
            } catch (NumberFormatException e) {
                response.sendError(400);
                return;
            }
        } else {
             if (!systemSecurityManager.isAuthorized(userSessionData, "create__assignments")) {
                response.sendError(403);
                return;
            }
        }

        Part filePart = request.getPart("file");
        InputStream fileContent = filePart.getInputStream();

        String fileName = filePart.getSubmittedFileName();
        fileName = StringEscapeUtils.escapeHtml4(fileName);
        if (fileName.startsWith("\\") || fileName.startsWith("/") || fileName.startsWith(".")) {
            response.sendError(400);
            return;
        }

        File uploadedFile = new File(FILE_UPLOAD_DIRECTORY + subDirectory + fileName);
        int numberOfTries = 5;
        int i = 0;
        while (uploadedFile.exists() && i < numberOfTries) {
            uploadedFile = new File(FILE_UPLOAD_DIRECTORY + subDirectory + java.util.UUID.randomUUID().toString() + "_" + fileName);
            ++i;
        }

        if (filePart.getSize() > MAX_FILE_SIZE) {
            response.sendError(400);
            return;
        }
        int read = 0;
        byte[] bytes = new byte[MAX_FILE_SIZE];
        OutputStream out = new FileOutputStream(uploadedFile);
        while ((read = fileContent.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();

        AssignmentSolution assignmentSolution = new AssignmentSolution(assignment, user, fileName);
        assignmentSolutionDataManager.create(assignmentSolution);
        response.getWriter().write("{\"fileName\": \"" + uploadedFile.getName() + "\"}");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uriPath = request.getPathInfo();

        if (uriPath == null || uriPath.isEmpty()) {
            response.sendError(400);
            return;
        }
        String uriParts[] = uriPath.split("/");
        if (uriParts.length != 3) {
            response.sendError(400);
            return;
        }
        String downloadType = uriParts[1];
        String requestedFileName = StringEscapeUtils.escapeHtml4(uriParts[2]);
        if (requestedFileName.startsWith("\\") || requestedFileName.startsWith("/") || requestedFileName.startsWith(".")) {
            response.sendError(400);
            return;
        }
        if (downloadType.equals("donwloadAssignmentAttachement")) {
            OutputStream outputStream = response.getOutputStream();
            Path filePath = Paths.get(FILE_UPLOAD_DIRECTORY + ASSIGNMENTS_ATTACHEMENTS_DIRECTORY + requestedFileName);
            File file = filePath.toFile();
            if (file.exists() && !file.isDirectory()) {
                byte[] fileBytes = Files.readAllBytes(filePath);
                outputStream.write(fileBytes);
            } else {
                response.setStatus(404);
            }
        } else {
            User user = userDataManager.find(userSessionData.getUserId());
            if (assignmentSolutionDataManager.isUserOwnerOfThisFile(user, requestedFileName)
                    || assignmentSolutionDataManager.isLecturerLeadOfThisDiscipline(user, requestedFileName)
                    || systemSecurityManager.isAuthorized(userSessionData, "read__assignments_solutions")) {
                OutputStream outputStream = response.getOutputStream();
                Path filePath = Paths.get(FILE_UPLOAD_DIRECTORY + ASSIGNMENTS_SOLUTIONS_DIRECTORY + requestedFileName);
                File file = filePath.toFile();
                if (file.exists() && !file.isDirectory()) {
                    byte[] fileBytes = Files.readAllBytes(filePath);
                    outputStream.write(fileBytes);
                    outputStream.flush();
                    outputStream.close();
                } else {
                    response.setStatus(404);
                }
            } else {
                response.setStatus(403);
            }
        }
    }

}
