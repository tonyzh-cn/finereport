package com.fr.util;

import com.fr.base.FRContext;
import com.fr.base.TableData;
import com.fr.data.TableDataSource;
import com.fr.data.impl.storeproc.StoreProcedure;
import com.fr.design.data.datapane.TableDataSourceOP;
import com.fr.design.data.tabledata.wrapper.*;
import com.fr.design.gui.itree.refreshabletree.ExpandMutableTreeNode;
import com.fr.file.DatasourceManager;
import com.fr.file.DatasourceManagerProvider;
import com.fr.general.NameObject;
import com.fr.stable.project.ProjectConstants;
import com.fr.third.org.hsqldb.lib.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by tony on 2016/4/3.
 */
public class XMLUtils {
    private static final String TEMPLATE_XML_FILE_PATH = FRContext.getCurrentEnv().getPath() + File.separator + ProjectConstants.RESOURCES_NAME
            + File.separator + "groupTree.xml";
    private static final String SERVER_XML_FILE_PATH="d:/serverTableData.xml";
    private static final String PROCEDURE_XML_FILE_PATH="d:/procedureTableData.xml";

    public static final int NODE_TAG_SERVER=1;
    public static final int NODE_TAG_TEMPLATE =2;
    public static final int NODE_TAG_PROCEDURE =3;

    /*
    * 1、读取xml文件
    * 2、查找父节点
    * 3、插入新节点
    * 4、重新写入xml文件
    */
    public static void insertNodeToTemplate(String nodeTag, String nodeName, String parentNodeTag, String parentNodeName, String workbook) throws DocumentException, IOException {
        Document document= getTemplateXMLDocument();
        buildXMLNode(document, parentNodeTag, parentNodeName, nodeTag, nodeName,workbook.split("\\.")[0]);
        writeDocumentIntoFile(document, TEMPLATE_XML_FILE_PATH);
    }

    public static void insertNodeToServer(String nodeTag,String nodeName,String parentNodeTag,String parentNodeName) throws DocumentException, IOException {
        Document document= getServerXMLDocument();
        buildXMLNode(document, parentNodeTag, parentNodeName, nodeTag, nodeName, null);
        writeDocumentIntoFile(document, SERVER_XML_FILE_PATH);
    }
    public static void insertNodeToProcedure(String nodeTag,String nodeName,String parentNodeTag,String parentNodeName) throws DocumentException, IOException {
        Document document= getProcedureXMLDocument();
        buildXMLNode(document, parentNodeTag, parentNodeName, nodeTag, nodeName, null);
        writeDocumentIntoFile(document, PROCEDURE_XML_FILE_PATH);
    }
    private static void buildXMLNode(Document document, String parentNodeTag, String parentNodeName, String nodeTag, String nodeName, String workbook) {
        Element workbookRoot=null;
        if(workbook!=null){
            workbookRoot=findElementByNodeTagAndNodeName(document.getRootElement(),"GroupTree",workbook.split("\\.")[0]);
        }else{
            workbookRoot=document.getRootElement();
        }
        Element parentEle= findElementByNodeTagAndNodeName(workbookRoot, parentNodeTag, parentNodeName);
        Element newEle=parentEle.addElement(nodeTag);
        newEle.addAttribute("name",nodeName);
    }
    public static void updateNodeNameOfTemplate(String nodeTag, String newName, String oldName,String workbook) throws DocumentException, IOException {
        Document document= getTemplateXMLDocument();
        Element workbookRoot=null;
        if(workbook!=null){
            workbookRoot=findElementByNodeTagAndNodeName(document.getRootElement(),"GroupTree",workbook.split("\\.")[0]);
        }else{
            workbookRoot=document.getRootElement();
        }
        Element ele= findElementByNodeTagAndNodeName(workbookRoot, nodeTag, oldName);
        ele.addAttribute("name", newName);
        writeDocumentIntoFile(document, TEMPLATE_XML_FILE_PATH);
    }

    public static void updateNodeNameOfServer(String nodeTag, String newName, String oldName) throws DocumentException, IOException {
        Document document= getServerXMLDocument();
        Element ele= findElementByNodeTagAndNodeName(document.getRootElement(), nodeTag, oldName);
        ele.addAttribute("name",newName);
        writeDocumentIntoFile(document, SERVER_XML_FILE_PATH);
    }
    public static void updateNodeNameOfProcedure(String nodeTag, String newName, String oldName) throws DocumentException, IOException {
        Document document= getProcedureXMLDocument();
        Element ele= findElementByNodeTagAndNodeName(document.getRootElement(), nodeTag, oldName);
        ele.addAttribute("name",newName);
        writeDocumentIntoFile(document, PROCEDURE_XML_FILE_PATH);
    }



