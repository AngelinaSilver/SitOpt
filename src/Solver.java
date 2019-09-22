 interface Solver {
	 /**
	  * The function would assign spaces to employees with no space.
	  * Try to find a solution with minimum moves of already assigned employees
	  * 
	  *  Will throw a SolverException in case couldn't be solved
	  * @param cmpny - current sitting
	  * @return true if employees' sitting was changed. Otherwise, false (no changed was made).
	  */
	public boolean populate( Company cmpny ) throws SolverException;
}
