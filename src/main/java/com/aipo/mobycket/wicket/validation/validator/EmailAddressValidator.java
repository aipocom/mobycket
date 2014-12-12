/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://aipostyle.com/
 */

package com.aipo.mobycket.wicket.validation.validator;

import java.util.regex.Pattern;

import org.apache.wicket.validation.validator.PatternValidator;

/**
 * 
 */
public class EmailAddressValidator extends PatternValidator {

  private static final long serialVersionUID = -4370896421024607370L;

  public static String PATTERN =
    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-\\+\\.]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z]{2,}){1}$)";

  public EmailAddressValidator() {
    super(PATTERN, Pattern.CASE_INSENSITIVE);
  }

}
