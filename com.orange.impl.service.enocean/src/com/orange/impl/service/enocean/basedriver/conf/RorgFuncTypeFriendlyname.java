package com.orange.impl.service.enocean.basedriver.conf;

/**
 * RorgFuncTypeFriendlyname object.
 */
public class RorgFuncTypeFriendlyname {

	private String rorg;
	private String func;
	private String type;
	private String friendlyname;

	/**
	 * @param rorg
	 *            value.
	 * @param func
	 *            value.
	 * @param type
	 *            value.
	 * @param friendlyname
	 *            value.
	 */
	public RorgFuncTypeFriendlyname(String rorg, String func, String type,
			String friendlyname) {
		this.rorg = rorg;
		this.func = func;
		this.type = type;
		this.friendlyname = friendlyname;
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

	public String toString() {
		return "" + this.getClass().getName() + "[rorg: " + getRorg()
				+ ", func: " + getFunc() + ", type: " + getType()
				+ ", friendlyname: " + getFriendlyname() + "]";
	}

}
