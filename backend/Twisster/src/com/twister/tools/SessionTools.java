package com.twister.tools;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SessionTools {

	
	public static String generateKey(int idUser, String login) {
		List<Character> list = IntStream
				.range(0,
						(IntStream.rangeClosed('A', 'Z').mapToObj(c -> "" + (char) c).collect(Collectors.joining())
								+ "1234567890" + idUser + login).toCharArray().length)
				.mapToObj(
						i -> (IntStream.rangeClosed('A', 'Z').mapToObj(c -> "" + (char) c).collect(Collectors.joining())
								+ "1234567890" + idUser + login).toCharArray()[i])
				.collect(Collectors.toList());
		
		Collections.shuffle(list);
		String str = "";
		for (Character character : list) {
			str = str + character;
		}
		return str;
	}
	
	
}
