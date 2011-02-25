/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://www.aipo.com
 */

package com.aipo.mobycket.wicket.protocol.http;

import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.gps.Gps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public final class WebContext implements Serializable {

  private static final long serialVersionUID = -7477412363746514142L;

  private static final Logger logger = LoggerFactory
    .getLogger(WebContext.class);

  public static final String DEFAULT_CHARACTER_SET = "UTF-8";

  public static final String RESOURCE_BUNDLE = "application";

  private final Mobylet mobylet;

  private final Carrier carrier;

  private final SmartPhoneType smartPhoneType;

  private final TabletType tabletType;

  private final HttpServletRequest request;

  private final HttpServletResponse response;

  private final ServletContext servletContext;

  protected WebContext(HttpServletRequest requset,
      HttpServletResponse response, ServletContext servletContext) {
    this.request = requset;
    this.response = response;
    String userAgent = requset.getHeader("user-agent");
    this.servletContext = servletContext;
    mobylet = getMobylet();
    if (mobylet != null) {
      // Feature Phone
      switch (mobylet.getCarrier()) {
        case DOCOMO:
          carrier = Carrier.DOCOMO;
          break;
        case AU:
          carrier = Carrier.AU;
          break;
        case SOFTBANK:
          carrier = Carrier.SOFTBANK;
          break;
        default:
          carrier = Carrier.OTHER;
          break;
      }
      // Smart Phone
      if (mobylet.getSmartPhoneType() != null) {
        switch (mobylet.getSmartPhoneType()) {
          case IPHONE:
            smartPhoneType = SmartPhoneType.IPHONE;
            break;
          case ANDROID:
            smartPhoneType = SmartPhoneType.ANDROID;
            break;
          default:
            smartPhoneType = null;
            break;
        }
      } else {
        smartPhoneType = null;
      }
      if (userAgent != null && userAgent.contains("iPad")) {
        tabletType = TabletType.IPAD;
      } else {
        tabletType = null;
      }
      // Tablet

      // Content-Type
      if (mobylet != null) {
        mobylet.setContentType(isMobileRequest()
          ? org.mobylet.core.type.ContentType.XHTML
          : org.mobylet.core.type.ContentType.HTML);
      }
    } else {
      carrier = Carrier.OTHER;
      smartPhoneType = null;
      tabletType = null;
    }

  }

  /**
   * キャリアを取得します。
   * 
   * @return 取得できない場合は {@link Carrier#OTHER} を返します。
   */
  public Carrier getCarrier() {
    return carrier;
  }

  /**
   * スマートフォンの種別を取得します。
   * 
   * @return 取得できない場合は {@link null} を返します。
   */
  public SmartPhoneType getSmartPhoneType() {
    return smartPhoneType;
  }

  /**
   * タブレットの種別を取得します。
   * 
   * @return 取得できない場合は {@link null} を返します。
   */
  public TabletType getTabletType() {
    return tabletType;
  }

  /**
   * GPS 情報を取得します。
   * 
   * @return 取得できない場合は null を返します。
   */
  public Gps getGps() {
    Mobylet mobylet = getMobylet();
    if (mobylet == null) {
      return null;
    }
    return mobylet.getGps();
  }

  /**
   * UID を取得します。
   * 
   * @return 取得できない場合は null を返します。
   */
  public String getUid() {
    Mobylet mobylet = getMobylet();
    if (mobylet == null) {
      return null;
    }
    return mobylet.getUid();
  }

  /**
   * GUID を取得します。
   * 
   * @return 取得できない場合は null を返します。
   */
  public String getGuid() {
    Mobylet mobylet = getMobylet();
    if (mobylet == null) {
      return null;
    }
    return mobylet.getGuid();
  }

  /**
   * 携帯からのリクエストかどうか判別します。
   * 
   * @return 携帯からのリクエストかどうか
   */
  public boolean isMobileRequest() {
    Mobylet mobylet = getMobylet();
    return (mobylet != null && (isDocomo() || isSoftbank() || isAU()));
  }

  /**
   * スマートフォンからのリクエストかどうか判別します。
   * 
   * @return
   */
  public boolean isSmartPhoneRequest() {
    Mobylet mobylet = getMobylet();
    return (mobylet != null && smartPhoneType != null);

  }

  /**
   * タブレットからのリクエストかどうか判別します。
   * 
   * @return
   */
  public boolean isTabletRequest() {
    Mobylet mobylet = getMobylet();
    return (mobylet != null && tabletType != null);

  }

  /**
   * DoCoMo 端末かどうか判別します。
   * 
   * @return DoCoMo 端末かどうか
   */
  public boolean isDocomo() {
    Mobylet mobylet = getMobylet();
    return (mobylet != null && Carrier.DOCOMO.equals(carrier));
  }

  /**
   * AU 端末かどうか判別します。
   * 
   * @return AU 端末かどうか
   */
  public boolean isAU() {
    Mobylet mobylet = getMobylet();
    return (mobylet != null && Carrier.AU.equals(carrier));
  }

  /**
   * Softbank 端末かどうか判別します。
   * 
   * @return ソフトバンク端末かどうか
   */
  public boolean isSoftbank() {
    Mobylet mobylet = getMobylet();
    return (mobylet != null && Carrier.SOFTBANK.equals(carrier));
  }

  /**
   * Windows Mobile 端末かどうか判別します。
   * 
   * @return Windows Mobile 端末かどうか
   */
  public boolean isWM() {
    Mobylet mobylet = getMobylet();
    return (mobylet != null && SmartPhoneType.WM.equals(smartPhoneType));
  }

  /**
   * iPhone 端末かどうか判別します。
   * 
   * @return iPhone 端末かどうか
   */
  public boolean isIPhone() {
    Mobylet mobylet = getMobylet();
    return (mobylet != null && SmartPhoneType.IPHONE.equals(smartPhoneType));
  }

  /**
   * Android 端末かどうか判別します。
   * 
   * @return Android 端末かどうか
   */
  public boolean isAndroid() {
    Mobylet mobylet = getMobylet();
    return (mobylet != null && SmartPhoneType.ANDROID.equals(smartPhoneType));
  }

  /**
   * iPad 端末かどうか判別します。
   * 
   * @return iPad 端末かどうか
   */
  public boolean isIPad() {
    Mobylet mobylet = getMobylet();
    return (mobylet != null && TabletType.IPAD.equals(tabletType));
  }

  public String getContentType() {
    Mobylet mobylet = getMobylet();
    if (mobylet == null) {
      return ContentType.HTML.get();
    }
    return isMobileRequest() ? ContentType.XHTML.get() : ContentType.HTML.get();
  }

  public String getContentCharsetName() {
    Mobylet mobylet = getMobylet();
    if (mobylet == null) {
      return DEFAULT_CHARACTER_SET;
    }
    return mobylet.getDialect().getContentCharsetName();
  }

  public Mobylet getMobylet() {
    try {
      return MobyletFactory.getInstance();
    } catch (Throwable ignore) {
      logger.info("mobylet is not initialized.");
      return null;
    }
  }

  public String getDirname() {
    if (isTabletRequest()) {
      return getTabletType().getDefaultDirname();
    } else if (isSmartPhoneRequest()) {
      return getSmartPhoneType().getDefaultDirname();
    } else {
      return getCarrier().getDefaultDirname();
    }
  }

  public String getProperty(String key) {
    try {
      ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE);
      return resourceBundle.getString(key);
    } catch (MissingResourceException ignore) {
      // ignore
      return null;
    }
  }

  public HttpServletRequest getHttpServletRequest() {
    return request;
  }

  /**
   * @return response
   */
  public HttpServletResponse getResponse() {
    return response;
  }

  /**
   * @return servletContext
   */
  public ServletContext getServletContext() {
    return servletContext;
  }
}
