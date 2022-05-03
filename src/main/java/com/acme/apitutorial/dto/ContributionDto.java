package com.acme.apitutorial.dto;

import com.acme.apitutorial.model.Contribution;
import com.acme.apitutorial.model.Project;
import com.acme.apitutorial.model.Techno;
import com.acme.apitutorial.model.User;
import com.acme.apitutorial.repository.ContributionRepository;
import com.acme.apitutorial.repository.ProjectRepository;
import com.acme.apitutorial.repository.TechnoRepository;
import com.acme.apitutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Component
public class ContributionDto {

    @NotNull(message = "The ProjectId is required")
    private Long projectId;

    @NotNull(message = "The TechnoId is required")
    private Long technoId;

    @NotNull(message = "The UserId is required")
    private Long userId;

    @NotNull(message = "The level is required")
    @Min(value = 0, message = "The min is 0")
    @Max(value = 10, message = "The max is 10")
    private Integer level;

    public Contribution toContribution(Project project, Techno techno, User user) {

        if (project != null && techno != null && user != null) {
            Contribution contribution = new Contribution();
            contribution.setProject(project);
            contribution.setTechno(techno);
            contribution.setUser(user);
            contribution.setLevel(level);

            return contribution;
        }
        return null;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getTechnoId() {
        return technoId;
    }

    public void setTechnoId(Long technoId) {
        this.technoId = technoId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
