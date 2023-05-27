package Entity.api;

import java.util.HashMap;
import java.util.Map;

public class APIResponse<T> {
	private String message;
    private boolean error;
    private Map<String, T> data;
    
    public APIResponse(String message, boolean error) {
        this.message = message;
        this.error = error;
    }

    public APIResponse(String message, boolean error, String key, T data) {
        this.message = message;
        this.error = error;
        this.data = new HashMap<>();
        this.data.put(key, data);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Map<String, T> getData() {
        return data;
    }

    public void setData(Map<String, T> data) {
        this.data = data;
    }
}
