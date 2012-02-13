/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://aipostyle.com/
 */

package com.aipo.mobycket.wicket.validation.validator;

import java.util.regex.Pattern;

import org.apache.wicket.validation.validator.PatternValidator;

/**
 * 先頭が０で始まることを許す、String型の数値Validater
 */
public class StringNumberValidator extends PatternValidator {

  private static final long serialVersionUID = -4370896421024607370L;

  public static String PATTERN = "^[1-9]+\\d*";

  public StringNumberValidator() {
    super(PATTERN, Pattern.CASE_INSENSITIVE);
  }

}
