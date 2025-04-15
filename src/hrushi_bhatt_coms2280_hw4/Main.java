package hrushi_bhatt_coms2280_hw4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Asks user for the Filename 
 * Pulls file 
 * Finds the pattern 
 * Encodes message
 * Calls msgtree
/**
 * 
 * @author Hrushi Bhatt
 *
 */
public class Main {
	
	public static void main(String[] args) throws IOException {
		System.out.println("Please enter filename to decode:");
		Scanner sc = new Scanner(System.in);
		String fileName = sc.nextLine();
		sc.close();

		
		String content = new String(Files.readAllBytes(Paths.get(fileName))).trim();
		int pos = content.lastIndexOf('\n');
		String pattern = content.substring(0, pos); 
		String binCode = content.substring(pos).trim(); // encoded message
		//MsgTree obj = new MsgTree(pattern);
		//PrintpreTree(obj);
		
		Set<Character> chars = new HashSet<>();
		for (char c : pattern.toCharArray()) {
			if (c != '^') {
				chars.add(c);
			}
		}
		String chardict = chars.stream().map(String::valueOf).collect(Collectors.joining());

		
		MsgTree root = new MsgTree(pattern);
		MsgTree.printCodes(root, chardict);
		root.decode(root, binCode);
	}
}