package de.cit.backend.mgmt.validation;

import java.util.ArrayList;
import java.util.List;

import de.cit.backend.mgmt.exceptions.BitflowException;
import de.cit.backend.mgmt.exceptions.BitflowFrontendError;
import de.cit.backend.mgmt.persistence.model.AgentDTO;
import de.cit.backend.mgmt.persistence.model.CapabilityDTO;
import de.cit.backend.mgmt.persistence.model.ConfigurationDTO;
import de.cit.backend.mgmt.persistence.model.PipelineDTO;
import de.cit.backend.mgmt.persistence.model.PipelineParameterDTO;
import de.cit.backend.mgmt.persistence.model.PipelineStepDTO;
import de.cit.backend.mgmt.persistence.model.ProjectDTO;
import de.cit.backend.mgmt.persistence.model.UserDTO;

public abstract class Validator {

	protected Object objectToValidate;
	protected String message;
	
	public Validator(Object obj, String message){
		this.message = message;
		this.objectToValidate = obj;
	}
	
	public abstract void validate() throws BitflowException;
	
	public static List<Validator> getUserValidators(UserDTO user, boolean validatePwd){
		List<Validator> validators = new ArrayList<>();
		//validators.add(new NotNullValidator(user.getRegisteredSince(), "Registration date must be set."));
		//validators.add(new NotNullValidator(user.getRole(), "User role must be set."));
		validators.add(new NotEmptyValidator(user.getEmail(), "Email must be provided."));
		validators.add(new NotEmptyValidator(user.getName(), "Name must be provided."));
		validators.add(new StringLengthValidator(user.getEmail(), "Limit for email is 128 characters.", 128));
		validators.add(new StringLengthValidator(user.getName(), "Limit for name is 128 characters.", 128));
		if(validatePwd){
			validators.add(new NotEmptyValidator(user.getPassword(), "Password must be provided."));
			validators.add(new StringLengthValidator(user.getPassword(), "Limit for password is 128 characters.", 128));
		}

		return validators;
	}

	public static List<Validator> getAgentValidators(AgentDTO agent){
		List<Validator> validators = new ArrayList<>();
		validators.add(new NotEmptyOrNullValidator(agent.getIpAddress(), "IP must be provided."));
		validators.add(new StringLengthValidator(agent.getIpAddress(), "Limit for ip is 128 characters.", 128));
		validators.add(new PortRangeValidator(agent.getPort()));
		return validators;
	}

	public static List<Validator> getCapabilityValidators(CapabilityDTO capability){
		List<Validator> validators = new ArrayList<>();
		validators.add(new NotEmptyValidator(capability.getName(), "Name must be provided."));
		validators.add(new StringLengthValidator(capability.getName(), "Limit for name is 64 characters.", 64));
		validators.add(new StringLengthValidator(capability.getDescription(), "Limit for description is 512 characters.", 512));
		validators.add(new StringLengthValidator(capability.getRequiredParams(), "Limit for required params is 128 characters.", 128));
		validators.add(new StringLengthValidator(capability.getOptionalParams(), "Limit for optional params is 128 characters.", 128));
		return validators;
	}

	public static List<Validator> getConfigurationValidators(ConfigurationDTO configuration){
		List<Validator> validators = new ArrayList<>();
		validators.add(new NotEmptyOrNullValidator(configuration.getConfigKey(), "Config key must be provided."));
		validators.add(new NotEmptyOrNullValidator(configuration.getConfigValue(), "Config value must be provided."));
		validators.add(new StringLengthValidator(configuration.getConfigKey(), "Limit for config key is 64 characters.", 64));
		validators.add(new StringLengthValidator(configuration.getConfigValue(), "Limit for config value is 64 characters.", 64));
		return validators;
	}

	public static List<Validator> getPipelineValidators(PipelineDTO pipeline){
		List<Validator> validators = new ArrayList<>();
		validators.add(new StringLengthValidator(pipeline.getName(), "Limit for name is 256 characters.", 256));
		validators.add(new StringLengthValidator(pipeline.getStatus(), "Limit for status is 32 characters.", 32));
		return validators;
	}

	public static List<Validator> getPipelineParameterValidators(PipelineParameterDTO pipelineParameter){
		List<Validator> validators = new ArrayList<>();
		validators.add(new NotEmptyValidator(pipelineParameter.getParamName(), "Parameter name must be provided."));
		validators.add(new NotEmptyValidator(pipelineParameter.getParamValue(), "Parameter value must be provided."));
		validators.add(new StringLengthValidator(pipelineParameter.getParamName(), "Limit for parameter name is 128 characters.", 128));
		validators.add(new StringLengthValidator(pipelineParameter.getParamValue(), "Limit for parameter value is 128 characters.", 128));
		return validators;
	}

	public static List<Validator> getPipelineStepValidators(PipelineStepDTO pipelineStep){
		List<Validator> validators = new ArrayList<>();
		validators.add(new NotNullValidator(pipelineStep.getType(), "Step type must be set."));
		validators.add(new NotNullValidator(pipelineStep.getStepNumber(), "Step number must be set."));
		validators.add(new NotEmptyValidator(pipelineStep.getContent(), "Content must be provided."));
		validators.add(new StringLengthValidator(pipelineStep.getStatus(), "Limit for status is 32 characters.", 32));
		validators.add(new StringLengthValidator(pipelineStep.getContent(), "Limit for content is 256 characters.", 256));
		validators.add(new PositiveNumberOrNullValidator(pipelineStep.getStepNumber(), "Pipeline step number must be positive."));
		return validators;
	}

	public static List<Validator> getProjectValidators(ProjectDTO project){
		List<Validator> validators = new ArrayList<>();
		validators.add(new NotNullValidator(project.getCreatedAt(), "Creation date must be set."));
		validators.add(new NotEmptyValidator(project.getName(), "Name must be provided."));
		validators.add(new StringLengthValidator(project.getName(), "Limit for name is 256 characters.", 256));
		return validators;
	}
	
	public static void validate(List<Validator> validators) throws BitflowException {
		for(Validator val : validators){
			val.validate();
		}
	}
}
