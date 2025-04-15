package hrushi_bhatt_coms2280_hw4;

import java.util.Stack;


/**
 * 
 * @author Hrushi Bhatt
 *
 */
public class MsgTree {
	public char payloadChar;
	public MsgTree left;
	public MsgTree right;
	
	

	/**
	 * Constructor building the tree from a string
	 * 
	 * @param encodingString string pulled from data file
	 */
	public MsgTree(String encodingString) {
		if (encodingString == null || encodingString.length() < 2) {
	        return;
	    }
	    // Create a stack to keep track of the nodes in the tree.
	    Stack<MsgTree> stk = new Stack<>();
	    int idx = 0;
	    this.payloadChar = encodingString.charAt(idx++);
	    stk.push(this);
	    MsgTree cur = this;
	    String lastOpt = "in";
	    // Loop through the remaining characters in the encoding string.
	    while (idx < encodingString.length()) {
	        MsgTree node = new MsgTree(encodingString.charAt(idx++));
	        // If the last operation was an insertion, attach the new node as the left child.
	        if (lastOpt.equals("in")) {
	            cur.left = node;
	            // If the new node's payload character is '^' push it onto the stack and continue inserting.
	            if (node.payloadChar == '^') {
	                cur = stk.push(node);
	                lastOpt = "in";
	            } 
	            // Otherwise, pop from the stack to move back up the tree.
	            else {
	                if (!stk.empty())
	                    cur = stk.pop();
	                lastOpt = "out";
	            }
	        } 
	        // If the last operation was moving up the tree, attach the new node as the right child.
	        else { 
	            cur.right = node;
	            // If the new node's payload character is '^', push it onto the stack and continue inserting.
	            if (node.payloadChar == '^') {
	                cur = stk.push(node);
	                lastOpt = "in";
	            } 
	            else {
	                if (!stk.empty())
	                    cur = stk.pop();
	                lastOpt = "out";
	            }
	        }
	    }
	}

	/**
	 * Constructor for a single node with null children
	 * 
	 * @param payloadChar
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
		this.right = null;
		this.left = null;
	}

	/**
	 * Method to print characters and their binary codes
	 * 
	 * @param root
	 * @param code
	 */
	public static void printCodes(MsgTree root, String code) {
		System.out.println("character code\n-------------------------");
		for (char ch : code.toCharArray()) {
			getCode(root, ch, binCode = "");
			System.out.println("    " + (ch == '\n' ? "\\n" : ch + " ") + "    " + binCode);
		}
	}

	private static String binCode;

	/**
	 * Gets code and recursivly calls itself setting the alphabet
	 * 
	 * @param root
	 * @param ch
	 * @param path
	 * @return boolean
	 */
	private static boolean getCode(MsgTree root, char ch, String path) {
		if (root != null) {
	        if (root.payloadChar == ch) {
	            binCode = path;
	            return true; // Indicate that the target character has been found.
	        }
	        // Recursively search the left subtree, appending '0' to the path for left traversal,
	        // or the right subtree, appending '1' to the path for right traversal.
	        return getCode(root.left, ch, path + "0") || getCode(root.right, ch, path + "1");
	    }
	    return false;
	}

	/**
	 * Decodes message using the pulled code alphabet
	 * 
	 * @param codes
	 * @param msg
	 */
	public void decode(MsgTree codes, String msg) {
		System.out.println("MESSAGE:");
	    // Initialize the current node to the root of the code tree.
	    MsgTree cur = codes;
	    // Create a StringBuilder to construct the decoded message.
	    StringBuilder sb = new StringBuilder();
	    // Loop through each character in the encoded message string.
	    for (int i = 0; i < msg.length(); i++) {
	        // Determine whether to move left or right in the tree based on the character 
	        char ch = msg.charAt(i);
	        cur = (ch == '0' ? cur.left : cur.right);
	        // Check if the current node is a leaf node
	        if (cur.payloadChar != '^') {
	            getCode(codes, cur.payloadChar, binCode = "");
	            sb.append(cur.payloadChar);
	            cur = codes;
	        }
	    }
	    System.out.println(sb.toString());
	    statistc(msg, sb.toString());
	}

	/**
	 * Extra credit- statistics. Pulls the encoded and decoded strings data to print
	 * 
	 * @param encodeStr
	 * @param decodeStr
	 */
	private void statistc(String encodeStr, String decodeStr) {
		System.out.println("STATISTICS:");
		System.out.println(String.format("Avg bits/char:\t%.1f", encodeStr.length() / (double) decodeStr.length()));
		System.out.println("Total characters:\t" + decodeStr.length());
		System.out.println(
				String.format("Space savings:\t%.1f%%", (1d - decodeStr.length() / (double) encodeStr.length()) * 100));
	}
}
