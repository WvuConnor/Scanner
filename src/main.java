class Main{
	public static void main(String[] args) {
		prompt();
	} //end main

	static void prompt() {
		System.out.print("Enter a string: ");
		String input = System.console().readLine();
		System.out.println("You entered: " + input);
	}
}
