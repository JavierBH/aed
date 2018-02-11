package aed.find;

import es.upm.aedlib.tree.Tree;
import java.util.Iterator;
import es.upm.aedlib.Position;

public class Find {
	/**
	 * Busca ficheros con nombre igual que fileName dentro el arbol directory, y
	 * devuelve un PositionList con el nombre completo de los ficheros
	 * (incluyendo el camino).
	 */
	public static void find(String fileName, Tree<String> directory) {
		String caminoActual = "";
		Position<String> cursor = directory.root();
		findInPos(cursor, caminoActual, fileName, directory);
	}

	private static void findInPos(Position<String> cursor, String caminoActual, String fileName,
			Tree<String> directory) {
		if (directory.root().element().equals(fileName))
			Printer.println("/" + directory.root().element());
		for (Position<String> w : directory.children(cursor)) {
			System.out.println(w.element());
			if (w.element().equals(fileName) && caminoActual.equals("")) {
				caminoActual = w.element().toString();
				for (Position<String> aux = w; !directory.isRoot(aux); aux = directory.parent(aux)) {
					caminoActual = directory.parent(aux).element().toString() + "/" + caminoActual;
				}
			}
			findInPos(w, caminoActual, fileName, directory);
		}
		if (caminoActual.equals(""))
			return;
		else
			Printer.println("/" + caminoActual);
	}
}
