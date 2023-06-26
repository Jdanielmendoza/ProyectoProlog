/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.clausula;

/**
 *
 * @author jdani
 */
public class clausula implements InterfazClausula {

    String Head;
    String[] Body;

    public clausula(clausula nuevaClausula) {
        this.Head = nuevaClausula.Head;
        this.Body = nuevaClausula.Body;
    }

    public clausula(String Head, String[] Body) {
        this.Head = Head;
        this.Body = Body;
    }

    public clausula(String[] Clausula) {
        this.Head = Clausula[0];
        for (int i = 1; i < Clausula.length; i++) {
            this.Body[i - 1] = Clausula[i];
        }
    }

    public clausula(String Clausula) {
        Clausula = Clausula.trim();
        Clausula = Clausula.replace(" ", "");
        Clausula = Clausula.replace(":-", ",");
        Clausula = Clausula.replace(".", "");
        String[] arregloDeLaClausula = Clausula.split(",");

        this.Body = new String[arregloDeLaClausula.length - 1];

        this.Head = arregloDeLaClausula[0];
        for (int i = 1; i < arregloDeLaClausula.length; i++) {
            this.Body[i - 1] = arregloDeLaClausula[i];
        }
    }

    public String getHead() {
        return Head;
    }

    public String[] getBody() {
        return Body;
    }

    @Override
    public boolean esUnHecho() {
        return this.Body.length == 0;
    }

    @Override
    public boolean esFail() {  // ?   nose si implementarlo en esta clase o en la otra 
        for (int i = 0; i < this.Body.length; i++) {
            if ("fail".equals(this.Body[i].toLowerCase())) {
                return true;
            }
            if ((this.Body.length - i) >= 2) { // a:-fail ,!  o   a:- !,fail
                if ( (("fail".equals(this.Body[i].toLowerCase())) || ("!".equals(this.Body[i].toLowerCase())) ) && ("!".equals(this.Body[i+1]) || "fail".equals(this.Body[i+1].toLowerCase()))) {
                    return true ; 
                }
            }
        }
        return false ; 
    }
    public boolean tieneAlgunCorte() {  
        for (String Body1 : this.Body) {
            if ("!".equals(Body1)) {
                return true;
            }
        }
        return false ; 
    }

    public static boolean sonIguales(clausula A, clausula B) {
        boolean sonIguales = true;
        if ((A.getHead().equals(B.getHead())) && (A.getBody().length) == B.getBody().length) {
            for (int i = 0; i < B.Body.length; i++) {
                if (!A.getBody()[i].equals(B.getBody()[i])) {
                    sonIguales = false;
                }
            }
            return sonIguales;
        } else {
            return !sonIguales;
        }
    }
    
    

    @Override
    public String toString() {
        String clausula = this.Head;
        if (this.Body.length > 0) {
            clausula += " :- ";
            clausula += String.join(",", this.Body).replace(",", ", ");   // es equivalente al for de abajo
//            for (int i = 0; i < this.Body.length ; i++) {
//                if(i == this.Body.length -1){
//                    clausula += this.Body[i]; 
//                }else{
//                    clausula += this.Body[i] + ", ";
//                }
//            }
        }
        return clausula + " .";
    }

}
