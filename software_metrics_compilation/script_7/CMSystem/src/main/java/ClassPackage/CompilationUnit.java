package ClassPackage;

import java.util.Collection;
import java.util.Vector;

public class CompilationUnit extends Scope
{

	private Collection<Class> classes = new Vector<Class>();
	
	public Collection<Class> getClasses()
	{
		return classes;
	}

	public void setClasses(Collection<Class> classes)
	{
		this.classes = classes;
	}

	public CompilationUnit(String codeString, Collection<Class> classes)
	{
		super(codeString);
		this.classes = classes;
	}

	/**
	 * Default constructor
	 */
	public CompilationUnit() 
	{
	}
	
	@Override
	public void print()
	{
		super.print();
		for(Scope s: classes)
		{
			s.print();
		}
	}

}
