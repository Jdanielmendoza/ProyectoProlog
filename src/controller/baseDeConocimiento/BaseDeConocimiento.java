/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.baseDeConocimiento;

import controller.clausula.clausula;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jdani
 */
public class BaseDeConocimiento {

    private List<clausula> baseDeConocimiento;

    public BaseDeConocimiento(List<clausula> baseDeConocimiento) {
        this.baseDeConocimiento = baseDeConocimiento;
    }

    public BaseDeConocimiento() {
        this.baseDeConocimiento = new ArrayList<>();
    }

    public void Agregar(clausula Clausula) {
        if (!"".equals(Clausula.toString())) {
            this.baseDeConocimiento.add(Clausula);
        }
    }

    public clausula getClausula(int index) {
        if (index < this.baseDeConocimiento.size()) {
            return this.baseDeConocimiento.get(index);
        }
        return null;
    }

    public clausula getClausula(String Head) {  // retorna la  primer clausula que coincida con Head
        int i = 0;
        while ((i < this.baseDeConocimiento.size() - 1) && (!this.getClausula(i).getHead().equals(Head))) {
            i++;
        }
        if (this.getClausula(i).getHead().equals(Head)) {
            return this.baseDeConocimiento.get(i);
        } else {
            return null;
        }
//        for ( clausula C : this.baseDeConocimiento) {
//            if(C.getHead() == null ? Head == null : C.getHead().equals(Head)){
//                return C; 
//            }
//            
//        }
    }

    public BaseDeConocimiento remove(int index, BaseDeConocimiento baseDeConocimiento) { //mandar una copia de la BD pero con una clausula menos 
        BaseDeConocimiento nuevaBaseDeConocimiento = new BaseDeConocimiento();
        for (int i = 0; i < baseDeConocimiento.baseDeConocimiento.size(); i++) {
            if (index != i) {
                nuevaBaseDeConocimiento.Agregar(baseDeConocimiento.getClausula(i));
            }
        }
        return nuevaBaseDeConocimiento;
    }

    public BaseDeConocimiento remove(clausula clausulaAEliminar, BaseDeConocimiento baseDeConocimiento) { //mandar una copia de la BD pero con una clausula menos 
        BaseDeConocimiento nuevaBaseDeConocimiento = new BaseDeConocimiento();
        for (int i = 0; i < baseDeConocimiento.baseDeConocimiento.size(); i++) {
            if (!clausula.sonIguales(clausulaAEliminar, baseDeConocimiento.getClausula(i))) {
                nuevaBaseDeConocimiento.Agregar(baseDeConocimiento.getClausula(i));
            }
        }
        return nuevaBaseDeConocimiento;
    }

    public boolean Deriva(String goal) {
        boolean derivaElGoal = false;
        clausula clausulaDelGoal = this.getClausula(goal);
        if (this.baseDeConocimiento.isEmpty() || clausulaDelGoal == null) {    // si la clausula no se encuentra en sigma 
            return false;
        } else if (clausulaDelGoal.esUnHecho()) {
            return true;
        } else {
            derivaElGoal = Deriva(clausulaDelGoal, new BaseDeConocimiento(this.baseDeConocimiento));
        }
        return derivaElGoal;
    }

    //                              [  a :- p]                 
    private boolean Deriva(clausula ClausulaActual, BaseDeConocimiento baseDeConocimientoActual) {  // metodo amigo de deriva(goal)

        if (baseDeConocimientoActual.baseDeConocimiento.isEmpty() || ClausulaActual == null) {
            return false;
        } else if (ClausulaActual.esUnHecho()) {
            return true;
        }
        //si llego hasta aca , significa que la clausula tiene un cuerpo [body]
        if (esTrueSii(ClausulaActual.getBody(), baseDeConocimientoActual)) {
            return true;
        } else {
            System.out.println(ClausulaActual + " fallo ");
            if (ClausulaActual.tieneAlgunCorte()) {
                System.out.println("la clausula " + ClausulaActual + " tiene un corte o '!' ");
                return false;
            } else {
                clausula nuevaClausulaDelBacktrack = backtrack(ClausulaActual.getHead(), baseDeConocimientoActual.remove(ClausulaActual, baseDeConocimientoActual));
                System.out.println("se hizo backtrack hacia :" + nuevaClausulaDelBacktrack);
                if (nuevaClausulaDelBacktrack == null) {
                    return false;
                } else {
                    return Deriva(nuevaClausulaDelBacktrack, baseDeConocimientoActual.remove(ClausulaActual, baseDeConocimientoActual));
                }
            }
        }

    }

