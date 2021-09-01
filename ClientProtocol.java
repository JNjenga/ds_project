public class ClientProtocol
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

	public static String processRequest(int code)
	{
		RequestCode rcode = RequestCode.values() [code];
		switch (rcode)
		{
			case NUMBER:
				return "Enter adm no:";
			case NAME:
				return "Enter your name:";
			case FACULTY:
				return "Enter faculty:";
			case COURSE:
				return "Enter course:";
			case DEGREE:
				return "Enter degree:";
			case CODE:
				return "Enter code:";
			case ALL:
				return "Enter all(separate with comma):\nadmno,name,faculty,course,degree,code";
			default:
				break;
		}

		return "Panic, unkown code!";
	}

}
