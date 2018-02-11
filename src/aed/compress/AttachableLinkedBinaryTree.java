package aed.compress;

import es.upm.aedlib.Position;
import es.upm.aedlib.tree.*;

public class AttachableLinkedBinaryTree<E> extends LinkedBinaryTree<E> implements AttachableBinaryTree<E> {

	public void attach(Position<E> pos, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
		this.insertLeft(pos, leftTree.root().element());
		this.insertRight(pos, rightTree.root().element());
		insert(this.left(pos), leftTree.root(), leftTree);
		insert(this.right(pos), rightTree.root(), rightTree);
	}

	public void insert(Position<E> pos, Position<E> element, BinaryTree<E> tree) {
		if (tree.hasLeft(element)) {
			this.insertLeft(pos, tree.left(element).element());
			insert(this.left(pos), tree.left(element), tree);
		}
		if (tree.hasRight(element)) {
			this.insertRight(pos, tree.right(element).element());
			insert(this.right(pos), tree.right(element), tree);
		}
	}
}
