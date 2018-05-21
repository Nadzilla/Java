
package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 *
 * @author runb-cs112
 *
 */
public class Polynomial {

	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage
	 * format of the polynomial is:
	 * 
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * 
	 * with the guarantee that degrees will be in descending order. For example:
	 * 
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * 
	 * which represents the polynomial:
	 * 
	 * <pre>
	 * 4 * x ^ 5 - 2 * x ^ 3 + 2 * x + 3
	 * </pre>
	 *
	 * @param sc
	 *            Scanner from which a polynomial is to be read
	 * @throws IOException
	 *             If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients
	 *         and degrees read from scanner
	 */
	public static Node read(Scanner sc) throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}

	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input
	 * polynomials. The returned polynomial MUST have all new nodes. In other words,
	 * none of the nodes of the input polynomials can be in the result.
	 *
	 * @param poly1
	 *            First input polynomial (front of polynomial linked list)
	 * @param poly2
	 *            Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the
	 *         returned node is the front of the result polynomial
	 */

	private static Node reverseLL(Node poly) {
		Node ptr = null;
		Node next;
		while (poly != null) {
			next = poly.next;
			poly.next = ptr;
			ptr = poly;
			poly = next;
		}
		return ptr;
	}

	private static void showPoly(Node poly) {
		Node ptr = poly;
		while (ptr != null) {
			System.out.print(ptr.term.toString() + " -> ");
			ptr = ptr.next;
		}
	}

	public static Node add(Node poly1, Node poly2) {

		int position = 0;
		Node poly3 = null;
		Node ptrPoly3 = poly3;
		if (poly1 == null && poly2 == null) {
			return null;
		}

		Node ptr1 = poly1;
		Node ptr2 = poly2;

		while (!(ptr1 == null && ptr2 == null)) {

			if (ptr1 != null && ptr2 != null) {
				if (ptr1.term.degree == ptr2.term.degree) {
					if (ptr1.term.coeff + ptr2.term.coeff == 0) {
						ptr1 = ptr1.next;
						ptr2 = ptr2.next;
					} else {
						poly3 = new Node(ptr1.term.coeff + ptr2.term.coeff, ptr1.term.degree, ptrPoly3);
						ptrPoly3 = poly3;

						position++;
						ptr1 = ptr1.next;
						ptr2 = ptr2.next;
					}

				} else if (ptr1.term.degree > ptr2.term.degree) {
					if (ptr2.term.coeff == 0) {
						ptr2 = ptr2.next;
					} else {
						poly3 = new Node(ptr2.term.coeff, ptr2.term.degree, ptrPoly3);
						ptrPoly3 = poly3;

						position++;
						ptr2 = ptr2.next;
					}

				} else if (ptr1.term.degree < ptr2.term.degree) {
					if (ptr1.term.coeff == 0) {
						ptr1 = ptr1.next;
					} else {
						poly3 = new Node(ptr1.term.coeff, ptr1.term.degree, ptrPoly3);
						ptrPoly3 = poly3;

						position++;
						ptr1 = ptr1.next;
					}
				}
			}
			if (ptr1 == null && ptr2 != null) {
				poly3 = new Node(ptr2.term.coeff, ptr2.term.degree, ptrPoly3);
				ptrPoly3 = poly3;

				position++;
				ptr2 = ptr2.next;
			}
			if (ptr2 == null && ptr1 != null) {
				poly3 = new Node(ptr1.term.coeff, ptr1.term.degree, ptrPoly3);
				ptrPoly3 = poly3;

				position++;
				ptr1 = ptr1.next;
			}
		}

		Node ptr = reverseLL(poly3);

		return ptr;

	}

	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input
	 * polynomials. The returned polynomial MUST have all new nodes. In other words,
	 * none of the nodes of the input polynomials can be in the result.
	 *
	 * @param poly1
	 *            First input polynomial (front of polynomial linked list)
	 * @param poly2
	 *            Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the
	 *         returned node is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		Node poly3 = null;
		Node ptrPoly3 = poly3;
		Node poly4 = null;
		Node temp1 = null;
		Node ptrTemp1 = temp1;
		Node b1 = null;
		Node b2 = null;
		int position = 2;

		if (poly1 == null || poly2 == null) {
			return null;
		}

		for (Node ptr1 = poly1; ptr1 != null; ptr1 = ptr1.next) {
			if (position == 4) {
				if (poly3 != null && temp1 != null) {

					b1 = reverseLL(poly3);
					b2 = reverseLL(ptrTemp1);

					poly4 = add(b1, b2);
					poly3 = null;
					ptrPoly3 = poly3;
					temp1 = null;
					ptrTemp1 = temp1;
				}
			} else if (position > 4 && position % 2 == 1) {

				b1 = reverseLL(poly3);
				b2 = reverseLL(ptrTemp1);

				poly4 = add(b1, poly4);
				poly3 = null;
				ptrPoly3 = poly3;

				temp1 = null;
				ptrTemp1 = temp1;
			} else if (position > 4 && position % 2 == 0) {

				b1 = reverseLL(poly3);
				b2 = reverseLL(ptrTemp1);

				poly4 = add(b2, poly4);
				poly3 = null;
				ptrPoly3 = poly3;

				temp1 = null;
				ptrTemp1 = temp1;
			}
			position++;

			for (Node ptr2 = poly2; ptr2 != null; ptr2 = ptr2.next) {

				if (position % 2 == 1) {
					poly3 = new Node(ptr1.term.coeff * ptr2.term.coeff, ptr1.term.degree + ptr2.term.degree, ptrPoly3);
					ptrPoly3 = poly3;

				} else {
					temp1 = new Node(ptr1.term.coeff * ptr2.term.coeff, ptr1.term.degree + ptr2.term.degree, ptrTemp1);
					ptrTemp1 = temp1;

				}

			}
		}
		if (position == 4) {
			if (poly3 != null && temp1 != null) {

				b1 = reverseLL(poly3);
				b2 = reverseLL(ptrTemp1);
				System.out.print("Show b1: ");
				showPoly(b1);
				System.out.print("Show b2: ");
				showPoly(b2);
				poly4 = add(b1, b2);
				poly3 = null;
				ptrPoly3 = poly3;
				temp1 = null;
				ptrTemp1 = temp1;
			}
		} else if (position > 4 && position % 2 == 1) {

			b1 = reverseLL(poly3);
			b2 = reverseLL(ptrTemp1);

			poly4 = add(b1, poly4);
			poly3 = null;
			ptrPoly3 = poly3;

			temp1 = null;
			ptrTemp1 = temp1;
		} else if (position > 4 && position % 2 == 0) {

			b1 = reverseLL(poly3);
			b2 = reverseLL(ptrTemp1);

			poly4 = add(b2, poly4);
			poly3 = null;
			ptrPoly3 = poly3;

			temp1 = null;
			ptrTemp1 = temp1;
		}

		return poly4;
	}

	/**
	 * Evaluates a polynomial at a given value.
	 *
	 * @param poly
	 *            Polynomial (front of linked list) to be evaluated
	 * @param x
	 *            Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		Node ptr = poly;
		float answer = 0;
		while (ptr != null) {

			answer += ptr.term.coeff * Math.pow(x, ptr.term.degree);

			ptr = ptr.next;
		}

		return answer;
	}

	/**
	 * Returns string representation of a polynomial
	 *
	 * @param poly
	 *            Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		}

		String retval = poly.term.toString();
		for (Node current = poly.next; current != null; current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}
}
