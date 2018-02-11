package aed.mergelists;

import es.upm.aedlib.Position;
import es.upm.aedlib.indexedlist.ArrayIndexedList;
import es.upm.aedlib.indexedlist.IndexedList;
import es.upm.aedlib.positionlist.NodePositionList;
import es.upm.aedlib.positionlist.PositionList;
import java.util.Comparator;

public class MergeLists {

	/**
	 * Merges two lists ordered using the comparator cmp, returning a new
	 * ordered list.
	 * 
	 * @returns a new list which is the ordered merge of the two argument lists
	 */

	public static <E> PositionList<E> merge(final PositionList<E> l1, final PositionList<E> l2,
			final Comparator<E> comp) {
		PositionList<E> resultado = new NodePositionList<E>();
		Position<E> cursor = l1.first();
		Position<E> cursorSecun = l2.first();

		for (int pos = 0; pos < (l1.size() + l2.size()); pos++) {
			if (pos < l1.size()) {
				resultado.addLast(cursor.element());
				cursor = l1.next(cursor);
			} else {
				resultado.addLast(cursorSecun.element());
				cursorSecun = l2.next(cursorSecun);
			}
		}

		for (Position<E> cursor1 = resultado.first(); cursor1 != null
				&& resultado.next(cursor1) != null; cursor1 = resultado.next(cursor1)) {
			for (Position<E> cursor2 = resultado.next(cursor1); cursor2 != null; cursor2 = resultado.next(cursor2)) {
				if (comp.compare(cursor2.element(), cursor1.element()) < 0) {
					E aux = cursor1.element();
					resultado.set(cursor1, cursor2.element());
					resultado.set(cursor2, aux);
				}

			}
		}
		return resultado;
	}

	/**
	 * Merges two lists ordered using the comparator cmp, returning a new
	 * ordered list.
	 * 
	 * @returns a new list which is the ordered merge of the two argument lists
	 */

	public static <E> IndexedList<E> merge(final IndexedList<E> l1, final IndexedList<E> l2, final Comparator<E> comp) {

		int poslista2 = 0;
		E aux;
		IndexedList<E> resultado = new ArrayIndexedList<E>();
		for (int posicion = 0; posicion < (l1.size() + l2.size()); posicion++) {
			if (posicion < l1.size()) {
				resultado.add(posicion, l1.get(posicion));
			} else {
				resultado.add(posicion, l2.get(poslista2));
				poslista2++;
			}
		}

		for (int i = 0; i < resultado.size() - 1; i++) {
			for (int j = 0; j < resultado.size() - i - 1; j++) {
				if (comp.compare(resultado.get(j + 1), resultado.get(j)) < 0) {
					aux = resultado.get(j + 1);
					resultado.set(j + 1, resultado.get(j));
					resultado.set(j, aux);
				}
			}
		}

		return resultado;
	}
}
