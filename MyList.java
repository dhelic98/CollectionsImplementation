package mylist;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

public class MyList<E> extends AbstractList<E> implements List<E>, Iterable<E> {
	private static final int INITIAL_CAPACITY = 16;
	private static final Object[] EMPTY_ARRAY = {};

	private int index;
	private E[] array;

	@SuppressWarnings("unchecked")
	public MyList() {
		this.array = (E[]) (new Object[INITIAL_CAPACITY]);
	}

	public MyList(E[] array) {
		this.array = Arrays.copyOf(array, array.length);
		this.index = getNumberOfElements();

	}

	private int getNumberOfElements() {
		int counter = 0;
		for (int i = 0; i < this.array.length; i++) {
			if (this.array[i] != null) {
				counter++;
			}
		}

		return counter;
	}

	@SuppressWarnings("unchecked")
	public MyList(int initialSize) {
		if (initialSize > 0) {
			this.array = (E[]) (new Object[initialSize]);

		} else if (initialSize == 0) {
			this.array = (E[]) EMPTY_ARRAY;
		} else {
			throw new IllegalArgumentException("Illegal Argument Exception: Initial capacity: " + initialSize);
		}
	}

	@Override
	public boolean add(E e) {
		if (this.index < this.array.length) {
			this.array[this.index] = e;
			increaseIndex();
			return true;
		} else {
			resizeArray();
			add(e);
		}

		return false;

	}

	private void increaseIndex() {
		this.index++;
	}

	private void decreaseIndex() {
		this.index--;
	}

	@Override
	public void add(int index, E element) {
		if (index >= this.array.length) {
			throw new IndexOutOfBoundsException("Index out of bounds, size: " + this.array.length + " index: " + index);
		} else {
			shiftArray(index, 1);
			this.array[index] = element;
			increaseIndex();
		}

	}

	private void shiftArray(int index, int size) {
		int space = size() + size;

		if (space <= this.array.length) {

			for (int i = space - 1; i > index; i--) {
				this.array[i] = this.array[i - 1];

			}

		} else {
			resizeArray();
			shiftArray(index, size);
		}
	}

	private void resizeArray() {
		int oldCapacity = size();
		int newCapacity = oldCapacity * 2;

		this.array = Arrays.copyOf(this.array, newCapacity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (c.size() + size() >= this.array.length) {
			resizeArray();
			addAll(c);
		} else {
			for (Object o : c) {
				add((E) o);
			}

			return true;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		if (index > size()) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		} else if (c.size() + size() >= this.array.length) {
			resizeArray();
			addAll(index, c);
		}

		else {

			for (Object o : c) {
				shiftArray(index, 1);
				this.array[index] = (E) o;
				index++;
				increaseIndex();

			}
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		this.array = (E[]) (new Object[INITIAL_CAPACITY]);
	}

	@Override
	public boolean contains(Object o) {
		for (int i = 0; i < this.array.length; i++) {
			if (this.array[i].equals(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		int counter = 0;
		for (Object o : c) {
			if (contains(o)) {
				counter++;
			}
		}

		if (counter == c.size()) {
			return true;
		}
		return false;
	}

	@Override
	public E get(int index) {
		if (index > size() - 1) {
			throw new IndexOutOfBoundsException("Index is larger than size of array");
		} else if (index < 0) {
			throw new IndexOutOfBoundsException("Index is larger than size of array");
		} else if (this.array[index] == null) {

		}
		return this.array[index];

	}

	@Override
	public int indexOf(Object o) {
		for (int i = 0; i < this.array.length; i++) {
			if (this.array[i].equals(o)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		for (int i = size() - 1; i >= 0; i--) {
			if (this.array[i].equals(o)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public boolean remove(Object o) {
		int index = Arrays.asList(this.array).indexOf(o);
		// System.out.println(index);
		if (index >= 0) {
			remove(index);
			return true;

		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E remove(int index) {
		E t = this.array[index];
		int length = size();
		E[] newArray = (E[]) new Object[length];
		boolean shifted = false;
		for (int i = 0; i < newArray.length - 1; i++) {
			if (i == index) {
				shifted = true;

			}

			if (shifted) {

				newArray[i] = this.array[i + 1];
			} else {
				newArray[i] = this.array[i];
			}
		}
		decreaseIndex();
		this.array = newArray;

		return t;

	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object o : c) {

			remove(o);

		}

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean retainAll(Collection<?> c) {
		if (c.size() < size()) {
			E[] data = (E[]) new Object[c.size()];
			setSize(data.length);
			int index = 0;
			for (Object o : c) {
				data[index] = (E) o;
				index++;
			}
			this.array=data;
			return true;
		}

		return false;
	}

	@Override
	public E set(int index, E element) {
		if (index < size()) {

		}
		return null;
	}

	@Override
	public int size() {
		return this.index;
	}

	private void setSize(int size) {
		this.index = size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		E[] newArray = (E[]) (new Object[size()]);
		for (int i = fromIndex; i < toIndex; i++) {
			newArray[i] = this.array[i];

		}
		setSize(newArray.length);
		return new MyList<>(newArray);

	}

	@SuppressWarnings("unchecked")
	@Override
	public E[] toArray() {
		E[] newArray = (E[]) (new Object[size()]);
		for (int i = 0; i < newArray.length; i++) {
			newArray[i] = this.array[i];
		}

		return newArray;
	}

	@SuppressWarnings({ "hiding" })
	@Override
	public <E> E[] toArray(E[] a) {
		return a;
	}

	public String toString() {
		StringJoiner sj = new StringJoiner(",", "[", "]");
		for (int i = 0; i < size(); i++) {
			sj.add(this.array[i] + "");
		}
		return sj.toString();

	}

}
