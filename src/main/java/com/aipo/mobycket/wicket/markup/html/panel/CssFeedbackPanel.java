/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://aipostyle.com/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.aipo.mobycket.wicket.markup.html.panel;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessagesModel;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * ul部のクラス指定ができるFeedbackPanel
 * 
 * @see org.apache.wicket.markup.html.panel.FeedbackPanel
 */
public class CssFeedbackPanel extends Panel implements IFeedback {
  /**
   * List for messages.
   */
  private final class MessageListView extends ListView<FeedbackMessage> {
    private static final long serialVersionUID = 1L;

    /**
     * @see org.apache.wicket.Component#Component(String)
     */
    public MessageListView(final String id) {
      super(id);
      setDefaultModel(newFeedbackMessagesModel());
    }

    /**
     * @see org.apache.wicket.markup.html.list.ListView#populateItem(org.apache.wicket.markup.html
     *      .list.ListItem)
     */
    @Override
    protected void populateItem(final ListItem<FeedbackMessage> listItem) {
      final FeedbackMessage message = listItem.getModelObject();
      message.markRendered();
      final IModel<String> replacementModel = new Model<String>() {
        private static final long serialVersionUID = 1L;

        /**
         * Returns feedbackPanel + the message level, eg 'feedbackPanelERROR'.
         * This is used as the class of the li / span elements.
         * 
         * @see org.apache.wicket.model.IModel#getObject()
         */
        @Override
        public String getObject() {
          return getCSSClass(message);
        }
      };

      final Component label = newMessageDisplayComponent("message", message);
      final AttributeModifier levelModifier =
        new AttributeModifier("class", replacementModel);
      label.add(levelModifier);
      listItem.add(levelModifier);
      listItem.add(label);
    }
  }

  private static final long serialVersionUID = 1L;

  /** Message view */
  private final MessageListView messageListView;

  private String cssClass;

  /**
   * @see org.apache.wicket.Component#Component(String)
   */
  public CssFeedbackPanel(final String id, String cssClass) {
    this(id, null, cssClass);
  }

  /**
   * cssClassを指定したFeedbackPanel
   * 
   * @see org.apache.wicket.Component#Component(String)
   * 
   * @param id
   * @param filter
   * @param cssClass
   */
  public CssFeedbackPanel(final String id, IFeedbackMessageFilter filter,
      String cssClass) {
    super(id);
    WebMarkupContainer messagesContainer =
      new WebMarkupContainer("feedbackul") {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isVisible() {
          return anyMessage();
        }
      };
    add(messagesContainer);
    messageListView = new MessageListView("messages");
    messageListView.setVersioned(false);
    messagesContainer.add(messageListView);
    messagesContainer.add(new AttributeAppender("class", new Model<String>(
      cssClass), " "));

    if (filter != null) {
      setFilter(filter);
    }
    this.cssClass = cssClass;
  }

  /**
   * Search messages that this panel will render, and see if there is any
   * message of level ERROR or up. This is a convenience method; same as calling
   * 'anyMessage(FeedbackMessage.ERROR)'.
   * 
   * @return whether there is any message for this panel of level ERROR or up
   */
  public final boolean anyErrorMessage() {
    return anyMessage(FeedbackMessage.ERROR);
  }

  /**
   * Search messages that this panel will render, and see if there is any
   * message.
   * 
   * @return whether there is any message for this panel
   */
  public final boolean anyMessage() {
    return anyMessage(FeedbackMessage.UNDEFINED);
  }

