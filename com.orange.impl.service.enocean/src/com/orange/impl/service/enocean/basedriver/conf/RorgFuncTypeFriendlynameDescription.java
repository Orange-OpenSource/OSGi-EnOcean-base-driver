package com.orange.impl.service.enocean.basedriver.conf;

/**
 * RorgFuncTypeFriendlynameDescription object.
 * 
 * Note that: description must come from EnOceanMessageDescription, apart for
 * specific/configured cases, e.g. for demo purpose.
 */
public class RorgFuncTypeFriendlynameDescription {

	private String rorg;
	private String func;
	private String type;
	private String friendlyname;
	private String description;

	/**
	 * @param rorg
	 *            value.
	 * @param func
	 *            value.
	 * @param type
	 *            value.
	 * @param friendlyname
	 *            value.
	 * @param description
	 *            value.
	 */
	public RorgFuncTypeFriendlynameDescription(String rorg, String func,
			String type, String friendlyname, String description) {
		this.rorg = rorg;
		this.func = func;
		this.type = type;
		this.friendlyname = friendlyname;
		this.description = description;
	}

	/**
	 * @return rorg value.
	 */
	public String getRorg() {
		return rorg;
	}

	/**
	 * @return func value.
	 */
	public String getFunc() {
		return func;
	}

	/**
	 * @return type value.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return friendlyname value.
	 */
	public String getFriendlyname() {
		return friendlyname;
	}

	/**
	 * @return description value.
	 */
	public String getDescription() {
		return description;
	}

	public String toString() {
		return "" + this.getClass().getName() + "[rorg: " + getRorg()
				+ ", func: " + getFunc() + ", type: " + getType()
				+ ", friendlyname: " + getFriendlyname() + ", description: "
				+ getDescription() + "]";
	}

}
