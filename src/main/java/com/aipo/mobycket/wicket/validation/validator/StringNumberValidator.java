/*
 * Copyright 2004-2014 Aimluck,Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