    public static Document getTemplateXMLDocument() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document   document = reader.read(new File(TEMPLATE_XML_FILE_PATH));
        return document;
    }

    public static Document getServerXMLDocument() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document   document = reader.read(new File(SERVER_XML_FILE_PATH));
        return document;
    }
    public static Document getProcedureXMLDocument() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document   document = reader.read(new File(PROCEDURE_XML_FILE_PATH));
        return document;
    }
    /**
     * 通过nodeTag和nodeName来唯一确定一个节点，遍历根节点找到目标节点
     **/
    public static Element findElementByNodeTagAndNodeName(Element root , String nodeTag, String nodeName) {
        if(nodeTag.equals("GroupTreeXMLManager")){
            return root;
        }
        Element result = null;
        if(isTargetNode(root.getName(),root.attributeValue("name"),nodeTag,nodeName)){
            result=root;
            return result;
        }
        for(Iterator i=root.elementIterator();i.hasNext();)
        {
            Element tempEle=(Element)i.next();
            result=findElementByNodeTagAndNodeName(tempEle,nodeTag,nodeName);
            if(result!=null&&isTargetNode(result.getName(),result.attributeValue("name"),nodeTag,nodeName)){
                break;
            }
        }
        return result;
    }

    private static boolean isTargetNode(String nodeTag, String nodeName, String targetNodeTag, String targetNodeName) {
        return !StringUtil.isEmpty(nodeTag)&&nodeTag.equals(targetNodeTag)&&!StringUtil.isEmpty(nodeName)&&nodeName.equals(targetNodeName);
    }

    /*
   * Write a XML file
   */
    private static void writeDocumentIntoFile(Document document,String fileName) throws IOException
    {
        OutputFormat format = OutputFormat.createPrettyPrint();// 创建文件输出的时候，自动缩进的格式
        format.setEncoding("UTF-8");//设置编码
        XMLWriter writer = new XMLWriter(new FileWriter(fileName),format);
        writer.write(document);
        writer.close();
    }


    public static void main(String[] args) {
//        try {
//            XMLUtils.insertNodeToTemplate("tabledata","data","group","group1");
//            XMLUtils.updateNodeNameOfTemplate("tabledata","ds11","ds1");
//            loadServerRoot();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println(new File("d:/tempplateTableData.xml").getName());
    }

    public static ExpandMutableTreeNode loadTemplateRoot(TableDataSource source, String fileName) throws DocumentException {
        Element workbookEle=findElementByNodeTagAndNodeName(getTemplateXMLDocument().getRootElement(),"GroupTree",fileName.split("\\.")[0]);
        if(workbookEle==null){
            return new ExpandMutableTreeNode();
        }
        ExpandMutableTreeNode rootNode=new ExpandMutableTreeNode();
        createTreeNode(rootNode,workbookEle,source,XMLUtils.NODE_TAG_TEMPLATE);
        return rootNode;

    }

    public static ExpandMutableTreeNode loadServerRoot() throws DocumentException {
        ExpandMutableTreeNode rootNode=new ExpandMutableTreeNode();
        createTreeNode(rootNode,getServerXMLDocument().getRootElement(),null,XMLUtils.NODE_TAG_SERVER);
        return rootNode;
    }

    public static ExpandMutableTreeNode loadAll(TableDataSource source,String fileName) throws DocumentException {
        ExpandMutableTreeNode treeNode=new ExpandMutableTreeNode();
        treeNode.add(loadTemplateRoot(source, fileName));
        treeNode.add(loadServerRoot());
        ExpandMutableTreeNode procedureNode=new ExpandMutableTreeNode();
        createTreeNode(procedureNode,getProcedureXMLDocument().getRootElement(),null,XMLUtils.NODE_TAG_PROCEDURE);
        treeNode.add(procedureNode);
        return treeNode;
    }

    /**
     * 递归调用将xml转换为界面树,包含了xml中的root节点，在界面中需要去除外层的root
     *
     * @return  包含root的界面树根节点
     */
    private static void createTreeNode(ExpandMutableTreeNode rootNode,Element parentNode,TableDataSource source,int nodeTag) {
        //当节点之内不再有子节点的时候，就创建新节点
        if(parentNode.elements().isEmpty()&&!StringUtil.isEmpty(parentNode.attributeValue("name"))){
            return;
        }
        // 如果节点之内还有子节点，那么就遍历所有子节点
        for (Iterator i = parentNode.elementIterator(); i.hasNext(); ) {
            Element childNode = (Element) i.next();
            if (childNode != null) {
                if (childNode.getNodeType() == Element.ELEMENT_NODE) {
                    ExpandMutableTreeNode treeNode= buildTreeNode(childNode.attributeValue("name"), childNode.getName(), nodeTag, source);
                    if(treeNode.getAllowsChildren()){
                        createTreeNode(treeNode, childNode, source, nodeTag);
                    }
                    rootNode.add(treeNode);
                }
            }
        }
    }

    /**
     * 创建一个树节点
     *
     * @param tableDataName 数据集和分组的名称,用来生成ExpandMutableTreeNode以及在模板中获取tabledata
     * @param nodeName      xml文档中对应的节点的tag名称，用来判断要生成的是否是分组group节点
     * @param nodeTag      nodeType对应了节点的tabledata的类型，即模板数据集，服务器数据集和存储过程
     * @param source        用来获取模板数据集
     * @return              返回生成的节点
     */
    private static ExpandMutableTreeNode buildTreeNode(String tableDataName, String nodeName, int nodeTag, TableDataSource source) {
        TableDataWrapper tdw=null;
        DatasourceManagerProvider mgr = DatasourceManager.getProviderInstance();
        ExpandMutableTreeNode treeNode=null;
        if("group".equals(nodeName)){
            //如果是分组group节点，那么只需要分组的名字，不需要tabledata
            treeNode=new ExpandMutableTreeNode(new NameObject(tableDataName, TableDataSourceOP.GROUP_TABLE_DATA),true);
        }else if(nodeTag== NODE_TAG_TEMPLATE){
            if (source != null&& !StringUtil.isEmpty(tableDataName)) {
                TableData td = source.getTableData(tableDataName);
                tdw = new TemplateTableDataWrapper(td, tableDataName);
            }
            treeNode=new ExpandMutableTreeNode(new NameObject(tableDataName,tdw));
        }else if(nodeTag==NODE_TAG_SERVER&& !StringUtil.isEmpty(tableDataName)){
            TableData td=mgr.getTableData(tableDataName);
            tdw = new ServerTableDataWrapper(td, tableDataName);
            treeNode=new ExpandMutableTreeNode(new NameObject(tableDataName,tdw));
        }else if(nodeTag== NODE_TAG_PROCEDURE && !StringUtil.isEmpty(tableDataName)){
            StoreProcedure storeProcedure = mgr.getProcedure(tableDataName);
            tdw = new StoreProcedureNameWrapper(tableDataName, storeProcedure);
            treeNode=new ExpandMutableTreeNode(new NameObject(tableDataName,tdw));
        }

        return treeNode;
    }


    public static void removeNodeNameOfTemplate(String nodeTag, String nodeName, String workbook) throws DocumentException, IOException {
        Document document= getTemplateXMLDocument();
        Element ele=findElementByNodeTagAndNodeName(findElementByNodeTagAndNodeName(document.getRootElement(),"GroupTree",workbook.split("\\.")[0]),nodeTag,nodeName);
        Element parentEle=ele.getParent();
        parentEle.remove(ele);
        writeDocumentIntoFile(document, TEMPLATE_XML_FILE_PATH);
    }

    public static void removeNodeNameOfProcedure(String nodeTag, String nodeName) throws DocumentException, IOException {
        Document document= getProcedureXMLDocument();
        Element ele=findElementByNodeTagAndNodeName(document.getRootElement(),nodeTag,nodeName);
        Element parentEle=ele.getParent();
        parentEle.remove(ele);
        writeDocumentIntoFile(document, PROCEDURE_XML_FILE_PATH);
    }

    public static void removeNodeNameOfServer(String nodeTag, String nodeName) throws DocumentException, IOException {
        Document document= getServerXMLDocument();
        Element ele=findElementByNodeTagAndNodeName(document.getRootElement(),nodeTag,nodeName);
        Element parentEle=ele.getParent();
        parentEle.remove(ele);
        writeDocumentIntoFile(document, SERVER_XML_FILE_PATH);

    }
}
