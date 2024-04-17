package ai.aem.airmiles.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.adobe.acs.commons.email.EmailService;
import com.adobe.acs.commons.email.EmailServiceConstants;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "= Mail Registration Servlet",
        "sling.servlet.methods=" + HttpConstants.METHOD_POST,
        "sling.servlet.paths=" + "/bin/airmiles-ai/int/mail-registration"})
public class MailRegistrationServlet  extends SlingAllMethodsServlet {
    private static final long serialVersionUID = 1L;

    @ObjectClassDefinition(name = "Analytics Service Configuration", description = "Service to get the Analytics URL")
	public static @interface Config {
	  /**
	   * Property to store the FROM Address for the AI Page
	   *
	   * @return String fromAddress
	   */
	  @AttributeDefinition(
	      name = "From Address",
	      description = "From addres for the AI Page"
	  )
	  String fromAddress();
	  
	  /**
	   * Property to store the the DESTINATION Address for the AI Page
	   *
	   * @return String destAddress
	   */
	  @AttributeDefinition(
	      name = "Destination Address",
	      description = "Destination addres for the AI Page"
	  )
	  String destAddress();
	  

	}
    
    private String fromAddress;
    
    private String destAddress;
	
	@Activate
	protected void activate(final Config config) {
		fromAddress = config.fromAddress();
		destAddress = config.destAddress();
	}
    
    @Reference
	transient EmailService emailService;
    
    @Override
    protected void doPost(final SlingHttpServletRequest req,
            final SlingHttpServletResponse resp) throws ServletException, IOException {    	
    	
    	String name = req.getParameter("name-registration-field");
    	String comments = req.getParameter("comments-registration-field");
    	String mail = req.getParameter("email-registration-field");    	
    	
    	String[] recipients = {destAddress};
    	String templatePath = "/etc/notification/email/acs-commons/mailregistration.html";
    	Map<String, String> emailParams = new HashMap<>();
    	emailParams.put("name",name);
    	emailParams.put(EmailServiceConstants.SENDER_EMAIL_ADDRESS,fromAddress);
    	if(!StringUtils.isEmpty(comments)) {
    		emailParams.put("comments",comments);
    	}    	
    	emailParams.put("mail",mail);
    	List<String> failureList = emailService.sendEmail(templatePath, emailParams, recipients);
    	
    	resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
    	
    	if (failureList.isEmpty()) {
    		resp.setStatus(HttpStatus.SC_OK);
    		resp.getWriter().write("{}");
    	} else {
    		resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    	}    	    	    	
    }
}
