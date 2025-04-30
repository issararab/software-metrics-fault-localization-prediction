package CMSystem;
import ClassPackage.AstExplorerVisitor;
import ClassPackage.Parser;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.FileWriter;

public class CMSRunner {


	private Parser parser;
	private String classPath;
	private String csvPath;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {


		String classPath = "";
		String csvPath = "";
        CMSRunner myCMS = new CMSRunner(classPath, csvPath);
        String text;
		try{
            /*
		    String project ="Chart";
		    for(int i=1;i<27;i++ ){
		        String path = "C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\ck-metrics\\"+project+"_"+i+"_src_paths";
                //text = readFile("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\ck-metrics\\Chart_1_src_paths");
                text = readFile(path);
                String[] tokens = text.split("\n");
                //[] line;
                System.out.println(project+"_"+i);
                FileWriter pw = new FileWriter("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\CSMSTool\\"+project+"_"+i+"_SCMS.csv",true);
                pw.append("class,Tot2Op,Max2Op,TotMaxOp,MaxTotOp,Tot2Lev,Max2Lev,TotMaxLev,MaxTotLev,Tot2DF,Max2DF,TotMaxDF,MaxTotDF,Tot2DU,Max2DU,TotMaxDU,MaxTotDU,TotInMetCall,MaxInMetCall,inOutDeg,pubMembers\n");
                for (String token:tokens) {
                    //System.out.println(token);
                    //line = token.split(",");
                    System.out.println((token.split(",")[1]).replace("\\","\\\\"));

                    //pw.write(myCMS.CMS((token.split(",")[1]).replace("\\","\\\\")));
                    String output = myCMS.CMS((token.split(",")[1]).replace("\\","\\\\"));
                    if(!output.equals("not_class"))
                        pw.append((token.split(",")[0])+","+output+"\n");

                }
                pw.flush();
                pw.close();
                //myCMS.CMS("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\scripts\\source_code_buggy_versions\\Chart\\Chart_4_buggy\\source\\org\\jfree\\chart\\plot\\XYCrosshairState.java");
		    }
            project ="Time";
            for(int i=1;i<28;i++ ){
                String path = "C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\ck-metrics\\"+project+"_"+i+"_src_paths";
                //text = readFile("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\ck-metrics\\Chart_1_src_paths");
                text = readFile(path);
                String[] tokens = text.split("\n");
                //[] line;
                System.out.println(project+"_"+i);
                FileWriter pw = new FileWriter("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\CSMSTool\\"+project+"_"+i+"_SCMS.csv",true);
                pw.append("class,Tot2Op,Max2Op,TotMaxOp,MaxTotOp,Tot2Lev,Max2Lev,TotMaxLev,MaxTotLev,Tot2DF,Max2DF,TotMaxDF,MaxTotDF,Tot2DU,Max2DU,TotMaxDU,MaxTotDU,TotInMetCall,MaxInMetCall,inOutDeg,pubMembers\n");
                for (String token:tokens) {
                    //System.out.println(token);
                    //line = token.split(",");
                    System.out.println((token.split(",")[1]).replace("\\","\\\\"));

                    //pw.write(myCMS.CMS((token.split(",")[1]).replace("\\","\\\\")));
                    String output = myCMS.CMS((token.split(",")[1]).replace("\\","\\\\"));
                    if(!output.equals("not_class"))
                        pw.append((token.split(",")[0])+","+output+"\n");

                }
                pw.flush();
                pw.close();
                //myCMS.CMS("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\scripts\\source_code_buggy_versions\\Chart\\Chart_4_buggy\\source\\org\\jfree\\chart\\plot\\XYCrosshairState.java");
            }
            project ="Mockito";
            for(int i=1;i<39;i++ ){
                String path = "C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\ck-metrics\\"+project+"_"+i+"_src_paths";
                //text = readFile("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\ck-metrics\\Chart_1_src_paths");
                text = readFile(path);
                String[] tokens = text.split("\n");
                //[] line;
                System.out.println(project+"_"+i);
                FileWriter pw = new FileWriter("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\CSMSTool\\"+project+"_"+i+"_SCMS.csv",true);
                pw.append("class,Tot2Op,Max2Op,TotMaxOp,MaxTotOp,Tot2Lev,Max2Lev,TotMaxLev,MaxTotLev,Tot2DF,Max2DF,TotMaxDF,MaxTotDF,Tot2DU,Max2DU,TotMaxDU,MaxTotDU,TotInMetCall,MaxInMetCall,inOutDeg,pubMembers\n");
                for (String token:tokens) {
                    //System.out.println(token);
                    //line = token.split(",");
                    System.out.println((token.split(",")[1]).replace("\\","\\\\"));

                    //pw.write(myCMS.CMS((token.split(",")[1]).replace("\\","\\\\")));
                    String output = myCMS.CMS((token.split(",")[1]).replace("\\","\\\\"));
                    if(!output.equals("not_class"))
                        pw.append((token.split(",")[0])+","+output+"\n");

                }
                pw.flush();
                pw.close();
                //myCMS.CMS("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\scripts\\source_code_buggy_versions\\Chart\\Chart_4_buggy\\source\\org\\jfree\\chart\\plot\\XYCrosshairState.java");
            }
            project ="Lang";
            for(int i=1;i<66;i++ ){
                String path = "C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\ck-metrics\\"+project+"_"+i+"_src_paths";
                //text = readFile("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\ck-metrics\\Chart_1_src_paths");
                text = readFile(path);
                String[] tokens = text.split("\n");
                //[] line;
                System.out.println(project+"_"+i);
                FileWriter pw = new FileWriter("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\CSMSTool\\"+project+"_"+i+"_SCMS.csv",true);
                pw.append("class,Tot2Op,Max2Op,TotMaxOp,MaxTotOp,Tot2Lev,Max2Lev,TotMaxLev,MaxTotLev,Tot2DF,Max2DF,TotMaxDF,MaxTotDF,Tot2DU,Max2DU,TotMaxDU,MaxTotDU,TotInMetCall,MaxInMetCall,inOutDeg,pubMembers\n");
                for (String token:tokens) {
                    //System.out.println(token);
                    //line = token.split(",");
                    System.out.println((token.split(",")[1]).replace("\\","\\\\"));

                    //pw.write(myCMS.CMS((token.split(",")[1]).replace("\\","\\\\")));
                    String output = myCMS.CMS((token.split(",")[1]).replace("\\","\\\\"));
                    if(!output.equals("not_class"))
                        pw.append((token.split(",")[0])+","+output+"\n");

                }
                pw.flush();
                pw.close();
                //myCMS.CMS("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\scripts\\source_code_buggy_versions\\Chart\\Chart_4_buggy\\source\\org\\jfree\\chart\\plot\\XYCrosshairState.java");
            }
            project ="Math";
            for(int i=1;i<107;i++ ){
                String path = "C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\ck-metrics\\"+project+"_"+i+"_src_paths";
                //text = readFile("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\ck-metrics\\Chart_1_src_paths");
                text = readFile(path);
                String[] tokens = text.split("\n");
                //[] line;
                System.out.println(project+"_"+i);
                FileWriter pw = new FileWriter("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\CSMSTool\\"+project+"_"+i+"_SCMS.csv",true);
                pw.append("class,Tot2Op,Max2Op,TotMaxOp,MaxTotOp,Tot2Lev,Max2Lev,TotMaxLev,MaxTotLev,Tot2DF,Max2DF,TotMaxDF,MaxTotDF,Tot2DU,Max2DU,TotMaxDU,MaxTotDU,TotInMetCall,MaxInMetCall,inOutDeg,pubMembers\n");
                for (String token:tokens) {
                    //System.out.println(token);
                    //line = token.split(",");
                    System.out.println((token.split(",")[1]).replace("\\","\\\\"));

                    //pw.write(myCMS.CMS((token.split(",")[1]).replace("\\","\\\\")));
                    String output = myCMS.CMS((token.split(",")[1]).replace("\\","\\\\"));
                    if(!output.equals("not_class"))
                        pw.append((token.split(",")[0])+","+output+"\n");

                }
                pw.flush();
                pw.close();
                //myCMS.CMS("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\scripts\\source_code_buggy_versions\\Chart\\Chart_4_buggy\\source\\org\\jfree\\chart\\plot\\XYCrosshairState.java");
            }
            project ="Closure";
            for(int i=1;i<134;i++ ){
                String path = "C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\ck-metrics\\"+project+"_"+i+"_src_paths";
                //text = readFile("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\ck-metrics\\Chart_1_src_paths");
                text = readFile(path);
                String[] tokens = text.split("\n");
                //[] line;
                System.out.println(project+"_"+i);
                FileWriter pw = new FileWriter("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\metric-summary\\metrics\\CSMSTool\\"+project+"_"+i+"_SCMS.csv",true);
                pw.append("class,Tot2Op,Max2Op,TotMaxOp,MaxTotOp,Tot2Lev,Max2Lev,TotMaxLev,MaxTotLev,Tot2DF,Max2DF,TotMaxDF,MaxTotDF,Tot2DU,Max2DU,TotMaxDU,MaxTotDU,TotInMetCall,MaxInMetCall,inOutDeg,pubMembers\n");
                for (String token:tokens) {
                    //System.out.println(token);
                    //line = token.split(",");
                    System.out.println((token.split(",")[1]).replace("\\","\\\\"));

                    //pw.write(myCMS.CMS((token.split(",")[1]).replace("\\","\\\\")));
                    String output = myCMS.CMS((token.split(",")[1]).replace("\\","\\\\"));
                    if(!output.equals("not_class"))
                        pw.append((token.split(",")[0])+","+output+"\n");

                }
                pw.flush();
                pw.close();
                //myCMS.CMS("C:\\Users\\bombarli\\Desktop\\TUM\\summer_2018\\practical\\scripts\\source_code_buggy_versions\\Chart\\Chart_4_buggy\\source\\org\\jfree\\chart\\plot\\XYCrosshairState.java");
            }
            */
            String project ="Time";
            for(int i=1;i<28;i++ ){
                String path = "..\\..\\script_7\\expected\\"+project+"_"+i+"_src_paths";

                text = readFile(path);
                String[] tokens = text.split("\n");

                System.out.println(project+"_"+i);
                FileWriter pw = new FileWriter("..\\"+project+"_"+i+"_SCMS.csv",true);
                pw.append("class,Tot2Op,Max2Op,TotMaxOp,MaxTotOp,Tot2Lev,Max2Lev,TotMaxLev,MaxTotLev,Tot2DF,Max2DF,TotMaxDF,MaxTotDF,Tot2DU,Max2DU,TotMaxDU,MaxTotDU,TotInMetCall,MaxInMetCall,inOutDeg,pubMembers\n");
                for (String token:tokens) {

                    System.out.println((token.split(",")[1]).replace("\\","\\\\"));
                    String output = myCMS.CMS((token.split(",")[1]).replace("\\","\\\\"));
                    if(!output.equals("not_class"))
                        pw.append((token.split(",")[0])+","+output+"\n");

                }
                pw.flush();
                pw.close();

            }

        }catch(IOException e){
            System.out.println("Read file exception!");
        }

	}

