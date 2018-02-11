package aed.secretaria;

import java.util.Comparator;

import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.positionlist.NodePositionList;

/**
 * Organizes the administration for an asignatura. An asignatura has a name
 * (e.g., "AED"), and the class keeps track of matriculated alumnos, and of
 * assigned notas for alumnos.
 */
public class AsignaturaAdmin {
	// Name of asignatura
	private String nombreAsignatura;

	// A list of pairs of matricula (String) and notas (integer)
	// Note that the nota should be null if no nota has
	// been assigned yet.
	private PositionList<Pair<String, Integer>> notas;

	/**
	 * Creates an asignatura administration object, where the asignatura has a
	 * name (e.g. "AED"), and which keeps tracks of matriculated alumnos (their
	 * matriculas), and assigned notas.
	 */

	public AsignaturaAdmin(String nombreAsignatura) {
		this.nombreAsignatura = nombreAsignatura;
		this.notas = new NodePositionList<Pair<String, Integer>>();
	}

	private Position<Pair<String, Integer>> busMatricula(String matricula) {
		Position<Pair<String, Integer>> cursor = notas.first();
		for (; cursor != null
				&& !cursor.element().getLeft().equals(matricula); cursor = notas.next(cursor));
		return cursor;
	}


	/**
	 * Returns the asignatura name.
	 * 
	 * @return the asignatura name.
	 */

	public String getNombreAsignatura() {
		return this.nombreAsignatura;
	}

	/**
	 * Adds a number of matriculas to the asignatura. Returns a list of the
	 * matriculas that were added, i.e., the list of matriculas which had not
	 * previously been added.
	 * 
	 * @return a list with the matriculas added.
	 */

	public PositionList<String> matricular(PositionList<String> matriculas) {
		PositionList<String> resultado = new NodePositionList<String>();
		for (Position<String> cursor = matriculas.first(); cursor != null
				&& cursor.element() != null; cursor = matriculas.next(cursor)) {
			if (busMatricula(cursor.element()) == null) {
				Pair aux = new Pair(cursor.element(), null);
				notas.addFirst(aux);
				resultado.addFirst(cursor.element());
			}
		}
		return resultado;
	}

	/**
	 * Removes a list of matriculas from the asignatura. Returns a list with the
	 * matriculas which were successfully removed. A matricula can be removed
	 * IF: i) the matricula was previously added and has not been removed since,
	 * AND ii) there is NO nota associated with the matricula.
	 * 
	 * @return a list with the matriculas that were removed.
	 */

	 public PositionList<String> desMatricular(PositionList<String> matriculas) {
		PositionList<String> aux = new NodePositionList<String>();
		for(Position<String> cursor = matriculas.first();cursor != null; cursor = matriculas.next(cursor)){
			if(busMatricula(cursor.element())!=null && busMatricula(cursor.element()).element().getRight() == null){
				aux.addFirst((busMatricula(cursor.element())).element().getLeft());
				notas.remove(busMatricula(cursor.element()));
			}
		}
		return aux;
	}

	/**
	 * Checks whether a matricula has been added to the asignatura.
	 * 
	 * @return true if the matricula has been added, false otherwise.
	 */

	 public boolean estaMatriculado(String matricula) {
			boolean esta = false;
			if(busMatricula(matricula)!= null){
				esta = true;
			}
			return esta;
		}

	/**
	 * Checks whether a matricula has received a nota. 
	 * @return true if the matricula has a nota, and false otherwise.
	 * @throws InvalidMatriculaException
	 *             if the matricula has not been added to the asignatura (or was
	 *             removed)
	 */
	
	public boolean tieneNota(String matricula) throws InvalidMatriculaException {
		if (busMatricula(matricula) == null) {
			throw new InvalidMatriculaException();
		}
		return busMatricula(matricula).element().getRight() != null;

	}
	/**
	 * Returns the nota of a matricula.
	 * 
	 * @return the nota of an matrciula.
	 * @throws InvalidMatriculaException
	 *             if the matricula has no nota assigned, or the matricula has
	 *             not been added to the asignatura (or was removed).
	 */
	
	public int getNota(String matricula) throws InvalidMatriculaException {
		if(busMatricula(matricula) == null || tieneNota(matricula) == false){
			throw new InvalidMatriculaException();
		}
		return busMatricula(matricula).element().getRight();
	}

	/**
	 * Assigns a nota for a matricula.
	 * 
	 * @throws InvalidMatriculaException
	 *             if the matricula has not been added to the asignatura (or was
	 *             removed).
	 */

	public void setNota(String matricula, int nota) throws InvalidMatriculaException {
		if(busMatricula(matricula) == null){
			throw new InvalidMatriculaException();
		}
		busMatricula(matricula).element().setRight(nota);
	}
	
	/**
	 * Returns a list with the matriculas who has a nota between the minimum
	 * nota minNota (including it) and the maximum nota maxNota (including it).
	 * 
	 * @return a list with the matriculas with notas between (including)
	 *         minNota...maxNota.
	 */
	
	public PositionList<String> alumnosEnRango(int minNota, int maxNota) {
		PositionList<String> res = new NodePositionList<String>();
		for (Position<Pair<String, Integer>> cursor = notas.first(); cursor != null; cursor = notas.next(cursor)) {
		if(cursor.element().getRight()!= null && cursor.element().getRight() >= minNota && 
				cursor.element().getRight() <= maxNota){ 
			  res.addFirst(cursor.element().getLeft());
			}
		}
		return res;
	}

	/**
	 * Calculates the average grade of the notas in the asignatura. NOTE. Does
	 * not count alumnos (matriculas) that have not been assigned a nota. NOTE.
	 * The average grade for an empty set of notas is defined to be 0.
	 * 
	 * @return the average grade of the asignatura.
	 */

	public double notaMedia() {
		int contador = 0;
		double notas = 0;
		for (Position<Pair<String, Integer>> cursor = this.notas.first(); cursor != null; cursor = this.notas.next(cursor)) {
			if (cursor.element().getRight() != null) {
				notas += (double) cursor.element().getRight().intValue();
				contador++;
			}
		}
		if (contador != 0) {
			return (notas / contador);
		} else {
			return 0;
		}
	}

}
