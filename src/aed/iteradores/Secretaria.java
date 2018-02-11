package aed.iteradores;

import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.*;
import java.util.Iterator;

/**
 * Administra una coleccion de asignaturas.
 */
public class Secretaria {
	private Iterable<AsignaturaAdmin> asignaturas;

	/**
	 * Empieza administrar una coleccion de asignaturas.
	 */
	public Secretaria(Iterable<AsignaturaAdmin> asignaturas) {
		this.asignaturas = asignaturas;
	}

	private AsignaturaAdmin findAsignatura(String asignatura) {
		Iterator<AsignaturaAdmin> it = asignaturas.iterator();
		AsignaturaAdmin res = null;
		while (it.hasNext() && res == null) {
			AsignaturaAdmin admin = it.next();
			if (admin.getNombreAsignatura().equals(asignatura)) {
				res = admin;
			}
		}
		return res;
	}

	private AsignaturaAdmin getAsignatura(String asignatura) throws InvalidAsignaturaException {
		AsignaturaAdmin admin = findAsignatura(asignatura);
		if (admin == null)
			throw new InvalidAsignaturaException();
		else
			return admin;
	}

	/**
	 * Matricula una coleccion de alumnos (representados por el parametro
	 * matriculas) en una asignatura.
	 * 
	 * @return los números de matricula de los alumnos matriculados.
	 * @throws InvalidAsignaturaException
	 *             si la asignatura no está siendo administrada por la
	 *             secretaría.
	 */
	public Iterable<String> matricular(String asignatura, Iterable<String> matriculas)
			throws InvalidAsignaturaException {
		return getAsignatura(asignatura).matricular(matriculas);
	}

	/**
	 * Desmatricula una coleccion de alumnos (representados por el parametro
	 * matriculas) de una asignatura.
	 * 
	 * @return las matriculas desmatriculados (que debían estar matriculados y
	 *         no tener nota).
	 * @throws InvalidAsignaturaException
	 *             si la asignatura no está siendo administrado por la
	 *             secretaria.
	 */
	public Iterable<String> desMatricular(String asignatura, Iterable<String> matriculas)
			throws InvalidAsignaturaException {
		return getAsignatura(asignatura).desMatricular(matriculas);
	}

	/**
	 * Calcula la nota media de un alumno (representado por su identificador de
	 * matricula) en todas asignaturas en las que esta matriculado. Si el alumno
	 * no tiene ninguna nota en ninguna asignatura, el metodo debe devolver 0.
	 * 
	 * @return la nota media del alumn
	 */

	public double notaMediaExpediente(String matricula) throws InvalidMatriculaException {
		int contador = 0;
		double notas = 0;
		AsignaturaAdmin elemento;
		Iterator<AsignaturaAdmin> iterador = asignaturas.iterator();
		while (iterador.hasNext()) {
			elemento = iterador.next();
			try {
				if (elemento.tieneNota(matricula) && elemento.estaMatriculado(matricula)) {
					notas += (double) elemento.getNota(matricula);
					contador++;
				}
			} catch (InvalidMatriculaException e) {
			}
		}
		if (contador != 0)
			return (notas / contador);
		else
			return 0;
	}

	/**
	 * Devuelve el nombre de la asignatura que tiene la mejor nota media,
	 * calculada usando las notas de todos los alumnos que tienen notas para esa
	 * asignatura. Si la secretaria no esta administrando ninguna asignatura, el
	 * metodo debe devolver null. Similarmente, si ningún alumno tiene nota en
	 * ninguna asignatura, el metodo tambien debe devolver null.
	 * 
	 * @return el nombre de la asignatura con la mejor nota media.
	 */

	public String mejorNotaMedia() {
		boolean bool = false;
		AsignaturaAdmin elemento = null;
		double aux = 0;
		String sol = null;
		Iterator<AsignaturaAdmin> iterador = asignaturas.iterator();
		while (iterador.hasNext()) {
			elemento = iterador.next();
			if (aux < elemento.notaMedia()) {
				aux = elemento.notaMedia();
				sol = elemento.getNombreAsignatura();
				bool = true;
			}
		}
		if (bool)
			return sol;
		else
			return null;
	}