    public static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

	/**
	 * Create the application.
	 */
	public CMSRunner(String srcFilePath,String csvPath) {
		//initialize();
		this.classPath = srcFilePath;
		this.csvPath = csvPath;
	}

    public String CMS(String srcFilePath) {
        //initialize();
        this.classPath = srcFilePath;
        parser = new Parser(new AstExplorerVisitor());
        try {
            parser.parseFile(this.classPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int[] result = new int[20];
        for(ClassPackage.Class cl:((Vector<ClassPackage.Class>)parser.getAstExplorerVisitor().getClassesList())){
            result[0] += cl.getTotalOfOperators();
            if(result[1] < cl.getMaximumOfOperators()) result[1] = cl.getMaximumOfOperators();
            result[2] += cl.getTotalOfMaximumOperators();
            if(result[3] < cl.getMaximumOfTotalOperators()) result[3] = cl.getMaximumOfTotalOperators();
            result[4] += cl.getTotalOfLevels();
            if( result[5] < cl.getMaximumOfLevels()) result[5] = cl.getMaximumOfLevels();
            result[6] += cl.getTotalOfMaximumLevels();
            if( result[7] < cl.getMaximumOfTotalLevels()) result[7] = cl.getMaximumOfTotalLevels();
            result[8] = cl.getTotalDataFlow();
            if( result[9] < cl.getMaximumDataFlow()) result[9] = cl.getMaximumDataFlow();
            result[10] = cl.getTotalOfMaximumDataFlow();
            if( result[11] < cl.getMaximumOfTotalDataFlow()) result[11] = cl.getMaximumOfTotalDataFlow();
            result[12] = cl.getTotalDataUsage();
            if ( result[13] < cl.getMaximulDataUsage()) result[13] = cl.getMaximulDataUsage();
            result[14] = cl.getTotalOfMaximumDataUsage();
            if (result[15] < cl.getMaximumOfTotalDataUsage()) result[15] = cl.getMaximumOfTotalDataUsage();
            result[16] = cl.getTotalOfMethodCalls();
            if( result[17] < cl.getMaximumOfMethodCalls()) result[17] = cl.getMaximumOfMethodCalls();

        }
        if(((Vector<ClassPackage.Class>)parser.getAstExplorerVisitor().getClassesList()).size() == 0){
            return "not_class";
        }else {
            result[18] = ((Vector<ClassPackage.Class>) parser.getAstExplorerVisitor().getClassesList()).elementAt(0).getInOutDegree();
            result[19] = ((Vector<ClassPackage.Class>) parser.getAstExplorerVisitor().getClassesList()).elementAt(0).getNumberOfPublicMembers();
        }


        //Sum operators
        System.out.println(java.util.Arrays.toString(result));

        return java.util.Arrays.toString(result).replaceAll("\\s+","").replace("[","").replace("]","");
    }

}
