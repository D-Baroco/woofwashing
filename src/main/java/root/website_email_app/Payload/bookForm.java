package root.website_email_app.Payload;

import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class bookForm {


    @NotBlank(message = "Name is mandatory")
    private String summary;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @NotBlank(message = "Description is mandatory")
    private String description;


    private String startDateTime;


    private String endDateTime;

    public @NotBlank(message = "Name is mandatory") String getSummary() {
        return summary;
    }

    public void setSummary(@NotBlank(message = "Name is mandatory") String summary) {
        this.summary = summary;
    }

    public @NotBlank(message = "Location is mandatory") String getLocation() {
        return location;
    }

    public void setLocation(@NotBlank(message = "Location is mandatory") String location) {
        this.location = location;
    }

    public @NotBlank(message = "Description is mandatory") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description is mandatory") String description) {
        this.description = description;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return "bookForm{" +
                "summary='" + summary + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                '}';
    }
}
