package aed.zork;

import java.util.Iterator;

import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.*;

public class Explorador {

	public static Pair<Object,PositionList<Lugar>> explora(Lugar inicialLugar){  
		PositionList<Lugar> lista = new NodePositionList<Lugar>();
		lista.addLast(inicialLugar);
		return exploraRec(inicialLugar, lista);
	}

	private static Pair<Object,PositionList<Lugar>> exploraRec(Lugar lugarActual, PositionList<Lugar>lista){	
		if(lugarActual.tieneTesoro()){
			return new Pair<Object, PositionList<Lugar>>(lugarActual.getTesoro(), lista);	 
		}else{
			lugarActual.marcaSueloConTiza();
			Iterator<Lugar> iterador = lugarActual.caminos().iterator();
			while(iterador.hasNext()){
				Lugar caminos = iterador.next();
				if(!caminos.sueloMarcadoConTiza()){
					lista.addLast(caminos);
					return exploraRec(caminos,lista);
				}
			}
		}
		lista.remove(lista.last());
		if(lista.isEmpty()) return null;
		return exploraRec(lista.last().element(),lista);
	}
}