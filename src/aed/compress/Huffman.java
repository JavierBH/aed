package aed.compress;

import es.upm.aedlib.Position;
import es.upm.aedlib.Entry;
import es.upm.aedlib.tree.*;
import es.upm.aedlib.priorityqueue.*;

public class Huffman {
	private BinaryTree<Character> huffmanTree;

	public Huffman(String text) {
		this.huffmanTree = constructHuffmanTree(text);
	}

	private BinaryTree<Character> constructHuffmanTree(String text) {
		int [] charCode = countChars(text);
		PriorityQueue<Integer,AttachableLinkedBinaryTree<Character>> Q = new SortedListPriorityQueue<Integer, AttachableLinkedBinaryTree<Character>>();
		for(int i = 0; i < charCode.length; i++){
			if(charCode[i] > 0){		
				AttachableLinkedBinaryTree<Character> tree = new AttachableLinkedBinaryTree<Character>();
				tree.addRoot((char) i);
				Q.enqueue(charCode[i], tree);
			}
		}
		while(Q.size() > 1){
			Entry<Integer, AttachableLinkedBinaryTree<Character>> left = Q.dequeue();
			Entry<Integer, AttachableLinkedBinaryTree<Character>> right = Q.dequeue();
			AttachableLinkedBinaryTree<Character> T = new AttachableLinkedBinaryTree<Character>();
			T.addRoot(' ');
			T.attach(T.root(), left.getValue(), right.getValue());
			Q.enqueue(left.getKey() + right.getKey(), T);
		}
		return  Q.dequeue().getValue();
	}
	
	public static int[] countChars(String text) {
		int[] res = new int[256];
		for (int i = 0; i < text.length(); i++) {
			res[text.codePointAt(i)] += 1;
		}
		return res;
	}

}
