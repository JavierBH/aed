package aed.actasnotas;

import java.util.Comparator;

import es.upm.aedlib.indexedlist.ArrayIndexedList;
import es.upm.aedlib.indexedlist.IndexedList;


public class ActaNotasAED implements ActaNotas{
	private IndexedList<Calificacion> list = new ArrayIndexedList<Calificacion>();
	int posicion;


	private void ordenador() {
		Calificacion aux;
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = 0; j < list.size() - i - 1; j++) {
				if (list.get(j + 1).getMatricula().compareTo(list.get(j).getMatricula()) < 0) {
					aux = list.get(j + 1);
					list.set(j + 1, list.get(j));
					list.set(j, aux);
				}
			}
		}
	}


	private int buscarMatricula (String matricula){
		int inicio = 0;
		int fin = this.list.size()-1;
		int pos;
		ordenador();
		while(inicio <= fin){
			pos = (inicio + fin)/2;
			if(this.list.get(pos).getMatricula().compareTo(matricula) == 0){
				return pos;
			}
			else if(this.list.get(pos).getMatricula().compareTo(matricula) < 0){
				inicio = pos + 1;
			}
			else{
				fin = pos - 1;
			}
		}
		return -1;	
	}

	@Override
	public void addCalificacion(String nombre, String matricula, int nota) throws CalificacionAlreadyExistsException {
		for(int i = 0; i < this.list.size();i++){
			if(matricula.equals(this.list.get(i).getMatricula())){
				throw new CalificacionAlreadyExistsException();
			}
		}
		Calificacion aux = new Calificacion(nombre, matricula, nota);
		this.list.add(posicion, aux);

		posicion++;

	}

	@Override
	public void updateNota(String matricula, int nota) throws InvalidMatriculaException {
		if(buscarMatricula(matricula) == -1){
			throw new InvalidMatriculaException();
		}
		this.list.get(buscarMatricula(matricula)).setNota(nota);		
	}

	@Override
	public void deleteCalificacion(String matricula) throws InvalidMatriculaException {
		if(buscarMatricula(matricula) == -1){
			throw new InvalidMatriculaException();
		}

		this.list.removeElementAt(buscarMatricula(matricula));

		posicion--;

	}

	@Override
	public Calificacion getCalificacion(String matricula) throws InvalidMatriculaException {		
		if(buscarMatricula(matricula) == -1){
			throw new InvalidMatriculaException();
		}
		return this.list.get(buscarMatricula(matricula));
	}

	@Override
	public IndexedList<Calificacion> getCalificaciones(Comparator<Calificacion> cmp) {
		Calificacion aux;
		for (int i = 0; i < this.list.size() - 1; i++) {
			for (int j = 0; j < this.list.size() - i - 1; j++) {
				if (cmp.compare(this.list.get(j), this.list.get(j + 1)) > 0) {
					aux = this.list.get(j + 1);
					this.list.set(j + 1, this.list.get(j));
					this.list.set(j, aux);
				}
			}
		}
		return this.list;
	}

	@Override
	public IndexedList<Calificacion> getAprobados(int notaMinima) {
		IndexedList<Calificacion> listResultado = new ArrayIndexedList<Calificacion>();
		int posicion = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNota() >= notaMinima) {
				listResultado.add(posicion, list.get(i));
				posicion++;
			}
		}
		return listResultado;
	}

}