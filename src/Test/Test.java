/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Test;
import controller.baseDeConocimiento.BaseDeConocimiento;
import controller.clausula.clausula; 
/**
 *
 * @author jdani
 */
public class Test {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    //System.out.println( String.join(",", Clausula.getBody()).replace(",", ", ")); // [ a , b ]  -> "a,b"
        
    
        BaseDeConocimiento baseDeConocimiento = new BaseDeConocimiento(); 
        baseDeConocimiento.Agregar(new clausula("  p :- r  "));
        baseDeConocimiento.Agregar(new clausula("  r :- q  "));
        baseDeConocimiento.Agregar(new clausula("  q :-  "));
        baseDeConocimiento.Agregar(new clausula("  d   "));
        baseDeConocimiento.Agregar(new clausula("  a :- fail ,v  , !  "));
        baseDeConocimiento.Agregar(new clausula("  h :- s , t  "));
        baseDeConocimiento.Agregar(new clausula("  s :- !, fail  "));
        baseDeConocimiento.Agregar(new clausula("    :-r, s, t "));
        baseDeConocimiento.Agregar(new clausula("  d :- q , p ,! ,r  , s  "));
        baseDeConocimiento.Agregar(new clausula("  a :- b ,c ,fail ,d  , !  "));
        clausula c = new clausula("w :- f, !, g .");
        System.out.println(c.tieneAlgunCorte());
        //System.out.println(baseDeConocimiento.Deriva("r"));
        //System.out.println(baseDeConocimiento.getClausula("k"));
         
        //System.out.println(baseDeConocimiento.getClausula(0));
        
    }
    
}
