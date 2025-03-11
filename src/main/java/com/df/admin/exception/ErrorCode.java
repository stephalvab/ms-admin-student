package com.df.admin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
  NO_CONTENT(
      HttpStatus.NO_CONTENT.value(),
      "Sin Contenido"),
  BAD_REQUEST(
      HttpStatus.BAD_REQUEST.value(),
          "Error en la petici\u00f3n del cliente."),
  INTERNAL_SERVER_ERROR(
          HttpStatus.INTERNAL_SERVER_ERROR.value(),
          "Error del Servidor");

  private final int code;
  private final String error;
}
