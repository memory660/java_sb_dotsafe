package com.acme.apitutorial.dto;

import com.acme.apitutorial.model.Contribution;
import com.acme.apitutorial.model.Project;
import com.acme.apitutorial.model.Techno;
import com.acme.apitutorial.model.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ContributionDto {

    @NotBlank(message = "The Project is required")
    private Project project;

    @NotBlank(message = "The Techno is required")
    private Techno techno;

    @NotBlank(message = "The User is required")
    private User user;

    @NotBlank(message = "The level is required")
    @Min(value = 0, message = "The min is 0")
    @Max(value = 10, message = "The max is 10")
    private Integer level;

    public Contribution toContribution() {
        Contribution contribution= new Contribution();
        contribution.setProject(project);
        contribution.setTechno(techno);
        contribution.setUser(user);
        contribution.setLevel(level);

        return contribution;
    }
}
