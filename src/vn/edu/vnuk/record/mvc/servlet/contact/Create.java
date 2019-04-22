package vn.edu.vnuk.record.mvc.servlet.contact;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vn.edu.vnuk.record.mvc.dao.ContactDao;
import vn.edu.vnuk.record.mvc.model.Contact;

@SuppressWarnings("serial")
@WebServlet("/addContact")
public class Create extends HttpServlet {
	
	/**
	 * 
	 */
	protected void service(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException {
		
		request.setCharacterEncoding ("UTF-8");
	    response.setCharacterEncoding ("UTF-8");
	    response.setContentType ("text / html; charset = UTF-8");
	    
		
		//	finding parameters in the request form
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		String dateInStringFormat = request.getParameter("date_of_birth");
		
		Calendar dateOfBirth = null;
		
		// 	converting string to data
		try {
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateInStringFormat);
			dateOfBirth = Calendar.getInstance();
			dateOfBirth.setTime(date);
		} 
		
		catch (ParseException e) {
			response.getWriter().println("Error while converting date");
			return;
		}
		
		//	building an Contact object
		Contact contact = new Contact();
		contact.setName(name);
		contact.setAddress(address);
		contact.setEmail(email);
		contact.setDateOfBirth(dateOfBirth);
		
		// 	save contact in DB
		ContactDao dao = new ContactDao();
		
		try {
			dao.create(contact);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/mvc?action=contact.Index");
//		RequestDispatcher rd = request.getRequestDispatcher("/contact-added.jsp");
        rd.forward(request,response);
		
	}
	
}