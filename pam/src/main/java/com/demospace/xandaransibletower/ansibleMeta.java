package com.demospace.xandaransibletower;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class ansibleMeta implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	@org.kie.api.definition.type.Label("Ansible Template Name")
	private java.lang.String templateName;
	@org.kie.api.definition.type.Label("Ansible Template ID")
	private java.lang.String templateId;
	@org.kie.api.definition.type.Label("Approval Required")
	private boolean approvalReq;

	@org.kie.api.definition.type.Label("Ansible Tower URL")
	private java.lang.String towerURL;

	@org.kie.api.definition.type.Label("Requester")
	private java.lang.String requester;

	@org.kie.api.definition.type.Label("Request Date")
	private java.time.LocalDateTime requestDate;

	@org.kie.api.definition.type.Label("Template Details")
	private java.lang.String templateDetails;

	@org.kie.api.definition.type.Label(value = "Job Completion Duration")
	private java.lang.String jobDuration;

	public ansibleMeta() {
	}

	public java.lang.String getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(java.lang.String templateName) {
		this.templateName = templateName;
	}

	public java.lang.String getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(java.lang.String templateId) {
		this.templateId = templateId;
	}

	public boolean isApprovalReq() {
		return this.approvalReq;
	}

	public void setApprovalReq(boolean approvalReq) {
		this.approvalReq = approvalReq;
	}

	public java.lang.String getTowerURL() {
		return this.towerURL;
	}

	public void setTowerURL(java.lang.String towerURL) {
		this.towerURL = towerURL;
	}

	public java.lang.String getRequester() {
		return this.requester;
	}

	public void setRequester(java.lang.String requester) {
		this.requester = requester;
	}

	public java.time.LocalDateTime getRequestDate() {
		return this.requestDate;
	}

	public void setRequestDate(java.time.LocalDateTime requestDate) {
		this.requestDate = requestDate;
	}

	public java.lang.String getTemplateDetails() {
		return this.templateDetails;
	}

	public void setTemplateDetails(java.lang.String templateDetails) {
		this.templateDetails = templateDetails;
	}

	public java.lang.String getJobDuration() {
		return this.jobDuration;
	}

	public void setJobDuration(java.lang.String jobDuration) {
		this.jobDuration = jobDuration;
	}

	public ansibleMeta(java.lang.String templateName,
			java.lang.String templateId, boolean approvalReq,
			java.lang.String towerURL, java.lang.String requester,
			java.time.LocalDateTime requestDate,
			java.lang.String templateDetails, java.lang.String jobDuration) {
		this.templateName = templateName;
		this.templateId = templateId;
		this.approvalReq = approvalReq;
		this.towerURL = towerURL;
		this.requester = requester;
		this.requestDate = requestDate;
		this.templateDetails = templateDetails;
		this.jobDuration = jobDuration;
	}



}