    private boolean esTrueSii(String[] body, BaseDeConocimiento baseDeConocimientoActual) {
        boolean literalEsTrueSii = true;
        for (String literalEnTurno : body) {
            boolean elLiteralEnTurnoEsCut = "!".equals(literalEnTurno);
            clausula nuevaClausula = baseDeConocimientoActual.getClausula(literalEnTurno); //  b
            System.out.println("se quiere demostrar :" + nuevaClausula);
            if ((baseDeConocimientoActual.baseDeConocimiento.isEmpty() || nuevaClausula == null) && !elLiteralEnTurnoEsCut) {  // si la clausula no esta en sigma 
                return false;
            } else if (elLiteralEnTurnoEsCut || nuevaClausula.esUnHecho()) {
                if (elLiteralEnTurnoEsCut) {
                    String[] literalesDespuesDeCut = Arrays.copyOfRange(body, indiceDel(literalEnTurno, body) + 1, body.length);
                    System.out.println("estos literales [" + String.join(",", literalesDespuesDeCut) + "] no pueden pedir backtrack");

//                    if(!esTrueSii(literalesDespuesDeCut, baseDeConocimientoActual)){
//                        return false ;
//                    } else{
//                        return true ; 
//                    }
                    continue;
                } else {
                    continue;   //continua con el siguiente literal si es que hay 
                }

            }

            if (esTrueSii(nuevaClausula.getBody(), baseDeConocimientoActual.remove(nuevaClausula, baseDeConocimientoActual))) {
                // equivale a colocar continue; 
            } else {
                System.out.println(nuevaClausula + " fallo ");
                
                if (nuevaClausula.tieneAlgunCorte() ) {
                    System.out.println("la clausula " + nuevaClausula + " tiene un corte o '!' ");
                    return false;
                } else {
                    clausula nuevaClausulaDelBacktrack = backtrack(nuevaClausula.getHead(), baseDeConocimientoActual.remove(nuevaClausula, baseDeConocimientoActual));
                    System.out.println("se hizo backtrack hacia :" + nuevaClausulaDelBacktrack);
                    if (nuevaClausulaDelBacktrack == null) { // es decir que no encontro otra cabe
                        return false;
                    } else {
                        return Deriva(nuevaClausulaDelBacktrack, baseDeConocimientoActual.remove(nuevaClausula, baseDeConocimientoActual));
                    }
                }

            }

        }
        return literalEsTrueSii;
    }

    public clausula backtrack(String Head, BaseDeConocimiento baseDeConocimientoActual) {  // retorna otra clausula que comienze con Head
        return baseDeConocimientoActual.getClausula(Head);
    }

    //---------------------------------------------------------------------------------------------------------------------
    private int indiceDel(String literalEnElBody, String[] body) {
        for (int i = 0; i < body.length; i++) {
            if (body[i].equals(literalEnElBody)) {
                return i;
            }
        }
        return -1;
    }

    
    
    @Override
    public String toString() {
        // String[] arreglo = this.baseDeConocimiento.toArray(new String[this.baseDeConocimiento.size()]);
        String Cadena = "";
        for (int i = 0; i < this.baseDeConocimiento.size(); i++) {
            Cadena += this.baseDeConocimiento.get(i).toString() + "\n";
        }

        return Cadena;
    }

    public boolean isEmpty() {
        return this.baseDeConocimiento.isEmpty();
    }
}
