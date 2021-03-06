package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author William Cullian
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    private int maxWordSize;
    private long totalNumCharacters;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
		size = 0;
		maxWordSize = 0;
		totalNumCharacters = 0;
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
		// iterate thru trie adding each char
		TrieNode curr = root;
	    for (int i = 0; i < word.length(); i++) {
	    	char c = word.toLowerCase().charAt(i);
			curr.insert(c);
			curr = curr.getChild(c);
		}
	    // check if it already existed
	    if (curr.endsWord()) {
			return false;
		}
	    // we add a new one
	    else{
	    	curr.setEndsWord(true);
	    	size++;
	    	// keep track of max word length and num chars
	    	totalNumCharacters += word.length();
	    	if(word.length() > maxWordSize){
	    		maxWordSize = word.length();
	    	}
	    	
	    	return true;
	    }
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    return size;
	}
	
	
	/** 
	 * Return length of the longest word in the dictionary.
	 */
	public int longestWord()
	{
	    return maxWordSize;
	}
	
	
	/** 
	 * Return the average length of words in the dictionary.
	 */
	public double averageWordLength()
	{
	    return (double)totalNumCharacters / size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
	    TrieNode curr = root;
	    for (int i = 0; i < s.length(); i++) {
			char c = s.toLowerCase().charAt(i);
			curr = curr.getChild(c);
			if(curr == null){
				return false;
			}
		}
	    if(curr.endsWord()){
	    	return true;
	    }
		return false;
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
 		List<String> completions = new ArrayList<String>();
	   	if (numCompletions <= 0) {
			return completions;
	   	}
		TrieNode stem = root;
		for (int i = 0; i < prefix.length(); i++) {
			char c = prefix.toLowerCase().charAt(i);
			stem = stem.getChild(c);
			if (stem == null) {
				return completions;
			}
		}
		if (stem.getValidNextCharacters() == null) {
			return completions;
		}
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
		
		// level order search
 		Queue <TrieNode> q = new LinkedList<TrieNode>();
 		q.add(stem);
 		while (!q.isEmpty()) {
			TrieNode curr = q.remove();
			if (curr != null) {
				// add word to completion list
				if (curr.endsWord()) {
					completions.add(curr.getText());					
				}
				// add chilins to queue
				Set<Character> chilins = curr.getValidNextCharacters();
				for (Character character : chilins) {
					q.add(curr.getChild(character));
				}
			}			
			// check limit
			if (completions.size() >= numCompletions) {
				return completions;
			}
		}
// 		System.out.println("Longest word: " + longestWord() + "\tAverage word length: " + averageWordLength());
        return completions;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}