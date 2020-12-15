package org.sasanlabs.framework;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents all the VulnerableEndpoints present in the
 * VulnerableApp-jsp project. This information is required by the Owasp
 * VulnerableApp's Generic UI framework.
 * 
 * @author KSASAN preetkaran20@gmail.com
 */
public class AllEndPointsResponseBean {
	
	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("Description")
	private String description;
	
	@JsonProperty("Detailed Information")
	private Collection<LevelResponseBean> levelResponseBeans;

	public AllEndPointsResponseBean(String name, String description, Collection<LevelResponseBean> levelResponseBeans) {
		super();
		this.name = name;
		this.description = description;
		this.levelResponseBeans = levelResponseBeans;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Collection<LevelResponseBean> getLevelResponseBeans() {
		return levelResponseBeans;
	}

	static class LevelResponseBean {
	    @JsonProperty("Level")
		private String level;
	    @JsonProperty("HtmlTemplate")
		private String htmlTemplate;

		public LevelResponseBean(String level, String htmlTemplate) {
			super();
			this.level = level;
			this.htmlTemplate = htmlTemplate;
		}

		public String getLevel() {
			return level;
		}

		public String getHtmlTemplate() {
			return htmlTemplate;
		}

	}

	static class AttackVectors {
	    @JsonProperty("CurlPayload")
		private String curlPayload;
	    @JsonProperty("Description")
		private String description;

		public AttackVectors(String curlPayload, String description) {
			super();
			this.curlPayload = curlPayload;
			this.description = description;
		}

		public String getCurlPayload() {
			return curlPayload;
		}

		public String getDescription() {
			return description;
		}

	}
}
