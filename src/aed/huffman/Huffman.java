package aed.huffman;

import es.upm.aedlib.Position;
import es.upm.aedlib.tree.BinaryTree;
import es.upm.aedlib.tree.LinkedBinaryTree;

/**
 * Defines metodos for Huffman encoding of text strings.
 */
public class Huffman {
	private LinkedBinaryTree<Character> huffmanTree;

	public Huffman(LinkedBinaryTree<Character> huffmanTree) {
		// NO CAMBIA ESTE METODO!!! Esta usado durante las pruebas
		this.huffmanTree = huffmanTree;
	}

	/**
	 * Creates a Huffman tree (and stores it in the attribute huffmanTree). The
	 * shape of the (binary) tree is defined by the array of char-codes.
	 */

	public Huffman(CharCode[] paths) {
		this.huffmanTree = new LinkedBinaryTree<Character>();
		this.huffmanTree.addRoot(' ');
		for (CharCode path : paths) {
			Character letra = path.getCh();
			String codigo = path.getCode();
			Position<Character> pos = this.huffmanTree.root();
			for (int i = 0; i < codigo.length(); i++) {
				Character bit = codigo.charAt(i);
				if (bit == '0') {
					if (!this.huffmanTree.hasLeft(pos))
						this.huffmanTree.insertLeft(pos, ' ');
					pos = this.huffmanTree.left(pos);
				} else {
					if (!this.huffmanTree.hasRight(pos))
						this.huffmanTree.insertRight(pos, ' ');
					pos = this.huffmanTree.right(pos);
				}
			}
			this.huffmanTree.set(pos, letra);
		}
	}
	//////////////////////////////////////////////////////////////////////

	/**
	 * Huffman encodes a text, returning a new text string containing only
	 * characters '0' and '1'.
	 */
	public String encode(String text) {
		String res = "";
		for (int i = 0; i < text.length(); i++) {
			String path = null;
			res = res + findCharacterCode(text.charAt(i), this.huffmanTree, this.huffmanTree.root(), path);
		}
		return res;
	}
	private String findCharacterCode(Character ch, BinaryTree<Character> tree, Position<Character> pos, String path) {
		if (pos.element().equals(ch)) return path;
		 else {
			if (tree.hasLeft(pos))
				path += findCharacterCode(ch, tree, tree.left(pos), path + '0');
			if (tree.hasRight(pos) && path == null)
				path = findCharacterCode(ch, tree, tree.right(pos), path + '1');
		}
		return path;
	}

	//////////////////////////////////////////////////////////////////////

	/**
	 * Given the Huffman encoded text argument (a string of only '0' and '1's),
	 * returns the corresponding normal text.
	 */
	public String decode(String encodedText) {
		String res = "";
		Position<Character> pos = this.huffmanTree.root();
		for (int i = 0; i < encodedText.length(); i++) {
			Character bit = encodedText.charAt(i);
			if (bit == '0') {
				if (!this.huffmanTree.left(pos).element().equals(' ')) {
					res += huffmanTree.left(pos).element();
					pos = this.huffmanTree.root();
				} else {
					pos = this.huffmanTree.left(pos);
				}
			} else {
				if (!this.huffmanTree.right(pos).element().equals(' ')) {
					res += huffmanTree.right(pos).element();
					pos = this.huffmanTree.root();
				} else {
					pos = this.huffmanTree.right(pos);
				}
			}
		}
		return res;
	}
}



