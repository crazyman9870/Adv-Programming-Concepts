package CommunicatingClasses;

/**
 * This class is designed to repsond to the user's batch summitted
 * Let's the user know if the submission was successful or not
 * @author aconstan
 *
 */

public class SubmitBatchOut {
	
	private boolean submitted;
	private String result;
	

	public SubmitBatchOut(int status)
	{
		setSubmitted(status);
		result = toString(status);
	}
		
	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(int status) {
		if(status == 0)
		{
			submitted = true;
		}
		else
		{
			submitted = false;
		}
	}
	
	public String getResult() {
		return result;
	}

	public String toString(int status)
	{
		StringBuilder sb = new StringBuilder();
		if(status == 0)
		{
			sb.append("TRUE\n");
		}
		else
		{
			sb.append("FALSE\n");
		}
		
		return sb.toString();
	}


}
