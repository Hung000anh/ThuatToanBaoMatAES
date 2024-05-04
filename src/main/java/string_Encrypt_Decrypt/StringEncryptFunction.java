package string_Encrypt_Decrypt;

import aes.AesDecryptFunctions;
import aes.AesEncryptFunctions;

public class StringEncryptFunction {

	public static String EncryptString(String plainText, String keyText) {
		
		int[] state = convertToIntArray(plainText);
		System.out.println("state: " + state[0] + " " + state[1] + " " + state[2] + " " + state[3]);
		int[] key = convertToIntArray(keyText);

		int[] C = AesEncryptFunctions.AesEncrypt(state, key);
                
		return formatHexString(C);
	}

	public static String DecryptString(String cipherText, String keyText) {
		int[] state = convertToIntArray(cipherText);
		int[] key = convertToIntArray(keyText);

		int[] C = AesDecryptFunctions.AesDecrypt(state, key);

		return formatHexString(C);
	}

	public static int[] convertToIntArray(String str) {
		int[] result = new int[4];
		result[0] = parseHexString(str.substring(0, 8));
		result[1] = parseHexString(str.substring(8, 16));
		result[2] = parseHexString(str.substring(16, 24));
		result[3] = parseHexString(str.substring(24, 32));
		
		return result;
	}

	public static String formatHexString(int[] arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append(String.format("%08x", arr[i]));
		}
		return sb.toString();
	}

	public static int parseHexString(String str) {
		int result = 0;
		for (int i = 0; i < str.length(); i++) {
	
			char c = Character.toLowerCase(str.charAt(i));
			int val = Character.digit(c, 16);
			if (val < 0) {
				throw new NumberFormatException("Invalid hex string: " + str);
			}
			result = result * 16 + val;
		}
		return result;
	}

	public static String plainTextToHexString(String plainText) {
		if (plainText == null) {
			return null;
		}

		char[] chars = plainText.toCharArray();
		StringBuilder hex = new StringBuilder();
		for (char ch : chars) {
			hex.append(Integer.toHexString((int) ch));
		}

		// pad with 0s until length of 32
		while (hex.length() < 32) {
			hex.append("0");
		}
		
		
		return hex.toString();
	}

	public static String hexToString(String hex) {
		// check for null
		if (hex == null || hex.length() == 0) {
			return "";
		}

		// remove padding zeros
		while (hex.charAt(hex.length() - 1) == '0') {
			hex = hex.substring(0, hex.length() - 1);
		}

		StringBuilder output = new StringBuilder("");
		for (int i = 0; i < hex.length(); i += 2) {
			String str = hex.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}
		
		
		return output.toString();
	}

}