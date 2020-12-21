/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.validador;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author roniere
 */
@Constraint(validatedBy = CpfValidator.class)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CPF {
   
  String message() default "CPF inválido";
  Class<?>[] groups() default { };
  Class<? extends Payload>[] payload() default { };
 
}