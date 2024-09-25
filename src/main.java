class Main{
	public static void main(String[] args) {
		String input = prompt();
	} //end main

	static String prompt() {
		System.out.print("Enter a string: ");
		String input = System.console().readLine();
		System.out.println("You entered: " + input);
		return input;
	}
}
