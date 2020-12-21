/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.estoque.exceptions;

import br.com.garcom.estoque.modelo.Produto;

/**
 *
 * @author evaldosavio
 */
public class EstoqueInsuficienteException extends Exception {

    public EstoqueInsuficienteException() {
    }

    public EstoqueInsuficienteException(String message) {
        super(message);
    }
}
