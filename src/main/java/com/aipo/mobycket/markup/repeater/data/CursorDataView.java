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
