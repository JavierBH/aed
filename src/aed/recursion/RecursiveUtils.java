package aed.recursion;

import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.*;


public class RecursiveUtils {


  /**
   * Return a^n. 
   * @return a^n.
   */
  public static int power(int a, int n) {
	  int res = 1;
	  if (n == 0) return 1;
	  if (a == 0 ) return 0;
	  else if (n>0) return power (a,n,n,res);
	  else return 1/ power (a,n,n,res);
  }
  
  public static int power(int a, int n, int i, int res) {
	  if(i == 0) return res;
	  else return  power (a,n,i-1,res = res * a);
	  }
  
  /**
   *  Returns true if the list parameter does not contain a null element.
   * @return true if the list does not contain a null element.
   */
  public static <E> boolean allNonNull(PositionList<E> l) {
	  if(l.isEmpty()) return true;
	  return allNonNull(l,l.first());
  }
  
  public static <E> boolean allNonNull(PositionList<E> l, Position<E> cursor) {
	  
	  if(cursor == null) return true;
	  if(cursor.element() == null) return false;
	  else return allNonNull(l, l.next(cursor));
	  }
  /**
   *  Returns the number of non-null elements in the parameter list.
   * @return the number of non-null elements in the parameter list.
   */
  public static <E> int countNonNull(PositionList<E> l) {
	  int contador = 0;
	  if(l.isEmpty()) return 0;
	  return countNonNull(l,l.first(), contador);
  }
  
  public static <E> int countNonNull(PositionList<E> l, Position<E> cursor, int contador) {
	  if(cursor == null) return contador;
	  if(cursor.element() == null) return countNonNull(l, l.next(cursor), contador);
	  else return countNonNull(l, l.next(cursor), contador+=1);
  }
}
