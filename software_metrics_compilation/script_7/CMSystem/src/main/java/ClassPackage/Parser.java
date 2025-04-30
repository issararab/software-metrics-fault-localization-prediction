package ClassPackage;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllBytes;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import ClassPackage.AstExplorerVisitor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.Document;


public class Parser
{

	private AstExplorerVisitor astExplorerVisitor;
	//private IProject project;
	private Integer loc;
	
	
	public Parser(AstExplorerVisitor astExplorerVisitor) {
		super();
	this.astExplorerVisitor = astExplorerVisitor;
	}



	public AST parseFile(String srcFilePath) throws IOException
	{
		

		

			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setResolveBindings(true);
			parser.setBindingsRecovery(true);
			Map<?, ?> options = JavaCore.getOptions();
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_7, options);
			parser.setCompilerOptions(options);

			String source = new String(readAllBytes(Paths.get(srcFilePath)), UTF_8);
			parser.setSource(source.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);

			CompilationUnit unit = (CompilationUnit) parser.createAST(null);

			unit.recordModifications();
			AST ast = unit.getAST();
			System.out.println(ast);

			unit.accept(this.astExplorerVisitor);

			Document doc = new Document(unit.toString());
			loc = doc.getNumberOfLines();
			

			Vector<Class> classes = astExplorerVisitor.getClassesList();

			Iterator<Class> itc = classes.iterator();
			while(itc.hasNext()){
				
				Class clazz = itc.next();
				
				System.out.println("+++> " + clazz.getName().toString() + " .");

				
			}
			
			return ast;
		
		
		
	}



	public AstExplorerVisitor getAstExplorerVisitor() {
		return astExplorerVisitor;
	}

	public Integer getLoc() {
		return loc;
	}

	public void setAstExplorerVisitor(AstExplorerVisitor astExplorerVisitor) {
		this.astExplorerVisitor = astExplorerVisitor;
	}



	public void setLoc(Integer loc) {
		this.loc = loc;
	}



	



	
	
	
}
