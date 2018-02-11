package aed.laberinto;

import es.upm.aedlib.lifo.*;
import java.util.Iterator;

public class Exploradora {

	
	/**
	 * Busca un tesoro en el laberinto, empezando en el lugar inicial:
	 * inicialLugar.
	 * 
	 * @return un Objeto tesoro encontrado, o null, si ningun tesoro existe en
	 *         la parte del laberinto que es alcanzable desde la posicion
	 *         inicial.
	 */
	
	public static Object explora(Lugar inicialLugar) {

		inicialLugar.printLaberinto();
		LIFO<Lugar> faltaPorExplorar = new LIFOList<Lugar>();
		faltaPorExplorar.push(inicialLugar);
		boolean aux = false, encontrado = false;
		Lugar elem = null;
		while (!faltaPorExplorar.isEmpty() && !encontrado) {

			elem = faltaPorExplorar.pop();

			if (elem.sueloMarcadoConTiza()) {
				aux = true;
			}

			if (elem.tieneTesoro()) {
				encontrado = true;
			}

			if (!aux && !encontrado) {
				elem.marcaSueloConTiza();
			}

			Iterator<Lugar> puntero = elem.caminos().iterator();
			while (puntero.hasNext() && !encontrado && !aux) {
				faltaPorExplorar.push(puntero.next());
			}
			aux = false;
		}
		
		if (!encontrado || elem == null)
			return null;
		else
			return elem.getTesoro();
	}
}
