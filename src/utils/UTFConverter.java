package utils;

public class UTFConverter {
	
	//UTF-32 simply represents each Unicode code point as the 32-bit integer of the same valu
	//same as UCS-4
	//Surrogate pairs are used to represent values between 65,536 (0x10000) and 1,114,111 (0x10FFFF)
	//UTF16, surrogate pairs two seperate 16 bit pairs if over the max 16bit
	//1. Get codepoint 
	//2.Subtract maximum possible bit representation(codepoint - 65,532)
	// 3. Move to binary, 20 digits
	//4. get the value of first and last 10 digits
	//5. add each number to start of surrogate range low(55.296), high(56.320)
	public static String UTF32toUTF16(int utf32) {
		//fixed chunck size of bits to each character
		

		//tells you if less than 16(1) or more than 16(2)	
		char[] test = new char[2];
		int find = Character.toChars(utf32, test, 0);
		System.out.println(find);
		
		StringBuilder fullString = new StringBuilder();
		for(char c : test) {
			//the two UTF 16 values
			System.out.println((int)c);
			String s = Integer.toHexString((int)c);
			System.out.println(s);
			fullString.append("\\u");
			fullString.append(s);
			
		}
	
		
		System.out.println(fullString.toString());
		
		return fullString.toString();
		
		//int i = str.codePointCount(0, str.length());
		//System.out.println("integer" + i);
		
		
		//int firstcode = str.codePointAt(0);
		//int secoundcode = str.codePointAt(1);
		//System.out.println("first "+ firstcode);
		//what
		//System.out.println("secound "+ secoundcode);

	}

	
	public static void main(String[] args) {
		System.out.println(UTF32toUTF16(128250));
		
		//UTF16 getvalue
		//char[] heyhey = Character.toChars(emoji_id);
		//alternative_txt.append(String.valueOf(heyhey));
	}
	
	
	
	
	

}
