/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garcom.utils;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author evaldosavio
 */
public class CalendarUtils {

    public static Date proximoDiaUtilSemFeriado(Date data, int qtdDias, boolean uteis) {
        int dias = 0;

        Calendar calendarData = Calendar.getInstance();
        calendarData.setTime(data);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendarData.get(Calendar.DATE));
        calendar.set(Calendar.MONTH, calendarData.get(Calendar.MONTH));


        while (dias < qtdDias) {
            calendar.add(Calendar.DATE, 1);
            if (uteis) {
                if (!(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
                    dias++;
                }
            } else {
                dias++;
            }
        }

        return calendar.getTime();
    }
}
