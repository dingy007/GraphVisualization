package com.graphvisual.myApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate );

		return "home.jsp";
	}

	@RequestMapping(value = "/filerequest", method = RequestMethod.GET)
	public void miserables_request(	HttpServletResponse response) {
		logger.info("Providing miserables.json file");

//		File file = new File("C:\\Users\\dineshkp\\Desktop\\GitRepo_local\\GraphVisualization\\GraphVisualizationHelper\\src\\main\\resources\\miserables.json");
//		File file = new File ("classpath:/miserables.json");
		String fileName = "miserables.json";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		try {
			
			InputStream is = new FileInputStream(file);
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());

			response.flushBuffer();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("IOError writing file to output stream");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			logger.info("Completed action...");
		}
	}

}
