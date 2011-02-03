/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2011 Aimluck,Inc.
 * http://aipostyle.com/
 */

package com.aipo.mobycket.markup.repeater.data;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;

/**
 * 
 */
public abstract class CursorDataView<T> extends DataView<T> {

  private static final long serialVersionUID = -6950961499058839407L;

  /**
   * @param id
   * @param dataProvider
   */
  protected CursorDataView(String id, IDataProvider<T> dataProvider) {
    super(id, dataProvider);
  }

  /**
   * @param id
   * @param dataProvider
   * @param itemsPerPage
   */
  protected CursorDataView(String id, IDataProvider<T> dataProvider,
      int itemsPerPage) {
    super(id, dataProvider, itemsPerPage);
  }

  @Override
  protected Iterator<IModel<T>> getItemModels() {
    int offset = getCurrentPage();
    int size = getViewSize();

    Iterator<IModel<T>> models = getItemModels(offset, size);

    models = new CappedIteratorAdapter<T>(models, size);

    return models;
  }

  private static class CappedIteratorAdapter<T> implements Iterator<IModel<T>> {
    private final int max;

    private int index;

    private final Iterator<IModel<T>> delegate;

    /**
     * Constructor
     * 
     * @param delegate
     *          delegate iterator
     * @param max
     *          maximum number of items that can be accessed.
     */
    public CappedIteratorAdapter(Iterator<IModel<T>> delegate, int max) {
      this.delegate = delegate;
      this.max = max;
    }

    /**
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    /**
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
      return (index < max) && delegate.hasNext();
    }

    /**
     * @see java.util.Iterator#next()
     */
    @Override
    public IModel<T> next() {
      if (index >= max) {
        throw new NoSuchElementException();
      }
      index++;
      return delegate.next();
    }

  };

}
