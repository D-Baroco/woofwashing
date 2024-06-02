package root.website_email_app.Controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import root.website_email_app.Payload.FormData;
import org.springframework.web.client.RestTemplate;
import root.website_email_app.Payload.bookForm;
import root.website_email_app.Service.EmailService;

import org.springframework.http.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
//import root.website_email_app.Payload.FormData;
//import root.website_email_app.Service.EmailService;

@Controller
public class WebsiteController {


    @Autowired
    private EmailService emailService;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/")
    public String webRoot(WebRequest webRequest) {
        System.out.println("*");
        System.out.println("*");
        System.out.println("Visit to front page");
        System.out.println("*");
        System.out.println("*");
        try {
            // Capture IP address, timestamp, and user agent
            String ipAddress = getClientIpAddress();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String userAgent = getUserAgent();

            // Create list of strings
            List<String> data = Arrays.asList(ipAddress, timestamp, userAgent);

            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create request entity
            HttpEntity<List<String>> requestEntity = new HttpEntity<>(data, headers);

            // Define the API URL
            String apiUrl = "http://10.128.0.2:80/addRow";

            // Send POST request
            restTemplate.postForObject(apiUrl, requestEntity, String.class);

        } catch (Exception e) {
            e.printStackTrace();
            return "index.html"; // Handle error appropriately
        }

        return "index.html";
    }
    private String getClientIpAddress() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return requestAttributes.getRequest().getRemoteAddr();
        }
        return "index.html";
    }
    private String getUserAgent() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            return request.getHeader("User-Agent");
        }
        return "index.html";
    }

    @GetMapping("/appointment")
    public String bookTime() {
        System.out.println("*");
        System.out.println("*");
        System.out.println("Visit to book page.");
        System.out.println("*");
        System.out.println("*");
        return "bookform.html";
    }

    @GetMapping("/how-it-works")
    public String infoPage() {
        System.out.println("*");
        System.out.println("*");
        System.out.println("Visit to info page.");
        System.out.println("*");
        System.out.println("*");
        return "how-it-works.html";
    }

    @GetMapping("/contact-us")
    public String contactForm() {
        System.out.println("*");
        System.out.println("*");
        System.out.println("Visit to contact page.");
        System.out.println("*");
        System.out.println("*");
        return "ContactUs.html";
    }


    @PostMapping("/submit")
    public String submitForm(@Valid @ModelAttribute("formData") FormData formData, BindingResult bindingResult) {
        System.out.println(formData.toString());
        if (bindingResult.hasErrors()) {
            return "error.html"; // Return to the form with validation errors
        }

        String subject = "New Form Submission";
        String message = "Email: " + formData.getEmail() + "\nPhone: " + formData.getPhone() + "\nDescription: " + formData.getDescription();

        emailService.sendSimpleMessage("postmaster@woofwashing.com", "danielbarocojr@gmail.com", subject, message);

        return "redirect:/Success.html";
    }


    /*@ModelAttribute("formData") was used previously to handle html5 form mapping -- this was replaced by RequestBody */
    @PostMapping("/book")
    public String submitAppointment(@Valid @ModelAttribute("formData") bookForm appData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "error.html"; // Return to the form with validation errors
        }

        try {
            // Parse startdatetime from String to Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date startdatetime = sdf.parse(appData.getStartDateTime());

            // Calculate enddatetime based on startdatetime
            Calendar cal = Calendar.getInstance();
            cal.setTime(startdatetime);
            cal.add(Calendar.HOUR, 1); // Adding 1 hour

            Date enddatetime = cal.getTime();

            // Set enddatetime in appData
            appData.setEndDateTime(sdf.format(enddatetime));
            appData.setStartDateTime(sdf.format(startdatetime));

            System.out.println(appData.toString());
            // External API URL
            String apiUrl = "http://10.128.0.2:80/addEvent";

            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create request entity
            HttpEntity<bookForm> request = new HttpEntity<>(appData, headers);

            try {
                // Send POST request
                restTemplate.postForObject(apiUrl, request, String.class);
            } catch (Exception e) {
                e.printStackTrace();
                return "error.html"; // Handle error appropriately
            }

            return "redirect:/Success.html";
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}





















