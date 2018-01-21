package de.cit.backend.api.converter;

import java.util.ArrayList;
import java.util.List;

import de.cit.backend.api.model.Project;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;

public class ProjectConverter implements Converter<ProjectDTO, Project> {

	public ProjectDTO convertToBackend(Project in) {
		if(in == null){
			return null;
		}
		
		ProjectDTO out = new ProjectDTO();
		out.setId(in.getID());
		out.setCreatedAt(in.getCreatedAt());
		out.setUserdata(new UserConverter().convertToBackend(in.getCreateUser()));
		out.setName(in.getName());
		
		return out;
	}

	public Project convertToFrontend(ProjectDTO in) {
		if(in == null){
			return null;
		}
		//FIXME move the converters into an EJB, so we can LazyLoad some properties
		Project out = new Project();
		out.setID(in.getId());
		out.setCreatedAt(in.getCreatedAt());
		out.setCreateUser(new UserConverter().convertToFrontend(in.getUserdata()));
		out.setName(in.getName());
		out.setUsers(new UserConverter().convertToFrontend(in.getProjectMembers()));
		
		return out;
	}

	public List<Project> convertToFrontend(List<ProjectDTO> in) {
		List<Project> out = new ArrayList<Project>();
		for (ProjectDTO projectDTO : in) {
			out.add(this.convertToFrontend(projectDTO));
		}
		return out;
	}
}
