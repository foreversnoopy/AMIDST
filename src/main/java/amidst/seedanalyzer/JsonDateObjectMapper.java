package amidst.seedanalyzer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;

import amidst.logging.AmidstLogger;

public class JsonDateObjectMapper implements ObjectMapper
{
	private com.fasterxml.jackson.databind.ObjectMapper objectMapper 
    	= new com.fasterxml.jackson.databind.ObjectMapper();
	
	public JsonDateObjectMapper()
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:s.S'Z'");

		this.objectMapper.setDateFormat(df);
	}

	public Object readValue(String value)
	{
	    try
	    {
			return objectMapper.readValue(value, WorkItem.class);
		}
	    catch (Exception e)
	    {
	    	String ex = e.toString();
	    	ex = ex.substring(0, Math.min(ex.length(), 200));
	    	
			AmidstLogger.warn(ex, "JSON serialization error.");
		}
	    
		return null;
	}
	
	public String writeValue(Object value)
	{
	    try
	    {
			return objectMapper.writeValueAsString(value);
		}
	    catch (JsonProcessingException e)
	    {
	    	String ex = e.toString();
	    	ex = ex.substring(0, Math.min(ex.length(), 200));
	    	
			AmidstLogger.warn(ex, "JSON serialization error.");
		}
	    
		return "";
	}
}