package io.sanchopansa.list;

import java.util.*;

/**
 * Данный класс описывает цикличный связанный список (Circular Linked List).
 * Последний элемент списка указывает на первый, а первый, в свою очередь, - на последний.
 * Основан на JRE классе LinkedList.
 * @author Sancho-Pansa
 * @param <T>
 * @version 0.1
 * @see java.util.LinkedList
 */
@SuppressWarnings("unused")
public class CircularLinkedList<T>
        extends AbstractSequentialList<T>
        implements java.io.Serializable, Cloneable {

    private int size = 0;
    private Node<T> first = null;
    private Node<T> last = null;

    public CircularLinkedList() {

    }

    /**
     * Создает цикличный список из элементов Collection, которые будут вставлены в список в
     * соответствии с итератором этой коллекции.
     * @param c Коллекция, которая будет вставлена в список
     */
    public CircularLinkedList(Collection<? extends T> c) {
        this();
        addAll(c);
    }

    private void linkFirst(T t) {
        final Node<T> f = first;
        final Node<T> newNode = new Node<>(null, t, f);
        first = newNode;
        if(f == null) {
            last = newNode;
            first.prev = last;
            last.next = first;
        } else {
            f.prev = newNode;
            last.prev = newNode;
        }
        size++;
    }

    private void linkLast(T t) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(l, t, first);
        last = newNode;
        if(l == null) {
            first = newNode;
            first.prev = last;
            last.next = first;
        } else {
            l.next = newNode;
            first.prev = newNode;
        }
        size++;
    }

    private void linkBefore(T t, Node<T> successor) {
        final Node<T> predecessor = successor.prev;
        final Node<T> newNode = new Node<>(predecessor, t, successor);
        predecessor.next = newNode;
        size++;
    }

    private void linkAfter(T t, Node<T> predecessor) {
        final Node<T> successor = predecessor.next;
        final Node<T> newNode = new Node<>(predecessor, t, successor);
        successor.prev = newNode;
        size++;
    }

    private T unlinkFirst() {
        if(first == null) {
            throw new IndexOutOfBoundsException();
        }
        final T element = first.item;
        final Node<T> successor = first.next;
        this.first.item = null;
        this.first.next = null;
        this.first.prev = null;
        this.first = successor;
        this.last.next = successor;
        size--;

        return element;
    }

    private T unlinkLast() {
        if(last == null) {
            throw new IndexOutOfBoundsException();
        }
        final T element = last.item;
        final Node<T> predecessor = last.prev;
        this.last.item = null;
        this.last.next = null;
        this.last.prev = null;
        this.last = predecessor;
        size--;

        return element;
    }

    private T unlink(Node<T> x) {
        final T element = x.item;
        final Node<T> predecessor = x.prev;
        final Node<T> successor = x.next;
        predecessor.next = successor;
        successor.prev = predecessor;
        x.prev = null;
        x.next = null;
        x.item = null;
        size--;
        return element;
    }

    /**
     * Возвращает первый элемент списка.
     *
     * @return первый элемент списка
     * @throws NoSuchElementException если список пуст
     */
    public T getFirst() {
        final Node<T> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return f.item;
    }

    /**
     * Возвращает последний элемент списка.
     * @return последний элемент списка
     * @throws NoSuchElementException если список пуст
     */
    public T getLast() {
        final Node<T> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return l.item;
    }

    /**
     * Отсоединяет и возвращает первый элемент списка
     * @return первый элемент списка
     * @throws NoSuchElementException если список пуст
     */
    public T removeFirst() {
        final Node<T> f = first;
        if (f == null)
            throw new NoSuchElementException();
        return unlinkFirst();
    }

    /**
     * Отсоединяет и возвращает последний элемент списка
     *
     * @return последний элемент списка
     * @throws NoSuchElementException если список пуст
     */
    public T removeLast() {
        final Node<T> l = last;
        if (l == null)
            throw new NoSuchElementException();
        return unlinkLast();
    }

    /**
     * Вставляет указанный элемент в начало списка. В кольце он будет назначен первым элементом.
     *
     * @param t добавляемый элемент
     */
    public void addFirst(T t) {
        linkFirst(t);
    }

    /**
     * Вставляет указанный элемент в конец списка.
     * <p>Эквивалентен методу {@link #add}.
     * @param t добавляемый элемент
     */
    public void addLast(T t) {
        linkLast(t);
    }

    /**
     * Возвращает число элементов. Если число элементов коллекции
     * превышает <tt>Integer.MAX_VALUE</tt>, вернет
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return число элементов в коллекции
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * <tt>true</tt>, если в коллекции нет элементов
     *
     * @return <tt>true</tt>, если в коллекции нет элементов
     */
    @Override
    public boolean isEmpty() {
        return this.first == null;
    }

    /**
     * Возвращает итератор по циклическому списку, который проходит один раз по кольцу.
     * @apiNote Неправильно сработает, если в кольце несколько полных копий одного объекта
     * @return an <tt>Iterator</tt> over the elements in this collection
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = first;
            private int counter = 0;
            @Override
            public boolean hasNext() {
                return counter < size();
            }

            @Override
            public T next() {
                T data = current.item;
                current = current.next;
                counter++;
                return data;
            }
        };
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListIterator<T>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public T next() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public T previous() {
                return null;
            }

            @Override
            public int nextIndex() {
                return 0;
            }

            @Override
            public int previousIndex() {
                return 0;
            }

            @Override
            public void remove() {

            }

            @Override
            public void set(T t) {

            }

            @Override
            public void add(T t) {

            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[this.size];
        Node<T> current = first;
        for(int i = 0; i < result.length; i++, current = current.next) {
            result[i] = current;
        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        if(a.length < size)
            a = (T1[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
        int i = 0;
        Object[] result = a;
        for(CircularLinkedList.Node<T> x = first; x != null; x = x.next)
            result[i++] = x.item;

        if(a.length > size)
            a[size] = null;

        return a;
    }

    /**
     * Ensures that this collection contains the specified element (optional
     * operation).  Returns <tt>true</tt> if this collection changed as a
     * result of the call.  (Returns <tt>false</tt> if this collection does
     * not permit duplicates and already contains the specified element.)<p>
     * <p>
     * Collections that support this operation may place limitations on what
     * elements may be added to this collection.  In particular, some
     * collections will refuse to add <tt>null</tt> elements, and others will
     * impose restrictions on the type of elements that may be added.
     * Collection classes should clearly specify in their documentation any
     * restrictions on what elements may be added.<p>
     * <p>
     * If a collection refuses to add a particular element for any reason
     * other than that it already contains the element, it <i>must</i> throw
     * an exception (rather than returning <tt>false</tt>).  This preserves
     * the invariant that a collection always contains the specified element
     * after this call returns.
     *
     * @param t element whose presence in this collection is to be ensured
     * @return <tt>true</tt> if this collection changed as a result of the
     * call
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *                                       is not supported by this collection
     * @throws ClassCastException            if the class of the specified element
     *                                       prevents it from being added to this collection
     * @throws NullPointerException          if the specified element is null and this
     *                                       collection does not permit null elements
     * @throws IllegalArgumentException      if some property of the element
     *                                       prevents it from being added to this collection
     * @throws IllegalStateException         if the element cannot be added at this
     *                                       time due to insertion restrictions
     */
    @Override
    public boolean add(T t) {
        linkLast(t);
        return true;
    }

    /**
     * Removes a single instance of the specified element from this
     * collection, if it is present (optional operation).  More formally,
     * removes an element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>, if
     * this collection contains one or more such elements.  Returns
     * <tt>true</tt> if this collection contained the specified element (or
     * equivalently, if this collection changed as a result of the call).
     *
     * @param o element to be removed from this collection, if present
     * @return <tt>true</tt> if an element was removed as a result of this call
     * @throws ClassCastException            if the type of the specified element
     *                                       is incompatible with this collection
     *                                       (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException          if the specified element is null and this
     *                                       collection does not permit null elements
     *                                       (<a href="#optional-restrictions">optional</a>)
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *                                       is not supported by this collection
     */
    @Override
    public boolean remove(Object o) {
        Node<T> x = first;
        if(o == null) {
            do {
                if(x.item == null) {
                    unlink(x);
                    return true;
                }
                x = x.next;
            } while(x != first);
        } else {
            do {
                if(o.equals(x.item)) {
                    unlink(x);
                    return true;
                }
                x = x.next;
            } while(x != first);
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("CircularLinked List: [");
        Object[] cllArray = this.toArray();
        for (int i = 0; i < cllArray.length - 1; i++) {
            result.append(cllArray[i].toString()).append(", ");
        }
        result.append("]\r\n");
        return result.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public CircularLinkedList<T> clone() {
        CircularLinkedList<T> clone;
        try {
            clone = (CircularLinkedList<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
        // Обнулить клон
        clone.first = null;
        clone.last = null;
        clone.size = 0;
        Node<T> x = first;
        do {
            clone.add(x.item);
            x = x.next;
        } while(x != first);
        return clone;
    }

    private static class Node<T> {

        private Node<T> prev;
        private T item;
        private Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