  /**
   * Search messages that this panel will render, and see if there is any
   * message of the given level.
   * 
   * @param level
   *          the level, see FeedbackMessage
   * @return whether there is any message for this panel of the given level
   */
  public final boolean anyMessage(int level) {
    List<FeedbackMessage> msgs = getCurrentMessages();

    for (FeedbackMessage msg : msgs) {
      if (msg.isLevel(level)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Gets whether model messages should be HTML escaped. Default is true.
   * 
   * @return whether model messages should be HTML escaped
   * 
   * @deprecated use {@link #getEscapeModelStrings()} instead
   */
  @Deprecated
  public final boolean getEscapeMessages() {
    return getEscapeModelStrings();
  }

  /**
   * @return Model for feedback messages on which you can install filters and
   *         other properties
   */
  public final FeedbackMessagesModel getFeedbackMessagesModel() {
    return (FeedbackMessagesModel) messageListView.getDefaultModel();
  }

  /**
   * @return The current message filter
   */
  public final IFeedbackMessageFilter getFilter() {
    return getFeedbackMessagesModel().getFilter();
  }

  /**
   * @return The current sorting comparator
   */
  public final Comparator<FeedbackMessage> getSortingComparator() {
    return getFeedbackMessagesModel().getSortingComparator();
  }

  /**
   * @see org.apache.wicket.Component#isVersioned()
   */
  @Override
  public boolean isVersioned() {
    return false;
  }

  /**
   * Sets whether model messages should be HTML escaped. Default is true.
   * 
   * @param escapeMessages
   *          whether model messages should be HTML escaped
   * 
   * @deprecated use {@link #setEscapeModelStrings(boolean)}
   */
  @Deprecated
  public final void setEscapeMessages(boolean escapeMessages) {
    setEscapeModelStrings(escapeMessages);
  }

  /**
   * Sets a filter to use on the feedback messages model
   * 
   * @param filter
   *          The message filter to install on the feedback messages model
   * 
   * @return CssFeedbackPanel this.
   */
  public final CssFeedbackPanel setFilter(IFeedbackMessageFilter filter) {
    getFeedbackMessagesModel().setFilter(filter);
    return this;
  }

  /**
   * @param maxMessages
   *          The maximum number of feedback messages that this feedback panel
   *          should show at one time
   * 
   * @return CssFeedbackPanel this.
   */
  public final CssFeedbackPanel setMaxMessages(int maxMessages) {
    messageListView.setViewSize(maxMessages);
    return this;
  }

  /**
   * Sets the comparator used for sorting the messages.
   * 
   * @param sortingComparator
   *          comparator used for sorting the messages.
   * 
   * @return CssFeedbackPanel this.
   */
  public final CssFeedbackPanel setSortingComparator(
      Comparator<FeedbackMessage> sortingComparator) {
    getFeedbackMessagesModel().setSortingComparator(sortingComparator);
    return this;
  }

  /**
   * Gets the css class for the given message.
   * 
   * @param message
   *          the message
   * @return the css class; by default, this returns feedbackPanel + the message
   *         level, eg 'feedbackPanelERROR', but you can override this method to
   *         provide your own
   */
  protected String getCSSClass(final FeedbackMessage message) {
    return "feedbackPanel" + message.getLevelAsString();
  }

  /**
   * Gets the currently collected messages for this panel.
   * 
   * @return the currently collected messages for this panel, possibly empty
   */
  protected final List<FeedbackMessage> getCurrentMessages() {
    final List<FeedbackMessage> messages = messageListView.getModelObject();
    return Collections.unmodifiableList(messages);
  }

  /**
   * Gets a new instance of FeedbackMessagesModel to use.
   * 
   * @return Instance of FeedbackMessagesModel to use
   */
  protected FeedbackMessagesModel newFeedbackMessagesModel() {
    return new FeedbackMessagesModel(this);
  }

  /**
   * Generates a component that is used to display the message inside the
   * feedback panel. This component must handle being attached to
   * <code>span</code> tags.
   * 
   * By default a {@link Label} is used.
   * 
   * Note that the created component is expected to respect feedback panel's
   * {@link #getEscapeModelStrings()} value
   * 
   * @param id
   *          parent id
   * @param message
   *          feedback message
   * @return component used to display the message
   */
  protected Component newMessageDisplayComponent(String id,
      FeedbackMessage message) {
    Serializable serializable = message.getMessage();
    Label label =
      new Label(id, (serializable == null) ? "" : serializable.toString());
    label.setEscapeModelStrings(CssFeedbackPanel.this.getEscapeModelStrings());
    return label;
  }

  public void addCss(String css) {
    this.cssClass =
      new StringBuilder(this.cssClass).append(cssClass).toString();
  }
}
