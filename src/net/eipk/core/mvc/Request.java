package net.eipk.core.mvc;

public class Request {

	private String requestPath;
	private String requestMethod;


	public String getRequestPath() {
		return requestPath;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public Request(String requestPath, String requestMethod) {
		super();
		this.requestPath = requestPath;
		this.requestMethod = requestMethod;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((requestMethod == null) ? 0 : requestMethod.hashCode());
		result = prime * result + ((requestPath == null) ? 0 : requestPath.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Request other = (Request) obj;
		if (requestMethod == null) {
			if (other.requestMethod != null)
				return false;
		} else if (!requestMethod.equals(other.requestMethod))
			return false;
		if (requestPath == null) {
			if (other.requestPath != null)
				return false;
		} else if (!requestPath.equals(other.requestPath))
			return false;
		return true;
	}

}
