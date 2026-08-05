package com.sistema_escolar.sistema.escolar.model.enums;

import java.time.DayOfWeek;

public enum DiasSemana {
    SEGUNDA,
    TERCA,
    QUARTA,
    QUINTA,
    SEXTA,
    SABADO,
    DOMINGO,
    ;


    public DiasSemana converter(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY -> {
                return DiasSemana.SEGUNDA;
            }
            case TUESDAY -> {
                return DiasSemana.TERCA;
            }
            case WEDNESDAY -> {
                return DiasSemana.QUARTA;
            }
            case THURSDAY -> {
                return DiasSemana.QUINTA;
            }
            case FRIDAY -> {
                return DiasSemana.SEXTA;
            }
            case SATURDAY -> {
                return DiasSemana.SABADO;
            }
            case SUNDAY -> {
                return DiasSemana.DOMINGO;
            }
            case null -> {
                return null;
            }
        }
    }

}
