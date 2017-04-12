/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vixcart.imgon.controller;

import com.vixcart.imgon.jsn.JSONParser;
import com.vixcart.imgon.message.CorrectMsg;
import com.vixcart.imgon.message.ResponseMsg;
import com.vixcart.imgon.message.ErrMsg;
import com.vixcart.imgon.req.mod.AddLogo;
import com.vixcart.imgon.support.controller.BlockAdminUser;
import com.vixcart.imgon.resp.mod.AddLogoFailureResponse;
import com.vixcart.imgon.resp.mod.AddLogoSuccessResponse;
import com.vixcart.imgon.result.AddLogoResult;
import com.vixcart.imgon.validation.AddLogoValidation;
import com.vixcart.imgon.imgpath.ImagePath;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

/**
 *
 * @author rifaie
 */
public class addLogo extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            String UPLOAD_DIRECTORY = ImagePath.getLOGO_PATH();
            int MEMORY_THRESHOLD = 1024 * 30;  // 30KB
            int MAX_FILE_SIZE = 1024 * 10; // 10KB
            int MAX_REQUEST_SIZE = 1024 * 20; // 20KB
            // configures upload settings
            DiskFileItemFactory factory = newDiskFileItemFactory(getServletContext(), new File(System.getProperty("java.io.tmpdir")));
            // sets memory threshold - beyond which files are stored in disk
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            // sets temporary location to store files
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload upload = new ServletFileUpload(factory);

            // sets maximum size of upload file
            upload.setFileSizeMax(MAX_FILE_SIZE);

            // sets maximum size of request (include file + form data)
            upload.setSizeMax(MAX_REQUEST_SIZE);

            // constructs the directory path to store upload file
            // this path is relative to application's directory
            String uploadPath = getServletContext().getRealPath("")
                    + File.separator + UPLOAD_DIRECTORY;

            // creates the directory if it does not exist
            List<File> temp = new ArrayList<>();
            String company = "";
            List<FileItem> formItems = upload.parseRequest(request);
            File storeFile = null;
            String fileName = "";
            long fileSize = 0;
            if (formItems != null && formItems.size() > 0) {
                // iterates over form's fields
                for (FileItem item : formItems) {
                    if (item.isFormField()) {
                        if (item.getFieldName().equalsIgnoreCase("cmp")) {
                            company = item.getString();
                            File uploadDir = new File(uploadPath + File.separator + company);
                            if (uploadDir.exists()) {
                                for (File f : uploadDir.listFiles()) {
                                    f.delete();
                                }
                                uploadDir.delete();
                            }
                            uploadDir.mkdirs();
                        }
                    } else {
                        fileName = item.getName();
                        fileSize = item.getSize();
                    }
                }
                Cookie ck = Servlets.getCookie(request, "at");
                String at = ck.getValue();
                AddLogo req = new AddLogo(at, company, fileName, fileSize);
                AddLogoValidation reqV = new AddLogoValidation(req);
                reqV.validation();
                AddLogoResult reqR = JSONParser.parseJSONADDAFF(reqV.toString());
                String validSubmission = reqR.getValidationResult();
                if (validSubmission.startsWith(CorrectMsg.CORRECT_MESSAGE)) {
                    for (FileItem item : formItems) {
                        // processes only fields that are not form fields
                        if (!item.isFormField()) {

                            Thread.sleep(10);

                            String filePath = uploadPath + File.separator + company + File.separator + "logo" + fileName.substring(fileName.lastIndexOf("."));
                            storeFile = new File(filePath);
                            // saves the file on disk
                            temp.add(storeFile);
                            item.write(storeFile);
                            AddLogoSuccessResponse SResp = new AddLogoSuccessResponse(ResponseMsg.RESP_OK, at);
                            out.write(SResp.toString());
                        }
                    }
                } else if (validSubmission.startsWith(ErrMsg.ERR_ERR)) {
                    if (reqR.getAt().startsWith(ErrMsg.ERR_MESSAGE)) {
                        // do nothing
                    } else if (reqR.getAdmintype().startsWith(ErrMsg.ERR_MESSAGE)) {
                        BlockAdminUser bau = new BlockAdminUser(req.getAdmin_id());
                        bau.block();
                    }
                    AddLogoFailureResponse FResp = new AddLogoFailureResponse(reqR, validSubmission);
                    out.write(FResp.toString());
                } else {
                    //exception response
                }
                out.flush();
                out.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(addLogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public static DiskFileItemFactory newDiskFileItemFactory(ServletContext context,
            File repository) {
        FileCleaningTracker fileCleaningTracker
                = FileCleanerCleanup.getFileCleaningTracker(context);
        DiskFileItemFactory factory
                = new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,
                        repository);
        factory.setFileCleaningTracker(fileCleaningTracker);
        return factory;
    }

}
