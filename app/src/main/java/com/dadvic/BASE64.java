package com.dadvic;


public class BASE64 {
	private static char[] TOCHAR = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
									'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
									'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
									
	public static String encode(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		
		int i = bytes.length;
		
		int k = i/3;

		for (int n = 0; n < k; n++) {
			if (n > 0 && n % 19 == 0) {
				builder.append('\n');
			}
			builder.append(convertToString(bytes[3*n], bytes[3*n+1], bytes[3*n+2]));
		}
		
		k = i % 3;
		if (k == 2) {
			char[] chars = convertToString(bytes[i-2], bytes[i-1], 0);
			
			chars[3] = '=';
			builder.append(chars);
		} else if (k == 1) {
			char[] chars = convertToString(bytes[i-1], 0, 0);
			
			chars[2] = '=';
			chars[3] = '=';
			builder.append(chars);
		}
		return builder.toString();
	}
	
	private static char[] convertToString(int b, int c, int d) {
		char[] result = new char[4];
		if (b < 0) {
			b += 256;
		}
		if (c < 0) {
			c += 256;
		}
		if (d < 0) {
			d += 256;
		}

		int f = d+(c+b*256)*256;

		result[3] = TOCHAR[f % 64];
		f /= 64;
		result[2] = TOCHAR[f % 64];
		f /= 64;
		result[1] = TOCHAR[f % 64];
		f /= 64;
		result[0] = TOCHAR[f % 64];
		
		return result;
	}

}
