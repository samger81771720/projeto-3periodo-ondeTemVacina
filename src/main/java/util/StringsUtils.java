package util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class StringsUtils {
	
	public static String cifrar(String textoOriginal) {
		return Hashing.sha256()
				  .hashString(textoOriginal, StandardCharsets.UTF_8)
				  .toString();
	}

}
