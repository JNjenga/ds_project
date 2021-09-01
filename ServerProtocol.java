import javax.swing.*;

public class ServerProtocol
{
	public enum RequestCode {
		NUMBER,
		NAME,
		FACULTY,
		COURSE,
		DEGREE,
		CODE,
		ALL
	};
	
	RequestCode requestCode;

	public int processRequest(String message)
	{
		if (message.equals("Number"))
			requestCode = RequestCode.NUMBER;
		else if (message.equals("Name"))
			requestCode = RequestCode.NAME;
		else if (message.equals("Faculty"))
			requestCode = RequestCode.FACULTY;
		else if (message.equals("Course"))
			requestCode = RequestCode.COURSE;
		else if (message.equals("Degree"))
			requestCode = RequestCode.DEGREE;
		else if (message.equals("Code"))
			requestCode = RequestCode.CODE;
		else if (message.equals("All"))
			requestCode = RequestCode.ALL;

		return requestCode.ordinal();
	}

	public void processResponse(JTable table, String message)
	{
		if(requestCode == RequestCode.ALL)
		{
			String [] data = message.split(",", 0);

			for(int i = 0 ; i < data.length; i ++)
			{
				table.setValueAt(data[i], i, 1);
			}
		}
		else 
		{
			table.setValueAt(message, requestCode.ordinal(), 1);
		}
	}
}
