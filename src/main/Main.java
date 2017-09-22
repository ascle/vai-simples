package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

import vaiCompiler.lexer.Lexer;
import vaiCompiler.lexer.LexerException;
import vaiCompiler.node.Start;
import vaiCompiler.node.Token;
import vaiCompiler.parser.Parser;
import vaiCompiler.parser.ParserException;


class Main {
	
	public static void main(String args[]) throws FileNotFoundException {
		String arquivo = "";
		if (args.length == 0)
			arquivo = System.getProperty("user.dir") + "/teste/progTest1";
		else
			arquivo = args[0];
		
		BufferedReader br = null;		
		
		try {						
			br = new BufferedReader(new FileReader(arquivo));
		} catch (FileNotFoundException e) {
			System.out.println(e);
			return;
		}
		
		Lexer l = new Lexer(new PushbackReader(br));
		Token t = null;
		String erros = "";
		
		System.out.println("Analise Lexica:");
		
		while (true){
			try{
				t = l.next();
			} catch (LexerException e){
				erros += e+"\n";
				continue;
			} catch (IOException e) {
				erros += e+"\n";
				continue;
			}
			
			// sai do loop
			if (t.getText().equals(""))
				break;
			
			Integer lenId = t.getClass().toString().split(" ")[1].split("\\.").length-1;
			String id = t.getClass().toString().split(" ")[1].split("\\.")[lenId];

//		
//			if(id.equals("TTidentacao"))
//				System.out.print(t.getText());
//			else if(id.equals("TTespaco"))
//				System.out.print(" ");
//			else if(id.equals("TTquebra"))
//				System.out.println();
//			else if(id.equals("TTcharIgnored"))
//				System.out.print("");
//			else
				System.out.print(id + "("+t.getText()+")");			
		}
		System.out.println("");
		if (!erros.trim().isEmpty())
			System.out.println(erros);	
		
		
		System.out.println("\n\nAnalise Sintatica:");
		try {						
			br = new BufferedReader(new FileReader(arquivo));
		} catch (FileNotFoundException e) {
			System.out.println(e);
			return;
		}
		
		l = new Lexer(new PushbackReader(br));
		
		Parser p = new Parser(l);
		try {
			Start tree = p.parse();
			tree.apply(new ASTPrinter());
		} catch (ParserException e) {			
			e.printStackTrace();
			System.err.println(e.getMessage());
		} catch (LexerException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	 }
}
