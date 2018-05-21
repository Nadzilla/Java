package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	TagNode root=null;
	
	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}
	

	

	
	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object. 
	 * 
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	public void build() {
		/** COMPLETE THIS METHOD **/
		 
		Stack<TagNode> file = new Stack<TagNode>();
		String string = ""; 
		string = sc.nextLine(); 
		 
		if (string.charAt(0) == '<') {
			System.out.println(string);
			root = new TagNode(string.substring(1,string.length()-1), null, null);  
			file.push(root);
			System.out.println(file.peek());
			System.out.println("I am pushing in the stack: " + root); 
		}
		TagNode ptr = root; 
		string = sc.nextLine();
		int loop = 0; 
		while (sc.hasNextLine() && file.isEmpty() == false) {
			if (string.charAt(0) == '<' && string.charAt(1) != '/') { // beginning of a tag 
				loop++; 
				System.out.println(loop);
				System.out.println(file.peek());
				if (file.peek().firstChild == null) {
					ptr.firstChild = new TagNode(string.substring(1,string.length()-1), null, null);
					 
					file.push(ptr.firstChild); 
					System.out.println("I am pushing in the stack: " + ptr.firstChild.toString()); 
					ptr = ptr.firstChild;
					
				} else {
					ptr.sibling = new TagNode(string.substring(1,string.length()-1), null, null);
					
					file.push(ptr.sibling);
					System.out.println("I am pushing in the stack: " + ptr.sibling.toString()); 
					ptr = ptr.sibling; 
					
				}
				
			}
			if (string.charAt(0) == '<' && string.charAt(1) == '/') { // ends a tag
				ptr = file.peek(); 
				System.out.println("removed from stack: " + ptr.toString()); 
				file.pop(); 
			}
			
			if (string.charAt(0) != '<') { // non-tag
				if (file.peek().firstChild == null) {
					ptr.firstChild = new TagNode(string, null, null);
					ptr = ptr.firstChild; 
				} else {
					ptr.sibling = new TagNode(string, null, null);
					ptr = ptr.sibling;
				}
			}
			if (sc.hasNextLine()) {
				string = sc.nextLine();
			}
		}
	}
	
	
	private void traverseReplaceTree(String tag, TagNode peak, String newTag) {
		if (peak == null) { 
			return;
		}
		if (peak.tag.equals(tag)) {
			peak.tag = newTag; 
		 
		}
	
		traverseReplaceTree(tag,peak.firstChild,newTag); 
		traverseReplaceTree(tag,peak.sibling,newTag); 
			
	
		
	}
	
	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	public void replaceTag(String oldTag, String newTag) {
		/** COMPLETE THIS METHOD **/
		traverseReplaceTree(oldTag, root, newTag); 
	}
	
	private void findRow(String tag, TagNode peak, int rowNumber) {
		if (peak == null) { 
			return;
		}
		if (peak.tag.equals(tag)) {
			rowNumber--; 
		}
		
		if (rowNumber == 0) {
			addBold(peak.firstChild); 		
			return; 
		}
		findRow(tag,peak.firstChild,rowNumber); 
		findRow(tag,peak.sibling,rowNumber); 
			
	
		
	}
	
	private void addBold(TagNode child) {
		TagNode temp = child.firstChild;
		child.firstChild = new TagNode("b",null,null); 
		child.firstChild.firstChild = new TagNode(temp.tag,null,temp.sibling); 
		if (child.sibling == null) {
			return; 
		} else {
			addBold(child.sibling); 
		}
	}
	
	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	
	
	public void boldRow(int row) {
		findRow("tr",root,row); 
	}
	
	
	
	
	private TagNode goToNextSibling(TagNode lol) {
		return lol = lol.sibling; 
	}
	
	private void traverseTreeRemove(String tag, TagNode peak) {
		if (peak== null) { 
			return;
		}
		if (peak.firstChild != null && peak.firstChild.tag.equals(tag)) {	
			TagNode temp = peak.firstChild; 
			peak.firstChild = peak.firstChild.firstChild; 
			
			if (temp.tag.equals("ul") || temp.tag.equals("ol")) {

				if (peak.firstChild.tag.equals("li")) 
					peak.firstChild.tag = "p"; 
			}
			if (peak.firstChild.sibling != null) {
				TagNode lol = peak.firstChild.sibling; 
				if (temp.tag.equals("ul") || temp.tag.equals("ol")) {
					if (lol.tag.equals("li")) 
						lol.tag = "p"; 
				}
				while (lol.sibling != null) {
					
					lol = goToNextSibling(peak.firstChild); 
					if (temp.tag.equals("ul") || temp.tag.equals("ol")) {
						if (lol.tag.equals("li")) 
							lol.tag = "p"; 
					}
				}
				if (temp.sibling != null) {
					
					lol.sibling = temp.sibling; 
				}
			} else {
				if (temp.sibling != null) {
					temp.firstChild.sibling = temp.sibling; 
				}
			}
			
			
			
		} 
		if (peak.sibling != null &&peak.sibling.tag.equals(tag)) {
			TagNode temp = peak.sibling; 
			peak.sibling = peak.sibling.firstChild; 
			TagNode lol = peak.sibling; 
			if (temp.tag.equals("ul") || temp.tag.equals("ol")) {
				if (lol.tag.equals("li")) 
					lol.tag = "p"; 
			}
			while (lol.sibling != null) {
				
				lol = goToNextSibling(lol); 
				if (temp.tag.equals("ul") || temp.tag.equals("ol")) {
					if (lol.tag.equals("li")) 
						lol.tag = "p"; 
				}
				
			}
			if (temp.sibling != null) {
				
				lol.sibling = temp.sibling; 
			}
		}
	
		traverseTreeRemove(tag,peak.firstChild); 
		traverseTreeRemove(tag,peak.sibling); 
	}
	
	
	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 * 
	 * 
	 */
	public void removeTag(String tag) {
		traverseTreeRemove(tag,root); 
	}
	
	
	
	private void traverseTreeAdd(String tag, TagNode peak, String word) {
		if (peak == null) { 
			return;
		}
		TagNode ptrChild = peak.firstChild; 
		TagNode ptrSib = peak.sibling; 
		String stringWord = ""; 
		String stringWordEqual = ""; 
		String stringOther = ""; 
		boolean equals = false; 
		Scanner sc1 = new Scanner(peak.tag); 
		while (sc1.hasNext() == true && peak.firstChild == null)
		{
		  stringWord = sc1.next(); 
		  System.out.println(stringWord);
		  char x = stringWord.charAt(stringWord.length()-1); 
		  	System.out.println(x); 
		  boolean punctuation = false; 
		  stringWordEqual = stringWord; 
		  if (x == '.' || x == ',' || x == '?'|| x == '!'|| x == ':'|| x == ';') {
			  System.out.println("Punctutaion: " + stringWord); 
			  stringWordEqual = stringWord.substring(0, stringWord.length()-1);
			  System.out.println("Punctutaion: " + stringWord); 
			  punctuation = true; 
		  }
		  if (stringWordEqual.equalsIgnoreCase(word)  && equals == false) {
			  
			  equals = true; 
			  
			  
			  if (stringOther.length() > 0) {
				  peak.tag = stringOther; 
				  peak.sibling = new TagNode(tag,new TagNode(stringWord,null,null),peak.sibling); 
			  } else {
				// no string before 
				  peak.tag = tag; 
				  peak.firstChild = new TagNode(stringWord,null,null); 
			  }
			  stringOther = ""; 
			
		  } else if (equals == false) {
			  stringOther += stringWord + " "  ; 
		  } else if (equals == true) {
			  stringOther += stringWord + " "  ; 
		  }
		  
		}
		
		if (equals == true && stringOther.length() > 0) {
			System.out.println("string other" + stringOther);
			if (peak.sibling != null) {
				peak.sibling.sibling= new TagNode(stringOther,null,peak.sibling.sibling); 
				ptrSib = peak.sibling.sibling; 
			} else {
				peak.sibling = new TagNode(stringOther,null,null); 
			}
			
		}
		 
		
		sc1.close();
		traverseTreeAdd(tag,ptrChild,word); 
		traverseTreeAdd(tag,ptrSib,word); 
		
	

	}
	
	/**
	 * Adds a tag around all occurrences of a word in the DOM tree.
	 * 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
	
	public void addTag(String word, String tag) {
		/** COMPLETE THIS METHOD **/
		traverseTreeAdd(tag,root,word);
	}
	
	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}
	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|---- ");
			} else {
				System.out.print("      ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}