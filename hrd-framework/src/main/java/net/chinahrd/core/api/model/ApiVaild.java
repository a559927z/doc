/**
*net.chinahrd.core.api.model
*/
package net.chinahrd.core.api.model;

import net.chinahrd.core.api.config.ApiInvalidType;

/**
 * @author htpeng
 *2016年10月17日下午5:12:59
 */
public class ApiVaild<T> {
	private boolean valid=false;
	private ApiInvalidType apiInvalidType=ApiInvalidType.NULL;
	private T result;
	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}
	/**
	 * @param valid the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	/**
	 * @return the apiInvalidType
	 */
	public ApiInvalidType getApiInvalidType() {
		return apiInvalidType;
	}
	/**
	 * @param apiInvalidType the apiInvalidType to set
	 */
	public void setApiInvalidType(ApiInvalidType apiInvalidType) {
		this.apiInvalidType = apiInvalidType;
	}
	/**
	 * @return the result
	 */
	public T getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(T result) {
		this.result = result;
	}

	
}
