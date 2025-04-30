package ClassPackage;


//import ClassPackage.CompilationUnit;
import ClassPackage.*;

import java.util.*;


import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class AstExplorerVisitor extends ASTVisitor
{
	
	private Stack<ClassPackage.Statement> stStack;
	private Stack<ClassPackage.Method> methodsStack;
	private Stack<ClassPackage.Class> classesStack;
	private Stack<ClassPackage.CompilationUnit> cuStack;

	private Vector<ClassPackage.Statement> statementsList;
	private Vector<ClassPackage.Method> methodsList;
	private Vector<ClassPackage.Class> classesList;
	private Vector<ClassPackage.CompilationUnit> cunitsList;
	
	/**
	 * Set of invoked methods belonging to other classes.
	 */
	AbstractSet<String> invoked = new HashSet<String>();

	/**
	 * Current level in source code
	 */ 
	private Integer nblevelsCount = 0;

	/**
	 * Max level in source code
	 */
	private Integer maxLevel = 0;

	/**
	 * Max number of operators 
	 */
	private Integer maxNumberOfOperators = 0;
	/**
	 * Count of public members in current class
	 */ 
	private Integer nbOfPublicMembers = 0;

	private Integer maxDataFlow = 0;

	private Integer maxDataUsage = 0;

	ClassPackage.Method topMethod;
	private Integer inOutNull = 0;

	/**
	 * Constructor
	 */
	public AstExplorerVisitor() 
	{
		super();

		stStack      = new Stack<ClassPackage.Statement>();
		methodsStack = new Stack<ClassPackage.Method>();
		classesStack = new Stack<ClassPackage.Class>();
		cuStack      = new Stack<ClassPackage.CompilationUnit>();

		statementsList = new Vector<ClassPackage.Statement>();
		methodsList    = new Vector<ClassPackage.Method>();
		classesList    = new Vector<ClassPackage.Class>();
		cunitsList     = new Vector<ClassPackage.CompilationUnit>();

		nblevelsCount = 0;

		maxLevel = 0;
		maxNumberOfOperators = 0;
		maxDataFlow = 0;
		maxDataUsage = 0;

		nbOfPublicMembers = 0;
	}

	/**
	 * Getters and setters
	 */ 
	public Vector<ClassPackage.CompilationUnit> getCunitsList()
	{
		return cunitsList;
	}

	public void setCunitsList(Vector<ClassPackage.CompilationUnit> cunitsList)
	{
		this.cunitsList = cunitsList;
	}

	public Vector<ClassPackage.Method> getMethodsList()
	{
		return methodsList;
	}

	public void setMethodsList(Vector<ClassPackage.Method> methodsList) 
	{
		this.methodsList = methodsList;
	}

	public Vector<ClassPackage.Class> getClassesList()
	{
		return classesList;
	}

	public void setClassesList(Vector<ClassPackage.Class> classesList)
	{
		this.classesList = classesList;
	}

	public Vector<ClassPackage.Statement> getStatementsList()
	{
		return statementsList;
	}

	public void setStatementsList(Vector<ClassPackage.Statement> statementsList)
	{
		this.statementsList = statementsList;
	}

	public Stack<ClassPackage.Statement> getStStack()
	{
		return stStack;
	}

	public Stack<ClassPackage.Method> getMethodsStack()
	{
		return methodsStack;
	}

	public Stack<ClassPackage.Class> getClassesStack()
	{
		return classesStack;
	}

	public Stack<ClassPackage.CompilationUnit> getCuStack()
	{
		return cuStack;
	}

	public void setStStack(Stack<ClassPackage.Statement> stStack)
	{
		this.stStack = stStack;
	}

	public void setMethodsStack(Stack<ClassPackage.Method> methodsStack)
	{
		this.methodsStack = methodsStack;
	}

	public void setClassesStack(Stack<ClassPackage.Class> classesStack)
	{
		this.classesStack = classesStack;
	}

	public void setCuStack(Stack<ClassPackage.CompilationUnit> cuStack)
	{
		this.cuStack = cuStack;
	}

	/**
	 * AST pre-visitor
	 * Push symbols into their corresponding stack to prepare them for processing
	 */
	@Override
	public void preVisit(ASTNode node)
	{
		super.preVisit(node);
		String name = "";

		/**
		 * Push statement for processing
		 */
		if(node instanceof Expression || node.getNodeType() == ASTNode.VARIABLE_DECLARATION_STATEMENT )
		{
			ClassPackage.Statement stmt = 
					new ClassPackage.Statement(node.toString());
			name = node.toString();
			stmt.setName(name);
			stStack.push(stmt);
		}

		switch (node.getNodeType())
		{
		case ASTNode.TYPE_DECLARATION :
			ClassPackage.Class c = new ClassPackage.Class(node.toString());
			c.setNode(node);
			name = ((TypeDeclaration) node).getName().toString();
			c.setName(name);
			classesStack.push(c);
			
			inOutNull = 0;
			break;

		case ASTNode.METHOD_DECLARATION : 
			ClassPackage.Method md =
					new ClassPackage.Method(node.toString());
			name = ((MethodDeclaration) node).getName().toString();
			md.setName(name);
			methodsStack.push(md);
			maxLevel = 0; // Reset max level (new method)
			maxNumberOfOperators = 0; // Reset max of operators
			maxDataFlow = 0; // Reset max of data flow
			maxDataUsage = 0; // Reset max of data usage
			break;

		case ASTNode.COMPILATION_UNIT : 
			ClassPackage.CompilationUnit cu = 
					new ClassPackage.CompilationUnit();
			name = "package";
			cu.setName(name);
			cuStack.push(cu);
			break;

		default: break;
		}

		/**
		 * Update level
		 */
		if(node.getNodeType() == ASTNode.BLOCK)
		{
			switch(node.getParent().getNodeType())
			{
			

			case ASTNode.WHILE_STATEMENT : 
			case ASTNode.FOR_STATEMENT : 
			case ASTNode.ENHANCED_FOR_STATEMENT : 
			case ASTNode.IF_STATEMENT: 
				nblevelsCount ++;
//				//-+-System.out.println("yyyyyyyyyyyyyy");
				if(nblevelsCount > maxLevel)
				{
					maxLevel = nblevelsCount;
//					//-+-System.out.println("nnnnnnnnnnnnnnnnnnnn");
				}
				break;	
			default: break;
			}
		}
	}


	private void printTree(ClassPackage.Scope tree)
	{
		tree.print();
	}

	/**
	 * AST visiting routines
	 * Cut-off by returning false to avoid going deeper down the AST
	 */ 
	@Override
	public boolean visit(MethodInvocation node)
	{
		//
		
		Expression exp = node.getExpression();
		ITypeBinding invocationType = null;
		IMember declaringMember = null;
		//-+-System.out.println("exp: " + exp);
		if (exp instanceof SimpleName) {
			//-+-System.out.println("yeaap" );
			SimpleName name = (SimpleName) exp;
			IBinding binding = name.resolveBinding();
			//-+-System.out.println("binding "+ binding );
			if (binding != null && binding instanceof IVariableBinding) {
				//-+-System.out.println("yeaap" );
				
				IVariableBinding varBinding = (IVariableBinding) binding;
				invocationType = varBinding.getType();
				if (varBinding.getDeclaringClass() != null) {
					declaringMember = (IMember) varBinding.
					getDeclaringClass().getJavaElement();
				}
				if (declaringMember == null) {
					if (varBinding.getDeclaringMethod() != null) {
						if (varBinding.getDeclaringMethod().getDeclaringClass() != null) {
							declaringMember = (IMember)	
							varBinding.getDeclaringMethod().
							getDeclaringClass().getJavaElement();	
						}
					}
				}
			}
		}


		IMethodBinding methodBinding = node.resolveMethodBinding();
		

		ClassPackage.Class topClass = classesStack.peek();
		TypeDeclaration topClassNode = (TypeDeclaration) topClass.getNode();

		if(methodBinding == null)
		{
			inOutNull  ++;

		}

		
		return super.visit(node);
	}


	@Override
	public boolean visit(Assignment node)
	{
		return false;
	}

	@Override
	public boolean visit(ArrayAccess node)
	{
		return false;
	}

	@Override
	public boolean visit(TypeDeclaration node)
	{

		
		FieldDeclaration[] classFields =  node.getFields();
		MethodDeclaration[] methodDeclarations = node.getMethods();
		Class cl = new Class(node.getName().toString());

		this.classesList.addElement(cl);
		for (FieldDeclaration classField :classFields)
		{
			int modifiers = classField.getModifiers();

			if (Modifier.isPublic(modifiers))
			{

				nbOfPublicMembers += classField.fragments().size();
			}
		}
		int TotOp = 0, MaxOp=0, TotMaxOp=0, MaxTotOp=0, TotLev=0, MaxLev=0, TotMaxLev=0, MaxTotLev=0, TotDF=0, MaxDF=0, TotMaxDF=0, MaxTotDF=0, TotDU=0, MaxDU=0, TotMaxDU=0, MaxTotDU=0, TotMetCal=0, MaxMetCal=0, InOutDeg=0, PubMem=0;
		List<String> inOutList = new ArrayList<String>();
		cl.setDeclaredMethods(new ArrayList<String>());
		for(MethodDeclaration methodDeclaration: methodDeclarations) {
			cl.addMethodName(methodDeclaration.getName().toString());
		}
		for(MethodDeclaration methodDeclaration: methodDeclarations)
		{
			int modifiers = methodDeclaration.getModifiers();
			if (Modifier.isPublic(modifiers))
			{
				nbOfPublicMembers += 1;
			}
			Method mt = new Method(methodDeclaration.getName().toString());
			mt.setCalledMethods(new ArrayList<String>());
			((Class)this.classesList.get(this.classesList.size()-1)).getMethods().add(mt);
			int lastMethod = ((Class)this.classesList.get(this.classesList.size()-1)).getMethods().size()-1;
			MST(methodDeclaration.getBody(), 0,lastMethod,mt);
			
			mt = ((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod);
			int totOp = 0,maxOp = 0, totLev = 0, maxLev = 0, totDf = 0, maxDf = 0, totDU = 0,maxDU = 0;
			for(int i=0; i< mt.getStatements().size();i++){
				Statement st = ((Vector<Statement>)mt.getStatements()).elementAt(i);
				int dfMetric = st.getUses().size();
				for(String d : st.getDefs()){
					for(int j=i+1; j< mt.getStatements().size();j++){
						if(((Vector<Statement>)mt.getStatements()).elementAt(j).getUses().contains(d)){
							dfMetric += j-i;
							break;
						}
					}
				}
				st.setDataFlow(dfMetric);
				totOp += st.getNumberOfOperators();
				totLev += st.getNumberOfLevels();
				totDf += st.getDataFlow();
				totDU += st.getDataUsage();
				if(st.getNumberOfOperators() > maxOp)
					maxOp = st.getNumberOfOperators();
				if(st.getNumberOfLevels() > maxLev)
					maxLev = st.getNumberOfLevels();
				if(st.getDataUsage() > maxDU)
					maxDU = st.getDataUsage();
				if(st.getDataFlow() > maxDf)
					maxDf = st.getDataFlow();
			}
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setTotalOfOperators(totOp);
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setTotalOfLevels(totLev);
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setTotalDataFlow(totDf);
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setTotalDataUsage(totDU);
			
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setMaximumOfOperators(maxOp);
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setMaximumOfLevels(maxLev);
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setMaximumDataFlow(maxDf);
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setMaximumDataUsage(maxDU);
			List<String> common = new ArrayList<String>(mt.getCalledMethods());
			common.retainAll(cl.getDeclaredMethods());
			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).elementAt(lastMethod).setTotalOfMethodCalls(common.size());
			
			TotOp += totOp;
			TotMaxOp += maxOp;
			TotLev += totLev;
			TotMaxLev += maxLev;
			TotDF += totDf;
			TotMaxDF += maxDf;
			TotDU += totDU;
			TotMaxDU += maxDU;

			if(maxOp > MaxOp)
				MaxOp = maxOp;
			if(totOp > MaxTotOp)
				MaxTotOp = totOp; 
			if(maxLev > MaxLev)
				MaxLev = maxLev;
			if(totLev > MaxTotLev)
				MaxTotLev = totLev;
			if(maxDf > MaxDF)
				MaxDF =maxDf;
			if(totDf > MaxTotDF)
				MaxTotDF = totDf;
			if(maxDU > MaxDU)
				MaxDU = maxDU;
			if(totDU > MaxTotDU)
				MaxTotDU = totDU;
			
			//TotMetCal
			TotMetCal += common.size();
			//MaxMetCal
			if(common.size() > MaxMetCal)
				MaxMetCal = common.size();
			//InOutDeg
			for(String name : mt.getCalledMethods())
				if(!inOutList.contains(name) && !common.contains(name))
					inOutList.add(name);

		}
		this.classesList.elementAt(this.classesList.size()-1).setTotalOfOperators(TotOp);
		this.classesList.elementAt(this.classesList.size()-1).setTotalOfMaximumOperators(TotMaxOp);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumOfOperators(MaxOp);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumOfTotalOperators(MaxTotOp);
		this.classesList.elementAt(this.classesList.size()-1).setTotalOfLevels(TotLev);
		this.classesList.elementAt(this.classesList.size()-1).setTotalOfMaximumLevels(TotMaxLev);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumOfLevels(MaxLev);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumOfTotalLevels(MaxTotLev);
		this.classesList.elementAt(this.classesList.size()-1).setTotalDataFlow(TotDF);
		this.classesList.elementAt(this.classesList.size()-1).setTotalOfMaximumDataFlow(TotMaxDF);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumDataFlow(MaxDF);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumOfTotalDataFlow(MaxTotDF);
		this.classesList.elementAt(this.classesList.size()-1).setTotalDataUsage(TotDU);
		this.classesList.elementAt(this.classesList.size()-1).setTotalOfMaximumDataUsage(TotMaxDU);
		this.classesList.elementAt(this.classesList.size()-1).setMaximulDataUsage(MaxDU);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumOfTotalDataUsage(MaxTotDU);
		this.classesList.elementAt(this.classesList.size()-1).setTotalOfMethodCalls(TotMetCal);
		this.classesList.elementAt(this.classesList.size()-1).setMaximumOfMethodCalls(MaxMetCal);
		this.classesList.elementAt(this.classesList.size()-1).setInOutDegree(inOutList.size());
		this.classesList.elementAt(this.classesList.size()-1).setNumberOfPublicMembers(nbOfPublicMembers);

		
		return super.visit(node);
	}

	@Override
	public boolean visit(ArrayCreation node)
	{	
		return false;
	}

	@Override
	public boolean visit(ArrayInitializer node)
	{
		return false;
	}

	@Override
	public boolean visit(ClassInstanceCreation node)
	{

		return false;
	}

	@Override
	public boolean visit(ConditionalExpression node)
	{	
		return false;
	}

	@Override
	public boolean visit(FieldAccess node)
	{
		return false;
	}

	@Override
	public boolean visit(InfixExpression node) 
	{	
		return false;
	}

	@Override
	public boolean visit(ParenthesizedExpression node)
	{	
		return false;
	}

	@Override
	public boolean visit(PostfixExpression node)
	{	
		return false;
	}

	@Override
	public boolean visit(PrefixExpression node)
	{	
		return false;
	}

	@Override
	public boolean visit(VariableDeclarationExpression node)
	{	
		return false;
	}

	@Override
	public boolean visit(VariableDeclarationFragment node)
	{
		return false;
	}

	/**
	 * Helper method for calculating the number of operators in a the given statement node
	 * Works by recursively processing nodes in the subtree for this statement
	 */
	private Integer calculateNumberOfOperators(ASTNode stmt)
	{
		Integer opcount = 0;

		switch(stmt.getNodeType())
		{
		case ASTNode.ARRAY_ACCESS:
			ArrayAccess arrAccess = (ArrayAccess) stmt;
			opcount = calculateNumberOfOperators(arrAccess.getArray()) +
					calculateNumberOfOperators(arrAccess.getIndex());
			break;

		case ASTNode.ARRAY_INITIALIZER:
			ArrayInitializer arrInit = (ArrayInitializer) stmt;
			@SuppressWarnings("unchecked")
			List<Expression> exprs = arrInit.expressions();
			for(Expression expr: exprs)
			{
				opcount += calculateNumberOfOperators(expr);
			}
			break;

		case ASTNode.INFIX_EXPRESSION:
			InfixExpression inExpr = (InfixExpression) stmt;
			opcount = 1 + calculateNumberOfOperators(inExpr.getLeftOperand())
					+ calculateNumberOfOperators(inExpr.getRightOperand());
			break;

		case ASTNode.POSTFIX_EXPRESSION:
			PostfixExpression postExpr = (PostfixExpression) stmt;
			opcount = 1 + calculateNumberOfOperators(postExpr.getOperand());
			break;

		case ASTNode.PREFIX_EXPRESSION:
			PrefixExpression preExpr = (PrefixExpression) stmt;
			opcount = 1 + calculateNumberOfOperators(preExpr.getOperand());
			break;

		case ASTNode.ASSIGNMENT :
			Assignment rhs = (Assignment) stmt;
			opcount = 1 + calculateNumberOfOperators(rhs.getLeftHandSide()) +
					calculateNumberOfOperators(rhs.getRightHandSide());
			break;

		case ASTNode.VARIABLE_DECLARATION_STATEMENT:
			VariableDeclarationStatement vds = (VariableDeclarationStatement) stmt;
			@SuppressWarnings("unchecked")
			List<VariableDeclarationFragment> frags = vds.fragments();
			for(VariableDeclarationFragment frag: frags)
			{
				opcount += calculateNumberOfOperators(frag);
			}
			break;

		case ASTNode.VARIABLE_DECLARATION_FRAGMENT:
			VariableDeclarationFragment vdf = (VariableDeclarationFragment) stmt;
			Expression exp = vdf.getInitializer();
			if (exp != null) opcount = 1 + calculateNumberOfOperators(exp);

			break;

		default: break;
		}

		return opcount;
	}
	

	/* method that tackles the block inside the method */
	public void MST(org.eclipse.jdt.core.dom.Statement st, int level, int indexMethod,Method mt){
		if(st == null)
			return;
		if(st.getNodeType() == st.FOR_STATEMENT || st.getNodeType() == st.WHILE_STATEMENT || st.getNodeType() == st.DO_STATEMENT || st.getNodeType() == st.BLOCK || st.getNodeType() == st.IF_STATEMENT || st.getNodeType() == st.TRY_STATEMENT || st.getNodeType() == st.SWITCH_STATEMENT){
			if (st.getNodeType() == st.BLOCK){
				for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)(((Block)st).statements()))
					MST(stt, level,indexMethod,mt);
			}
			
			if (st.getNodeType() == st.FOR_STATEMENT || st.getNodeType() == st.WHILE_STATEMENT || st.getNodeType() == st.DO_STATEMENT){
				if(st.getNodeType() == st.FOR_STATEMENT ){
					int numOp = 0,lev = 0, DF = 0, DU = 0;
					//expression
					AST astSub = ((ForStatement)st).getExpression().getAST();

					astStatementExplorer astStmExplorer = new astStatementExplorer();
					((ForStatement)st).getExpression().accept(astStmExplorer);

					int nbOps = astStmExplorer.getNumberOfOperators();
					Statement s = new Statement(st.toString());
					s.setNumberOfOperators(nbOps);
					s.setNumberOfLevels(level);
					s.setDataUsage((astStmExplorer.getDataUsage()));
					s.setDefs(astStmExplorer.getDefinitions());
					s.setUses(adjust(astStmExplorer.getUses(),astStmExplorer.getDefinitions()));
					for(String name : astStmExplorer.getInvocMethodList())
						mt.addMethodName(name);

					//updaters
					((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).get(indexMethod).getStatements().add(s);

					for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)((Block)((ForStatement)st).getBody()).statements())
						MST(stt, level+1,indexMethod,mt);
				}else if(st.getNodeType() == st.WHILE_STATEMENT){
					AST astSub = ((WhileStatement)st).getExpression().getAST();

					astStatementExplorer astStmExplorer = new astStatementExplorer();
					((WhileStatement)st).getExpression().accept(astStmExplorer);
					Integer nbOps = astStmExplorer.getNumberOfOperators();


					Statement s = new Statement(st.toString());
					s.setNumberOfOperators(nbOps);
					s.setNumberOfLevels(level);
					s.setDataUsage((astStmExplorer.getDataUsage()));
					s.setDefs(astStmExplorer.getDefinitions());
					s.setUses(adjust(astStmExplorer.getUses(),astStmExplorer.getDefinitions()));
					for(String name : astStmExplorer.getInvocMethodList())
						mt.addMethodName(name);

					((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).get(indexMethod).getStatements().add(s);
					for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)((Block)((WhileStatement)st).getBody()).statements())
						MST(stt, level+1,indexMethod,mt);
				}else if (st.getNodeType() == st.DO_STATEMENT){
					AST astSub = ((DoStatement)st).getExpression().getAST();
					astStatementExplorer astStmExplorer = new astStatementExplorer();
					((DoStatement)st).getExpression().accept(astStmExplorer);
					Integer nbOps = astStmExplorer.getNumberOfOperators();

					Statement s = new Statement(st.toString());
					s.setNumberOfOperators(nbOps);
					s.setNumberOfLevels(level);
					s.setDataUsage((astStmExplorer.getDataUsage()));
					s.setDefs(astStmExplorer.getDefinitions());
					s.setUses(adjust(astStmExplorer.getUses(),astStmExplorer.getDefinitions()));
					for(String name : astStmExplorer.getInvocMethodList())
						mt.addMethodName(name);

					((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).get(indexMethod).getStatements().add(s);
					for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)((Block)((DoStatement)st).getBody()).statements())
						MST(stt, level+1,indexMethod,mt);
				}
				
			
			} else if (st.getNodeType() == st.IF_STATEMENT){
				AST astSub = ((IfStatement)st).getExpression().getAST();

				astStatementExplorer astStmExplorer = new astStatementExplorer();
				((IfStatement)st).getExpression().accept(astStmExplorer);
				Integer nbOps = astStmExplorer.getNumberOfOperators();

				Statement s = new Statement(st.toString());
				s.setNumberOfOperators(nbOps);
				s.setNumberOfLevels(level);
				s.setDataUsage((astStmExplorer.getDataUsage()));
				s.setDefs(astStmExplorer.getDefinitions());
				s.setUses(adjust(astStmExplorer.getUses(),astStmExplorer.getDefinitions()));
				for(String name : astStmExplorer.getInvocMethodList())
					mt.addMethodName(name);

				((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).get(indexMethod).getStatements().add(s);

                if ((((IfStatement)st).getThenStatement()).getNodeType() == st.IF_STATEMENT ) {
					MST((((IfStatement) st).getThenStatement()), level + 1, indexMethod,mt);
				}else {

                	if((((IfStatement)st).getThenStatement()).getNodeType() == ((IfStatement) st).RETURN_STATEMENT)
						MST((((IfStatement)st).getThenStatement()), level + 1, indexMethod,mt);
                	else {

                		for (org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>) ((Block) ((IfStatement) st).getThenStatement()).statements())
							MST(stt, level + 1, indexMethod,mt);
					}

				}

				if((((IfStatement)st).getElseStatement()) != null){
				    if ((((IfStatement)st).getElseStatement()).getNodeType() == st.IF_STATEMENT )
                        MST((((IfStatement)st).getElseStatement()), level+1,indexMethod,mt);
				    else
                        for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)((Block)((IfStatement)st).getElseStatement()).statements())
                            MST(stt, level+1,indexMethod,mt);
                }

				
			}else if(st.getNodeType() == st.TRY_STATEMENT){
				for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)((Block)((TryStatement)st).getBody()).statements())
					MST(stt, level+1,indexMethod,mt);
				if((((TryStatement)st).getFinally()) != null)
					for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)((Block)((TryStatement)st).getFinally()).statements())
						MST(stt, level+1,indexMethod,mt);
			}else if(st.getNodeType() == st.SWITCH_STATEMENT){
				AST astSub = ((SwitchStatement)st).getExpression().getAST();

				astStatementExplorer astStmExplorer = new astStatementExplorer();
				((SwitchStatement)st).getExpression().accept(astStmExplorer);
				Integer nbOps = astStmExplorer.getNumberOfOperators();

				Statement s = new Statement(st.toString());
				s.setNumberOfOperators(nbOps);
				s.setNumberOfLevels(level);
				s.setDataUsage((astStmExplorer.getDataUsage()));
				s.setDefs(astStmExplorer.getDefinitions());
				s.setUses(adjust(astStmExplorer.getUses(),astStmExplorer.getDefinitions()));
				for(String name : astStmExplorer.getInvocMethodList())
					mt.addMethodName(name);

				((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).get(indexMethod).getStatements().add(s);
				for(org.eclipse.jdt.core.dom.Statement stt : (List<org.eclipse.jdt.core.dom.Statement>)(((SwitchStatement)st).statements()))
					MST(stt, level+1,indexMethod,mt);
			}
		} else{
			AST astSub = st.getAST();

			astStatementExplorer astStmExplorer = new astStatementExplorer();
			st.accept(astStmExplorer);
			Integer nbOps = astStmExplorer.getNumberOfOperators();

			Statement s = new Statement(st.toString());
			s.setNumberOfOperators(nbOps);
			s.setNumberOfLevels(level);
			s.setDataUsage((astStmExplorer.getDataUsage()));
			s.setDefs(astStmExplorer.getDefinitions());
			s.setUses(adjust(astStmExplorer.getUses(),astStmExplorer.getDefinitions()));
			for(String name : astStmExplorer.getInvocMethodList())
				mt.addMethodName(name);

			((Vector<Method>)((Class)this.classesList.get(this.classesList.size()-1)).getMethods()).get(indexMethod).getStatements().add(s);
		}

	}
	
	public Vector<String> adjust(Vector<String> U,Vector<String> D){
		for(String d : D){
			if(Collections.frequency(U, d) == 1){
				U.remove(d);
			}
			
		}
		HashSet hs = new HashSet();
		hs.addAll(U);
		U.clear();
		U.addAll(hs);
		return U;
	}
	
}