	/**
	 * Devuelve todas las notas de un alumno (representado por su identificador
	 * de matrícula) como una coleccion de objetos Pair(NombreAsignatura,
	 * Nota).
	 * 
	 * @return una coleccion de pares de las notas de la matricula en todas las
	 *         asignaturas.
	 */


 public Iterable<Pair<String,Integer>> expediente(String matricula) {
		PositionList<Pair<String,Integer>> lista = new NodePositionList<Pair<String,Integer>>();
		Iterator<AsignaturaAdmin> iterador = asignaturas.iterator();	
		while(iterador.hasNext()){
			AsignaturaAdmin elemento = iterador.next();
			try {
				if(elemento.estaMatriculado(matricula) && elemento.tieneNota(matricula)){	
					Pair<String, Integer> aux = new Pair<String, Integer>("",0) ;
					aux.setLeft(elemento.getNombreAsignatura());
					aux.setRight(elemento.getNota(matricula));
					lista.addFirst(aux);
				}
			} catch (InvalidMatriculaException e) {
			}
		}
		return lista;
	}
	/**
	 * Devuelve una coleccion con todas los pares de asignaturas --
	 * representados como Pair(NombreAsignatura1, NombreAsignatura2) -- que no
	 * tienen alumnos en comun. El metodo NO debe devolver nunca un par
	 * Pair(NombreAsignatura,NombreAsignatura), es decir, con nombres iguales de
	 * asignaturas. Si dos asignaturas A1 y A2 no tienen ningún alumno en
	 * común, para ellas se puede devolver: (i) Pair(A1,A2), o (ii)
	 * Pair(A1,A2), Pair(A2,A1), o (iii) Pair(A2,A1).
	 * 
	 * @return una coleccion que contiene todos los pares de asignaturas que no
	 *         tienen ningún alumno en comun.
	 */

	public Iterable<Pair<String, String>> asignaturasNoConflictivas() {
		Iterator<AsignaturaAdmin> iterador1 = asignaturas.iterator();
		PositionList<Pair<String, String>> lista = new NodePositionList<Pair<String, String>>();
		while (iterador1.hasNext()) {
			AsignaturaAdmin asignatura1 = iterador1.next();
			Iterator<AsignaturaAdmin> iterador2 = asignaturas.iterator();
			AsignaturaAdmin asignatura2;
			while (iterador2.hasNext()) {
				asignatura2 = iterador2.next();
				if (!this.compartenAlumnos(asignatura1, asignatura2)) {
					Pair<String, String> aux = new Pair<String, String>("", "");
					aux.setLeft(asignatura1.getNombreAsignatura());
					aux.setRight(asignatura2.getNombreAsignatura());
					lista.addFirst(aux);
				}
			}
		}
		return lista;
	}

	/**
	 * Devuelve true si dos asignaturas a1 y a2 tienen algun alumno en comun.
	 * 
	 * @return true si las dos asignaturas no tienen ningun alumno en comun.
	 */

	private boolean compartenAlumnos(AsignaturaAdmin a1, AsignaturaAdmin a2) {

		boolean resultado = a1.getNombreAsignatura().equals(a2.getNombreAsignatura());
		if (resultado)
			return resultado;
		
		resultado = false;
		Iterator<String> iterador1 = a1.matriculados().iterator();
		String matriculado1, matriculado2;
		while (iterador1.hasNext() && !resultado) {
			matriculado1 = iterador1.next();
			Iterator<String> iterador2 = a2.matriculados().iterator();
			while (iterador2.hasNext() && !resultado) {
				matriculado2 = iterador2.next();
				if (matriculado1.equals(matriculado2)) {
					resultado = true;
				}
			}
		}
		return resultado;
	}
}
